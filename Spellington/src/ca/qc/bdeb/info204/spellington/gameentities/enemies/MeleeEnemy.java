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
import org.newdawn.slick.geom.Rectangle;

/**
 * An Enemy that will attack the player in melee range.
 *
 * @author Cristian Aldea
 * @see Enemy
 */
public class MeleeEnemy extends Enemy {

    private Animation animWalkL;
    private Animation animWalkR;

    int attackRange;

    public MeleeEnemy(float x, float y, AnimState mouvementState, float GRAVITY_MODIFIER, EnemyType enemyType) {
        super(x, y, mouvementState, GRAVITY_MODIFIER, enemyType);
        switch (this.enemyType) {
            case FIRE_SLIME:
            case ICE_SLIME:
            case LIGHTNING_SLIME:
                attackRange = 0;
                break;
            case KEEPER:
            case GUARD:
                attackRange = 60;
                break;

        }
    }

    @Override
    public void render(Graphics g) {
        renderGeneralInfo(g);
        float tempX = getX() - 75;
        float tempY = getY() - 15;
        float tempWidth = 200;
        float tempHeight = 113;
        switch (this.animState) {
            case STANDING_L:
                imgStandingLeft.draw(tempX, tempY, tempWidth, tempHeight);
                g.drawRect(getCenterX() - attackRange, y + 20, attackRange, 30);
                break;
            case STANDING_R:
                imgStandingRight.draw(tempX, tempY, tempWidth, tempHeight);
                g.drawRect(getCenterX(), y + 20, attackRange, 30);
                break;
            case WALKING_L:
                animWalkL.draw(tempX, tempY, tempWidth, tempHeight);
                g.drawRect(getCenterX() - attackRange, y + 20, attackRange, 30);
                break;
            case WALKING_R:
                animWalkR.draw(tempX, tempY, tempWidth, tempHeight);
                g.drawRect(getCenterX(), y + 20, attackRange, 30);
                break;
            case ATTACK_L:
                animAttackL.draw(tempX, tempY, tempWidth, tempHeight);
                if (animAttackL.isStopped()) {
                    animAttackL.restart();
                    this.animState = AnimState.STANDING_L;
                }
                break;
            case ATTACK_R:
                animAttackR.draw(tempX, tempY, tempWidth, tempHeight);
                if (animAttackR.isStopped()) {
                    animAttackR.restart();
                    this.animState = AnimState.STANDING_R;
                }
                break;

        }
    }

    @Override
    public void move(float time, Spellington spellington, ArrayList<Projectile> activeProjectiles, Tile[][] mapinfo) {
        if (willDoAction && !(this.animState == AnimState.ATTACK_L || this.animState == AnimState.ATTACK_R)) {
            if (deltaXSpellington < 0) {
                this.animState = AnimState.STANDING_L;
            } else if (deltaXSpellington > 0) {
                this.animState = AnimState.STANDING_R;
            }

            if (deltaXSpellington < -attackRange) {
                this.speedVector.sub(Vector2D.multVectorScalar(X_ACC, time));
                if (this.speedVector.getX() < -MAX_X_SPEED) {
                    this.speedVector.setX(-MAX_X_SPEED);
                }
                this.setAnimState(AnimState.WALKING_L);
            } else if (deltaXSpellington > attackRange) {
                this.speedVector.add(Vector2D.multVectorScalar(X_ACC, time));
                if (this.speedVector.getX() > MAX_X_SPEED) {
                    this.speedVector.setX(MAX_X_SPEED);
                }
                this.setAnimState(AnimState.WALKING_R);
            } else {
                slowDown(time);
            }
            if (collisionBottom && deltaYSpellington < -40) {
                this.speedVector.setY(INIT_JUMP_SPEED);
            } else if (!willDoAction && this.animState == AnimState.WALKING_L) {
                this.animState = AnimState.STANDING_L;
            } else if (!willDoAction && this.animState == AnimState.WALKING_R) {
                this.animState = AnimState.STANDING_R;
            }
        } else {
            if (this.animState == AnimState.WALKING_R) {
                this.animState = AnimState.STANDING_R;
            } else if (this.animState == AnimState.WALKING_L) {
                this.animState = AnimState.STANDING_L;
            }
            slowDown(time);
        }

    }

    @Override
    public void attack(float time, Spellington spellington, ArrayList<Projectile> activeProjectiles, Tile[][] mapinfo) {
        if (attackCooldown == 0) {
            if (this.enemyType == EnemyType.FIRE_SLIME
                    || this.enemyType == EnemyType.ICE_SLIME
                    || this.enemyType == EnemyType.LIGHTNING_SLIME) {
                if (this.getBounds().intersects(spellington.getBounds())) {
                    this.attackCooldown = totalAttackCooldown;
                    spellington.subLifePoint(damage, damageType);
                }
            }
            if (this.animState == AnimState.STANDING_L
                    || this.animState == AnimState.WALKING_L) {
                Rectangle hitBox = new Rectangle(getCenterX() - attackRange, y + 20, attackRange, 30);
                if (hitBox.intersects(spellington.getBounds())) {
                    this.animState = AnimState.ATTACK_L;
                    this.attackCooldown = totalAttackCooldown;
                    spellington.subLifePoint(damage, damageType);
                }

            } else if (this.animState == AnimState.STANDING_R
                    || this.animState == AnimState.WALKING_R) {
                Rectangle hitBox = new Rectangle(getCenterX(), y + 20, attackRange, 30);
                if (hitBox.intersects(spellington.getBounds())) {
                    this.animState = AnimState.ATTACK_R;
                    this.attackCooldown = totalAttackCooldown;
                    spellington.subLifePoint(damage, damageType);
                }
            }
        }
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
                temp[j] = new Image("res/image/animation/enemies/" + tempString + "/attackL/(" + (j + 1) + ").png");
            }
            animAttackL = new Animation(temp, 30);
            animAttackL.setLooping(false);

            temp = new Image[15];
            for (int j = 0; j < temp.length; j++) {
                temp[j] = new Image("res/image/animation/enemies/" + tempString + "/attackR/(" + (j + 1) + ").png");
            }
            animAttackR = new Animation(temp, 30);
            animAttackR.setLooping(false);

            temp = new Image[40];
            for (int j = 0; j < temp.length; j++) {
                temp[j] = new Image("res/image/animation/enemies/" + tempString + "/walkL/(" + (j + 1) + ").png");
            }
            animWalkL = new Animation(temp, 20);

            temp = new Image[40];
            for (int j = 0; j < temp.length; j++) {
                temp[j] = new Image("res/image/animation/enemies/" + tempString + "/walkR/(" + (j + 1) + ").png");
            }
            animWalkR = new Animation(temp, 20);
        } catch (SlickException ex) {
            Logger.getLogger(MeleeEnemy.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

}
