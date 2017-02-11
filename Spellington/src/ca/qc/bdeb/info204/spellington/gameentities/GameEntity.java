package ca.qc.bdeb.info204.spellington.gameentities;

/**
 * A entity hat will be in and interact with the world.
 * @author Fallen Angel
 */
public class GameEntity {
    
     public enum Elements {Fire, Ice, Electricity }
     
    
    protected float x;
    protected float y;

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

   
    
    
    
}
