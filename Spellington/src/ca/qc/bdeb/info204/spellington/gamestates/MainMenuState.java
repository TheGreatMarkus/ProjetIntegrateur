package ca.qc.bdeb.info204.spellington.gamestates;

import ca.qc.bdeb.info204.spellington.GameCore;
import java.awt.Font;
import java.awt.FontFormatException;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
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
    private static final String MENU_TITLE = "Le réveil de Spellington, testage";
    private static final String MENU_PESEZ_POUR_JOUER = "Pesez Enter pour Jouer.";
    private static final String MENU_PESEZ_POUR_QUITTER = "Pesez Escape pour Quitter.";

    @Override
    public int getID() {
        return GameCore.MAIN_MENU_STATE_ID;
    }

    @Override
    public void init(GameContainer gc, StateBasedGame game) throws SlickException {
        try {
            font = Font.createFont(Font.TRUETYPE_FONT, this.getClass().getResourceAsStream("/resources/fonts/Prince Valiant.ttf"));
            font = font.deriveFont(Font.BOLD, 30.0f);
            uniFont = new UnicodeFont(font);
            uniFont.getEffects().add(new ColorEffect(java.awt.Color.white));
            uniFont.addAsciiGlyphs();
            uniFont.loadGlyphs();
        } catch (FontFormatException ex) {
            Logger.getLogger(MainMenuState.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(MainMenuState.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void render(GameContainer gc, StateBasedGame game, Graphics g) throws SlickException {
        //Rendering title text using the text dimensions to center.
        g.setColor(Color.white);
        g.setFont(uniFont);
        g.drawString(MENU_TITLE, gc.getWidth() / 2 - tempWidth / 2, gc.getHeight() / 2 - tempHeight / 2);

        drawText(gc, g, MENU_TITLE, true, true, x, y);
        drawText(gc, g, MENU_PESEZ_POUR_JOUER, true, true, x, y);
        drawText(gc, g, MENU_PESEZ_POUR_QUITTER, true, true, x, y);
    }

    @Override
    public void update(GameContainer gc, StateBasedGame game, int delta) throws SlickException {
        if (gc.getInput().isKeyDown(Input.KEY_ENTER)) {
            game.enterState(GameCore.PLAY_STATE_ID, new FadeOutTransition(), new FadeInTransition());
        }
        if (gc.getInput().isKeyDown(Input.KEY_ESCAPE)) {
            gc.exit();
        }

    }

    /**
     * Writes text in the menu.
     *
     * @param centerH If the text is centered horizontally.
     * @param centerV If the text is centered vertically.
     * @param x X position of the text.
     * @param y Y position of the text.
     */
    private void drawText(GameContainer gc, Graphics g, String text, boolean centerH, boolean centerV, int x, int y) {
        if (centerH) {
            int tempWidth = uniFont.getWidth(MENU_TITLE);
            x = gc.getWidth() / 2 - tempWidth / 2;

        }
        if (centerV) {
            int tempHeight = uniFont.getHeight(MENU_TITLE);
            y = gc.getHeight() / 2 - tempHeight / 2;
        }
        g.drawString(text, x, y);
    }

}
