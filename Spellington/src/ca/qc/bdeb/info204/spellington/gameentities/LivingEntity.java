package ca.qc.bdeb.info204.spellington.gameentities;

import ca.qc.bdeb.info204.spellington.calculations.Vector2D;
import ca.qc.bdeb.info204.spellington.gameentities.enemies.Enemy;

/**
 * A DynamicEntity that can be affected by damage and has different animations
 * for mouvement.
 *
 * @author Cristian Aldea
 * @see DynamicEntity
 */
public abstract class LivingEntity extends DynamicEntity {

    public static enum AnimState {
        STANDING_R,
        STANDING_L,
        WALKING_R,
        WALKING_L,
        JUMP_R,
        JUMP_L,
        WALL_R,
        WALL_L,
        ATTACK_R,
        ATTACK_L
    }

    protected AnimState animState;
    protected int lifePoint;
    protected int maxLifePoint;
    protected int resElectricity;
    protected int resIce;
    protected int resFire;

    protected int invulnTime;
    protected int maxInvulnTime;

    protected float maxXSpeed;
    protected Vector2D xAcc;
    protected Vector2D jumpVector;

    public LivingEntity(float x, float y, float width, float height, AnimState animState, float gravMod) {
        super(x, y, width, height, gravMod, new Vector2D(0, 0));
        this.animState = animState;
        this.collisionTop = false;
        this.collisionBottom = false;
        this.collisionRight = false;
        this.collisionLeft = false;
        this.gravModifier = gravMod;
        this.lifePoint = maxLifePoint;
        this.invulnTime = 0;
    }

    /**
     * Removes hp from this entity according to its resistances.
     *
     * @param damage The numerical value for the damage.
     * @param element the ElementalType for the damage.
     * @
     */
    public void subLifePoint(int damage, ElementalType element) {
        if (invulnTime == 0) {
            switch (element) {
                case FIRE:
                    damage = damage - this.resFire;
                    break;
                case ICE:
                    damage = damage - this.resIce;
                    break;
                case LIGHTNING:
                    damage = damage - this.resElectricity;
                    break;
                case NEUTRAL:;
                    break;
            }

            if (damage < 0) {
                damage = 0;
            }
            if (damage != 0) {
                invulnTime = maxInvulnTime;
            }

            lifePoint = lifePoint - damage;

            if (lifePoint < 0) {
                lifePoint = 0;
            }

        }
    }

    /**
     * Resets the collision state for this Entity
     */
    public void setLifePoint(int lifePoint) {
        this.lifePoint = lifePoint;
    }

    public void setResElectricity(int resElectricity) {
        this.resElectricity = resElectricity;
    }

    public void setResIce(int resIce) {
        this.resIce = resIce;
    }

    public void setResFire(int resFire) {
        this.resFire = resFire;
    }

    public int getLifePoint() {
        return lifePoint;
    }

    public int getResElectricity() {
        return resElectricity;
    }

    public int getResIce() {
        return resIce;
    }

    public int getResFire() {
        return resFire;
    }

    public AnimState getAnimState() {
        return animState;
    }

    public void setAnimState(AnimState animState) {
        this.animState = animState;
    }

    public void addLifePoint(int i) {
        lifePoint = lifePoint + i;
        if (lifePoint > maxLifePoint) {
            lifePoint = maxLifePoint;
        }
    }

    public int getMaxLifePoint() {
        return maxLifePoint;
    }

}
