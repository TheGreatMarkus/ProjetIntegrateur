package ca.qc.bdeb.info204.spellington.calculations;

import ca.qc.bdeb.info204.spellington.GameCore;
import org.newdawn.slick.Input;
import org.newdawn.slick.KeyListener;

/**
 *
 * @author Cristian
 */
public class IncantationTextManager implements KeyListener {

    public IncantationTextManager() {
    }

    @Override
    public void keyPressed(int key, char c) {
        //Please see http://www.asciitable.com/ for complete ASCII table IDs
        if (GameManager.getStateBasedGame() != null) {
            if (GameManager.getStateBasedGame().getCurrentState().getID() == GameCore.PLAY_STATE_ID) {

                if (c == 8 && !SpellingSystem.getIncantationText().isEmpty()) {//Backspace char ID
                    SpellingSystem.setIncantationText(SpellingSystem.getIncantationText().substring(0, SpellingSystem.getIncantationText().length() - 1));
                } else if (c < 32) {
                    //Do nothing
                } else {
                    SpellingSystem.setIncantationText(SpellingSystem.getIncantationText() + c);
                }
            }
        }
        System.out.println("Current text : " + SpellingSystem.getIncantationText());
    }

    @Override
    public void keyReleased(int key, char c) {
    }

    @Override
    public void setInput(Input input) {
    }

    @Override
    public boolean isAcceptingInput() {
        return true;
    }

    @Override
    public void inputEnded() {
    }

    @Override
    public void inputStarted() {
    }

}
