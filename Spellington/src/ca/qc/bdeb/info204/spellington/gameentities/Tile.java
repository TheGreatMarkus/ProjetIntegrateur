/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ca.qc.bdeb.info204.spellington.gameentities;

import org.newdawn.slick.geom.Rectangle;

/**
 *
 * @author Fallen Angel
 */
public class Tile extends StaticEntity {

    public static enum TileState {
        PASSABLE,
        IMPASSABLE,
    }

    public Tile(float x, float y, float width, float height) {
        super(x, y, width, height);
    }

    public Rectangle getBounds() {
        return new Rectangle(x, y, width, height);

    }

}
