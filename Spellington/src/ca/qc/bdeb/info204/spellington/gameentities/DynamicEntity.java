package ca.qc.bdeb.info204.spellington.gameentities;

/**
 *
 * @author Celtis
 */
public abstract class DynamicEntity extends GameEntity {

    protected float xSpeed;
    protected float ySpeed;

    /**
     *
     * @param x
     * @param y
     * @param width
     * @param height
     */
    public DynamicEntity(float x, float y, float width, float height) {
        super(x, y, width, height);
    }

    public float getxSpeed() {
        return xSpeed;
    }

    public void setxSpeed(float xSpeed) {
        this.xSpeed = xSpeed;
    }

    public float getySpeed() {
        return ySpeed;
    }

    public void setySpeed(float ySpeed) {
        this.ySpeed = ySpeed;
    }

    
    
}
