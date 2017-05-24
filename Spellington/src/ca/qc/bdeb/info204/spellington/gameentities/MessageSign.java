package ca.qc.bdeb.info204.spellington.gameentities;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

/**
 *Classe qui gère les pancartes du tutoriel
 * 
 * @author Fallen Angel
 * 
 */
public class MessageSign extends StaticEntity {

    private String message;
    private Image imgSign;
    private boolean showMessage;

    public MessageSign(float x, float y, float width, float height, String message) {
        super(x, y, width, height);
        this.message = message;
        
        try{
        imgSign = new Image("src/res/image/messageSign.png");
        }catch (SlickException se) {System.out.println("Erreur, image de pancarte introuvable.");}
        
    }

    public void update(Spellington spellington) {
        if (spellington.getBounds().intersects(this.getBounds())) {
            showMessage = true;
        } else {
            showMessage = false;
        }
    }

    public void render(Graphics g) {
        imgSign.draw( x, y+50, width, height);
        if (showMessage) {
            g.setColor(Color.black);
            g.fillRect(this.getCenterX() - 200, y - 200, 620, 200);
            g.setColor(Color.white);
            g.drawRect(this.getCenterX() - 200, y - 200, 620, 200);
            g.drawString(message, this.getCenterX() - 190, y - 190);
        }

    }
}
