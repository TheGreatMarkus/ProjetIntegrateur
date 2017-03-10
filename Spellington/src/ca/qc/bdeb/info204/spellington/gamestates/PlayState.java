package ca.qc.bdeb.info204.spellington.gamestates;

import ca.qc.bdeb.info204.spellington.GameCore;
import ca.qc.bdeb.info204.spellington.calculations.Calculations;
import ca.qc.bdeb.info204.spellington.calculations.Vector2D;
import ca.qc.bdeb.info204.spellington.gameentities.LivingEntity;
import ca.qc.bdeb.info204.spellington.gameentities.Spellington;
import ca.qc.bdeb.info204.spellington.gameentities.Tile;
import java.awt.Dimension;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.tiled.TiledMap;

/**
 * A BasicGameState corresponding to the playing part of the game.
 *
 * @author Cristian Aldea
 */
public class PlayState extends BasicGameState {

    private static Image IMG_GAME_CROSSHAIR;

    private TiledMap map;
    private Spellington spellington;
    private Tile[][] mapCollision;
    private Tile[][] mapEvent;

    public static final Vector2D GRAV_ACC = new Vector2D(0, 0.001f);
    public static final Dimension DIM_MAP = new Dimension(32, 18);

    //debug variable
    private static boolean debugMode = false;

    @Override
    public void init(GameContainer gc, StateBasedGame game) throws SlickException {

        IMG_GAME_CROSSHAIR = new Image("res/image/cursor/small_crosshair.png");

        map = new TiledMap("res/map/mapTestGrotte.tmx");
        extractMapInfo();
        spellington = new Spellington(65, 760, LivingEntity.MouvementState.STANDING_R);
    }

    @Override
    public void render(GameContainer gc, StateBasedGame game, Graphics g) throws SlickException {
        g.scale(GameCore.SCALE, GameCore.SCALE);//doit être la première ligne de render

        g.setColor(Color.white);
        map.render(0, 0, 0);

        g.setColor(Color.blue);
        spellington.render(g);

        g.setColor(Color.white);
        g.drawString("ESC : Menu / F3 : DEBUG ", GameCore.RENDER_SIZE.width - 230, 20);

        float renderMouseX = gc.getInput().getMouseX() / GameCore.SCALE;
        float renderMouseY = gc.getInput().getMouseY() / GameCore.SCALE;
        IMG_GAME_CROSSHAIR.draw(renderMouseX - 12, renderMouseY - 12, 25, 25);
        debugInfo(g, gc);

    }

    @Override
    public void update(GameContainer gc, StateBasedGame game, int delta) throws SlickException {
        if (gc.getInput().isKeyPressed(Input.KEY_ESCAPE)) {
            game.enterState(GameCore.PAUSE_MENU_STATE_ID);
        }
        if (gc.getInput().isKeyPressed(Input.KEY_F3)) {
            debugMode = !debugMode;
        }
        spellington.update(gc.getInput(), delta);
        Calculations.checkMapCollision(mapCollision, spellington);
    }

    /**
     *
     * @author Cristian Aldea
     */
    private void extractMapInfo() {
        mapCollision = new Tile[DIM_MAP.height][DIM_MAP.width];
        for (int i = 0; i < map.getHeight(); i++) {
            for (int j = 0; j < map.getWidth(); j++) {
                if (map.getTileId(j, i, 1) == map.getTileSet(1).firstGID + 11) {
                    mapCollision[i][j] = new Tile(50 * j, 50 * i, 50, 50, Tile.TileState.PASSABLE, Tile.TileEvent.NONE);
                } else {
                    mapCollision[i][j] = new Tile(50 * j, 50 * i, 50, 50, Tile.TileState.IMPASSABLE, Tile.TileEvent.NONE);
                }
            }
        }
        mapEvent = new Tile[DIM_MAP.height][DIM_MAP.width];
        for (int i = 0; i < map.getHeight(); i++) {
            for (int j = 0; j < map.getWidth(); j++) {
                if (map.getTileId(j, i, 2) == 0) {
                    mapEvent[i][j] = new Tile(50 * j, 50 * i, 50, 50, Tile.TileState.PASSABLE, Tile.TileEvent.NONE);
                }
                if (map.getTileId(j, i, 2) == 1) {
                    mapEvent[i][j] = new Tile(50 * j, 50 * i, 50, 50, Tile.TileState.PASSABLE, Tile.TileEvent.EXIT);
                }
                if (map.getTileId(j, i, 2) == 2) {
                    mapEvent[i][j] = new Tile(50 * j, 50 * i, 50, 50, Tile.TileState.PASSABLE, Tile.TileEvent.SPAWN);
                }
                if (map.getTileId(j, i, 2) == 3) {
                    mapEvent[i][j] = new Tile(50 * j, 50 * i, 50, 50, Tile.TileState.PASSABLE, Tile.TileEvent.SPAWN);
                }
                if (map.getTileId(j, i, 2) == 4) {
                    mapEvent[i][j] = new Tile(50 * j, 50 * i, 50, 50, Tile.TileState.PASSABLE, Tile.TileEvent.SPAWN);
                }
                if (map.getTileId(j, i, 2) == 5) {
                    mapEvent[i][j] = new Tile(50 * j, 50 * i, 50, 50, Tile.TileState.PASSABLE, Tile.TileEvent.LEVER);
                } else {
                    mapEvent[i][j] = new Tile(50 * j, 50 * i, 50, 50, Tile.TileState.PASSABLE, Tile.TileEvent.NONE);
                }
            }
        }
    }

