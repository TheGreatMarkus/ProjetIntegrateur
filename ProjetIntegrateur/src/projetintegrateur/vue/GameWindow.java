package projetintegrateur.vue;

import javax.swing.JFrame;
import projetintegrateur.GameWorld;
import projetintegrateur.controler.Controler;

/**
 * Fenetre de l'application.
 * @author Fallen Angel
 */
public class GameWindow extends JFrame {

    private RenderPanel renderPanel ;

    public GameWindow(Controler controler, GameWorld gameWorld) {
        this.addKeyListener(controler);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        renderPanel = new RenderPanel(gameWorld);
        this.add(renderPanel);
        
        this.setUndecorated(true);
        this.setExtendedState(JFrame.MAXIMIZED_BOTH);
        this.setVisible(true);
    }

}
