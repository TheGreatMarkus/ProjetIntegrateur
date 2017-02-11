package ca.qc.bdeb.info204.spellington.gameentities;

/**
 *
 * @author Celtis
 */
public abstract class LivingEntity extends GameEntity {

    protected int lifePoint;

    protected int resElectric;
    protected int resIce;
    protected int resFire;

    protected float movementSpeed;

    public void setLifePoint(int lifePoint) {
        this.lifePoint = lifePoint;
    }

    public void setResElectric(int resElectric) {
        this.resElectric = resElectric;
    }

    public void setResIce(int resIce) {
        this.resIce = resIce;
    }

    public void setResFire(int resFire) {
        this.resFire = resFire;
    }

    public void setMovementSpeed(float movementSpeed) {
        this.movementSpeed = movementSpeed;
    }

    public int getLifePoint() {
        return lifePoint;
    }

    public int getResElectric() {
        return resElectric;
    }

    public int getResIce() {
        return resIce;
    }

    public int getResFire() {
        return resFire;
    }

    public float getMovementSpeed() {
        return movementSpeed;
    }

}