    /**
     * Displays information about spellington for debug purposes
     *
     * @param g
     */
    private void debugInfo(Graphics g, GameContainer gc) {
        if (debugMode) {
            float actualMouseX = gc.getInput().getMouseX();
            float actualMouseY = gc.getInput().getMouseY();
            float renderMouseX = gc.getInput().getMouseX() / GameCore.SCALE;
            float renderMouseY = gc.getInput().getMouseY() / GameCore.SCALE;

            map.render(0, 0, 1);
            g.setColor(Color.red);

            for (int i = 0; i < DIM_MAP.height; i++) {
                g.drawRect(Calculations.TargetJ * 50, i * 50, 50, 50);
            }
            for (int j = 0; j < DIM_MAP.width; j++) {
                g.drawRect(j * 50, Calculations.TargetI * 50, 50, 50);
            }

            g.setColor(Color.lightGray);
            int textY = 10;
            int textX = 10;
            int textYIncrement = 15;
            g.drawString("DEBUG", textX, textY);
            textY += textYIncrement;
            g.drawString("FPS : " + gc.getFPS(), textX, textY);
            textY += textYIncrement;
            g.drawString("Actual Mouse Position : (" + actualMouseX + "," + actualMouseY + ")", textX, textY);
            textY += textYIncrement;
            g.drawString("Render Mouse Position : (" + renderMouseX + "," + renderMouseY + ")", textX, textY);
            textY += textYIncrement;
            g.drawString("Spellington Position : (" + spellington.getX() + "," + spellington.getY() + ")", textX, textY);
            textY += textYIncrement;
            g.drawString("Spellington Speed : (" + spellington.getSpeedVector().getX() + "," + spellington.getSpeedVector().getY() + ")", textX, textY);
            textY += textYIncrement;
            g.drawString("Collision :", textX, textY);
            textY += textYIncrement;

            int startingX = 10;
            int startingY = textY + 10;
            int tempSize = 25;
            g.drawRect(startingX + tempSize, startingY, tempSize, tempSize); //Top
            g.drawRect(startingX + tempSize, startingY + tempSize * 2, tempSize, tempSize); //Bottom
            g.drawRect(startingX + tempSize * 2, startingY + tempSize, tempSize, tempSize); //Right
            g.drawRect(startingX, startingY + tempSize, tempSize, tempSize); //Left
            if (spellington.getCollisionTop()) {
                g.fillRect(startingX + tempSize, startingY, tempSize, tempSize);
            }
            if (spellington.getCollisionBottom()) {
                g.fillRect(startingX + tempSize, startingY + tempSize * 2, tempSize, tempSize);
            }
            if (spellington.getCollisionRight()) {
                g.fillRect(startingX + tempSize * 2, startingY + tempSize, tempSize, tempSize);
            }
            if (spellington.getCollisionLeft()) {
                g.fillRect(startingX, startingY + tempSize, tempSize, tempSize);
            }
            g.fillOval(renderMouseX - 1, renderMouseY - 1, 3, 3);
        }
        GameCore.clearInputRecord(gc);
    }

    @Override
    public int getID() {
        return GameCore.PLAY_STATE_ID;
    }
}
