package ca.qc.bdeb.info204.spellington.gameentities;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;

/**
 * Main protegonist of the game.
 *
 * @author Fallen Angel
 */
public class Spellington extends GameEntity {

    private static Image IMG_SPELLINGTON;
    private static final float SPELLINGTON_SPEED = 0.5f;

    public Spellington() throws SlickException {
        IMG_SPELLINGTON = new Image("resources/images/spellington.jpg");
        this.setX(500);
        this.setY(500);

    }

    /**
     *
     * @param input
     * @param delta Delta of frame. To keep speed consistent regardless of frame
     * length.
     */
    public void update(Input input, int delta) {
        if (input.isKeyDown(Input.KEY_RIGHT) && input.isKeyDown(Input.KEY_LEFT)) {
            //If both buttons are pressed, the player does not move on the x axis.
        } else if (input.isKeyDown(Input.KEY_RIGHT)) {
            this.setX(this.getX() + SPELLINGTON_SPEED * delta);
        } else if (input.isKeyDown(Input.KEY_LEFT)) {
            this.setX(this.getX() - SPELLINGTON_SPEED * delta);
        }
        if (input.isKeyDown(Input.KEY_UP) && input.isKeyDown(Input.KEY_DOWN)) {
            //If both buttons are pressed, the player does not move on the y axis.
        } else if (input.isKeyDown(Input.KEY_UP)) {
            this.setY(this.getY() - SPELLINGTON_SPEED * delta);
        } else if (input.isKeyDown(Input.KEY_DOWN)) {
            this.setY(this.getY() + SPELLINGTON_SPEED * delta);
        }

    }

    public void render(Graphics g) {
        g.drawImage(IMG_SPELLINGTON, this.getX(), this.getY());
    }

}
