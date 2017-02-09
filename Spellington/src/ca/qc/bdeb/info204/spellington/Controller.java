package ca.qc.bdeb.info204.spellington;

import java.util.ArrayList;
import org.newdawn.slick.*;

public class Controller implements KeyListener {

    private final ArrayList<Integer> pressedKeys = new ArrayList<>();

    public Controller() {
    }

    @Override
    public void keyPressed(int key, char c) {
        if (!pressedKeys.contains(key)) {
            pressedKeys.add(key);
        }

    }

    @Override
    public void keyReleased(int key, char c) {
        pressedKeys.remove(key);
    }

    @Override
    public void inputStarted() {

    }

    @Override
    public void inputEnded() {

    }

    @Override
    public boolean isAcceptingInput() {
        return false; // temporairement (à changer)
    }

    @Override
    public void setInput(Input input) {

    }

    public ArrayList<Integer> getPressedKeys() {
        return pressedKeys;
    }

}
