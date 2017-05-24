package ca.qc.bdeb.info204.spellington.gamestates;

import ca.qc.bdeb.info204.spellington.GameCore;
import ca.qc.bdeb.info204.spellington.calculations.GameManager;
import ca.qc.bdeb.info204.spellington.textEntities.MenuItem;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.Input;
import static ca.qc.bdeb.info204.spellington.gamestates.MainMenuState.fontMenu;

/**
 * A BasicGameState that will let the user to change options such as controls
 * and volume.
 *
 * @author Cristian Aldea
 * @see BasicGameState
 */
public class OptionsMenuState extends BasicGameState {

    private static final String OM_TITLE = "Options";
    private static final String OM_BACK = "Revenir";

    private MenuItem mnuItemTitle;
    private MenuItem mnuItemBack;

    /**
     * Initialises the BasicGameState
     *
     * @param gc the GameContainer
     * @param game the StateBasedGame
     * @throws SlickException General Slick exception
     */
    @Override
    public void init(GameContainer gc, StateBasedGame game) throws SlickException {

        mnuItemTitle = new MenuItem(gc, MenuItem.MenuItemType.TEXT, OM_TITLE, true, false, 0, MainMenuState.TEXT_GAP, fontMenu.getWidth(OM_TITLE), fontMenu.getHeight(OM_TITLE));
        mnuItemBack = new MenuItem(gc, MenuItem.MenuItemType.BUTTON, OM_BACK, false, false, MainMenuState.TEXT_GAP, MainMenuState.TEXT_GAP, fontMenu.getWidth(OM_BACK), fontMenu.getHeight(OM_BACK));
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
        g.setFont(fontMenu);
        mnuItemTitle.render(g);
        mnuItemBack.render(g);

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
        mnuItemBack.detHoveredOver(mouseX, mouseY);

        boolean triedToClick = gc.getInput().isMousePressed(Input.MOUSE_LEFT_BUTTON);

        if (mnuItemBack.getHoveredOver() && triedToClick) {
            game.enterState(GameCore.ID_MAIN_MENU_STATE);
        }
        GameCore.clearInputRecord(gc);
    }

    /**
     * @return The ID of this BasicGameState.
     */
    @Override
    public int getID() {
        return GameCore.ID_OPTIONS_MENU_STATE;
    }
}
