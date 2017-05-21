package ca.qc.bdeb.info204.spellington.gameentities;

import java.awt.Dimension;

/**
 * A StaticEntity that will be part of the map of the game.
 *
 * @author Cristian Aldea
 * @see StaticEntity
 */
public class Tile extends StaticEntity {

    public static final Dimension DIM_TILE = new Dimension(50, 50);

    public static enum TileState {
        PASSABLE,
        IMPASSABLE,
        LAVA,
        SLIPPERY
    }

    public static enum TileEvent {
        NONE,
        SPELLINGTON_EXIT,
        SPELLINGTON_ENTRY,
        DUMMY_SPAWN,
        RANDOM_SLIME_SPAWN,
        MELEE_ENEMY_SPAWN,
        RANGED_ENEMY_SPAWN,
        TREASURE_SPAWN,
        MAGE_ENEMY_SPAWN,
        WHAT_IS_THIS,
        MESSAGE_1,
        MESSAGE_2,
        MESSAGE_3
    }
    private TileState tileState;

    private TileEvent tileEvent;

    public Tile(float x, float y, float width, float height, TileState tileState, TileEvent tileEvent) {
        super(x, y, width, height);
        this.tileState = tileState;
        this.tileEvent = tileEvent;
    }

    public TileState getTileState() {
        return tileState;
    }

    public void setTileState(TileState tileState) {
        this.tileState = tileState;
    }

    public TileEvent getTileEvent() {
        return tileEvent;
    }

}
