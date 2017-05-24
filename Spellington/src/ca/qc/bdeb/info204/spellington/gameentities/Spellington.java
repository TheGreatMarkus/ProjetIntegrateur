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
    private static final float GRAV_MOD = 2;
    private static final float MAX_X_SPEED = 0.5f;
    private static final Vector2D X_ACC = new Vector2D(0.003f, 0);
    private static final Vector2D JUMP_VECTOR = new Vector2D(0, -0.45f);
    private static final float WJ_POWER = -0.9f;
    private static final float WJ_ANGLE = (float) Math.toRadians(65);
    private static final Vector2D LEFT_WJ_VECTOR = new Vector2D(WJ_POWER * (float) Math.cos(WJ_ANGLE), WJ_POWER * (float) Math.sin(WJ_ANGLE));
    private static final Vector2D RIGHT_WJ_VECTOR = new Vector2D(-WJ_POWER * (float) Math.cos(WJ_ANGLE), WJ_POWER * (float) Math.sin(WJ_ANGLE));
    private static final Dimension PLAYER_SIZE = new Dimension(45, 100);

    private int airJumps;
    private int maxAirJumps;
    private int jumpTime;

    private static final int MAX_JUMP_TIME = 100;

    public Spellington(float x, float y, AnimState mouvementState) throws SlickException {
        super(x, y, PLAYER_SIZE.width, PLAYER_SIZE.height, mouvementState, GRAV_MOD);
        initAnimation();

        maxLifePoint = 100;
        maxInvulnTime = 1000;
        lifePoint = maxLifePoint;
        maxXSpeed = MAX_X_SPEED;
        xAcc = X_ACC;
        jumpVector = JUMP_VECTOR;

        resElectricity = 0;
        resIce = 0;
        resFire = 0;

        maxAirJumps = 1;
        airJumps = maxAirJumps;
        jumpTime = 0;

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
        //If Spellington is currently invulnerable, his invulnTime will go down.
        if (this.invulnTime > 0) {
            this.invulnTime -= time;
            if (invulnTime < 0) {
                invulnTime = 0;
            }
        }

        //This code moves Spellington according to the mouse. Will be implemented later.
//        On divise par scale pour match la position de la souris avec le scale du render
//        float mouseX = (float) input.getMouseX() / GameCore.scale;
//        float mouseY = (float) input.getMouseY() / GameCore.scale;
//        //Using equation d = (vf^2 - vi^2)/2a. Distance from where Spellington should stop when approching the mouse
//        float SLOWDOWN_DISTANCE = (this.speedVector.getX() * this.speedVector.getX()) / (2.0f * xAcc.getX());
//        Correction of speed according to collision state
//        General handling of mouvement in x for spellington
//        if (input.isMouseButtonDown(Input.MOUSE_LEFT_BUTTON) && Math.abs(mouseX - this.getCenterX()) <= Math.abs(this.speedVector.getX() * time)) {
//            this.setCenterX(mouseX);
//            this.speedVector.setX(0);
//        } else if (input.isMouseButtonDown(Input.MOUSE_LEFT_BUTTON) && Math.abs(mouseX - this.getCenterX()) <= SLOWDOWN_DISTANCE) {
//            if (mouseX > this.getCenterX() && this.speedVector.getX() > 0) {
//                this.speedVector.sub(Vector2D.multVectorScalar(xAcc, time));
//
//            } else if (mouseX < this.getCenterX() && this.speedVector.getX() < 0) {
//                this.speedVector.add(Vector2D.multVectorScalar(xAcc, time));
//            }
//        } else if (input.isMouseButtonDown(Input.MOUSE_LEFT_BUTTON) && mouseX > this.getCenterX()) {
//            this.speedVector.add(Vector2D.multVectorScalar(xAcc, time));
//            if (this.speedVector.getX() > maxXSpeed) {
//                this.speedVector.setX(maxXSpeed);
//            }
//        } else if (input.isMouseButtonDown(Input.MOUSE_LEFT_BUTTON) && mouseX < this.getCenterX()) {
//            this.speedVector.sub(Vector2D.multVectorScalar(xAcc, time));
//            if (this.speedVector.getX() < -maxXSpeed) {
//                this.speedVector.setX(-maxXSpeed);
//            }
//        } else if (!input.isMouseButtonDown(Input.MOUSE_LEFT_BUTTON) && this.speedVector.getX() > 0) {
//            this.speedVector.sub(Vector2D.multVectorScalar(xAcc, time));
//            if (this.speedVector.getX() < Vector2D.multVectorScalar(xAcc, time).getX()) {
//                this.speedVector.setX(0);
//            }
//        } else if (!input.isMouseButtonDown(Input.MOUSE_LEFT_BUTTON) && this.speedVector.getX() < 0) {
//            this.speedVector.add(Vector2D.multVectorScalar(xAcc, time));
//            if (this.speedVector.getX() > -Vector2D.multVectorScalar(xAcc, time).getX()) {
//                this.speedVector.setX(0);
//            }
//        }
        if (input.isKeyDown(Input.KEY_RIGHT) && input.isKeyDown(Input.KEY_LEFT)) {
            if (this.speedVector.getX() > 0) {
                this.speedVector.sub(xAcc.getMultScalar(time));
                if (this.speedVector.getX() < xAcc.getMultScalar(time).getX()) {
                    this.speedVector.setX(0);
                }
            } else if (this.speedVector.getX() < 0) {
                this.speedVector.add(xAcc.getMultScalar(time));
                if (this.speedVector.getX() > -xAcc.getMultScalar(time).getX()) {
                    this.speedVector.setX(0);
                }
            }
        } else if (input.isKeyDown(Input.KEY_RIGHT)) {
            this.speedVector.add(xAcc.getMultScalar(time));
            if (this.speedVector.getX() > maxXSpeed) {
                this.speedVector.setX(maxXSpeed);
            }
        } else if (input.isKeyDown(Input.KEY_LEFT)) {
            this.speedVector.sub(xAcc.getMultScalar(time));
            if (this.speedVector.getX() < -maxXSpeed) {
                this.speedVector.setX(-maxXSpeed);
            }
        } else if (this.speedVector.getX() > 0) {
            this.speedVector.sub(xAcc.getMultScalar(time));
            if (this.speedVector.getX() < xAcc.getMultScalar(time).getX()) {
                this.speedVector.setX(0);
            }
        } else if (this.speedVector.getX() < 0) {
            this.speedVector.add(xAcc.getMultScalar(time));
            if (this.speedVector.getX() > -xAcc.getMultScalar(time).getX()) {
                this.speedVector.setX(0);
            }
        }

        //Temporary boolean because of how isMousePressed or isKeyPressed works.
        boolean triedToJump = false;

        if (input.isKeyPressed(Input.KEY_UP)) {
            triedToJump = true;
        }

        //Jumping
        if (collisionBottom || collisionLeft || collisionRight) {
            airJumps = maxAirJumps;
        }

        if (input.isKeyDown(Input.KEY_UP) && jumpTime > 0) {
            speedVector.add(jumpVector.getMultScalar(0.009f * time));
        }
        if (jumpTime > 0) {
            jumpTime -= time;
            if (jumpTime < 0) {
                jumpTime = 0;
            }
        }

        if (triedToJump && collisionBottom) {
            this.speedVector.setY(jumpVector.getY());
            jumpTime = MAX_JUMP_TIME;
        } else if (triedToJump && collisionLeft && !collisionBottom) {
            this.speedVector.set(RIGHT_WJ_VECTOR);
        } else if (triedToJump && collisionRight && !collisionBottom) {
            this.speedVector.set(LEFT_WJ_VECTOR);
        } else if (triedToJump && !collisionBottom && !collisionLeft && !collisionLeft && airJumps > 0) {
            this.speedVector.setY(jumpVector.getMultScalar(AIR_JUMP_POWER).getY());
            jumpTime = MAX_JUMP_TIME;
            airJumps--;
        }

        this.setAnimState(detMouvementState(input));
        this.speedVector.add(PlayState.GRAV_ACC.getMultScalar(time * gravModifier));
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
        Color tempColor;
        if (invulnTime > 0) {
            tempColor = new Color(255, 0, 0, 200);
        } else {
            tempColor = new Color(255, 255, 255, 255);
        }

        float tempX = x - 65;
        float tempY = y;
        float tempWidth = 178;
        float tempHeight = 100;
        switch (this.getAnimState()) {
            case STANDING_L:
                imgStandingL.draw(tempX, tempY, tempWidth, tempHeight, tempColor);
                break;
            case STANDING_R:
                imgStandingR.draw(tempX, tempY, tempWidth, tempHeight, tempColor);
                break;
            case JUMP_L:
                imgJumpL.draw(tempX, tempY, tempWidth, tempHeight, tempColor);
                break;
            case JUMP_R:
                imgJumpR.draw(tempX, tempY, tempWidth, tempHeight, tempColor);
                break;
            case WALL_L:
                imgWallL.draw(tempX - 2, tempY, tempWidth, tempHeight, tempColor);
                break;
            case WALL_R:
                imgWallR.draw(tempX + 3, tempY, tempWidth, tempHeight, tempColor);
                break;
            case WALKING_L:
                animWalkL.draw(tempX, tempY, tempWidth, tempHeight, tempColor);
                break;
            case WALKING_R:
                animWalkR.draw(tempX, tempY, tempWidth, tempHeight, tempColor);
                break;
        }
        if (PlayState.debugMode) {
            g.drawRect(x, y, PLAYER_SIZE.width, PLAYER_SIZE.height);
            //g.setColor(Color.red);
            //g.drawRect(tempX, tempY, tempWidth, tempHeight);
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

    public void setInvulnTime(int invulnTime) {
        this.invulnTime = invulnTime;
    }
    
    

}
