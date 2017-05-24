package ca.qc.bdeb.info204.spellington;

import ca.qc.bdeb.info204.spellington.calculations.GameManager;
import ca.qc.bdeb.info204.spellington.calculations.IncantationTextManager;
import ca.qc.bdeb.info204.spellington.calculations.SpellingSystem;
import ca.qc.bdeb.info204.spellington.gamestates.LevelSelectionState;
import ca.qc.bdeb.info204.spellington.gamestates.PlayState;
import ca.qc.bdeb.info204.spellington.gamestates.MainMenuState;
import ca.qc.bdeb.info204.spellington.gamestates.SpellBookState;
import ca.qc.bdeb.info204.spellington.gamestates.OptionsMenuState;
import ca.qc.bdeb.info204.spellington.gamestates.PauseMenuState;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.Toolkit;
import java.io.File;
import java.io.IOException;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

/**
 * Core of the game.
 *
 * @author Cristian Aldea.
 */
public class GameCore extends StateBasedGame {

    public static final Dimension SCREEN_SIZE = Toolkit.getDefaultToolkit().getScreenSize();
    //public static final Dimension SCREEN_SIZE = new Dimension(1920, 1080);
    //public static final Dimension SCREEN_SIZE = new Dimension(1700, 1020);
    public static final Dimension PLAY_RENDER_SIZE = new Dimension(1600, 900);
    public static final Dimension DIM_MAP = new Dimension(32, 18);
    public static float SCALE;
    public static final int ID_MAIN_MENU_STATE = 0;
    public static final int ID_PLAY_STATE = 1;
    public static final int ID_OPTIONS_MENU_STATE = 2;
    public static final int ID_PAUSE_MENU_STATE = 3;
    public static final int ID_SPELLBOOK_STATE = 4;
    public static final int ID_LEVEL_SELECTION_STATE = 5;
    public static final int ID_SAVE_SCREEN_STATE = 6;

    private static final String GAME_TITLE = "Réveil de Spellington";

    private static final int TARGER_FPS = 60;

    private static AppGameContainer appGameContainer;

    private static Font fontPaladin;
    private static Font fontMeath;
    public static Random rand = new Random();

    /**
     * Main method of the program.
     *
     * @param args the command line arguments.
     * @throws SlickException A General Slick exception.
     * @author Cristian Aldea.
     */
    public static void main(String[] args) throws SlickException {
        System.setProperty("org.lwjgl.librarypath", new File("lib/natives").getAbsolutePath());
        System.setProperty("net.java.games.input.librarypath", new File("lib/natives").getAbsolutePath());
        /*Calculation of the SCALE of the in-game render. Uses the width and 
         height of Screen Size and the Target render size to determine smallest 
         SCALE.*/

        if (((float) SCREEN_SIZE.width / (float) PLAY_RENDER_SIZE.width) < ((float) SCREEN_SIZE.height / (float) PLAY_RENDER_SIZE.height)) {
            SCALE = ((float) SCREEN_SIZE.width / (float) PLAY_RENDER_SIZE.width);
        } else {
            //gotta center according to x
            SCALE = ((float) SCREEN_SIZE.height / (float) PLAY_RENDER_SIZE.height);
        }

        try {
            fontPaladin = Font.createFont(Font.TRUETYPE_FONT, GameCore.class.getResourceAsStream("/res/font/Paladin.ttf"));
            fontMeath = Font.createFont(Font.TRUETYPE_FONT, GameCore.class.getResourceAsStream("/res/font/Meath.ttf"));
        } catch (FontFormatException ex) {
            Logger.getLogger(GameCore.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(GameCore.class.getName()).log(Level.SEVERE, null, ex);
        }

        appGameContainer = new AppGameContainer(new GameCore(), SCREEN_SIZE.width, SCREEN_SIZE.height, false);
        appGameContainer.setMouseGrabbed(false);
        appGameContainer.setTargetFrameRate(TARGER_FPS);
        appGameContainer.setVSync(true);
        appGameContainer.setIcon("res/image/icon.png");
        appGameContainer.setTitle(GAME_TITLE);
        appGameContainer.setShowFPS(false);

        //Start of the game.
        appGameContainer.start();

    }

    public GameCore() throws SlickException {
        super(GAME_TITLE);
    }

    /**
     * Initialises the different states and Managers that are going to be used
     * in the game.
     *
     * @param gc The GameContainer.
     * @throws SlickException General Slick exception.
     */
    @Override
    public void initStatesList(GameContainer gc) throws SlickException {
        double initTime = System.nanoTime();

        SpellingSystem.initSpellingSystem(GameManager.getGameSave());
        GameManager.initGameManager(this);

        gc.getInput().addKeyListener(new IncantationTextManager());
        //It is important to keep the state addition order.
        this.addState(new MainMenuState());
        this.addState(new PlayState());
        this.addState(new OptionsMenuState());
        this.addState(new PauseMenuState());
        this.addState(new SpellBookState());
        this.addState(new LevelSelectionState());

        //Initialise game states.
        this.getState(ID_MAIN_MENU_STATE).init(gc, this);
        this.getState(ID_PLAY_STATE).init(gc, this);
        this.getState(ID_SPELLBOOK_STATE).init(gc, this);
        this.getState(ID_OPTIONS_MENU_STATE).init(gc, this);
        this.getState(ID_PAUSE_MENU_STATE).init(gc, this);

        double finalTime = System.nanoTime();
        System.out.println("Time spent loading game :" + (finalTime - initTime) / 1000000000.0 + " seconds");
        System.out.println("");
        //The game will being in the menu.
        this.enterState(ID_MAIN_MENU_STATE);

    }

    /**
     * Clears the input cache.
     *
     * @param gc The GameContainer.
     * @author Cristian Aldea.
     */
    public static void clearInputRecord(GameContainer gc) {
        gc.getInput().clearKeyPressedRecord();
        gc.getInput().clearMousePressedRecord();
    }

    /**
     * Creates a new Paladin Font.
     *
     * @param style The style of the font.
     * @param size The size of the font.
     * @return A Paladin Font
     * @author Cristian Aldea.
     */
    public static Font getFontPaladin(int style, float size) {
        return fontPaladin.deriveFont(style, size);
    }

    /**
     * Creates a new Viking Font.
     *
     * @param style The style of the font.
     * @param size The size of the font.
     * @return A Paladin Font
     * @author Cristian Aldea.
     */
    public static Font getFontMeath(int style, float size) {
        return fontMeath.deriveFont(style, size);
    }

}
