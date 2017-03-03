package ca.qc.bdeb.info204.spellington.gameentities;

import ca.qc.bdeb.info204.spellington.GameCore;
import ca.qc.bdeb.info204.spellington.calculations.Vector2D;
import ca.qc.bdeb.info204.spellington.gamestates.PlayState;
import java.awt.Dimension;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;

/**
 * A LivingEntity that will be controlled by the player.
 *
 * @author Cristian Aldea
 * @see LivingEntity
 */
public class Spellington extends LivingEntity {

    private static Image IMG_SPELLINGTON;
    //Temporairy code fo air jumps
    private int airJumps;
    private static final int MAX_AIR_JUMPS = 1;
    private static final float AIR_JUMP_POWER = 0.6f;

    private static final int INIT_MAX_LIFE = 100;
    private static final int MASS = 2;
    private static final float MAX_X_SPEED = 0.7f;
    private static final Vector2D X_ACC = new Vector2D(0.003f, 0);
    private static final Vector2D INIT_JUMP_SPEED = new Vector2D(0, -0.8f);
    //WJ : WallJump
    private static final float WJ_ANGLE = (float) Math.toRadians(60);
    //WJ : WallJump
    private static final Vector2D LEFT_WJ_INIT_SPEED = new Vector2D(INIT_JUMP_SPEED.getY() * (float) Math.cos(WJ_ANGLE), INIT_JUMP_SPEED.getY() * (float) Math.sin(WJ_ANGLE));
    private static final Vector2D RIGHT_WJ_INIT_SPEED = new Vector2D(-INIT_JUMP_SPEED.getY() * (float) Math.cos(WJ_ANGLE), INIT_JUMP_SPEED.getY() * (float) Math.sin(WJ_ANGLE));

    private static final Dimension SPELLINGTON_SIZE = new Dimension(45, 90);

    /**
     *
     * @param x
     * @param y
     * @param mouvementState
     * @throws SlickException
     */
    public Spellington(float x, float y,MouvementState mouvementState) throws SlickException {
        super(x, y, SPELLINGTON_SIZE.width, SPELLINGTON_SIZE.height, mouvementState);
        lifePoint = INIT_MAX_LIFE;

        resElectricity = 0;
        resIce = 0;
        resFire = 0;

        airJumps = MAX_AIR_JUMPS;
        IMG_SPELLINGTON = new Image("resources/images/spellington.png");

    }

    /**
     *
     * @param input
     * @param time Delta of frame. To keep speed consistent regardless of frame
     * length.
     */
    public void update(Input input, float time) {
        //On divize par SCALE pour match la position de la souris avec le scale du render
        float mouseX = (float) input.getMouseX() / GameCore.SCALE;
        float mouseY = (float) input.getMouseY() / GameCore.SCALE;
        //Using equation d = (vf^2 - vi^2)/2a
        float SLOWDOWN_DISTANCE = (this.getSpeedVector().getX() * this.getSpeedVector().getX()) / (2.0f * X_ACC.getX());
        //Correction of speed according to collision state
        if (this.getCollisionBottom() || this.getCollisionTop()) {
            this.getSpeedVector().setY(0);

        }
        if (this.getCollisionRight() || this.getCollisionLeft()) {
            this.getSpeedVector().setX(0);
        }

        //General handling of mouvement in x for spellington
        if (input.isMouseButtonDown(Input.MOUSE_LEFT_BUTTON) && Math.abs(mouseX - this.getCenterX()) <= Math.abs(this.getSpeedVector().getX() * time)) {
            this.getSpeedVector().setX(0);
            this.setCenterX(mouseX);
        } else if (input.isMouseButtonDown(Input.MOUSE_LEFT_BUTTON) && Math.abs(mouseX - this.getCenterX()) <= SLOWDOWN_DISTANCE) {
            if (mouseX > this.getCenterX()) {
                this.getSpeedVector().sub(Vector2D.multVectorScalar(X_ACC, time));
                if (this.getSpeedVector().getX() < Vector2D.multVectorScalar(X_ACC, time).getX()) {
                    this.getSpeedVector().setX(0);
                }
            } else if (mouseX < this.getCenterX()) {
                this.getSpeedVector().add(Vector2D.multVectorScalar(X_ACC, time));
                if (this.getSpeedVector().getX() > -Vector2D.multVectorScalar(X_ACC, time).getX()) {
                    this.getSpeedVector().setX(0);
                }
            }
        } else if (input.isMouseButtonDown(Input.MOUSE_LEFT_BUTTON) && mouseX > this.getCenterX()) {
            this.getSpeedVector().add(Vector2D.multVectorScalar(X_ACC, time));
            if (this.getSpeedVector().getX() > MAX_X_SPEED) {
                this.getSpeedVector().setX(MAX_X_SPEED);
            }
        } else if (input.isMouseButtonDown(Input.MOUSE_LEFT_BUTTON) && mouseX < this.getCenterX()) {
            this.getSpeedVector().sub(Vector2D.multVectorScalar(X_ACC, time));
            if (this.getSpeedVector().getX() < -MAX_X_SPEED) {
                this.getSpeedVector().setX(-MAX_X_SPEED);
            }
        } else if (this.getSpeedVector().getX() > 0) {
            this.getSpeedVector().sub(Vector2D.multVectorScalar(X_ACC, time));
            if (this.getSpeedVector().getX() < Vector2D.multVectorScalar(X_ACC, time).getX()) {
                this.getSpeedVector().setX(0);
            }
        } else if (this.getSpeedVector().getX() < 0) {
            this.getSpeedVector().add(Vector2D.multVectorScalar(X_ACC, time));
            if (this.getSpeedVector().getX() > -Vector2D.multVectorScalar(X_ACC, time).getX()) {
                this.getSpeedVector().setX(0);
            }
        }

        //Temporairy boolean because of a problem with isMousePressed.
        boolean triedToJump = false;
        if (input.isMousePressed(Input.MOUSE_RIGHT_BUTTON)) {
            triedToJump = true;
        }
        //Jumping

        if (collisionBottom) {
            airJumps = MAX_AIR_JUMPS;
        }
        if (triedToJump && collisionBottom) {
            this.getSpeedVector().setY(INIT_JUMP_SPEED.getY());
        } else if (triedToJump && collisionLeft && !collisionBottom) {
            this.getSpeedVector().set(RIGHT_WJ_INIT_SPEED);
        } else if (triedToJump && collisionRight && !collisionBottom) {
            this.getSpeedVector().set(LEFT_WJ_INIT_SPEED);
        } else if (triedToJump && !collisionBottom && !collisionLeft
                && !collisionLeft && airJumps > 0) {
            this.getSpeedVector().setY(INIT_JUMP_SPEED.getY() * AIR_JUMP_POWER);
            airJumps--;
        }

        this.getSpeedVector().add(Vector2D.multVectorScalar(PlayState.GRAV_FORCE, time * MASS));
        this.setX(this.getX() + this.getSpeedVector().getX() * time);
        this.setY(this.getY() + this.getSpeedVector().getY() * time);
        //Reset collision for the next frame
        this.resetCollisionState();
    }

    public void render(Graphics g) {
        g.drawImage(IMG_SPELLINGTON, this.getX() - 68, this.getY() - 10);
        g.drawRect(x, y, SPELLINGTON_SIZE.width, SPELLINGTON_SIZE.height);
    }

}
