package ca.qc.bdeb.info204.spellington;

import ca.qc.bdeb.info204.spellington.gamestates.PlayState;
import ca.qc.bdeb.info204.spellington.gamestates.MainMenuState;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.io.File;
import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

/**
 * Core of the game.
 *
 * @author Fallen Angel
 */
public class GameCore extends StateBasedGame {

    private static final String GAME_TITLE = "Réveil de Spellington";
    private static final Dimension SCREEN_SIZE = Toolkit.getDefaultToolkit().getScreenSize();

    public static final int MAIN_MENU_STATE_ID = 0;
    public static final int PLAY_STATE_ID = 1;
    private static AppGameContainer appGameContainer;

    /**
     * @param args the command line arguments
     * @throws org.newdawn.slick.SlickException
     */
    public static void main(String[] args) throws SlickException {
        //Pour set les natives nécessaires aux libraires, don't touch.
        System.setProperty("org.lwjgl.librarypath", new File("lib/natives").getAbsolutePath());
        System.setProperty("net.java.games.input.librarypath", new File("lib/natives").getAbsolutePath());

        appGameContainer = new AppGameContainer(new GameCore());
        //appGameContainer setup.
        //appGameContainer.setDisplayMode(SCREEN_SIZE.width, SCREEN_SIZE.height, true);
        appGameContainer.setDisplayMode(750, 750, false);
        appGameContainer.setIcon("src/resources/icon.png");
        appGameContainer.setTitle(GAME_TITLE);
        //Start game.
        appGameContainer.start();

    }

    public GameCore() {
        super(GAME_TITLE);

    }

    @Override
    public void initStatesList(GameContainer gc) throws SlickException {
        //It is important to keep the state addition order. 
        this.addState(new MainMenuState());
        this.addState(new PlayState());

        //Initialise game states.
        this.getState(MAIN_MENU_STATE_ID).init(gc, this);
        this.getState(PLAY_STATE_ID).init(gc, this);

        //The game will being in the menu.
        this.enterState(MAIN_MENU_STATE_ID);
    }

}
