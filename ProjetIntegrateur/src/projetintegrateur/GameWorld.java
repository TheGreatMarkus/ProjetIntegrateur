package projetintegrateur;

import projetintegrateur.controler.Controler;

/**
 * Centre logique de l'application.
 * @author Fallen Angel
 */
public class GameWorld implements Runnable {
    //

    public static enum GameState {
        MENU,
        PLAYING,
        PAUSE,
        GAME_OVER
    }

    private Thread gameThread;
    private GameState gameState;

    public GameWorld(Controler controler) {
        gameState = GameState.PLAYING;

        gameThread = new Thread(this);
        gameThread.start();
    }

    @Override
    public void run() {
        while (true) {
        //Cette boucle continuera jusqu'a la terminaison du programme
            while (gameState == GameState.PLAYING) {
            //Cette boucle est en marche pendant le jeu
                System.out.println("Hello");
            }
        }
    }

}
