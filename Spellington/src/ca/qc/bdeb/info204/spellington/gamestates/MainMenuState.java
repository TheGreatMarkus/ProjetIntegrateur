package ca.qc.bdeb.info204.spellington.gamestates;

import ca.qc.bdeb.info204.spellington.GameCore;
import ca.qc.bdeb.info204.spellington.calculations.GameManager;
import ca.qc.bdeb.info204.spellington.textEntities.MenuItem;
import ca.qc.bdeb.info204.spellington.textEntities.MenuItem.MenuItemType;
import java.awt.Font;
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

/**
 * A BasicGameState corresponding with the main menu of the game.
 *
 * @author Cristian Aldea
 * @see GameCore
 */
public class MainMenuState extends BasicGameState {

    //Default menu font. Can be changed.
    public static Image IMG_MENU_CURSOR;

    //Default menu font.
    protected static UnicodeFont fontMenu;
    protected static UnicodeFont fontCredit;
    private Image backGround;

    //Text for the main menu.
    private static final String MM_TITLE = "Le réveil de Spellington";
    private static final String MM_NEW_GAME = "Nouvelle partie";
    private static final String MM_LOAD_GAME = "Charger une partie";
    private static final String MM_OPTIONS = "Options";
    private static final String MM_EXIT = "Quitter";

    private MenuItem mnuItemTitle;
    private MenuItem mnuItemNewGame;
    private MenuItem mnuItemLoadGame;
    private MenuItem mnuItemOptions;
    private MenuItem mnuItemExit;

    public static final float TEXT_GAP = 10.0f * GameCore.SCALE;

    /**
     * Initialises the BasicGameState
     *
     * @param gc the GameContainer
     * @param game the StateBasedGame
     * @throws SlickException General Slick exception
     */
    @Override
    public void init(GameContainer gc, StateBasedGame game) throws SlickException {
        IMG_MENU_CURSOR = new Image("res/image/cursor/small_cursor.png");
        //Initialisation du font pour le menu.

        backGround = new Image("res/image/background/backgroundMenu.png");

        fontMenu = new UnicodeFont(GameCore.getFontPaladin(Font.BOLD, 80.0f * GameCore.SCALE));
        fontMenu.addAsciiGlyphs();
        fontMenu.getEffects().add(new ColorEffect(java.awt.Color.LIGHT_GRAY));
        fontMenu.loadGlyphs();

        fontCredit = new UnicodeFont(GameCore.getFontPaladin(Font.BOLD, 20.0f * GameCore.SCALE));
        fontCredit.addAsciiGlyphs();
        fontCredit.getEffects().add(new ColorEffect(java.awt.Color.WHITE));
        fontCredit.loadGlyphs();

        mnuItemTitle = new MenuItem(gc, MenuItemType.TEXT, MM_TITLE, true, false, 0, TEXT_GAP, fontMenu.getWidth(MM_TITLE), fontMenu.getHeight(MM_TITLE));
        mnuItemNewGame = new MenuItem(gc, MenuItemType.BUTTON, MM_NEW_GAME, true, true, 0, 0, fontMenu.getWidth(MM_NEW_GAME), fontMenu.getHeight(MM_NEW_GAME));
        mnuItemLoadGame = new MenuItem(gc, MenuItemType.BUTTON, MM_LOAD_GAME, true, false, 0, mnuItemNewGame.getY() + mnuItemNewGame.getHeight() + TEXT_GAP, fontMenu.getWidth(MM_LOAD_GAME), fontMenu.getHeight(MM_LOAD_GAME));
        mnuItemOptions = new MenuItem(gc, MenuItemType.BUTTON, MM_OPTIONS, true, false, 0, mnuItemLoadGame.getY() + mnuItemLoadGame.getHeight() + TEXT_GAP, fontMenu.getWidth(MM_OPTIONS), fontMenu.getHeight(MM_OPTIONS));
        mnuItemExit = new MenuItem(gc, MenuItemType.BUTTON, MM_EXIT, true, false, 0, mnuItemOptions.getY() + mnuItemOptions.getHeight() + TEXT_GAP, fontMenu.getWidth(MM_EXIT), fontMenu.getHeight(MM_EXIT));

    }

    /**
     * Renders the BasicGameState
     *
     * @param gc the GameContainer
     * @param game the StateBasedGame
     * @param g The Graphics component
     * @throws SlickException General Slick exception
     */
    @Override
    public void render(GameContainer gc, StateBasedGame game, Graphics g) throws SlickException {
        backGround.draw(0, 0, GameCore.SCREEN_SIZE.width, GameCore.SCREEN_SIZE.height);
        g.setColor(Color.white);
        g.setFont(fontMenu);

        mnuItemTitle.render(g);
        mnuItemNewGame.render(g);
        mnuItemLoadGame.render(g);
        mnuItemOptions.render(g);
        mnuItemExit.render(g);

        renderMouseCursor(gc);
        g.setFont(fontCredit);
        g.setColor(Color.white);
        String credit = "Par Cristian Aldea, Celtis de Chardon et Tarik Benakezouh";
        g.drawString(credit, GameCore.SCREEN_SIZE.width / 2 - fontCredit.getWidth(credit) / 2, GameCore.SCREEN_SIZE.height - 30);

    }

    /**
     * Updates the BasicGameState
     *
     * @param gc the GameContainer
     * @param game the StateBasedGame
     * @param delta the delta of the frame
     * @throws SlickException General Slick exception
     */
    @Override
    public void update(GameContainer gc, StateBasedGame game, int delta) throws SlickException {
        int mouseX = gc.getInput().getMouseX();
        int mouseY = gc.getInput().getMouseY();
        mnuItemNewGame.detHoveredOver(mouseX, mouseY);
        mnuItemLoadGame.detHoveredOver(mouseX, mouseY);
        mnuItemOptions.detHoveredOver(mouseX, mouseY);
        mnuItemExit.detHoveredOver(mouseX, mouseY);

        boolean triedToClick = gc.getInput().isMousePressed(Input.MOUSE_LEFT_BUTTON);

        if (mnuItemNewGame.getHoveredOver() && triedToClick) {
            GameManager.newGame();
        }
        if (mnuItemLoadGame.getHoveredOver() && triedToClick && mnuItemLoadGame.getClickable()) {
            ((LevelSelectionState) (game.getState(GameCore.ID_LEVEL_SELECTION_STATE))).prepareLevel(GameManager.getGameSave());
            game.enterState(GameCore.ID_LEVEL_SELECTION_STATE);
        }
        if (mnuItemOptions.getHoveredOver() && triedToClick) {
            game.enterState(GameCore.ID_OPTIONS_MENU_STATE);
        }
        if (mnuItemExit.getHoveredOver() && triedToClick) {
            GameManager.saveGameSave();
            gc.exit();
        }

        GameCore.clearInputRecord(gc);

    }

    /**
     * @return The ID of this BasicGameState.
     */
    @Override
    public int getID() {
        return GameCore.ID_MAIN_MENU_STATE;
    }

    /**
     * Renders the mouse in a menu state.
     *
     * @param gc The GameContainer
     */
    public static void renderMouseCursor(GameContainer gc) {
        float renderMouseX = gc.getInput().getMouseX();
        float renderMouseY = gc.getInput().getMouseY();
        IMG_MENU_CURSOR.draw(renderMouseX, renderMouseY, 25f * GameCore.SCALE, 25f * GameCore.SCALE);
    }

}
