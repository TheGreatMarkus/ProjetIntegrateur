package ca.qc.bdeb.info204.spellington.gamestates;

import ca.qc.bdeb.info204.spellington.GameCore;
import ca.qc.bdeb.info204.spellington.calculations.GameAnimation;
import ca.qc.bdeb.info204.spellington.calculations.Calculations;
import ca.qc.bdeb.info204.spellington.calculations.GameManager;
import ca.qc.bdeb.info204.spellington.calculations.SpellingSystem;
import ca.qc.bdeb.info204.spellington.calculations.Vector2D;
import ca.qc.bdeb.info204.spellington.gameentities.LivingEntity;
import ca.qc.bdeb.info204.spellington.gameentities.Projectile;
import ca.qc.bdeb.info204.spellington.gameentities.Spellington;
import ca.qc.bdeb.info204.spellington.gameentities.enemies.Enemy;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontFormatException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.UnicodeFont;
import org.newdawn.slick.font.effects.ColorEffect;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.tiled.TiledMap;

/**
 * A BasicGameState corresponding to the playing part of the game.
 *
 * @author Cristian Aldea
 */
public class PlayState extends BasicGameState {

    private UnicodeFont fontSpellChant;

    private Image IMG_GAME_CROSSHAIR;

    private TiledMap map;
    private Spellington spellington;

    public ArrayList<Projectile> activeProjectiles = new ArrayList<>();
    public ArrayList<GameAnimation> activeAnimations = new ArrayList<>();
    public ArrayList<Enemy> activeEnemy = new ArrayList<>();

    public static final Vector2D GRAV_ACC = new Vector2D(0, 0.001f);
    public static final Dimension DIM_MAP = new Dimension(32, 18);

    //debug variable
    public static boolean debugMode = false;
    private static boolean displayHUD = true;

    //Variables and constants related to the rendering of the HUD
    private Image statsBarHUD, inputTextHUD, passiveSpellHUD, activeSpellHUD, redPotionHUD, greenPotionHUD, bluePotionHUD, icePotionHUD;

