package ca.qc.bdeb.info204.spellington;

import ca.qc.bdeb.info204.spellington.calculations.SpellingSystem;
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
import java.util.logging.Level;
import java.util.logging.Logger;
import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

/**
 * Core of the game.
 *
 * @author Cristian Aldea
 */
public class GameCore extends StateBasedGame {

    //For testing and seeing the console text
    //public static final Dimension SCREEN_SIZE = Toolkit.getDefaultToolkit().getScreenSize();
    public static final Dimension SCREEN_SIZE = new Dimension(800, 450);
    //public static final Dimension SCREEN_SIZE = new Dimension(1600, 900);
    public static final Dimension RENDER_SIZE = new Dimension(1600, 900);
    public static float SCALE;
    public static final int MAIN_MENU_STATE_ID = 0;
    public static final int PLAY_STATE_ID = 1;
    public static final int OPTIONS_MENU_STATE_ID = 2;
    public static final int PAUSE_MENU_STATE_ID = 3;
    public static final int SPELLBOOK_STATE_ID = 4;

    private static final String GAME_TITLE = "Réveil de Spellington";

    private static final int TARGER_FPS = 60;

    private static AppGameContainer appGameContainer;

    public static Font fontPaladin;

    /**
     * Main method of the program
     *
     * @param args the command line arguments
     * @throws org.newdawn.slick.SlickException
     */
    public static void main(String[] args) throws SlickException {
        System.setProperty("org.lwjgl.librarypath", new File("lib/natives").getAbsolutePath());
        System.setProperty("net.java.games.input.librarypath", new File("lib/natives").getAbsolutePath());
        /*Calculation of the scale of the in-game render. Uses the width and 
         height of Screen Size and the Target render size to determine smallest 
         scale.*/

        if (((float) SCREEN_SIZE.width / (float) RENDER_SIZE.width) < ((float) SCREEN_SIZE.height / (float) RENDER_SIZE.height)) {
            SCALE = ((float) SCREEN_SIZE.width / (float) RENDER_SIZE.width);
        } else {
            SCALE = ((float) SCREEN_SIZE.height / (float) RENDER_SIZE.height);
        }

        try {
            fontPaladin = Font.createFont(Font.TRUETYPE_FONT, GameCore.class.getResourceAsStream("/res/font/Paladin.ttf"));
        } catch (FontFormatException ex) {
            Logger.getLogger(GameCore.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(GameCore.class.getName()).log(Level.SEVERE, null, ex);
        }

        appGameContainer = new AppGameContainer(new GameCore(), SCREEN_SIZE.width, SCREEN_SIZE.height, false);
        appGameContainer.setMouseGrabbed(true);
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

    @Override
    public void initStatesList(GameContainer gc) throws SlickException {
        //It is important to keep the state addition order.
        this.addState(new MainMenuState());
        this.addState(new PlayState());
        this.addState(new OptionsMenuState());
        this.addState(new PauseMenuState());
        this.addState(new SpellBookState());

        //Initialise game states.
        this.getState(MAIN_MENU_STATE_ID).init(gc, this);
        this.getState(PLAY_STATE_ID).init(gc, this);
        this.getState(SPELLBOOK_STATE_ID).init(gc, this);
        this.getState(OPTIONS_MENU_STATE_ID).init(gc, this);
        this.getState(PAUSE_MENU_STATE_ID).init(gc, this);
        SpellingSystem.initSpellingSystem();
        
//The game will being in the menu.
        this.enterState(MAIN_MENU_STATE_ID);
    }

    public static void clearInputRecord(GameContainer gc) {
        gc.getInput().clearKeyPressedRecord();
        gc.getInput().clearMousePressedRecord();
    }

}
