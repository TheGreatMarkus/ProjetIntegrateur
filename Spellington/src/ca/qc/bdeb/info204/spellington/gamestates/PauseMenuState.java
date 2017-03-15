package ca.qc.bdeb.info204.spellington.gamestates;

import ca.qc.bdeb.info204.spellington.GameCore;
import static ca.qc.bdeb.info204.spellington.gamestates.MainMenuState.IMG_MENU_CURSOR;
import ca.qc.bdeb.info204.spellington.textEntities.MenuItem;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;
import static ca.qc.bdeb.info204.spellington.gamestates.MainMenuState.fontMenu;

/**
 *
 * @author Fallen Angel
 */
public class PauseMenuState extends BasicGameState {

    private static final String PM_TITLE = "Pause";
    private static final String PM_RESUME = "Revenir au jeu";
    private static final String PM_MAIN_MENU = "Revenir au menu";

    private MenuItem mnuItemTitle;
    private MenuItem mnuItemResume;
    private MenuItem mnuItemMainMenu;

    private static float textGap;

    @Override
    public void init(GameContainer gc, StateBasedGame game) throws SlickException {
        textGap = 10.0f * GameCore.SCALE;

        mnuItemTitle = new MenuItem(gc, MenuItem.MenuItemType.TITLE, PM_TITLE, true, false, 0, textGap, fontMenu.getWidth(PM_TITLE), fontMenu.getHeight(PM_TITLE));
        mnuItemResume = new MenuItem(gc, MenuItem.MenuItemType.BUTTON, PM_RESUME, true, true, 0, 0, fontMenu.getWidth(PM_RESUME), fontMenu.getHeight(PM_RESUME));
        mnuItemMainMenu = new MenuItem(gc, MenuItem.MenuItemType.BUTTON, PM_MAIN_MENU, true, false, 0, mnuItemResume.getY() + mnuItemResume.getHeight() + textGap, fontMenu.getWidth(PM_MAIN_MENU), fontMenu.getHeight(PM_MAIN_MENU));

    }

    @Override
    public void render(GameContainer gc, StateBasedGame game, Graphics g) throws SlickException {
        g.setFont(fontMenu);
        mnuItemTitle.render(g, gc);
        mnuItemResume.render(g, gc);
        mnuItemMainMenu.render(g, gc);

        float renderMouseX = gc.getInput().getMouseX();
        float renderMouseY = gc.getInput().getMouseY();
        IMG_MENU_CURSOR.draw(renderMouseX, renderMouseY, 25f * GameCore.SCALE, 25f * GameCore.SCALE);
    }

    @Override
    public void update(GameContainer gc, StateBasedGame game, int delta) throws SlickException {
        int mouseX = gc.getInput().getMouseX();
        int mouseY = gc.getInput().getMouseY();
        mnuItemResume.detHoveredOver(mouseX, mouseY);
        mnuItemMainMenu.detHoveredOver(mouseX, mouseY);

        boolean triedToClick = gc.getInput().isMousePressed(Input.MOUSE_LEFT_BUTTON);

        if (mnuItemResume.getHoveredOver() && triedToClick) {
            game.enterState(GameCore.PLAY_STATE_ID);
        }

        if (mnuItemMainMenu.getHoveredOver() && triedToClick) {
            game.enterState(GameCore.MAIN_MENU_STATE_ID);
        }
        GameCore.clearInputRecord(gc);
    }

    @Override
    public int getID() {
        return GameCore.PAUSE_MENU_STATE_ID;
    }

}
