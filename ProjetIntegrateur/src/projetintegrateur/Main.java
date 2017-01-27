package projetintegrateur;

import projetintegrateur.controler.Controler;
import projetintegrateur.vue.GameWindow;

/**
 * Classe Main de l'application.
 * 
 * @author Fallen Angel
 */
public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Controler controler = new Controler();
        GameWorld gameWorld = new GameWorld(controler);
        GameWindow gameWindow = new GameWindow(controler, gameWorld);
    }

}
