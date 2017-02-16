package ca.qc.bdeb.info204.spellington.gameentities;

/**
 *
 * @author Fallen Angel
 */
public class Tile extends StaticEntity {

    public static enum TileState {
        PASSABLE,
        IMPASSABLE,
    }
    private TileState tileState;

    public Tile(float x, float y, float width, float height, TileState tileState) {
        super(x, y, width, height);
        this.tileState = tileState;
    }

    public TileState getTileState() {
        return tileState;
    }

    public void setTileState(TileState tileState) {
        this.tileState = tileState;
    }
    
    

}
