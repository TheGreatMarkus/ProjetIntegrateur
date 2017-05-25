package ca.qc.bdeb.info204.spellington.gameentities;

import java.awt.Dimension;
import org.newdawn.slick.geom.Rectangle;

/**
 * An entity that is in the game.
 *
 * @see GameEntity
 * @author Cristian Aldea
 */
public abstract class GameEntity {

    public static enum ElementalType {
        FIRE,
        ICE,
        LIGHTNING,
        NEUTRAL
    }

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

    public Rectangle getBounds() {
        return new Rectangle(x, y, width, height);
    }

    public float getCenterX() {
        return x + width / 2;
    }

    public float getCenterY() {
        return y + height / 2;
    }

    public float getMaxX() {
        return x + width;
    }

    public float getMaxY() {
        return y + height;
    }

    public void setLocation(float x, float y) {
        this.x = x;
        this.y = y;
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

    public void setDim(Dimension dim) {
        this.height = dim.height;
        this.width = dim.width;
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
