package ca.qc.bdeb.info204.spellington.gamestates;

import ca.qc.bdeb.info204.spellington.GameCore;
import ca.qc.bdeb.info204.spellington.calculations.GameManager;
import ca.qc.bdeb.info204.spellington.calculations.GameSave;
import static ca.qc.bdeb.info204.spellington.gamestates.MainMenuState.fontMenu;
import ca.qc.bdeb.info204.spellington.textEntities.MenuItem;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

/**
 * A BasicGameState that will allow the player to select a level to play.
 *
 * @author Cristian Aldea
 */
public class LevelSelectionState extends BasicGameState {

    //Text for the button in this menu. LS stands for "Level selection"
    private static final String LS_TITLE = "Le réveil de Spellington";
    private static final String LS_RETURN = "Retour";
    private static final String LS_LEVEL1 = "Niveau 1";
    private static final String LS_LEVEL2 = "Niveau 2";
    private static final String LS_LEVEL3 = "Niveau 3";
    private static final String LS_LEVEL4 = "Niveau 4";
    private static final String LS_LEVEL5 = "Niveau 5";

    private MenuItem mnuItemTitle;
    private MenuItem mnuItemReturn;
    private MenuItem mnuItemLevel1;
    private MenuItem mnuItemLevel2;
    private MenuItem mnuItemLevel3;
    private MenuItem mnuItemLevel4;
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
        mnuItemTitle = new MenuItem(gc, MenuItem.MenuItemType.TEXT, LS_TITLE, true, false, 0, MainMenuState.TEXT_GAP, fontMenu.getWidth(LS_TITLE), fontMenu.getHeight(LS_TITLE));
        mnuItemReturn = new MenuItem(gc, MenuItem.MenuItemType.BUTTON, LS_RETURN, false, false, MainMenuState.TEXT_GAP, MainMenuState.TEXT_GAP, fontMenu.getWidth(LS_RETURN), fontMenu.getHeight(LS_RETURN));
        mnuItemLevel1 = new MenuItem(gc, MenuItem.MenuItemType.BUTTON, LS_LEVEL1, false, false, MainMenuState.TEXT_GAP, mnuItemTitle.getY() + mnuItemTitle.getHeight() * 1.5f, fontMenu.getWidth(LS_LEVEL1), fontMenu.getHeight(LS_LEVEL1));
        mnuItemLevel2 = new MenuItem(gc, MenuItem.MenuItemType.BUTTON, LS_LEVEL2, false, false, MainMenuState.TEXT_GAP, mnuItemLevel1.getY() + mnuItemLevel1.getHeight() + MainMenuState.TEXT_GAP, fontMenu.getWidth(LS_LEVEL2), fontMenu.getHeight(LS_LEVEL2));
        mnuItemLevel3 = new MenuItem(gc, MenuItem.MenuItemType.BUTTON, LS_LEVEL3, false, false, MainMenuState.TEXT_GAP, mnuItemLevel2.getY() + mnuItemLevel2.getHeight() + MainMenuState.TEXT_GAP, fontMenu.getWidth(LS_LEVEL3), fontMenu.getHeight(LS_LEVEL3));
        mnuItemLevel4 = new MenuItem(gc, MenuItem.MenuItemType.BUTTON, LS_LEVEL4, false, false, MainMenuState.TEXT_GAP, mnuItemLevel3.getY() + mnuItemLevel3.getHeight() + MainMenuState.TEXT_GAP, fontMenu.getWidth(LS_LEVEL4), fontMenu.getHeight(LS_LEVEL4));
        //mnuItemLevel5 = new MenuItem(gc, MenuItem.MenuItemType.BUTTON, LS_LEVEL5, false, false, MainMenuState.TEXT_GAP, mnuItemLevel4.getY() + mnuItemLevel4.getHeight() + MainMenuState.TEXT_GAP, fontMenu.getWidth(LS_LEVEL5), fontMenu.getHeight(LS_LEVEL5));

        backgroundMenu2 = new Image("src/res/image/background/backgroundMenu2.png");
    }

    public void prepareLevel(GameSave gameSave) {
        if (gameSave != null) {
            mnuItemLevel2.setClickable(gameSave.isLvl1Complete());
            mnuItemLevel3.setClickable(gameSave.isLvl2Complete());
            mnuItemLevel4.setClickable(gameSave.isLvl3Complete());
            //mnuItemLevel5.setClickable(gameSave.isLvl4Complete());
        }
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
        backgroundMenu2.draw(0, 0, GameCore.SCREEN_SIZE.width, GameCore.SCREEN_SIZE.height);

        g.setColor(Color.white);
        g.setFont(fontMenu);
        mnuItemTitle.render(g);
        mnuItemReturn.render(g);
        mnuItemLevel1.render(g);
        mnuItemLevel2.render(g);
        mnuItemLevel3.render(g);
        mnuItemLevel4.render(g);
        //mnuItemLevel5.render(g);
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
        mnuItemReturn.detHoveredOver(mouseX, mouseY);
        mnuItemLevel1.detHoveredOver(mouseX, mouseY);
        mnuItemLevel2.detHoveredOver(mouseX, mouseY);
        mnuItemLevel3.detHoveredOver(mouseX, mouseY);
        mnuItemLevel4.detHoveredOver(mouseX, mouseY);
        //mnuItemLevel5.detHoveredOver(mouseX, mouseY);

        boolean triedToClick = gc.getInput().isMousePressed(Input.MOUSE_LEFT_BUTTON);

        if (mnuItemReturn.getHoveredOver() && triedToClick && mnuItemReturn.getClickable()) {
            game.enterState(GameCore.ID_MAIN_MENU_STATE);
        }

        if (mnuItemLevel1.getHoveredOver() && triedToClick && mnuItemLevel1.getClickable()) {
            GameManager.levelSelected(1);
            game.enterState(GameCore.ID_PLAY_STATE);
        }
        if (mnuItemLevel2.getHoveredOver() && triedToClick && mnuItemLevel2.getClickable()) {
            GameManager.levelSelected(2);
            game.enterState(GameCore.ID_PLAY_STATE);
        }
        if (mnuItemLevel3.getHoveredOver() && triedToClick && mnuItemLevel3.getClickable()) {
            GameManager.levelSelected(3);
            game.enterState(GameCore.ID_PLAY_STATE);
        }
        if (mnuItemLevel4.getHoveredOver() && triedToClick && mnuItemLevel4.getClickable()) {
            GameManager.levelSelected(4);
            game.enterState(GameCore.ID_PLAY_STATE);
        }
//        if (mnuItemLevel5.getHoveredOver() && triedToClick && mnuItemLevel5.getClickable()) {
//            GameManager.levelSelected(5);
//            game.enterState(GameCore.ID_PLAY_STATE);
//        }

        GameCore.clearInputRecord(gc);
    }

    /**
     * @return The ID of this BasicGameState.
     */
    @Override
    public int getID() {
        return GameCore.ID_LEVEL_SELECTION_STATE;
    }

}
