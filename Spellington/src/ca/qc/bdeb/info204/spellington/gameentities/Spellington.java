package ca.qc.bdeb.info204.spellington.gameentities;

import ca.qc.bdeb.info204.spellington.gamestates.PlayState;
import java.awt.Dimension;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;

/**
 * Main protegonist of the game.
 *
 * @author Fallen Angel
 */
public class Spellington extends LivingEntity {

    private static Image IMG_SPELLINGTON;

    private static final int SPELLINGTON_INITIAL_MAX_LIFE = 100;
    private static final float SPELLINGTON_NORMAL_ACC = 0.1f; //Possibilité de changer ceci
    private static final float SPELLINGTON_MAX_SPEED = 0.3f;
    private static final float SPELLINGTON_JUMP_SPEED = -1.0f;

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
        this.setySpeed(this.getySpeed() + PlayState.GRAVITY);

        if (this.getCollisionBottom()) {
            this.setySpeed(0);
        }
        if (this.getCollisionRight() || this.getCollisionLeft()) {
            this.setxSpeed(0);
        }
        if (this.getCollisionRight() && input.isKeyDown(Input.KEY_RIGHT)
                || this.getCollisionLeft() && input.isKeyDown(Input.KEY_LEFT)) {
            this.setySpeed(0);
        }

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

        if (input.isKeyDown(Input.KEY_UP) && collisionBottom) {
            this.setySpeed(SPELLINGTON_JUMP_SPEED);
        }
        if (input.isKeyDown(Input.KEY_UP) && collisionLeft && !collisionBottom) {
            this.setxSpeed((float) Math.cos(Math.toRadians(45)) * -SPELLINGTON_JUMP_SPEED);
            this.setySpeed((float) Math.sin(Math.toRadians(45)) * SPELLINGTON_JUMP_SPEED);
        }
        if (input.isKeyDown(Input.KEY_UP) && collisionRight && !collisionBottom) {
            this.setxSpeed((float) Math.cos(Math.toRadians(45)) * SPELLINGTON_JUMP_SPEED);
            this.setySpeed((float) Math.sin(Math.toRadians(45)) * SPELLINGTON_JUMP_SPEED);
        }

        this.setX(this.getX() + this.getxSpeed() * delta);
        this.setY(this.getY() + this.getySpeed() * delta);
    }

    public void render(Graphics g) {
        //g.drawImage(IMG_SPELLINGTON, this.getX(), this.getY());
        g.fillRect(x, y, 50, 100);
    }

}
