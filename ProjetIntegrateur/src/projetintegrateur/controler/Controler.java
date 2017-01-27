package projetintegrateur.controler;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;

/**
 * Controleur de l'application. Cette classe va recevoir tous les touches
 * appuyées du claviers et les stocker dans une liste.
 *
 * @author Fallen Angel
 */
public class Controler implements KeyListener {

    private static ArrayList<Integer> pressedKeys;

    public Controler() {
        pressedKeys = new ArrayList<>();
    }

    @Override
    public void keyPressed(KeyEvent ke) {
        if (!pressedKeys.contains(ke.getExtendedKeyCode())) {
            pressedKeys.add((Integer) ke.getExtendedKeyCode());
        }
    }

    @Override
    public void keyReleased(KeyEvent ke) {
        pressedKeys.remove((Integer) ke.getExtendedKeyCode());
    }

    @Override
    public void keyTyped(KeyEvent ke) {
        //Méthode pas utilisée.
    }

}
