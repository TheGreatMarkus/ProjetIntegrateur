package ca.qc.bdeb.info204.spellington.spell;

import ca.qc.bdeb.info204.spellington.GameCore;
import ca.qc.bdeb.info204.spellington.calculations.Calculations;
import ca.qc.bdeb.info204.spellington.gameentities.GameAnimation;
import ca.qc.bdeb.info204.spellington.calculations.SpellingSystem;
import ca.qc.bdeb.info204.spellington.calculations.Vector2D;
import ca.qc.bdeb.info204.spellington.gameentities.GameEntity;
import ca.qc.bdeb.info204.spellington.gameentities.Projectile;
import ca.qc.bdeb.info204.spellington.gameentities.Projectile.ProjectileSourceType;
import ca.qc.bdeb.info204.spellington.gameentities.Spellington;
import ca.qc.bdeb.info204.spellington.gameentities.enemies.Enemy;
import java.util.ArrayList;
import org.newdawn.slick.Animation;
import org.newdawn.slick.Input;

/**
 * A spell that creates projectiles.
 *
 * @author Cristian Aldea
 * @see Spell
 */
public class ProjectileSpell extends Spell {

    protected float initSpeed;
    protected float gravModifier;
    protected int damage;

    protected static final float INIT_SPEED_MOD = 0.002f;
    protected static final float INIT_SPEED_MIN = 0.2f;
    protected static final float INIT_SPEED_MAX = 1.2f;

    public ProjectileSpell(int id, GameEntity.ElementalType element, String name, String shortDescription, int uses, Animation animation, float size, float initSpeed, float gravModifier, int damage) {
        super(id, element, name, shortDescription, uses, animation, size, size);
        this.initSpeed = initSpeed;
        this.gravModifier = gravModifier;
        this.damage = damage;
    }

    /**
     * Determines the angle that will correspond to the projectiles initial
     * speed angle.
     *
     * @param spellington The playable protagonist.
     * @param input The Slick class that handles input.
     * @return The angle with which to create the projectile.
     */
    public float detAngle(Spellington spellington, Input input) {
        return Calculations.detAngle((float) input.getMouseX() / GameCore.SCALE - spellington.getCenterX(), (float) input.getMouseY() / GameCore.SCALE - spellington.getCenterY());
    }

    /**
     * Creates a projectile.
     *
     * @param spellington The playable protagonist.
     * @param input The Slick class that handles input.
     * @param source The source of the projectile
     * @return The created projectile.
     */
    public Projectile createSpellProjectile(Spellington spellington, Input input, ProjectileSourceType source) {
        Projectile tempProj;
        float originX = spellington.getCenterX();
        float originY = spellington.getCenterY();
        float mouseX = (float) input.getMouseX() / GameCore.SCALE;
        float mouseY = (float) input.getMouseY() / GameCore.SCALE;
        float distance = Vector2D.distance(originX, originY, mouseX, mouseY);
        float speedMult = distance * (INIT_SPEED_MOD * GameCore.SCALE);
        if (speedMult < INIT_SPEED_MIN) {
            speedMult = INIT_SPEED_MIN;
        } else if (speedMult > INIT_SPEED_MAX) {
            speedMult = INIT_SPEED_MAX;
        }
        float angle = this.detAngle(spellington, input);
        Vector2D tempVector = new Vector2D(initSpeed * speedMult, angle, true);

        tempProj = new Projectile(originX - getWidth() / 2, originY - getHeight() / 2, getHeight(), tempVector, gravModifier, animation, this.damage, this.element, source);
        return tempProj;
    }

    @Override
    public void spellActivation(Spellington spellington, Input input, ArrayList<GameAnimation> activeAnimations, ArrayList<Projectile> activeProjectiles, ArrayList<Enemy> activeEnemy) {
        Projectile tempProjectile = this.createSpellProjectile(spellington, input, ProjectileSourceType.PLAYER);
        activeProjectiles.add(tempProjectile);
    }

    @Override
    public void endOfActivation(Spellington spellington, ArrayList<GameAnimation> activeAnimations) {

    }

    public float getInitSpeed() {
        return initSpeed;
    }

    public void setInitSpeed(float initSpeed) {
        this.initSpeed = initSpeed;
    }

    public float getGravModifier() {
        return gravModifier;
    }

    public void setGravModifier(float gravModifier) {
        this.gravModifier = gravModifier;
    }

    public void spellCollisionEffect(Spellington spellington, int x, int y, String direction) {
        if (this.id == SpellingSystem.ID_TELEPORTATION) {
            switch (direction) {
                case "top":
                    spellington.setLocation(x, y);
                    ;
                    break; //corriger les collisions selon la direction de la collision
                case "bottom":
                    spellington.setLocation(x, y);
                    ;
                    break;
                case "right":
                    spellington.setLocation(x, y);
                    ;
                    break;
                case "left":
                    spellington.setLocation(x, y);
                    ;
                    break;

            }

        }
    }

    public int getDamage() {
        return damage;
    }

    public void setDamage(int damage) {
        this.damage = damage;
    }

}
