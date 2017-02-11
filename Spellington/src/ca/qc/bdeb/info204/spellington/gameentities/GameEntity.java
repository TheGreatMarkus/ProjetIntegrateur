package ca.qc.bdeb.info204.spellington.gameentities;

/**
 * A entity that will be in the game and interact with the world.
 *
 * @author Fallen Angel
 */
public class GameEntity {

    protected float x;
    protected float y;
    protected float width;
    protected float height;

    public GameEntity(float x, float y, float width, float height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }
    
    

    public float getX() {
        return x;
    }

    public void setX(float x) {
        this.x = x;
    }

    public float getY() {
        return y;
    }

    public void setY(float y) {
        this.y = y;
    }

    public float getWidth() {
        return width;
    }

    public void setWidth(float width) {
        this.width = width;
    }

    public float getHeight() {
        return height;
    }

    public void setHeight(float height) {
        this.height = height;
    }
    
    

}
