package ca.qc.bdeb.info204.spellington.calculations;

import ca.qc.bdeb.info204.spellington.GameCore;
import org.newdawn.slick.Input;
import org.newdawn.slick.KeyListener;

/**
 * Class that manages the text input of the keyboard to change the
 * incantationText.
 *
 * @author Cristian
 * @see SpellingSystem
 */
public class IncantationTextManager implements KeyListener {

    public IncantationTextManager() {
    }

    /**
     * Changes the incantationText when a key is pressed on the keyboard.
     *
     * @param key The key that was pressed
     * @param c The character corresponding to the pressed key.
     * @see SpellingSystem
     * @author Cristian Aldea
     */
    @Override
    public void keyPressed(int key, char c) {
        //See http://www.asciitable.com/ for complete ASCII table IDs
        if (GameManager.getStateBasedGame() != null && GameManager.getStateBasedGame().getCurrentState().getID() == GameCore.PLAY_STATE_ID) {
            if (c == 8 && !SpellingSystem.getIncantationText().isEmpty()) {//Backspace char ID
                SpellingSystem.setIncantationText(SpellingSystem.getIncantationText().substring(0, SpellingSystem.getIncantationText().length() - 1));
            } else if (c < 65) {
                //Do nothing
            } else if (SpellingSystem.getIncantationText().length() < 30) {
                SpellingSystem.setIncantationText(SpellingSystem.getIncantationText() + c);
            }

        }
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
