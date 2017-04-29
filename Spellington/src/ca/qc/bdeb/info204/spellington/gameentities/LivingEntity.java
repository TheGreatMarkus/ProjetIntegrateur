package ca.qc.bdeb.info204.spellington.gameentities;

import ca.qc.bdeb.info204.spellington.calculations.Vector2D;

/**
 * A DynamicEntity that can be affected by damage among other things.
 *
 * @author Cristian Aldea
 * @see DynamicEntity
 */
public abstract class LivingEntity extends DynamicEntity {

    public static enum MouvementState {
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

    protected boolean collisionTop;
    protected boolean collisionBottom;
    protected boolean collisionRight;
    protected boolean collisionLeft;
    protected MouvementState mouvementState;
    protected int lifePoint;
    protected int maxLifePoint;
    protected int resElectricity;
    protected int resIce;
    protected int resFire;

    public LivingEntity(float x, float y, float width, float height, MouvementState mouvementState, float gravMod, int maxLifePoint) {
        super(x, y, width, height, gravMod, new Vector2D(0, 0));
        this.mouvementState = mouvementState;
        collisionTop = false;
        collisionBottom = false;
        collisionRight = false;
        collisionLeft = false;
        this.gravModifier = gravMod;
        this.maxLifePoint = maxLifePoint;
        this.lifePoint = maxLifePoint;
    }

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

    public boolean getCollisionTop() {
        return collisionTop;
    }

    public void setCollisionTop(boolean collisionTop) {
        this.collisionTop = collisionTop;
    }

    public boolean getCollisionBottom() {
        return collisionBottom;
    }

    public void setCollisionBottom(boolean collisionBottom) {
        this.collisionBottom = collisionBottom;
    }

    public boolean getCollisionRight() {
        return collisionRight;
    }

    public void setCollisionRight(boolean collisionRight) {
        this.collisionRight = collisionRight;
    }

    public boolean getCollisionLeft() {
        return collisionLeft;
    }

    public void setCollisionLeft(boolean collisionLeft) {
        this.collisionLeft = collisionLeft;
    }

    public void resetCollisionState() {
        this.collisionTop = false;
        this.collisionBottom = false;
        this.collisionRight = false;
        this.collisionLeft = false;
    }

    public MouvementState getMouvementState() {
        return mouvementState;
    }

    public void setMouvementState(MouvementState mouvementState) {
        this.mouvementState = mouvementState;
    }

    public void addLifePoint(int i) {
        lifePoint = lifePoint + i;
        if (lifePoint > maxLifePoint) {
            lifePoint = maxLifePoint;
        }
    }

    public void subLifePoint(int i, ElementalType element) {
        switch (element) {
            case FIRE:
                i = i - this.resFire;
                break;
            case ICE:
                i = i - this.resIce;
                break;
            case LIGHTNING:
                i = i - this.resElectricity;
                break;
            case NEUTRAL:;
                break;
        }

        if (i < 0) {
            i = 0;
        }

        lifePoint = lifePoint - i;

        if (lifePoint < 0) {
            lifePoint = 0;
        }
    }

    public int getMaxLifePoint() {
        return maxLifePoint;
    }

}
