package ca.qc.bdeb.info204.spellington.gameentities;

import ca.qc.bdeb.info204.spellington.GameCore;
import ca.qc.bdeb.info204.spellington.calculations.Vector2D;
import ca.qc.bdeb.info204.spellington.gamestates.PlayState;
import java.awt.Dimension;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.newdawn.slick.Animation;
import org.newdawn.slick.Color;
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

    private static Image imgJumpL;
    private static Image imgJumpR;
    private static Image imgStandingL;
    private static Image imgStandingR;
    private static Image imgWallL;
    private static Image imgWallR;
    private static Animation animWalkL;
    private static Animation animWalkR;

    //Temporairy code fo air jumps
    private int airJumps;
    private int MAX_AIR_JUMPS = 1;
    private static final float AIR_JUMP_POWER = 0.6f;

    public static final int INIT_MAX_LIFE = 100;
    private static final float GRAVITY_MODIFIER = 2;

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
    public Spellington(float x, float y, MouvementState mouvementState) throws SlickException {
        super(x, y, SPELLINGTON_SIZE.width, SPELLINGTON_SIZE.height, mouvementState, GRAVITY_MODIFIER, INIT_MAX_LIFE);
        initAnimation();

        resElectricity = 0;
        resIce = 0;
        resFire = 0;

        airJumps = MAX_AIR_JUMPS;

    }

    /**
     *
     * @param input
     * @param time Delta of frame. To keep speed consistent regardless of frame
     * length.
     */
    public void update(Input input, float time) {
        //To slowdown time for testing purposes
        //time *= 0.5;
        //On divize par SCALE pour match la position de la souris avec le scale du render
        float mouseX = (float) input.getMouseX() / GameCore.SCALE;
        float mouseY = (float) input.getMouseY() / GameCore.SCALE;
        //Using equation d = (vf^2 - vi^2)/2a. Distance from where Spellington should stop when approching the mouse
        float SLOWDOWN_DISTANCE = (this.speedVector.getX() * this.speedVector.getX()) / (2.0f * X_ACC.getX());

        //Correction of speed according to collision state
        if (this.collisionBottom || this.collisionTop) {
            this.speedVector.setY(0);
        }
        if (this.collisionRight || this.collisionLeft) {
            this.speedVector.setX(0);
        }

        //General handling of mouvement in x for spellington
        if (input.isMouseButtonDown(Input.MOUSE_LEFT_BUTTON) && Math.abs(mouseX - this.getCenterX()) <= Math.abs(this.speedVector.getX() * time)) {
            this.setCenterX(mouseX);
            this.speedVector.setX(0);
        } else if (input.isMouseButtonDown(Input.MOUSE_LEFT_BUTTON) && Math.abs(mouseX - this.getCenterX()) <= SLOWDOWN_DISTANCE) {
            if (mouseX > this.getCenterX() && this.speedVector.getX() > 0) {
                this.speedVector.sub(Vector2D.multVectorScalar(X_ACC, time));

            } else if (mouseX < this.getCenterX() && this.speedVector.getX() < 0) {
                this.speedVector.add(Vector2D.multVectorScalar(X_ACC, time));
            }
        } else if (input.isMouseButtonDown(Input.MOUSE_LEFT_BUTTON) && mouseX > this.getCenterX()) {
            this.speedVector.add(Vector2D.multVectorScalar(X_ACC, time));
            if (this.speedVector.getX() > MAX_X_SPEED) {
                this.speedVector.setX(MAX_X_SPEED);
            }
        } else if (input.isMouseButtonDown(Input.MOUSE_LEFT_BUTTON) && mouseX < this.getCenterX()) {
            this.speedVector.sub(Vector2D.multVectorScalar(X_ACC, time));
            if (this.speedVector.getX() < -MAX_X_SPEED) {
                this.speedVector.setX(-MAX_X_SPEED);
            }
        } else if (!input.isMouseButtonDown(Input.MOUSE_LEFT_BUTTON) && this.speedVector.getX() > 0) {
            this.speedVector.sub(Vector2D.multVectorScalar(X_ACC, time));
            if (this.speedVector.getX() < Vector2D.multVectorScalar(X_ACC, time).getX()) {
                this.speedVector.setX(0);
            }
        } else if (!input.isMouseButtonDown(Input.MOUSE_LEFT_BUTTON) && this.speedVector.getX() < 0) {
            this.speedVector.add(Vector2D.multVectorScalar(X_ACC, time));
            if (this.speedVector.getX() > -Vector2D.multVectorScalar(X_ACC, time).getX()) {
                this.speedVector.setX(0);
            }
        }

        //Temporary boolean because of a problem with isMousePressed.
        boolean triedToJump = false;
        if (input.isMousePressed(Input.MOUSE_RIGHT_BUTTON)) {
            triedToJump = true;
        }
        //Jumping

        if (collisionBottom) {
            airJumps = MAX_AIR_JUMPS;
        }
        if (triedToJump && collisionBottom) {
            this.speedVector.setY(INIT_JUMP_SPEED.getY());
        } else if (triedToJump && collisionLeft && !collisionBottom) {
            this.speedVector.set(RIGHT_WJ_INIT_SPEED);
        } else if (triedToJump && collisionRight && !collisionBottom) {
            this.speedVector.set(LEFT_WJ_INIT_SPEED);
        } else if (triedToJump && !collisionBottom && !collisionLeft
                && !collisionLeft && airJumps > 0) {
            this.speedVector.setY(INIT_JUMP_SPEED.getY() * AIR_JUMP_POWER);
            airJumps--;
        }
        this.setMouvementState(detMouvementState(input, mouseX));
        this.speedVector.add(Vector2D.multVectorScalar(PlayState.GRAV_ACC, time * GRAVITY_MODIFIER));
        this.setX(this.getX() + this.speedVector.getX() * time);
        this.setY(this.getY() + this.speedVector.getY() * time);
        //Reset collision for the next frame
        this.resetCollisionState();
    }

    public void render(Graphics g) {
        float tempX = x - 68;
        float tempY = y - 10;
        float tempWidth = 178;
        float tempHeight = 100;
        switch (this.getMouvementState()) {
            case STANDING_L:
                imgStandingL.draw(tempX, tempY, tempWidth, tempHeight);
                break;
            case STANDING_R:
                imgStandingR.draw(tempX, tempY, tempWidth, tempHeight);
                break;
            case JUMP_L:
                imgJumpL.draw(tempX, tempY, tempWidth, tempHeight);
                break;
            case JUMP_R:
                imgJumpR.draw(tempX, tempY, tempWidth, tempHeight);
                break;
            case WALL_L:
                imgWallL.draw(tempX, tempY, tempWidth, tempHeight);
                break;
            case WALL_R:
                imgWallR.draw(tempX, tempY, tempWidth, tempHeight);
                break;
            case WALKING_L:
                animWalkL.draw(tempX, tempY, tempWidth, tempHeight);
                break;
            case WALKING_R:
                animWalkR.draw(tempX, tempY, tempWidth, tempHeight);
                break;
        }
        if (PlayState.debugMode) {
            g.drawRect(x, y, SPELLINGTON_SIZE.width, SPELLINGTON_SIZE.height);
            g.setColor(Color.red);
            g.drawRect(tempX, tempY, tempWidth, tempHeight);
        }
    }

    private void initAnimation() {
        try {
            imgJumpL = new Image("res/image/animation/spellington/jump_l.png");
            imgJumpR = new Image("res/image/animation/spellington/jump_r.png");
            imgStandingL = new Image("res/image/animation/spellington/standing_l.png");
            imgStandingR = new Image("res/image/animation/spellington/standing_r.png");
            imgWallL = new Image("res/image/animation/spellington/wall_l.png");
            imgWallR = new Image("res/image/animation/spellington/wall_r.png");

            Image[] tempImgWalkL = new Image[40];
            for (int i = 0; i < tempImgWalkL.length; i++) {
                tempImgWalkL[i] = new Image("res/image/animation/spellington/walk_l/" + "walk_l (" + (i + 1) + ")" + ".png");
            }

            Image[] tempImgWalkR = new Image[40];
            for (int i = 0; i < tempImgWalkR.length; i++) {
                tempImgWalkR[i] = new Image("res/image/animation/spellington/walk_r/" + "walk_r (" + (i + 1) + ")" + ".png");
            }

            animWalkL = new Animation(tempImgWalkL, 15);
            animWalkR = new Animation(tempImgWalkR, 15);
        } catch (SlickException ex) {
            Logger.getLogger(Spellington.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private MouvementState detMouvementState(Input input, float mouseX) {
        if (!collisionBottom && collisionLeft) {
            return MouvementState.WALL_L;
        } else if (!collisionBottom && collisionRight) {
            return MouvementState.WALL_R;
        }

        if (input.isMouseButtonDown(Input.MOUSE_LEFT_BUTTON)) {
            if (mouseX < this.getCenterX()) {
                if (collisionBottom) {
                    return MouvementState.WALKING_L;
                } else {
                    return MouvementState.JUMP_L;
                }

            } else if (mouseX > this.getCenterX()) {
                if (collisionBottom) {
                    return MouvementState.WALKING_R;
                } else {
                    return MouvementState.JUMP_R;
                }
            } else if (collisionBottom && mouvementState == MouvementState.WALKING_L) {
                return MouvementState.STANDING_L;
            } else if (collisionBottom && mouvementState == MouvementState.WALKING_R) {
                return MouvementState.STANDING_R;
            } else if (!collisionBottom && mouvementState == MouvementState.JUMP_L) {
                return MouvementState.JUMP_L;
            } else if (!collisionBottom && mouvementState == MouvementState.JUMP_R) {
                return MouvementState.JUMP_R;
            } else if (!collisionBottom && mouvementState == MouvementState.STANDING_L) {
                return MouvementState.JUMP_L;
            } else if (!collisionBottom && mouvementState == MouvementState.STANDING_R) {
                return MouvementState.JUMP_R;
            } else if (collisionBottom && mouvementState == MouvementState.JUMP_L) {
                return MouvementState.STANDING_L;
            } else if (collisionBottom && mouvementState == MouvementState.JUMP_R) {
                return MouvementState.STANDING_R;
            }
        } else if (collisionBottom && mouvementState == MouvementState.WALKING_L) {
            return MouvementState.STANDING_L;
        } else if (collisionBottom && mouvementState == MouvementState.WALKING_R) {
            return MouvementState.STANDING_R;
        } else if (!collisionBottom && mouvementState == MouvementState.JUMP_L) {
            return MouvementState.JUMP_L;
        } else if (!collisionBottom && mouvementState == MouvementState.JUMP_R) {
            return MouvementState.JUMP_R;
        } else if (!collisionBottom && mouvementState == MouvementState.STANDING_L) {
            return MouvementState.JUMP_L;
        } else if (!collisionBottom && mouvementState == MouvementState.STANDING_R) {
            return MouvementState.JUMP_R;
        } else if (!collisionBottom && mouvementState == MouvementState.WALL_L) {
            return MouvementState.JUMP_L;
        } else if (!collisionBottom && mouvementState == MouvementState.WALL_R) {
            return MouvementState.JUMP_R;
        } else if (collisionBottom && mouvementState == MouvementState.JUMP_L) {
            return MouvementState.STANDING_L;
        } else if (collisionBottom && mouvementState == MouvementState.JUMP_R) {
            return MouvementState.STANDING_R;
        } else if (collisionBottom && mouvementState == MouvementState.WALL_L) {
            return MouvementState.STANDING_L;
        } else if (collisionBottom && mouvementState == MouvementState.WALL_R) {
            return MouvementState.STANDING_R;
        }
        if (mouvementState == MouvementState.STANDING_L) {
            return MouvementState.STANDING_L;
        }
        if (mouvementState == MouvementState.STANDING_R) {
            return MouvementState.STANDING_R;
        }

        return MouvementState.WALL_L;
    }

    public int getMAX_AIR_JUMPS() {
        return MAX_AIR_JUMPS;
    }

    public void setMAX_AIR_JUMPS(int MAX_AIR_JUMPS) {
        this.MAX_AIR_JUMPS = MAX_AIR_JUMPS;
    }

}
