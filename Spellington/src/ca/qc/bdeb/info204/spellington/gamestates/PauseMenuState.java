package ca.qc.bdeb.info204.spellington.gamestates;

import ca.qc.bdeb.info204.spellington.GameCore;
import ca.qc.bdeb.info204.spellington.calculations.GameManager;
import ca.qc.bdeb.info204.spellington.textEntities.MenuItem;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;
import static ca.qc.bdeb.info204.spellington.gamestates.MainMenuState.fontMenu;
import org.newdawn.slick.Image;

/**
 * A BasicGameState taht corresponds to the pause menu.
 *
 * @author Cristian Aldea
 */
public class PauseMenuState extends BasicGameState {

    private static final String PM_TITLE = "Pause";
    private static final String PM_RESUME = "Revenir au jeu";
    private static final String PM_SPELLINGBOOK = "Grimoire";
    private static final String PM_MAIN_MENU = "Revenir au menu";

    private MenuItem mnuItemTitle;
    private MenuItem mnuItemResume;
    private MenuItem mnuSpellingBook;
    private MenuItem mnuItemMainMenu;
    
    private Image backgroundMenu2;

    /**
     * Initialises the BasicGameState
     *
     * @param gc the GameContainer
     * @param game the StateBasedGame
     * @throws SlickException General Slick exception
     */
    @Override
    public void init(GameContainer gc, StateBasedGame game) throws SlickException {

        mnuItemTitle = new MenuItem(gc, MenuItem.MenuItemType.TEXT, PM_TITLE, true, false, 0, MainMenuState.TEXT_GAP, fontMenu.getWidth(PM_TITLE), fontMenu.getHeight(PM_TITLE));
        mnuItemResume = new MenuItem(gc, MenuItem.MenuItemType.BUTTON, PM_RESUME, true, true, 0, 0, fontMenu.getWidth(PM_RESUME), fontMenu.getHeight(PM_RESUME));
        mnuSpellingBook = new MenuItem(gc, MenuItem.MenuItemType.BUTTON, PM_SPELLINGBOOK, true, false, 0, mnuItemResume.getY() + mnuItemResume.getHeight() + MainMenuState.TEXT_GAP, fontMenu.getWidth(PM_SPELLINGBOOK), fontMenu.getHeight(PM_SPELLINGBOOK));
        mnuItemMainMenu = new MenuItem(gc, MenuItem.MenuItemType.BUTTON, PM_MAIN_MENU, true, false, 0, mnuSpellingBook.getY() + mnuSpellingBook.getHeight() + MainMenuState.TEXT_GAP, fontMenu.getWidth(PM_MAIN_MENU), fontMenu.getHeight(PM_MAIN_MENU));
    
    backgroundMenu2 = new Image("src/res/image/background/backgroundMenu2.png");
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
        backgroundMenu2.draw(0,0, GameCore.SCREEN_SIZE.width,GameCore.SCREEN_SIZE.height);
        
        g.setFont(fontMenu);
        mnuItemTitle.render(g);
        mnuItemResume.render(g);
        mnuSpellingBook.render(g);
        mnuItemMainMenu.render(g);

        MainMenuState.renderMouseCursor(gc);
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
        mnuItemResume.detHoveredOver(mouseX, mouseY);
        mnuSpellingBook.detHoveredOver(mouseX, mouseY);
        mnuItemMainMenu.detHoveredOver(mouseX, mouseY);

        boolean triedToClick = gc.getInput().isMousePressed(Input.MOUSE_LEFT_BUTTON);

        if (mnuItemResume.getHoveredOver() && triedToClick) {
            game.enterState(GameCore.ID_PLAY_STATE);
        }

        if (mnuSpellingBook.getHoveredOver() && triedToClick) {
            game.enterState(GameCore.ID_SPELLBOOK_STATE);
        }

        if (mnuItemMainMenu.getHoveredOver() && triedToClick) {
            game.enterState(GameCore.ID_MAIN_MENU_STATE);
            GameManager.loadGameSave();
        }
        GameCore.clearInputRecord(gc);
    }

    /**
     * @return The ID of this BasicGameState.
     */
    @Override
    public int getID() {
        return GameCore.ID_PAUSE_MENU_STATE;
    }

}
