package ca.qc.bdeb.info204.spellington.gameentities.enemies;

import ca.qc.bdeb.info204.spellington.calculations.Vector2D;
import ca.qc.bdeb.info204.spellington.gameentities.Projectile;
import ca.qc.bdeb.info204.spellington.gameentities.Spellington;
import ca.qc.bdeb.info204.spellington.gameentities.Tile;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.newdawn.slick.Animation;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

/**
 *
 * @author 1522888
 */
public class MeleeEnemy extends Enemy {

    private Image imgStandingLeft;
    private Image imgStandingRight;
    private Animation animAttackL;
    private Animation animAttackR;
    private Animation animWalkL;
    private Animation animWalkR;

    public MeleeEnemy(float x, float y, MouvementState mouvementState, float GRAVITY_MODIFIER, EnemyType enemyType) {
        super(x, y, mouvementState, GRAVITY_MODIFIER, enemyType);
    }

    @Override
    public void render(Graphics g) {
        renderGeneral(g);
        float tempX = getX() - 75;
        float tempY = getY() - 15;
        float tempWidth = 200;
        float tempHeight = 113;
        switch (this.mouvementState) {
            case STANDING_L:
                imgStandingLeft.draw(tempX, tempY, tempWidth, tempHeight);
                break;
            case STANDING_R:
                imgStandingRight.draw(tempX, tempY, tempWidth, tempHeight);
                break;
            case WALKING_L:
                animWalkL.draw(tempX, tempY, tempWidth, tempHeight);
                break;
            case WALKING_R:
                animWalkR.draw(tempX, tempY, tempWidth, tempHeight);
                break;
            case ATTACK_L:
                animAttackL.draw(tempX, tempY, tempWidth, tempHeight);
                if (animAttackL.isStopped()) {
                    this.mouvementState = MouvementState.STANDING_L;
                }
                break;
            case ATTACK_R:
                animAttackR.draw(tempX, tempY, tempWidth, tempHeight);
                if (animAttackR.isStopped()) {
                    this.mouvementState = MouvementState.STANDING_R;
                }
                break;

        }
    }

    @Override
    public void move(float time, Spellington spellington, ArrayList<Projectile> activeProjectiles, Tile[][] mapinfo) {
        if (willDoAction) {
            if (deltaXSpellington < 0) {
                this.speedVector.sub(Vector2D.multVectorScalar(X_ACC, time));
                if (this.speedVector.getX() < -MAX_X_SPEED) {
                    this.speedVector.setX(-MAX_X_SPEED);
                }
                this.setMouvementState(MouvementState.WALKING_L);
            } else if (deltaXSpellington > 0) {
                this.speedVector.add(Vector2D.multVectorScalar(X_ACC, time));
                if (this.speedVector.getX() > MAX_X_SPEED) {
                    this.speedVector.setX(MAX_X_SPEED);
                }
                this.setMouvementState(MouvementState.WALKING_R);
            }
            if (collisionBottom && deltaYSpellington < -40) {
                this.speedVector.setY(INIT_JUMP_SPEED);
            } else if (!willDoAction && this.mouvementState == MouvementState.WALKING_L) {
                this.mouvementState = MouvementState.STANDING_L;
            } else if (!willDoAction && this.mouvementState == MouvementState.WALKING_R) {
                this.mouvementState = MouvementState.STANDING_R;
            }
        } else if (this.mouvementState == MouvementState.WALKING_R) {
            this.mouvementState = MouvementState.STANDING_R;
        } else if (this.mouvementState == MouvementState.WALKING_L) {
            this.mouvementState = MouvementState.STANDING_L;
        }

    }

    @Override
    public void attack(float time, Spellington spellington, ArrayList<Projectile> activeProjectiles, Tile[][] mapinfo) {
    }

    @Override
    public void loadAnimations() {
        String tempString = "keeper";
        if (this.enemyType == EnemyType.KEEPER) {
            tempString = "keeper";
        }
        try {
            imgStandingRight = new Image("res/image/animation/enemies/" + tempString + "/standingR.png");
            imgStandingLeft = new Image("res/image/animation/enemies/" + tempString + "/standingL.png");

            Image[] temp = new Image[15];
            for (int j = 0; j < temp.length; j++) {
                temp[j] = new Image("res/image/animation/enemies/" + tempString + "/attackL/ (" + (j + 1) + ").png");
            }
            animAttackL = new Animation(temp, 30);
            animAttackL.setLooping(false);

            temp = new Image[15];
            for (int j = 0; j < temp.length; j++) {
                temp[j] = new Image("res/image/animation/enemies/" + tempString + "/attackR/ (" + (j + 1) + ").png");
            }
            animAttackR = new Animation(temp, 30);
            animAttackR.setLooping(false);

            temp = new Image[40];
            for (int j = 0; j < temp.length; j++) {
                temp[j] = new Image("res/image/animation/enemies/" + tempString + "/walkL/ (" + (j + 1) + ").png");
            }
            animWalkL = new Animation(temp, 20);

            temp = new Image[40];
            for (int j = 0; j < temp.length; j++) {
                temp[j] = new Image("res/image/animation/enemies/" + tempString + "/walkR/ (" + (j + 1) + ").png");
            }
            animWalkR = new Animation(temp, 20);
        } catch (SlickException ex) {
            Logger.getLogger(MeleeEnemy.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

}
