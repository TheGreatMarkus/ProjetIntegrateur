package ca.qc.bdeb.info204.spellington.gameentities;

import java.awt.Dimension;
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
    private static final float SPELLINGTON_NORMAL_ACC = 0.5f; //Possibilité de changer ceci
    private static final float SPELLINGTON_MAX_SPEED = 0.5f;

    public static final Dimension SPELLINGTON_SIZE = new Dimension(50, 100);

    /**
     *
     * @param x
     * @param y
     * @throws SlickException
     */
    public Spellington(float x, float y) throws SlickException {
        super(x, y, SPELLINGTON_SIZE.width, SPELLINGTON_SIZE.height);
        lifePoint = SPELLINGTON_INITIAL_MAX_LIFE;
        xSpeed = 0;
        ySpeed = 0;

        resElectricity = 0;
        resIce = 0;
        resFire = 0;

        IMG_SPELLINGTON = new Image("resources/images/spellington.png");

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

            this.setxSpeed(this.getxSpeed() + SPELLINGTON_NORMAL_ACC);
            if (this.getxSpeed() > SPELLINGTON_MAX_SPEED) {
                this.setxSpeed(SPELLINGTON_MAX_SPEED);
            }
        } else if (input.isKeyDown(Input.KEY_LEFT)) {
            this.setxSpeed(this.getxSpeed() - SPELLINGTON_NORMAL_ACC);
            if (this.getxSpeed() < -SPELLINGTON_MAX_SPEED) {
                this.setxSpeed(-SPELLINGTON_MAX_SPEED);
            }
        } else {
            this.setxSpeed(0);
        }
        if (input.isKeyDown(Input.KEY_UP) && input.isKeyDown(Input.KEY_DOWN)) { // à changer pour pas voler

        } else if (input.isKeyDown(Input.KEY_UP)) {
            this.setySpeed(this.getySpeed() - SPELLINGTON_NORMAL_ACC);
            if (this.getySpeed() < -SPELLINGTON_MAX_SPEED) {
                this.setySpeed(-SPELLINGTON_MAX_SPEED);
            }
        } else if (input.isKeyDown(Input.KEY_DOWN)) {
            this.setySpeed(this.getySpeed() + SPELLINGTON_NORMAL_ACC);
            if (this.getySpeed() > SPELLINGTON_MAX_SPEED) {
                this.setySpeed(SPELLINGTON_MAX_SPEED);
            }
        } else {
            this.setySpeed(0);

        }
//        if (this.getCollisionTop() || this.getCollisionBottom()) {
//            this.setySpeed(0);
//        }
//        
//        if (this.getCollisionRight() || this.getCollisionLeft()) {
//            this.setxSpeed(0);
//        }
        this.setX(this.getX() + this.getxSpeed() * delta);
        this.setY(this.getY() + this.getySpeed() * delta);
    }

    public void render(Graphics g) {
        //g.drawImage(IMG_SPELLINGTON, this.getX(), this.getY());
        g.drawRect(x, y, 50, 100);
    }

    

}
