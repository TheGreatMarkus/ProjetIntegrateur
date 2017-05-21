package ca.qc.bdeb.info204.spellington.gameentities;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;

/**
 *
 * @author Fallen Angel
 */
public class MessageSign extends StaticEntity {

    private String message;
    private Image imgSign;
    private boolean showMessage;

    public MessageSign(float x, float y, float width, float height, String message) {
        super(x, y, width, height);
        this.message = message;

    }

    public void update(Spellington spellington) {
        if (spellington.getBounds().intersects(this.getBounds())) {
            showMessage = true;
        } else {
            showMessage = false;
        }
    }

    public void render(Graphics g) {
        //Draw a temp sign, will be using an image after
        g.setColor(Color.white);
        g.drawRect(x - 20, y, width + 40, height);
        g.drawRect(x + 20, y + 50, width - 40, height);
        g.drawString("SIGN", x + 7, y + 15);
        if (showMessage) {
            g.setColor(Color.black);
            g.fillRect(this.getCenterX() - 100, y - 200, 200, 100);
            g.setColor(Color.white);
            g.drawRect(this.getCenterX() - 100, y - 200, 200, 100);
            g.drawString(message, this.getCenterX() - 40, y - 190);
        }

    }
}
