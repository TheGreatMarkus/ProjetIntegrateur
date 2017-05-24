package ca.qc.bdeb.info204.spellington.gameentities;

import ca.qc.bdeb.info204.spellington.calculations.Calculations;
import ca.qc.bdeb.info204.spellington.calculations.SpellingSystem;
import ca.qc.bdeb.info204.spellington.calculations.Vector2D;
import ca.qc.bdeb.info204.spellington.gameentities.enemies.Enemy;
import ca.qc.bdeb.info204.spellington.gamestates.PlayState;
import java.util.ArrayList;
import org.newdawn.slick.Animation;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.geom.Circle;

/**
 * A DynamicEntity that can be fired by other entities in the game.
 *
 * @author Celtis
 * @see DynamicEntity
 */
public class Projectile extends DynamicEntity {

    private final Animation animation;
    private int damage;
    private ElementalType damageType;
    private float renderW;
    private int bounces;
    private int sourceID;

    private float rotation;

    public Projectile(int id, float x, float y, float size, Vector2D speedVector, float gravMod, Animation anim, int damage, ElementalType damageType) {
        super(x, y, size, size, gravMod, speedVector);

        /*-1 if from an enemy,-2 if from a test, any positive integer will 
        correspond to a projectile coming from the player*/
        sourceID = id;
        this.bounces = 0;
        if (sourceID == SpellingSystem.ID_LIGHTNING_BOUNCING_BALL) {
            this.bounces = 15;
        }
        if (sourceID == SpellingSystem.ID_EXPLOSIVE_BALL) {
            this.bounces = 1;
        }
        if (sourceID == SpellingSystem.ID_ICE_SPIKE_BALL) {
            this.bounces = 2;
        }
        this.animation = anim;
        this.damage = damage;
        this.damageType = damageType;
        this.rotation = 0;
        if (animation != null) {
            float heightRatio = this.height / (float) animation.getHeight();
            renderW = animation.getWidth() * heightRatio;
            if (renderW / this.height > 4) {
                renderW = this.height * 4;
            }

        }
    }

    /**
     * Updates the position of this projectile every frame.
     *
     * @param time the time passed this frame.
     * @
     */
    public void update(float time) {
        this.speedVector.add(PlayState.GRAV_ACC.getMultScalar(time * gravModifier));
        this.setX(this.x + this.getSpeedVector().getX() * time);
        this.setY(this.y + this.getSpeedVector().getY() * time);
        this.resetCollisionState();
    }

    /**
     * Draws the projectile on the screen.
     *
     * @param g The Graphics component.
     */
    public void render(Graphics g) {
        if (sourceID == SpellingSystem.ID_POTION_ACID) {
            g.rotate(x + width / 2, y + height / 2, (float) Math.toDegrees(rotation));
            if (this.animation != null) {
                this.animation.draw(x + getWidth() - renderW, getY(), renderW, getHeight());
            }
            if (PlayState.debugMode) {
                g.drawRect(x, y, width, height);
            }
            g.rotate(x + width / 2, y + width / 2, -(float) Math.toDegrees(rotation));
            rotation += 0.1;
        } else {
            float tempAngle = Calculations.detAngle(this.speedVector.getX(), this.speedVector.getY());
            g.rotate(x + width / 2, y + height / 2, (float) Math.toDegrees(tempAngle));
            if (this.animation != null) {
                this.animation.draw(x + getWidth() - renderW, getY(), renderW, getHeight());
            }
            if (PlayState.debugMode) {
                g.drawRect(x, y, width, height);
            }
            g.rotate(x + width / 2, y + width / 2, -(float) Math.toDegrees(tempAngle));
        }
    }

    public void bounce() {
        if (collisionLeft || collisionRight) {
            this.speedVector.setX(-this.speedVector.getX() * 0.6f);
        }
        if (collisionTop || collisionBottom) {
            this.speedVector.setY(-this.speedVector.getY() * 0.6f);
        }
        this.bounces--;
    }

    public void projectileEffet(Spellington spellington, ArrayList<Enemy> activeEnemies, ArrayList<GameAnimation> activeAnimations, Tile[][] map) {
        switch (sourceID) {
            case SpellingSystem.ID_TELEPORTATION:
                spellington.setLocation(this.getCenterX() - spellington.width / 2, this.getCenterY() - spellington.height / 2);
                Calculations.checkMapCollision(map, spellington);
                break;
            case SpellingSystem.ID_EXPLOSIVE_BALL:
                Circle explosion = new Circle(this.getCenterX(), this.getCenterY(), 200);
                for (Enemy enemy : activeEnemies) {
                    if (enemy.getBounds().intersects(explosion)) {
                        enemy.subLifePoint(damage, damageType);
                    }
                }

                activeAnimations.add(new GameAnimation(this.getCenterX() - 200, this.getCenterY() - 200, 400, 400, SpellingSystem.getAnimExplosion().copy(), false, 0));
                break;
            case 2:

                break;
            case 3:
                break;
        }
    }

    public int getDamage() {
        return damage;
    }

    public void setDamage(int damage) {
        this.damage = damage;
    }

    public ElementalType getDamageType() {
        return damageType;
    }

    public void setDamageType(ElementalType damageType) {
        this.damageType = damageType;
    }

    public int getBounces() {
        return bounces;
    }

    public int getSourceID() {
        return sourceID;
    }

}
