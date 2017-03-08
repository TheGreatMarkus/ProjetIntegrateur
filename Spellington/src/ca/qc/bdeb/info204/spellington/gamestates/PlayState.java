package ca.qc.bdeb.info204.spellington.gamestates;

import ca.qc.bdeb.info204.spellington.GameCore;
import ca.qc.bdeb.info204.spellington.calculations.Calculations;
import ca.qc.bdeb.info204.spellington.calculations.Vector2D;
import ca.qc.bdeb.info204.spellington.gameentities.Spellington;
import ca.qc.bdeb.info204.spellington.gameentities.Tile;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import static java.awt.SystemColor.text;
import java.awt.Toolkit;
import java.awt.font.FontRenderContext;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.state.transition.FadeInTransition;
import org.newdawn.slick.state.transition.FadeOutTransition;
import org.newdawn.slick.tiled.TiledMap;

/**
 * A BasicGameState corresponding to the playing part of the game.
 *
 * @author Cristian Aldea
 */
public class PlayState extends BasicGameState {

    private TiledMap map;
    private Spellington spellington;
    private Tile[][] mapCollision;
    private Tile[][] mapEvent;

    public static final Vector2D GRAV_FORCE = new Vector2D(0, 0.001f);
    public static final Dimension DIM_MAP = new Dimension(32, 18);

    //Temporary debug variable
    private static boolean debugMode = false;
    
    //Variables and constants related to the rendering of the HUD
    private Image statsBarHUD, inputTextHUD, passiveSpellHUD, activeSpellHUD, redPotionHUD, greenPotionHUD, bluePotionHUD, icePotionHUD;
    private static final int BARS_Y = 5;
    private int statsBarOffSetX = 75; //common X position of the color bars
    private int xGap = 5, xGap2 = 40;
    private int healthBarY = 11 + BARS_Y; //Y position of the health bar
    private int xpBarY = 54 + BARS_Y; //Y position of the xp bar
    private static final int STATSBARWIDTH = 258;
    private static final int STATSBARHEIGHT = 27;
    private static int alpha = 127; //50% color transparency
    private static final Color HEALTHCOLOR = new Color(255, 0, 0, alpha), XPCOLOR = new Color(0, 0, 255, alpha);

    @Override
    public void init(GameContainer gc, StateBasedGame game) throws SlickException {

        //Very bad implementation of 
        map = new TiledMap("src/resources/map/mapTestGrotte.tmx");
        extractMapInfo();
        spellington = new Spellington(1500, 400);

        this.statsBarHUD = new Image("src/resources/map/HUD/statsBar.png");
        this.inputTextHUD = new Image("src/resources/map/HUD/textRectangle.png");
        this.passiveSpellHUD = new Image("src/resources/map/HUD/utilitySquare.png");
        this.activeSpellHUD = new Image("src/resources/map/HUD/utilitySquare.png");
        this.redPotionHUD = new Image("src/resources/map/HUD/redPotion.png");
        this.greenPotionHUD = new Image("src/resources/map/HUD/greenPotion.png");
        this.bluePotionHUD = new Image("src/resources/map/HUD/bluePotion.png");
        this.icePotionHUD = new Image("src/resources/map/HUD/icePotion.png");
    }

    @Override
    public void render(GameContainer gc, StateBasedGame game, Graphics g) throws SlickException {
        g.scale(GameCore.SCALE, GameCore.SCALE);//doit être la permière ligne de render

        g.setColor(Color.white);
        map.render(0, 0, 0);

        g.setColor(Color.blue);
        spellington.render(g);

        g.setColor(Color.white);
        g.drawString("ESC : Menu / F3 : DEBUG ", 10, GameCore.RENDER_SIZE.height - 40);

        debugInfo(g, gc);

        displayHUD(g);
    }