    @Override
    public void init(GameContainer gc, StateBasedGame game) throws SlickException {
        //Loading font for the spell chant in the HUD.
        try {
            Font tempFont = Font.createFont(Font.TRUETYPE_FONT, GameCore.class.getResourceAsStream("/res/font/Viking.ttf"));
            tempFont = tempFont.deriveFont(20f);
            fontSpellChant = new UnicodeFont(tempFont);

        } catch (FontFormatException ex) {
            Logger.getLogger(GameCore.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(GameCore.class.getName()).log(Level.SEVERE, null, ex);
        }

        fontSpellChant.addAsciiGlyphs();
        fontSpellChant.getEffects().add(new ColorEffect(java.awt.Color.white));
        fontSpellChant.loadGlyphs();

        //Loading crosshair image.
        IMG_GAME_CROSSHAIR = new Image("res/image/cursor/small_crosshair.png");
        //Loading test map information.

        //Loading HUD image components
        this.statsBarHUD = new Image("src/res/image/HUD/statsBar.png");
        this.inputTextHUD = new Image("src/res/image/HUD/textRectangle.png");
        this.passiveSpellHUD = new Image("src/res/image/HUD/utilitySquare.png");
        this.activeSpellHUD = new Image("src/res/image/HUD/utilitySquare.png");
        this.redPotionHUD = new Image("src/res/image/HUD/redPotion.png");
        this.greenPotionHUD = new Image("src/res/image/HUD/greenPotion.png");
        this.bluePotionHUD = new Image("src/res/image/HUD/bluePotion.png");
        this.icePotionHUD = new Image("src/res/image/HUD/icePotion.png");
    }

    public void prepareLevel(TiledMap currentMap, int spellingtonX, int spellingtonY) throws SlickException {
        spellington = new Spellington(spellingtonX, spellingtonY, LivingEntity.MouvementState.STANDING_R);
        map = currentMap;
    }

    @Override
    public void render(GameContainer gc, StateBasedGame game, Graphics g) throws SlickException {
        g.scale(GameCore.SCALE, GameCore.SCALE);//doit être la première ligne de render

        g.setColor(Color.white);
        map.render(0, 0, 0);

        g.setColor(Color.blue);
        spellington.render(g);

        g.setColor(Color.white);
        g.drawString("ESC : Menu / F3 : TOGGLE DEBUG / F4 : TOGGLE HUD", 10, GameCore.RENDER_SIZE.height - 40);

        float renderMouseX = gc.getInput().getMouseX() / GameCore.SCALE;
        float renderMouseY = gc.getInput().getMouseY() / GameCore.SCALE;
        IMG_GAME_CROSSHAIR.draw(renderMouseX - 12, renderMouseY - 12, 25, 25);

        for (int i = 0; i < activeProjectiles.size(); i++) {
            activeProjectiles.get(i).render(g);
        }

        for (int i = 0; i < activeAnimations.size(); i++) {
            activeAnimations.get(i).render(g, spellington);
        }

        debugInfo(g, gc);

        displayHUD(g);
    }

    @Override
    public void update(GameContainer gc, StateBasedGame game, int delta) throws SlickException {
        if (delta > 40) {
            delta = 40;
        }
        if (gc.getInput().isKeyPressed(Input.KEY_ESCAPE)) {
            game.enterState(GameCore.PAUSE_MENU_STATE_ID);
        }
        if (gc.getInput().isKeyPressed(Input.KEY_F3)) {
            debugMode = !debugMode;
        }
        if (gc.getInput().isKeyPressed(Input.KEY_F4)) {
            displayHUD = !displayHUD;
        }
        spellington.update(gc.getInput(), delta);
        Calculations.checkMapCollision(GameManager.getMapInformation(), spellington);

        SpellingSystem.update(gc.getInput(), spellington, activeProjectiles, activeAnimations, activeEnemy);

        ArrayList<Projectile> projectilesToBeRemoved = new ArrayList<>();
        ArrayList<Enemy> temp = new ArrayList<>();

        for (int i = 0; i < activeProjectiles.size(); i++) {
            activeProjectiles.get(i).update((float) delta);
            if (Calculations.checkProjectileCollision(GameManager.getMapInformation(), temp, activeProjectiles.get(i))) {
                projectilesToBeRemoved.add(activeProjectiles.get(i));
            }
        }

        for (int j = 0; j < activeAnimations.size(); j++) {
            activeAnimations.get(j).update();

        }

        activeProjectiles.removeAll(projectilesToBeRemoved);
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

            int textY = 120;
            for (int i = 0; i < DIM_MAP.height; i++) {
                g.drawRect(Calculations.TargetJ * 50, i * 50, 50, 50);
            }
            for (int j = 0; j < DIM_MAP.width; j++) {
                g.drawRect(j * 50, Calculations.TargetI * 50, 50, 50);
            }

            g.setColor(Color.lightGray);
            //int textY = 10;
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

    private void displayHUD(Graphics g) throws SlickException {
        if (displayHUD) {
            int statsBarOffSetX = 75; //common X position of the stats bars
            int xGap = 5;
            final int BARS_Y = 5; //Universal Y position for most HUD components
            int healthBarY = 11 + BARS_Y; //Y position of the health bar
            int xpBarY = 54 + BARS_Y; //Y position of the xp bar
            final int STATSBARWIDTH = 381;
            final int STATSBARHEIGHT = 27;
            int alpha = 127; //50% color transparency
            final Color HEALTHCOLOR = new Color(255, 0, 0, alpha), XPCOLOR = new Color(0, 0, 255, alpha);

            String incantationText = SpellingSystem.getIncantationText();
            g.setFont(fontSpellChant);

            int passiveX = GameCore.RENDER_SIZE.width - xGap - passiveSpellHUD.getWidth();
            int activeX = passiveX - xGap - activeSpellHUD.getWidth();
            int icePositionX = activeX - xGap - redPotionHUD.getWidth();
            int bluePositionX = icePositionX - xGap - bluePotionHUD.getWidth();
            int greenPositionX = bluePositionX - xGap - greenPotionHUD.getWidth();
            int redPositionX = greenPositionX - xGap - redPotionHUD.getWidth();

            g.drawImage(this.statsBarHUD, xGap, BARS_Y);
            g.drawImage(this.inputTextHUD, (GameCore.RENDER_SIZE.width / 2 - inputTextHUD.getWidth() / 2), BARS_Y);
            g.drawImage(this.passiveSpellHUD, passiveX, BARS_Y);
            g.drawImage(this.activeSpellHUD, activeX, BARS_Y);
            g.drawImage(this.redPotionHUD, redPositionX, BARS_Y);
            g.drawImage(this.greenPotionHUD, greenPositionX, BARS_Y);
            g.drawImage(this.bluePotionHUD, bluePositionX, BARS_Y);
            g.drawImage(this.icePotionHUD, icePositionX, BARS_Y);

            g.drawString(incantationText, (GameCore.RENDER_SIZE.width / 2) - (fontSpellChant.getWidth(incantationText) / 2), BARS_Y + 12);
            g.drawString("Passive", activeX, BARS_Y + passiveSpellHUD.getHeight());
            g.drawString("Active", passiveX, BARS_Y + passiveSpellHUD.getHeight());
            g.drawString("1", redPositionX + 3, BARS_Y + redPotionHUD.getHeight());
            g.drawString("2", greenPositionX + 3, BARS_Y + greenPotionHUD.getHeight());
            g.drawString("3", bluePositionX + 3, BARS_Y + bluePotionHUD.getHeight());
            g.drawString("4", icePositionX + 3, BARS_Y + icePotionHUD.getHeight());

            g.setColor(HEALTHCOLOR);
            g.fillRect(statsBarOffSetX, healthBarY, ((float) spellington.getLifePoint() / (float) Spellington.INIT_MAX_LIFE) * (float) STATSBARWIDTH, STATSBARHEIGHT);
            g.setColor(XPCOLOR);
            g.fillRect(statsBarOffSetX, xpBarY, .5f * STATSBARWIDTH, STATSBARHEIGHT);
        }
    }

    @Override
    public int getID() {
        return GameCore.PLAY_STATE_ID;
    }
}
