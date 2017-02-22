package ca.qc.bdeb.info204.spellington.gameentities;

/**
 * A StaticEntity that will compose the map of the game.
 *
 * @author Cristian Aldea
 * @see StaticEntity
 */
public class Tile extends StaticEntity {

    public static enum TileState {
        PASSABLE,
        IMPASSABLE,
    }
    public static enum TileEvent {
        NONE,
        DAMAGE,
        SPAWN,
        EXIT,
        LEVER,
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

}
