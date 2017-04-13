/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ca.qc.bdeb.info204.spellington.gamestates;

import ca.qc.bdeb.info204.spellington.GameCore;
import ca.qc.bdeb.info204.spellington.calculations.GameManager;
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

    @Override
    public int getID() {
        return GameCore.LEVEL_SELECTION_STATE_ID;
    }

    @Override
    public void init(GameContainer gc, StateBasedGame game) throws SlickException {
    }

    @Override
    public void render(GameContainer gc, StateBasedGame game, Graphics g) throws SlickException {
        g.drawString("this is the currently empty level selection screen. Click to enter level 1", 50, 50);
    }

    @Override
    public void update(GameContainer gc, StateBasedGame game, int delta) throws SlickException {
        if(gc.getInput().isMousePressed(Input.MOUSE_LEFT_BUTTON)){
            GameManager.levelSelected(1);
            game.enterState(GameCore.PLAY_STATE_ID);
        }
    }

}
