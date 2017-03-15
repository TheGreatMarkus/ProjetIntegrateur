package ca.qc.bdeb.info204.spellington.gameentities;

import ca.qc.bdeb.info204.spellington.calculations.Vector2D;

/**
 * A GameEntity that is able to move within the game.
 *
 * @author Celtis
 * @see GameEntity
 */
public abstract class DynamicEntity extends GameEntity {

    protected boolean collisionTop;
    protected boolean collisionBottom;
    protected boolean collisionRight;
    protected boolean collisionLeft;

    protected Vector2D speedVector;

    /**
     *
     * @param x
     * @param y
     * @param width
     * @param height
     */
    public DynamicEntity(float x, float y, float width, float height) {
        super(x, y, width, height);
        collisionTop = false;
        collisionBottom = false;
        collisionRight = false;
        collisionLeft = false;
        speedVector = new Vector2D(0, 0);
    }

    public Vector2D getSpeedVector() {
        return speedVector;
    }
}
