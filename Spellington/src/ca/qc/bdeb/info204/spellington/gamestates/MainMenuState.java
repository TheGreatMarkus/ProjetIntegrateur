package ca.qc.bdeb.info204.spellington.gamestates;

import ca.qc.bdeb.info204.spellington.GameCore;
import ca.qc.bdeb.info204.spellington.textEntities.MenuItem;
import ca.qc.bdeb.info204.spellington.textEntities.MenuItem.MenuItemType;
import java.awt.Font;
import java.awt.FontFormatException;
import java.io.IOException;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.UnicodeFont;
import org.newdawn.slick.font.effects.ColorEffect;
import org.newdawn.slick.font.effects.OutlineEffect;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

/**
 * A BasicGameState corresponding with the main menu of the game.
 *
 * @author Cristian Aldea
 * @see GameCore
 */
public class MainMenuState extends BasicGameState {

    public static Image IMG_MENU_CURSOR;

//Default menu font.
    public static UnicodeFont universalFont;
    private Image backGround;

    //Text for the main menu.
    private static final String MM_TITLE = "Le réveil de Spellington";
    private static final String MM_PLAY = "Jouer";
    private static final String MM_OPTIONS = "Options";
    private static final String MM_EXIT = "Quitter";

    private MenuItem mnuItemTitle;
    private MenuItem mnuItemPlay;
    private MenuItem mnuItemOptions;
    private MenuItem mnuItemExit;

    private static float textGap;

    @Override
    public void init(GameContainer gc, StateBasedGame game) throws SlickException {
        IMG_MENU_CURSOR = new Image("resources/images/cursor/small_cursor.png");
        //Initialisation du font pour le menu.
        textGap = 10.0f * GameCore.SCALE;

        backGround = new Image("src/resources/images/menu/mm_background.jpg");
        try {
            Font tempTitleFont = Font.createFont(Font.TRUETYPE_FONT, this.getClass().getResourceAsStream("/resources/fonts/Paladin.ttf"));
            tempTitleFont = tempTitleFont.deriveFont(Font.BOLD, 110.0f * GameCore.SCALE);
            universalFont = new UnicodeFont(tempTitleFont);
            universalFont.addAsciiGlyphs();
            universalFont.getEffects().add(new ColorEffect(java.awt.Color.black));
            universalFont.getEffects().add(new OutlineEffect(1, java.awt.Color.white));
            universalFont.loadGlyphs();
        } catch (FontFormatException ex) {

        } catch (IOException ex) {

        }
        mnuItemTitle = new MenuItem(gc, MenuItemType.TITLE, MM_TITLE, true, false, 0, textGap, universalFont.getWidth(MM_TITLE), universalFont.getHeight(MM_TITLE));
        mnuItemPlay = new MenuItem(gc, MenuItemType.BUTTON, MM_PLAY, true, true, 0, mnuItemTitle.getY() + mnuItemTitle.getHeight() + textGap, universalFont.getWidth(MM_PLAY), universalFont.getHeight(MM_PLAY));
        mnuItemOptions = new MenuItem(gc, MenuItemType.BUTTON, MM_OPTIONS, true, false, 0, mnuItemPlay.getY() + mnuItemPlay.getHeight() + textGap, universalFont.getWidth(MM_OPTIONS), universalFont.getHeight(MM_OPTIONS));
        mnuItemExit = new MenuItem(gc, MenuItemType.BUTTON, MM_EXIT, true, false, 0, mnuItemOptions.getY() + mnuItemOptions.getHeight() + textGap, universalFont.getWidth(MM_EXIT), universalFont.getHeight(MM_EXIT));

    }

    @Override
    public void render(GameContainer gc, StateBasedGame game, Graphics g) throws SlickException {
        backGround.draw(0, 0, GameCore.SCALE);
        g.setColor(Color.white);
        g.setFont(universalFont);

        mnuItemTitle.render(g, gc);
        mnuItemPlay.render(g, gc);
        mnuItemOptions.render(g, gc);
        mnuItemExit.render(g, gc);
        float tempScale = 0.7f;
        float renderMouseX = gc.getInput().getMouseX();
        float renderMouseY = gc.getInput().getMouseY();
        IMG_MENU_CURSOR.draw(renderMouseX, renderMouseY, tempScale);

    }

    @Override
    public void update(GameContainer gc, StateBasedGame game, int delta) throws SlickException {
        int mouseX = gc.getInput().getMouseX();
        int mouseY = gc.getInput().getMouseY();
        mnuItemPlay.detHoveredOver(mouseX, mouseY);
        mnuItemOptions.detHoveredOver(mouseX, mouseY);
        mnuItemExit.detHoveredOver(mouseX, mouseY);

        boolean triedToClick = gc.getInput().isMousePressed(Input.MOUSE_LEFT_BUTTON);

        if (mnuItemPlay.getHoveredOver() && triedToClick) {
            game.enterState(GameCore.PLAY_STATE_ID);
        }
        if (mnuItemOptions.getHoveredOver() && triedToClick) {
            game.enterState(GameCore.OPTIONS_MENU_STATE_ID);
        }
        if (mnuItemExit.getHoveredOver() && triedToClick) {
            gc.exit();
        }

    }

    @Override
    public int getID() {
        return GameCore.MAIN_MENU_STATE_ID;
    }

}
