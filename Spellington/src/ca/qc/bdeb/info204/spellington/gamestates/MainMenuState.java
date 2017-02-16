package ca.qc.bdeb.info204.spellington.gamestates;

import ca.qc.bdeb.info204.spellington.GameCore;
import java.awt.Font;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.TrueTypeFont;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.state.transition.FadeInTransition;
import org.newdawn.slick.state.transition.FadeOutTransition;

/**
 * GameState corresponding with the game menu
 *
 * @author Fallen Angel
 */
public class MainMenuState extends BasicGameState {

    //Default menu font. Can be changed.
    private static final TrueTypeFont MENU_FONT = new TrueTypeFont(new Font("Times New Roman", Font.PLAIN, 20), false);

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
        
    }

    @Override
    public void render(GameContainer gc, StateBasedGame game, Graphics g) throws SlickException {
        //Rendering title text using the text dimensions to center.
        g.setColor(Color.white);
        g.setFont(MENU_FONT);
        
        int tempHeight = MENU_FONT.getHeight(MENU_TITLE);
        int tempWidth = MENU_FONT.getWidth(MENU_TITLE);
        g.drawString(MENU_TITLE, gc.getWidth() / 2 - tempWidth / 2, gc.getHeight() / 2 - tempHeight / 2);

        tempHeight = MENU_FONT.getHeight(MENU_PESEZ_POUR_JOUER);
        tempWidth = MENU_FONT.getWidth(MENU_PESEZ_POUR_JOUER);
        g.drawString(MENU_PESEZ_POUR_JOUER, gc.getWidth() / 2 - tempWidth / 2, (gc.getHeight() / 2 - tempHeight / 2) + tempHeight);

        tempHeight = MENU_FONT.getHeight(MENU_PESEZ_POUR_QUITTER);
        tempWidth = MENU_FONT.getWidth(MENU_PESEZ_POUR_QUITTER);
        g.drawString(MENU_PESEZ_POUR_QUITTER, gc.getWidth() / 2 - tempWidth / 2, (gc.getHeight() / 2 - tempHeight / 2) + 2 * tempHeight);

        g.setColor(new Color(150, 50, 50));
        g.drawOval(gc.getInput().getMouseX() - 15, gc.getInput().getMouseY() - 15, 30, 30);
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
    private void drawText(boolean centerH, boolean centerV, int x, int y) {

    }

}
