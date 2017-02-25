package ca.qc.bdeb.info204.spellington.gamestates;

import ca.qc.bdeb.info204.spellington.GameCore;
import ca.qc.bdeb.info204.spellington.textEntities.MenuItem;
import java.awt.Font;
import java.awt.FontFormatException;
import java.io.IOException;
import java.util.ArrayList;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.UnicodeFont;
import org.newdawn.slick.font.effects.ColorEffect;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.state.transition.FadeInTransition;
import org.newdawn.slick.state.transition.FadeOutTransition;

/**
 * A BasicGameState corresponding with the main menu of the game.
 *
 * @author Cristian Aldea
 * @see GameCore
 */
public class MainMenuState extends BasicGameState {

    //Default menu font. Can be changed.
    private static Font font;
    private static UnicodeFont uniFont;

    //Text for the menu.
    private static final String MM_TITLE = "Le réveil de Spellington";
    private static final String MM_PLAY = "Jouer";
    private static final String MM_OPTIONS = "Options";
    private static final String MM_EXIT = "Quitter";

    private ArrayList<MenuItem> MM_TEXTS;

    @Override
    public void init(GameContainer gc, StateBasedGame game) throws SlickException {
        //Initialisation du font pour le menu.
        MM_TEXTS = new ArrayList<>();
        try {
            font = Font.createFont(Font.TRUETYPE_FONT, this.getClass().getResourceAsStream("/resources/fonts/Paladin.ttf"));
            font = font.deriveFont(Font.BOLD, 60.0f);
            uniFont = new UnicodeFont(font);
            uniFont.getEffects().add(new ColorEffect(java.awt.Color.white));
            uniFont.addAsciiGlyphs();
            uniFont.loadGlyphs();
        } catch (FontFormatException ex) {

        } catch (IOException ex) {

        }
        MM_TEXTS.add(new MenuItem(gc, MM_TITLE, true, false, 0, 10, uniFont.getWidth(MM_TITLE), uniFont.getHeight(MM_TITLE)));

        MM_TEXTS.add(new MenuItem(gc, MM_PLAY, true, false, 0, 400, uniFont.getWidth(MM_PLAY), uniFont.getHeight(MM_PLAY)));
        MM_TEXTS.add(new MenuItem(gc, MM_OPTIONS, true, false, 0, 500, uniFont.getWidth(MM_OPTIONS), uniFont.getHeight(MM_OPTIONS)));
        MM_TEXTS.add(new MenuItem(gc, MM_EXIT, true, false, 0, 600, uniFont.getWidth(MM_EXIT), uniFont.getHeight(MM_EXIT)));

    }

    @Override
    public void render(GameContainer gc, StateBasedGame game, Graphics g) throws SlickException {
        //g.scale(GameCore.SCALE, GameCore.SCALE);
        //Rendering title text using the text dimensions to center.
        g.setColor(Color.white);
        g.setFont(uniFont);

        for (int i = 0; i < MM_TEXTS.size(); i++) {
            MM_TEXTS.get(i).render(g, gc);
        }
    }

    @Override
    public void update(GameContainer gc, StateBasedGame game, int delta) throws SlickException {
        for (int i = 0; i < MM_TEXTS.size(); i++) {
            MM_TEXTS.get(i).setHoveredOver(
                    MM_TEXTS.get(i).detHoveredOver(gc.getInput().getMouseX(), gc.getInput().getMouseY()));
        }
        boolean triedToClick = gc.getInput().isMousePressed(Input.MOUSE_LEFT_BUTTON);

        if (MM_TEXTS.get(1).getHoveredOver() && triedToClick) {
            game.enterState(GameCore.PLAY_STATE_ID, new FadeOutTransition(), new FadeInTransition());
        }
        if (MM_TEXTS.get(2).getHoveredOver() && triedToClick) {
            game.enterState(GameCore.OPTIONS_MENU_STATE_ID, new FadeOutTransition(), new FadeInTransition());
        }
        if (MM_TEXTS.get(3).getHoveredOver() && triedToClick) {
            gc.exit();
        }

    }

    @Override
    public int getID() {
        return GameCore.MAIN_MENU_STATE_ID;
    }
    
}
