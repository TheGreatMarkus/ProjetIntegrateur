package ca.qc.bdeb.info204.spellington.gameentities;

import ca.qc.bdeb.info204.spellington.calculations.Calculations;
import ca.qc.bdeb.info204.spellington.calculations.Vector2D;
import ca.qc.bdeb.info204.spellington.gamestates.PlayState;
import org.newdawn.slick.Animation;
import org.newdawn.slick.Graphics;

/**
 * A DynamicEntity that can be fired by other entities in the game.
 *
 * @author Celtis
 * @see DynamicEntity
 */
public class Projectile extends DynamicEntity {

    public enum SourceType {
        PLAYER,
        ENEMY,
        TEST
    }

    private Animation animation;
    private int damage;
    private ElementalType damageType;
    private SourceType source;

    public Projectile(float x, float y, int width, int height, Vector2D speedVector, float gravMod, Animation anim, int damage, ElementalType damageType, SourceType source) {
        super(x, y, width, height, gravMod, speedVector);
        this.animation = anim;
        this.damage = damage;
        this.damageType = damageType;
        this.source = source;

    }

    public void update(float time) {
        this.speedVector.add(Vector2D.multVectorScalar(PlayState.GRAV_ACC, time * gravModifier));
        this.setX(this.getX() + this.getSpeedVector().getX() * time);
        this.setY(this.getY() + this.getSpeedVector().getY() * time);
    }

    public void render(Graphics g) {

        float tempAngle = Calculations.detAngle(this.speedVector.getX(), this.speedVector.getY());
        g.rotate(x + width / 2, y + height / 2, (float) Math.toDegrees(tempAngle));
        if (this.animation != null) {
            this.animation.draw(x, y, width, width);
        }
        g.drawRect(x, y, width, height);
        g.rotate(x + width / 2, y + height / 2, -(float) Math.toDegrees(tempAngle));
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

    public SourceType getSource() {
        return source;
    }

    public void setSource(SourceType source) {
        this.source = source;
    }
    
    

}
