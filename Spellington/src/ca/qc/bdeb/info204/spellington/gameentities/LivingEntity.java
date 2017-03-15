package ca.qc.bdeb.info204.spellington.gameentities;

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
        WALL_L
    }

    protected boolean collisionTop;
    protected boolean collisionBottom;
    protected boolean collisionRight;
    protected boolean collisionLeft;

    protected MouvementState mouvementState;

    protected int lifePoint;

    protected int resElectricity;
    protected int resIce;
    protected int resFire;

    public LivingEntity(float x, float y, float width, float height, MouvementState mouvementState) {
        super(x, y, width, height);
        this.mouvementState = mouvementState;
        collisionTop = false;
        collisionBottom = false;
        collisionRight = false;
        collisionLeft = false;
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
    
    

}
