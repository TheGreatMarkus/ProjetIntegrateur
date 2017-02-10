/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ca.qc.bdeb.info204.spellington.gamestates;

import ca.qc.bdeb.info204.spellington.GameCore;
import ca.qc.bdeb.info204.spellington.gameentities.Spellington;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.state.transition.FadeInTransition;
import org.newdawn.slick.state.transition.FadeOutTransition;
import org.newdawn.slick.tiled.TiledMap;

/**
 *
 * @author Fallen Angel
 */
public class PlayState extends BasicGameState{
    
    
    TiledMap map;
    Spellington spellington;

    @Override
    public int getID() {
        return GameCore.PLAY_STATE_ID;
    }

    @Override
    public void init(GameContainer container, StateBasedGame game) throws SlickException {
         map = new TiledMap("src/resources/map/grotte test.tmx");
         spellington = new Spellington();
    }

    @Override
    public void render(GameContainer gc, StateBasedGame game, Graphics g) throws SlickException {
        map.render(0, 0);
        spellington.render(g);
    }

    @Override
    public void update(GameContainer gc, StateBasedGame game, int delta) throws SlickException {
        if (gc.getInput().isKeyDown(Input.KEY_ESCAPE)) {
            game.enterState(GameCore.MAIN_MENU_STATE_ID, new FadeOutTransition(), new FadeInTransition());
        }
        spellington.update(gc.getInput(), delta);
    }
    
}
