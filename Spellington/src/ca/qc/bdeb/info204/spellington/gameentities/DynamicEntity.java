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
    protected float gravModifier;

    public DynamicEntity(float x, float y, float width, float height, float gravMod, Vector2D speedVector) {
        super(x, y, width, height);
        this.collisionTop = false;
        this.collisionBottom = false;
        this.collisionRight = false;
        this.collisionLeft = false;
        this.speedVector = speedVector;
        this.gravModifier = gravMod;
    }

    public Vector2D getSpeedVector() {
        return speedVector;
    }

    public void setSpeedVector(Vector2D speedVector) {
        this.speedVector = speedVector;
    }

    public void resetCollisionState() {
        this.collisionTop = false;
        this.collisionBottom = false;
        this.collisionRight = false;
        this.collisionLeft = false;
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

}