    @Override
    public void update(GameContainer gc, StateBasedGame game, int delta) throws SlickException {
        if (gc.getInput().isKeyDown(Input.KEY_ESCAPE)) {
            game.enterState(GameCore.MAIN_MENU_STATE_ID, new FadeOutTransition(), new FadeInTransition());
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
        mapEvent = new Tile[18][32];
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
            g.setColor(Color.lightGray);

            map.render(0, 0, 1);
            g.setColor(Color.white);

            int textY = 120;
            int textX = 10;
            int textYIncrement = 15;
            g.drawString("DEBUG", textX, textY);
            textY += textYIncrement;
            g.drawString("FPS : " + gc.getFPS(), textX, textY);
            textY += textYIncrement;
            g.drawString("Spellington X : " + spellington.getX(), textX, textY);
            textY += textYIncrement;
            g.drawString("Spellington Y : " + spellington.getY(), textX, textY);
            textY += textYIncrement;
            g.drawString("Spellington X Speed : " + spellington.getSpeedVector().getX(), textX, textY);
            textY += textYIncrement;
            g.drawString("Spellington Y Speed : " + spellington.getSpeedVector().getY(), textX, textY);
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
            g.fillOval(gc.getInput().getMouseX() / GameCore.SCALE - 5, gc.getInput().getMouseY() / GameCore.SCALE - 5, 10, 10);
        }
    }

    @Override
    public int getID() {
        return GameCore.PLAY_STATE_ID;
    }

    private void displayHUD(Graphics g) throws SlickException {
        String incantationText = "Text";
        AffineTransform affinetransform = new AffineTransform();     
        FontRenderContext frc = new FontRenderContext(affinetransform,true,true);
        Font font = new Font("Tahoma", Font.PLAIN, 16);//the third variable controls the text's position 
        
        int incantationTextWidth = (int)(font.getStringBounds(incantationText, frc).getWidth());//prototype of the text's position calculation 
        int incantationTextX = (GameCore.RENDER_SIZE.width / 2) - (incantationTextWidth / 2); 
        int passiveX = GameCore.RENDER_SIZE.width - xGap - passiveSpellHUD.getWidth();
        int activeX = passiveX - xGap - activeSpellHUD.getWidth();
        int redPositionX = (GameCore.RENDER_SIZE.width / 2 + inputTextHUD.getWidth() / 2) + xGap2;
        int greenPositionX = (GameCore.RENDER_SIZE.width / 2 + inputTextHUD.getWidth() / 2) + xGap2 + redPotionHUD.getWidth() + xGap;
        int bluePositionX = (GameCore.RENDER_SIZE.width / 2 + inputTextHUD.getWidth() / 2) + xGap2 + redPotionHUD.getWidth() + greenPotionHUD.getWidth() + (xGap * 2);
        int icePositionX = (GameCore.RENDER_SIZE.width / 2 + inputTextHUD.getWidth() / 2) + xGap2 + redPotionHUD.getWidth() + greenPotionHUD.getWidth() + bluePotionHUD.getWidth() + (xGap * 3);
        
        g.drawImage(this.statsBarHUD, xGap, BARS_Y);
        g.drawImage(this.inputTextHUD, (GameCore.RENDER_SIZE.width / 2 - inputTextHUD.getWidth() / 2), BARS_Y);
        g.drawImage(this.passiveSpellHUD, passiveX, BARS_Y);
        g.drawImage(this.activeSpellHUD, activeX, BARS_Y);
        g.drawImage(this.redPotionHUD, redPositionX, BARS_Y);
        g.drawImage(this.greenPotionHUD, greenPositionX, BARS_Y);
        g.drawImage(this.bluePotionHUD, bluePositionX, BARS_Y);
        g.drawImage(this.icePotionHUD, icePositionX, BARS_Y);
        
        g.drawString(incantationText, incantationTextX, BARS_Y + 12);
        g.drawString("Passive", activeX, BARS_Y + 100);
        g.drawString("Active", passiveX, BARS_Y + 100);
        g.drawString("1", redPositionX + (redPotionHUD.getWidth() / 2) - 3, BARS_Y + redPotionHUD.getHeight());
        g.drawString("2", greenPositionX + (greenPotionHUD.getWidth() / 2) - 3, BARS_Y + greenPotionHUD.getHeight());
        g.drawString("3", bluePositionX + (bluePotionHUD.getWidth() / 2) - 3, BARS_Y + bluePotionHUD.getHeight());
        g.drawString("4", icePositionX + (icePotionHUD.getWidth() / 2) - 3, BARS_Y + icePotionHUD.getHeight());
        

        g.setColor(HEALTHCOLOR);
        g.fillRect(statsBarOffSetX, healthBarY, (spellington.getSLifePoint() / Spellington.INIT_MAX_LIFE) * STATSBARWIDTH , STATSBARHEIGHT); 
        g.setColor(XPCOLOR);
        g.fillRect(statsBarOffSetX, xpBarY, .5f * STATSBARWIDTH, STATSBARHEIGHT);
    }
}
