package ca.qc.bdeb.info204.spellington.gamestates;

import ca.qc.bdeb.info204.spellington.GameCore;
import ca.qc.bdeb.info204.spellington.calculations.GameManager;
import static ca.qc.bdeb.info204.spellington.gamestates.MainMenuState.fontMenu;
import ca.qc.bdeb.info204.spellington.textEntities.MenuItem;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

/**
 *
 * @author Fallen Angel
 */
public class LevelSelectionState extends BasicGameState {

    //Text for the button in this menu. LS stands for "Level selection"
    private static final String LS_TITLE = "Le réveil de Spellington";
    private static final String LS_LEVEL1 = "Niveau 1";
    private static final String LS_LEVEL2 = "Niveau 2";
    private static final String LS_LEVEL3 = "Niveau 3";
    private static final String LS_LEVEL4 = "Niveau 4";
    private static final String LS_LEVEL5 = "Niveau 5";

    private MenuItem mnuItemTitle;
    private MenuItem mnuItemLevel1;
    private MenuItem mnuItemLevel2;
    private MenuItem mnuItemLevel3;
    private MenuItem mnuItemLevel4;
    private MenuItem mnuItemLevel5;

    @Override
    public void init(GameContainer gc, StateBasedGame game) throws SlickException {
        mnuItemTitle = new MenuItem(gc, MenuItem.MenuItemType.TEXT, LS_TITLE, true, false, 0, MainMenuState.TEXT_GAP, fontMenu.getWidth(LS_TITLE), fontMenu.getHeight(LS_TITLE));
        mnuItemLevel1 = new MenuItem(gc, MenuItem.MenuItemType.BUTTON, LS_LEVEL1, false, false, MainMenuState.TEXT_GAP, mnuItemTitle.getY() + mnuItemTitle.getHeight() * 1.5f, fontMenu.getWidth(LS_LEVEL1), fontMenu.getHeight(LS_LEVEL1));
        mnuItemLevel2 = new MenuItem(gc, MenuItem.MenuItemType.BUTTON, LS_LEVEL2, false, false, MainMenuState.TEXT_GAP, mnuItemLevel1.getY() + mnuItemLevel1.getHeight() + MainMenuState.TEXT_GAP, fontMenu.getWidth(LS_LEVEL2), fontMenu.getHeight(LS_LEVEL2));
        mnuItemLevel3 = new MenuItem(gc, MenuItem.MenuItemType.BUTTON, LS_LEVEL3, false, false, MainMenuState.TEXT_GAP, mnuItemLevel2.getY() + mnuItemLevel2.getHeight() + MainMenuState.TEXT_GAP, fontMenu.getWidth(LS_LEVEL3), fontMenu.getHeight(LS_LEVEL3));
        mnuItemLevel4 = new MenuItem(gc, MenuItem.MenuItemType.BUTTON, LS_LEVEL4, false, false, MainMenuState.TEXT_GAP, mnuItemLevel3.getY() + mnuItemLevel3.getHeight() + MainMenuState.TEXT_GAP, fontMenu.getWidth(LS_LEVEL4), fontMenu.getHeight(LS_LEVEL4));
        mnuItemLevel5 = new MenuItem(gc, MenuItem.MenuItemType.BUTTON, LS_LEVEL5, false, false, MainMenuState.TEXT_GAP, mnuItemLevel4.getY() + mnuItemLevel4.getHeight() + MainMenuState.TEXT_GAP, fontMenu.getWidth(LS_LEVEL5), fontMenu.getHeight(LS_LEVEL5));
        mnuItemLevel3.setClickable(false);
        mnuItemLevel4.setClickable(false);
        mnuItemLevel5.setClickable(false);
    }

    @Override
    public void render(GameContainer gc, StateBasedGame game, Graphics g) throws SlickException {
        g.setColor(Color.white);
        g.setFont(fontMenu);
        mnuItemTitle.render(g, gc);
        mnuItemLevel1.render(g, gc);
        mnuItemLevel2.render(g, gc);
        mnuItemLevel3.render(g, gc);
        mnuItemLevel4.render(g, gc);
        mnuItemLevel5.render(g, gc);
        MainMenuState.renderMouseCursor(gc);
    }

    @Override
    public void update(GameContainer gc, StateBasedGame game, int delta) throws SlickException {
        int mouseX = gc.getInput().getMouseX();
        int mouseY = gc.getInput().getMouseY();
        mnuItemLevel1.detHoveredOver(mouseX, mouseY);
        mnuItemLevel2.detHoveredOver(mouseX, mouseY);
        mnuItemLevel3.detHoveredOver(mouseX, mouseY);
        mnuItemLevel4.detHoveredOver(mouseX, mouseY);
        mnuItemLevel5.detHoveredOver(mouseX, mouseY);

        boolean triedToClick = gc.getInput().isMousePressed(Input.MOUSE_LEFT_BUTTON);

        if (mnuItemLevel1.getHoveredOver() && triedToClick) {
            GameManager.levelSelected(1);
            game.enterState(GameCore.PLAY_STATE_ID);
        }
        if (mnuItemLevel2.getHoveredOver() && triedToClick) {
            GameManager.levelSelected(2);
            game.enterState(GameCore.PLAY_STATE_ID);
        }
        if (mnuItemLevel3.getHoveredOver() && triedToClick) {
            GameManager.levelSelected(3);
            game.enterState(GameCore.PLAY_STATE_ID);
        }
        if (mnuItemLevel4.getHoveredOver() && triedToClick) {
            GameManager.levelSelected(4);
            game.enterState(GameCore.PLAY_STATE_ID);
        }
        if (mnuItemLevel5.getHoveredOver() && triedToClick) {
            GameManager.levelSelected(5);
            game.enterState(GameCore.PLAY_STATE_ID);
        }

        GameCore.clearInputRecord(gc);
    }

    @Override
    public int getID() {
        return GameCore.LEVEL_SELECTION_STATE_ID;
    }
}
