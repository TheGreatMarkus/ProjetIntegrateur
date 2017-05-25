package ca.qc.bdeb.info204.spellington.gameentities;

import ca.qc.bdeb.info204.spellington.GameCore;
import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

/**
 * A StaticEntity that will act as a message giver to the player.
 *
 * @author Cristian Aldea
 * @see StaticEntity
 */
public class MessageSign extends StaticEntity {

    private String message;
    private Image imgSign;
    private boolean showMessage;

    public MessageSign(float x, float y, float width, float height, String message) {
        super(x, y, width, height);
        this.message = message;

        try {
            imgSign = new Image("res/image/messageSign.png");
        } catch (SlickException se) {
            System.out.println("Erreur, image de pancarte introuvable.");
        }

    }

    public void update(Spellington spellington) {
        if (spellington.getBounds().intersects(this.getBounds())) {
            showMessage = true;
        } else {
            showMessage = false;
        }
    }

    public void render(Graphics g) {
        imgSign.draw(x, y + 50, width, height);
        if (showMessage) {
            float tempWidth = 560 * GameCore.SCALE;
            g.setColor(Color.black);
            g.scale(1f / GameCore.SCALE, 1f / GameCore.SCALE);
            g.fillRect(this.getCenterX() * GameCore.SCALE - tempWidth / 2f, y - 200, tempWidth, 230);
            g.setColor(Color.white);
            g.drawRect(this.getCenterX() * GameCore.SCALE - tempWidth / 2f, y - 200, tempWidth, 230);
            g.drawString(message, this.getCenterX() * GameCore.SCALE - tempWidth / 2f + 10f * GameCore.SCALE, y - 190);
            g.scale(GameCore.SCALE, GameCore.SCALE);
        }

    }
}
