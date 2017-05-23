package ca.qc.bdeb.info204.spellington.gameentities.enemies;

import ca.qc.bdeb.info204.spellington.gameentities.Projectile;
import ca.qc.bdeb.info204.spellington.gameentities.Spellington;
import ca.qc.bdeb.info204.spellington.gameentities.Tile;
import java.awt.Point;
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

    public MeleeEnemy(float x, float y, EnemyType enemyType) {
        super(x, y, enemyType);
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
        float tempX = getX() - 5;
        float tempY = getY() - 10;
        float tempWidth = 60;
        float tempHeight = 60;
        if (this.enemyType == EnemyType.KEEPER || this.enemyType == EnemyType.GUARD) {
            tempWidth = 200;
            tempHeight = 113;
            tempX = getX() - 75;
            tempY = getY() - 15;
        }
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
        if (canSeePlayer && !(this.animState == AnimState.ATTACK_L || this.animState == AnimState.ATTACK_R)) {
            if (deltaXPlayer < 0) {
                this.animState = AnimState.STANDING_L;
            } else if (deltaXPlayer > 0) {
                this.animState = AnimState.STANDING_R;
            }
            Point target = getTarget(spellington, mapinfo);
            pathingToTarget(target, time);
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
            if (this.enemyType == EnemyType.FIRE_SLIME || this.enemyType == EnemyType.ICE_SLIME || this.enemyType == EnemyType.LIGHTNING_SLIME) {
                if (this.getBounds().intersects(spellington.getBounds())) {
                    this.attackCooldown = totalAttackCooldown;
                    spellington.subLifePoint(damage, damageType);
                }
            } else if (this.enemyType == EnemyType.GUARD || this.enemyType == EnemyType.KEEPER) {
                if (this.animState == AnimState.STANDING_L || this.animState == AnimState.WALKING_L) {
                    Rectangle hitBox = new Rectangle(getCenterX() - attackRange, y + 20, attackRange, 30);
                    if (hitBox.intersects(spellington.getBounds())) {
                        this.animState = AnimState.ATTACK_L;
                        this.attackCooldown = totalAttackCooldown;
                        spellington.subLifePoint(damage, damageType);
                    }

                } else if (this.animState == AnimState.STANDING_R || this.animState == AnimState.WALKING_R) {
                    Rectangle hitBox = new Rectangle(getCenterX(), y + 20, attackRange, 30);
                    if (hitBox.intersects(spellington.getBounds())) {
                        this.animState = AnimState.ATTACK_R;
                        this.attackCooldown = totalAttackCooldown;
                        spellington.subLifePoint(damage, damageType);
                    }
                }
            }
        }
    }

    private Point getTarget(Spellington spellington, Tile[][] mapinfo) {
        //Will always target the player for now.
        return new Point((int) spellington.getX(), (int) spellington.getY());
    }

    private void pathingToTarget(Point target, float time) {
        float deltaX = target.x - this.x;
        float deltaY = target.y - this.y;
        if (deltaX < -attackRange) {
            this.speedVector.sub(xAcc.getMultScalar(time));
            if (this.speedVector.getX() < -maxXSpeed) {
                this.speedVector.setX(-maxXSpeed);
            }
            this.setAnimState(AnimState.WALKING_L);
        } else if (deltaX > attackRange) {
            this.speedVector.add(xAcc.getMultScalar(time));
            if (this.speedVector.getX() > maxXSpeed) {
                this.speedVector.setX(maxXSpeed);
            }
            this.setAnimState(AnimState.WALKING_R);
        } else {
            slowDown(time);
        }
        if (collisionBottom && deltaY < -40) {
            this.speedVector.setY(jumpVector.getY());
        } else if (!canSeePlayer && this.animState == AnimState.WALKING_L) {
            this.animState = AnimState.STANDING_L;
        } else if (!canSeePlayer && this.animState == AnimState.WALKING_R) {
            this.animState = AnimState.STANDING_R;
        }
    }

    @Override
    public void loadAnimations() {
        String tempString = "";
        switch (this.enemyType) {
            case KEEPER:
                tempString = "keeper";
                break;
            case GUARD:
                tempString = "guard";
                break;
            case FIRE_SLIME:
                tempString = "slime/red";
                break;
            case ICE_SLIME:
                tempString = "slime/blue";
                break;
            case LIGHTNING_SLIME:
                tempString = "slime/yellow";
                break;

        }

        try {
            switch (this.enemyType) {
                case KEEPER: {
                    Image[] temp = new Image[40];
                    for (int j = 0; j < temp.length; j++) {
                        temp[j] = new Image("res/image/animation/enemies/" + tempString + "/walkL/(" + (j + 1) + ").png");
                    }
                    animWalkL = new Animation(temp, 20);
                    temp = new Image[40];
                    for (int j = 0; j < temp.length; j++) {
                        temp[j] = new Image("res/image/animation/enemies/" + tempString + "/walkR/(" + (j + 1) + ").png");
                    }
                    animWalkR = new Animation(temp, 20);
                    imgStandingRight = new Image("res/image/animation/enemies/" + tempString + "/standingR.png");
                    imgStandingLeft = new Image("res/image/animation/enemies/" + tempString + "/standingL.png");
                    temp = new Image[15];
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
                    break;
                }
                case GUARD: {
                    Image[] temp = new Image[41];
                    for (int j = 0; j < temp.length; j++) {
                        temp[j] = new Image("res/image/animation/enemies/" + tempString + "/walkL/ (" + (j + 1) + ").png");
                    }
                    animWalkL = new Animation(temp, 20);
                    temp = new Image[41];
                    for (int j = 0; j < temp.length; j++) {
                        temp[j] = new Image("res/image/animation/enemies/" + tempString + "/walkR/ (" + (j + 1) + ").png");
                    }
                    animWalkR = new Animation(temp, 20);
                    imgStandingRight = new Image("res/image/animation/enemies/" + tempString + "/standingR.png");
                    imgStandingLeft = new Image("res/image/animation/enemies/" + tempString + "/standingL.png");
                    temp = new Image[21];
                    for (int j = 0; j < temp.length; j++) {
                        temp[j] = new Image("res/image/animation/enemies/" + tempString + "/attackL/ (" + (j + 1) + ").png");
                    }
                    animAttackL = new Animation(temp, 30);
                    animAttackL.setLooping(false);
                    temp = new Image[21];
                    for (int j = 0; j < temp.length; j++) {
                        temp[j] = new Image("res/image/animation/enemies/" + tempString + "/attackR/ (" + (j + 1) + ").png");
                    }
                    animAttackR = new Animation(temp, 30);
                    animAttackR.setLooping(false);
                    break;
                }
                case FIRE_SLIME:
                case ICE_SLIME:
                case LIGHTNING_SLIME: {
                    Image[] temp = new Image[20];
                    for (int j = 0; j < temp.length; j++) {
                        temp[j] = new Image("res/image/animation/enemies/" + tempString + "/walkL/ (" + (j + 1) + ").png");
                    }
                    animWalkL = new Animation(temp, 20);
                    temp = new Image[20];
                    for (int j = 0; j < temp.length; j++) {
                        temp[j] = new Image("res/image/animation/enemies/" + tempString + "/walkR/ (" + (j + 1) + ").png");
                    }
                    animWalkR = new Animation(temp, 20);
                    imgStandingLeft = new Image("res/image/animation/enemies/" + tempString + "/standing.png");
                    imgStandingRight = imgStandingLeft;
                    animAttackL = animWalkL;
                    animAttackR = animWalkR;
                    break;
                }
                default:
                    break;
            }
        } catch (SlickException ex) {
            Logger.getLogger(MeleeEnemy.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

}
