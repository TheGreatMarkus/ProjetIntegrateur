package ca.qc.bdeb.info204.spellington.gameentities;

import org.newdawn.slick.geom.Rectangle;

/**
 * A entity that will be in the game and interact with the world.
 *
 * @author Cristian Aldea
 */
public class GameEntity extends Rectangle{

    public GameEntity(float x, float y, float width, float height) {
        super(x, y, width, height);
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }
}
