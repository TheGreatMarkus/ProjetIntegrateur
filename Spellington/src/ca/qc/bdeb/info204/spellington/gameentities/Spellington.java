package ca.qc.bdeb.info204.spellington.gameentities;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Rectangle;

/**
 * Main protegonist of the game.
 *
 * @author Fallen Angel
 */
public class Spellington extends LivingEntity {

    private static Image IMG_SPELLINGTON;

    private static final int SPELLINGTON_INITIAL_MAX_LIFE = 100;
    private static final float SPELLINGTON_NORMAL_SPEED = 0.5f;

    public Spellington() throws SlickException {
        lifePoint = SPELLINGTON_INITIAL_MAX_LIFE;

        resElectric = 0;
        resIce = 0;
        resFire = 0;

        movementSpeed = SPELLINGTON_NORMAL_SPEED;

        IMG_SPELLINGTON = new Image("resources/images/spellington.png");
        x = 500;
        y = 500;
        width = 50;
        height = 100;

    }

    /**
     *
     * @param input
     * @param delta Delta of frame. To keep speed consistent regardless of frame
     * length.
     */
    public void update(Input input, int delta) {
        if (input.isKeyDown(Input.KEY_RIGHT) && input.isKeyDown(Input.KEY_LEFT)) {// à changer devrais suivre la souris

        } else if (input.isKeyDown(Input.KEY_RIGHT)) {
            this.setX(this.getX() + movementSpeed * delta);
        } else if (input.isKeyDown(Input.KEY_LEFT)) {
            this.setX(this.getX() - movementSpeed * delta);
        }
        if (input.isKeyDown(Input.KEY_UP) && input.isKeyDown(Input.KEY_DOWN)) { // à changer pour pas voler

        } else if (input.isKeyDown(Input.KEY_UP)) {
            this.setY(this.getY() - movementSpeed * delta);
        } else if (input.isKeyDown(Input.KEY_DOWN)) {
            this.setY(this.getY() + movementSpeed * delta);
        }

    }

    public void render(Graphics g) {
        //g.drawImage(IMG_SPELLINGTON, this.getX(), this.getY());
        g.drawRect(x, y, 50, 100);
    }

    public Rectangle getBounds() {
        return new Rectangle(x, y, width, height);
    }

}
