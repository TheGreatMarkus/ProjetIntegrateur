package ca.qc.bdeb.slick2dtuto;

import java.util.ArrayList;
import org.newdawn.slick.*;

public class Controller implements KeyListener {

    private ArrayList<Integer> ListControls = new ArrayList<>();

    public Controller() {
    }

    @Override
    public void keyPressed(int key, char c) {
        ListControls.add(key);

    }

    @Override
    public void keyReleased(int i, char c) {

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

    public ArrayList<Integer> getListControls() {
        return ListControls;
    }

}
