package ca.qc.bdeb.info204.spellington.gamestates;

import ca.qc.bdeb.info204.spellington.GameCore;
import static ca.qc.bdeb.info204.spellington.gamestates.MainMenuState.IMG_MENU_CURSOR;
import ca.qc.bdeb.info204.spellington.textEntities.MenuItem;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;
import static ca.qc.bdeb.info204.spellington.gamestates.MainMenuState.universalFont;
import org.newdawn.slick.Input;

/**
 *
 * @author Fallen Angel
 */
public class OptionsMenuState extends BasicGameState {

    private static float textGap;

    private static final String OM_TITLE = "Options";
    private static final String OM_BACK = "Revenir";

    private MenuItem mnuItemTitle;
    private MenuItem mnuItemBack;

    @Override
    public void init(GameContainer gc, StateBasedGame game) throws SlickException {
        textGap = 10.0f * GameCore.SCALE;
        mnuItemTitle = new MenuItem(gc, MenuItem.MenuItemType.TITLE, OM_TITLE, true, false, 0, textGap, universalFont.getWidth(OM_TITLE), universalFont.getHeight(OM_TITLE));
        mnuItemBack = new MenuItem(gc, MenuItem.MenuItemType.BUTTON, OM_BACK, false, false, textGap, textGap, universalFont.getWidth(OM_BACK), universalFont.getHeight(OM_BACK));
    }

    @Override
    public void render(GameContainer gc, StateBasedGame game, Graphics g) throws SlickException {
        g.setFont(universalFont);
        mnuItemTitle.render(g, gc);
        mnuItemBack.render(g, gc);

        float renderMouseX = gc.getInput().getMouseX();
        float renderMouseY = gc.getInput().getMouseY();
        IMG_MENU_CURSOR.draw(renderMouseX, renderMouseY, 25f * GameCore.SCALE, 25f * GameCore.SCALE);
    }

    @Override
    public void update(GameContainer gc, StateBasedGame game, int delta) throws SlickException {
        int mouseX = gc.getInput().getMouseX();
        int mouseY = gc.getInput().getMouseY();
        mnuItemBack.detHoveredOver(mouseX, mouseY);

        boolean triedToClick = gc.getInput().isMousePressed(Input.MOUSE_LEFT_BUTTON);

        if (mnuItemBack.getHoveredOver() && triedToClick) {
            game.enterState(GameCore.MAIN_MENU_STATE_ID);
        }
        GameCore.clearInputRecord(gc);
    }

    @Override
    public int getID() {
        return GameCore.OPTIONS_MENU_STATE_ID;
    }
}
