package ca.qc.bdeb.info204.spellington.gameentities;

import ca.qc.bdeb.info204.spellington.GameCore;
import ca.qc.bdeb.info204.spellington.gamestates.PlayState;
import java.awt.Dimension;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;

/**
 * Main protegonist of the game.
 *
 * @author Cristian Aldea
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
        //On divize par SCALE pour match la position de la souris avec le scale du render
        float mouseX = (float) input.getMouseX() / GameCore.SCALE;
        float mouseY = (float) input.getMouseY() / GameCore.SCALE;

        this.setySpeed(this.getySpeed() + PlayState.GRAVITY);

        if (this.getCollisionBottom()) {
            this.setySpeed(0);

        }
        if (this.getCollisionRight() || this.getCollisionLeft()) {
            this.setxSpeed(0);
        }
        if (getySpeed() > 0) {
            if (this.getCollisionRight() && input.isMouseButtonDown(Input.MOUSE_LEFT_BUTTON) && mouseX > this.getCenterX()
                    || this.getCollisionLeft() && input.isMouseButtonDown(Input.MOUSE_LEFT_BUTTON) && mouseX < this.getCenterX()) {
                this.setySpeed(0);
            }
        }

        if (input.isMouseButtonDown(Input.MOUSE_LEFT_BUTTON)
                && mouseX > this.getCenterX()) {
            this.setxSpeed(this.getxSpeed() + SPELLINGTON_NORMAL_ACC);
            if (this.getxSpeed() > SPELLINGTON_MAX_SPEED) {
                this.setxSpeed(SPELLINGTON_MAX_SPEED);
            }
        } else if (input.isMouseButtonDown(Input.MOUSE_LEFT_BUTTON)
                && mouseX < this.getCenterX()) {
            this.setxSpeed(this.getxSpeed() - SPELLINGTON_NORMAL_ACC);
            if (this.getxSpeed() < -SPELLINGTON_MAX_SPEED) {
                this.setxSpeed(-SPELLINGTON_MAX_SPEED);
            }
        } else {
            this.setxSpeed(0);
        }
        boolean triedToJump = false;
        if (input.isMousePressed(Input.MOUSE_RIGHT_BUTTON)) {
            triedToJump = true;
        }

        if (triedToJump && collisionBottom) {
            this.setySpeed(SPELLINGTON_JUMP_SPEED);
        } else if (triedToJump && collisionLeft && !collisionBottom) {

            this.setxSpeed((float) Math.cos(Math.toRadians(45)) * -SPELLINGTON_JUMP_SPEED);
            this.setySpeed((float) Math.sin(Math.toRadians(45)) * SPELLINGTON_JUMP_SPEED);
        } else if (triedToJump && collisionRight && !collisionBottom) {
            this.setxSpeed((float) Math.cos(Math.toRadians(45)) * SPELLINGTON_JUMP_SPEED);
            this.setySpeed((float) Math.sin(Math.toRadians(45)) * SPELLINGTON_JUMP_SPEED);
        }

        this.setX(this.getX() + this.getxSpeed() * delta);
        this.setY(this.getY() + this.getySpeed() * delta);
        this.resetCollisionState();
    }

    public void render(Graphics g) {
        //g.drawImage(IMG_SPELLINGTON, this.getX(), this.getY());
        g.fillRect(x, y, 50, 100);
    }

}
