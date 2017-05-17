package ca.qc.bdeb.info204.spellington.gameentities;

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

    private static final float AIR_JUMP_POWER = 0.8f;

    public static final int INIT_MAX_LIFE = 100;
    private static final float GRAVITY_MODIFIER = 2;
    private static final float MAX_X_SPEED = 0.5f;
    private static final Vector2D X_ACC = new Vector2D(0.003f, 0);
    private static final float INIT_JUMP_SPEED = -0.8f;

    //WJ : WallJump
    private static final float WJ_ANGLE = (float) Math.toRadians(65);
    private static final Vector2D LEFT_WJ_INIT_SPEED = new Vector2D(INIT_JUMP_SPEED * (float) Math.cos(WJ_ANGLE), INIT_JUMP_SPEED * (float) Math.sin(WJ_ANGLE));
    private static final Vector2D RIGHT_WJ_INIT_SPEED = new Vector2D(-INIT_JUMP_SPEED * (float) Math.cos(WJ_ANGLE), INIT_JUMP_SPEED * (float) Math.sin(WJ_ANGLE));

    private static final Dimension SPELLINGTON_SIZE = new Dimension(45, 90);

    private int airJumps;
    private int maxAirJumps = 1;

    public Spellington(float x, float y, AnimState mouvementState) throws SlickException {
        super(x, y, SPELLINGTON_SIZE.width, SPELLINGTON_SIZE.height, mouvementState, GRAVITY_MODIFIER, INIT_MAX_LIFE);
        initAnimation();

        resElectricity = 0;
        resIce = 0;
        resFire = 0;

        airJumps = maxAirJumps;

    }

    /**
     * Updates the position of Spellington
     *
     * @param input The input Class that manages the input for the program.
     * @param time Duration of frame.
     */
    public void update(Input input, float time) {
        if (this.collisionBottom || this.collisionTop) {
            this.speedVector.setY(0);
        }
        if (this.collisionRight || this.collisionLeft) {
            this.speedVector.setX(0);
        }

//        On divise par scale pour match la position de la souris avec le scale du render
//        float mouseX = (float) input.getMouseX() / GameCore.scale;
//        float mouseY = (float) input.getMouseY() / GameCore.scale;
//        //Using equation d = (vf^2 - vi^2)/2a. Distance from where Spellington should stop when approching the mouse
//        float SLOWDOWN_DISTANCE = (this.speedVector.getX() * this.speedVector.getX()) / (2.0f * X_ACC.getX());
//        Correction of speed according to collision state
//        General handling of mouvement in x for spellington
//        if (input.isMouseButtonDown(Input.MOUSE_LEFT_BUTTON) && Math.abs(mouseX - this.getCenterX()) <= Math.abs(this.speedVector.getX() * time)) {
//            this.setCenterX(mouseX);
//            this.speedVector.setX(0);
//        } else if (input.isMouseButtonDown(Input.MOUSE_LEFT_BUTTON) && Math.abs(mouseX - this.getCenterX()) <= SLOWDOWN_DISTANCE) {
//            if (mouseX > this.getCenterX() && this.speedVector.getX() > 0) {
//                this.speedVector.sub(Vector2D.multVectorScalar(X_ACC, time));
//
//            } else if (mouseX < this.getCenterX() && this.speedVector.getX() < 0) {
//                this.speedVector.add(Vector2D.multVectorScalar(X_ACC, time));
//            }
//        } else if (input.isMouseButtonDown(Input.MOUSE_LEFT_BUTTON) && mouseX > this.getCenterX()) {
//            this.speedVector.add(Vector2D.multVectorScalar(X_ACC, time));
//            if (this.speedVector.getX() > MAX_X_SPEED) {
//                this.speedVector.setX(MAX_X_SPEED);
//            }
//        } else if (input.isMouseButtonDown(Input.MOUSE_LEFT_BUTTON) && mouseX < this.getCenterX()) {
//            this.speedVector.sub(Vector2D.multVectorScalar(X_ACC, time));
//            if (this.speedVector.getX() < -MAX_X_SPEED) {
//                this.speedVector.setX(-MAX_X_SPEED);
//            }
//        } else if (!input.isMouseButtonDown(Input.MOUSE_LEFT_BUTTON) && this.speedVector.getX() > 0) {
//            this.speedVector.sub(Vector2D.multVectorScalar(X_ACC, time));
//            if (this.speedVector.getX() < Vector2D.multVectorScalar(X_ACC, time).getX()) {
//                this.speedVector.setX(0);
//            }
//        } else if (!input.isMouseButtonDown(Input.MOUSE_LEFT_BUTTON) && this.speedVector.getX() < 0) {
//            this.speedVector.add(Vector2D.multVectorScalar(X_ACC, time));
//            if (this.speedVector.getX() > -Vector2D.multVectorScalar(X_ACC, time).getX()) {
//                this.speedVector.setX(0);
//            }
//        }
        if (input.isKeyDown(Input.KEY_RIGHT) && input.isKeyDown(Input.KEY_LEFT)) {
            if (this.speedVector.getX() > 0) {
                this.speedVector.sub(Vector2D.multVectorScalar(X_ACC, time));
                if (this.speedVector.getX() < Vector2D.multVectorScalar(X_ACC, time).getX()) {
                    this.speedVector.setX(0);
                }
            } else if (this.speedVector.getX() < 0) {
                this.speedVector.add(Vector2D.multVectorScalar(X_ACC, time));
                if (this.speedVector.getX() > -Vector2D.multVectorScalar(X_ACC, time).getX()) {
                    this.speedVector.setX(0);
                }
            }
        } else if (input.isKeyDown(Input.KEY_RIGHT)) {
            this.speedVector.add(Vector2D.multVectorScalar(X_ACC, time));
            if (this.speedVector.getX() > MAX_X_SPEED) {
                this.speedVector.setX(MAX_X_SPEED);
            }
        } else if (input.isKeyDown(Input.KEY_LEFT)) {
            this.speedVector.sub(Vector2D.multVectorScalar(X_ACC, time));
            if (this.speedVector.getX() < -MAX_X_SPEED) {
                this.speedVector.setX(-MAX_X_SPEED);
            }
        } else if (!input.isKeyDown(Input.KEY_RIGHT) && !input.isKeyDown(Input.KEY_LEFT) && this.speedVector.getX() > 0) {
            this.speedVector.sub(Vector2D.multVectorScalar(X_ACC, time));
            if (this.speedVector.getX() < Vector2D.multVectorScalar(X_ACC, time).getX()) {
                this.speedVector.setX(0);
            }
        } else if (!input.isKeyDown(Input.KEY_RIGHT) && !input.isKeyDown(Input.KEY_LEFT) && this.speedVector.getX() < 0) {
            this.speedVector.add(Vector2D.multVectorScalar(X_ACC, time));
            if (this.speedVector.getX() > -Vector2D.multVectorScalar(X_ACC, time).getX()) {
                this.speedVector.setX(0);
            }
        }

        //Temporary boolean because of a problem with isMousePressed.
        boolean triedToJump = false;

        if (input.isKeyPressed(Input.KEY_UP)) {
            triedToJump = true;
        }
        //Jumping

        if (collisionBottom) {
            airJumps = maxAirJumps;
        }
        if (triedToJump && collisionBottom) {
            this.speedVector.setY(INIT_JUMP_SPEED);
        } else if (triedToJump && collisionLeft && !collisionBottom) {
            this.speedVector.set(RIGHT_WJ_INIT_SPEED);
        } else if (triedToJump && collisionRight && !collisionBottom) {
            this.speedVector.set(LEFT_WJ_INIT_SPEED);
        } else if (triedToJump && !collisionBottom && !collisionLeft
                && !collisionLeft && airJumps
                > 0) {
            this.speedVector.setY(INIT_JUMP_SPEED * AIR_JUMP_POWER);
            airJumps--;
        }

        this.setAnimState(detMouvementState(input));
        this.speedVector.add(Vector2D.multVectorScalar(PlayState.GRAV_ACC, time * GRAVITY_MODIFIER));
        this.setX(this.x + this.speedVector.getX() * time);
        this.setY(this.y + this.speedVector.getY() * time);
        //Reset collision for the next frame

        this.resetCollisionState();
    }

    /**
     * Draws Spellington on the screen.
     *
     * @param g The Graphics component.
     */
    public void render(Graphics g) {
        float tempX = x - 68;
        float tempY = y - 10;
        float tempWidth = 178;
        float tempHeight = 100;
        switch (this.getAnimState()) {
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
                imgWallL.draw(tempX - 2, tempY, tempWidth, tempHeight);
                break;
            case WALL_R:
                imgWallR.draw(tempX + 3, tempY, tempWidth, tempHeight);
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

    /**
     * Loads the animations for Spellington.
     */
    private void initAnimation() {
        try {
            imgJumpL = new Image("res/image/animation/spellington/jumpL.png");
            imgJumpR = new Image("res/image/animation/spellington/jumpR.png");
            imgStandingL = new Image("res/image/animation/spellington/standingL.png");
            imgStandingR = new Image("res/image/animation/spellington/standingR.png");
            imgWallL = new Image("res/image/animation/spellington/wallL.png");
            imgWallR = new Image("res/image/animation/spellington/wallR.png");

            Image[] tempImgWalkL = new Image[40];
            for (int i = 0; i < tempImgWalkL.length; i++) {
                tempImgWalkL[i] = new Image("res/image/animation/spellington/walkL/" + "(" + (i + 1) + ")" + ".png");
            }

            Image[] tempImgWalkR = new Image[40];
            for (int i = 0; i < tempImgWalkR.length; i++) {
                tempImgWalkR[i] = new Image("res/image/animation/spellington/walkR/" + "(" + (i + 1) + ")" + ".png");
            }

            animWalkL = new Animation(tempImgWalkL, 15);
            animWalkR = new Animation(tempImgWalkR, 15);

        } catch (SlickException ex) {
            Logger.getLogger(Spellington.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Determines the mouvement state of Spellington.
     *
     * @param input The input Class that manages the input for the program.
     * @return the current mouvement state of Spellington.
     */
    private AnimState detMouvementState(Input input) {
//        if (!collisionBottom && collisionLeft) {
//            return AnimState.WALL_L;
//        } else if (!collisionBottom && collisionRight) {
//            return AnimState.WALL_R;
//        }
//
//        if (input.isMouseButtonDown(Input.MOUSE_LEFT_BUTTON)) {
//            if (mouseX < this.getCenterX()) {
//                if (collisionBottom) {
//                    return AnimState.WALKING_L;
//                } else {
//                    return AnimState.JUMP_L;
//                }
//
//            } else if (mouseX > this.getCenterX()) {
//                if (collisionBottom) {
//                    return AnimState.WALKING_R;
//                } else {
//                    return AnimState.JUMP_R;
//                }
//            } else if (collisionBottom && animState == AnimState.WALKING_L) {
//                return AnimState.STANDING_L;
//            } else if (collisionBottom && animState == AnimState.WALKING_R) {
//                return AnimState.STANDING_R;
//            } else if (!collisionBottom && animState == AnimState.JUMP_L) {
//                return AnimState.JUMP_L;
//            } else if (!collisionBottom && animState == AnimState.JUMP_R) {
//                return AnimState.JUMP_R;
//            } else if (!collisionBottom && animState == AnimState.STANDING_L) {
//                return AnimState.JUMP_L;
//            } else if (!collisionBottom && animState == AnimState.STANDING_R) {
//                return AnimState.JUMP_R;
//            } else if (collisionBottom && animState == AnimState.JUMP_L) {
//                return AnimState.STANDING_L;
//            } else if (collisionBottom && animState == AnimState.JUMP_R) {
//                return AnimState.STANDING_R;
//            }
//        } else if (collisionBottom && animState == AnimState.WALKING_L) {
//            return AnimState.STANDING_L;
//        } else if (collisionBottom && animState == AnimState.WALKING_R) {
//            return AnimState.STANDING_R;
//        } else if (!collisionBottom && animState == AnimState.JUMP_L) {
//            return AnimState.JUMP_L;
//        } else if (!collisionBottom && animState == AnimState.JUMP_R) {
//            return AnimState.JUMP_R;
//        } else if (!collisionBottom && animState == AnimState.STANDING_L) {
//            return AnimState.JUMP_L;
//        } else if (!collisionBottom && animState == AnimState.STANDING_R) {
//            return AnimState.JUMP_R;
//        } else if (!collisionBottom && animState == AnimState.WALL_L) {
//            return AnimState.JUMP_L;
//        } else if (!collisionBottom && animState == AnimState.WALL_R) {
//            return AnimState.JUMP_R;
//        } else if (collisionBottom && animState == AnimState.JUMP_L) {
//            return AnimState.STANDING_L;
//        } else if (collisionBottom && animState == AnimState.JUMP_R) {
//            return AnimState.STANDING_R;
//        } else if (collisionBottom && animState == AnimState.WALL_L) {
//            return AnimState.STANDING_L;
//        } else if (collisionBottom && animState == AnimState.WALL_R) {
//            return AnimState.STANDING_R;
//        }
//        if (animState == AnimState.STANDING_L) {
//            return AnimState.STANDING_L;
//        }
//        if (animState == AnimState.STANDING_R) {
//            return AnimState.STANDING_R;
//        }

        if (!collisionBottom && collisionLeft) {
            return AnimState.WALL_L;
        } else if (!collisionBottom && collisionRight) {
            return AnimState.WALL_R;
        }
        if (input.isKeyDown(Input.KEY_LEFT) && input.isKeyDown(Input.KEY_RIGHT)) {
            if (animState == AnimState.WALKING_L) {
                return AnimState.STANDING_L;
            } else if (animState == AnimState.WALKING_R) {
                return AnimState.STANDING_R;
            }
        } else if (input.isKeyDown(Input.KEY_LEFT)) {
            if (collisionBottom) {
                return AnimState.WALKING_L;
            } else {
                return AnimState.JUMP_L;
            }

        } else if (input.isKeyDown(Input.KEY_RIGHT)) {
            if (collisionBottom) {
                return AnimState.WALKING_R;
            } else {
                return AnimState.JUMP_R;
            }
        } else if (collisionBottom && animState == AnimState.WALKING_L) {
            return AnimState.STANDING_L;
        } else if (collisionBottom && animState == AnimState.WALKING_R) {
            return AnimState.STANDING_R;
        } else if (!collisionBottom && animState == AnimState.STANDING_L) {
            return AnimState.JUMP_L;
        } else if (!collisionBottom && animState == AnimState.STANDING_R) {
            return AnimState.JUMP_R;
        } else if (collisionBottom && animState == AnimState.JUMP_L) {
            return AnimState.STANDING_L;
        } else if (collisionBottom && animState == AnimState.JUMP_R) {
            return AnimState.STANDING_R;
        } else if (!collisionBottom && animState == AnimState.WALL_L) {
            return AnimState.JUMP_L;
        } else if (!collisionBottom && animState == AnimState.WALL_R) {
            return AnimState.JUMP_R;
        } else if (collisionBottom && animState == AnimState.WALL_L) {
            return AnimState.STANDING_L;
        } else if (collisionBottom && animState == AnimState.WALL_R) {
            return AnimState.STANDING_R;
        }

        return this.animState;
    }

    public int getMaxAirJumps() {
        return maxAirJumps;
    }

    public void setMaxAirJumps(int maxAirJumps) {
        this.maxAirJumps = maxAirJumps;
    }

    public int getAirJumps() {
        return airJumps;
    }

    public void setAirJumps(int airJumps) {
        this.airJumps = airJumps;
    }

}
