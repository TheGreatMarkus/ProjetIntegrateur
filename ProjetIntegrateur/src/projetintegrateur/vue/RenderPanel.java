package projetintegrateur.vue;

import java.awt.Graphics;
import java.awt.Graphics2D;
import javax.swing.JPanel;
import projetintegrateur.GameWorld;

/**
 *
 * @author Fallen Angel
 */
public class RenderPanel extends JPanel {
    //Ceci sera le panneau qui va dessiner tout le jeu.
    
    private GameWorld gameWorld;

    public RenderPanel(GameWorld gameWorld) {
        this.gameWorld = gameWorld;
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;

    }

}
