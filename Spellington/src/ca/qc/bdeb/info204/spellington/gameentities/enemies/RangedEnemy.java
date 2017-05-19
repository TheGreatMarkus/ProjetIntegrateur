package ca.qc.bdeb.info204.spellington.gameentities.enemies;

import ca.qc.bdeb.info204.spellington.calculations.Calculations;
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
 * @author Cristian Aldea
 * @see Enemy
 */
public class RangedEnemy extends Enemy {

    protected Animation animProjectile;

    private float projectileSize;

    public RangedEnemy(float x, float y, EnemyType enemyType) {
        super(x, y, enemyType);
        switch (this.enemyType) {
            case ARCHER:
                projectileSize = 20;
                break;
            case CROSSBOWMAN:
                projectileSize = 20;
                break;
            case PYROMANCER:
                projectileSize = 20;
                break;
            case CRYOMANCER:
                projectileSize = 20;
                break;
            case ELECTROMANCER:
                projectileSize = 20;
                break;
            default:
                break;
        }
    }

    @Override
    public void render(Graphics g) {
        renderGeneralInfo(g);
        float tempY = getY() - 30;
        float tempXLeft = getX() - 58;
        float tempXRight = getX() - 70;
        float tempWidth = 178;
        float tempHeight = 100;
        switch (this.animState) {
            case STANDING_L:
                imgStandingLeft.draw(tempXLeft, tempY, tempWidth, tempHeight);
                break;
            case STANDING_R:
                imgStandingRight.draw(tempXRight, tempY, tempWidth, tempHeight);
                break;
            case ATTACK_L:
                animAttackL.draw(tempXLeft, tempY, tempWidth, tempHeight);
                if (animAttackL.isStopped()) {
                    this.animState = AnimState.STANDING_L;
                    animAttackL.restart();
                }
                break;
            case ATTACK_R:
                animAttackR.draw(tempXRight, tempY, tempWidth, tempHeight);
                if (animAttackR.isStopped()) {
                    this.animState = AnimState.STANDING_R;
                    animAttackR.restart();
                }
                break;

        }

    }

    @Override
    public void move(float time, Spellington spellington, ArrayList<Projectile> activeProjectiles, Tile[][] mapinfo) {
        if (canAttackSpellington) {
            if (this.animState != AnimState.ATTACK_R && this.animState != AnimState.ATTACK_L) {
                if (spellington.getCenterX() <= this.getCenterX()) {
                    this.setAnimState(AnimState.STANDING_L);
                } else if (spellington.getCenterX() > this.getCenterX()) {
                    this.setAnimState(AnimState.STANDING_R);
                }
            }
        }

    }

    @Override
    public void attack(float time, Spellington spellington, ArrayList<Projectile> activeProjectiles, Tile[][] mapinfo) {
        if (spellingtonInRange) {
            if (attackCooldown == 0) {
                if (this.enemyType == EnemyType.ARCHER) {
                    Calculations.enemyTryToShootCurvedProjectile(this, spellington, activeProjectiles, mapinfo);
                } else if (this.enemyType == EnemyType.CROSSBOWMAN) {
                    Calculations.enemyTryToShootStraightProjectile(this, spellington, activeProjectiles, mapinfo);
                }
            }
        }
    }

    @Override
    public void loadAnimations() {
        String tempString = "";

        if (this.enemyType == EnemyType.ARCHER) {
            tempString = "archer";
        } else if (this.enemyType == EnemyType.CROSSBOWMAN) {
            tempString = "crossbowman";
        }

        try {
            imgStandingRight = new Image("res/image/animation/enemies/" + tempString + "/standingR.png");
            imgStandingLeft = new Image("res/image/animation/enemies/" + tempString + "/standingL.png");

            Image[] temp = new Image[1];
            temp[0] = new Image("res/image/animation/enemies/" + tempString + "/projectile.png");
            animProjectile = new Animation(temp, 30);

            temp = new Image[17];
            for (int j = 0; j < temp.length; j++) {
                temp[j] = new Image("res/image/animation/enemies/" + tempString + "/attackL/(" + (j + 1) + ").png");
            }
            animAttackL = new Animation(temp, 20);
            animAttackL.setLooping(false);

            temp = new Image[17];
            for (int j = 0; j < temp.length; j++) {
                temp[j] = new Image("res/image/animation/enemies/" + tempString + "/attackR/(" + (j + 1) + ").png");
            }
            animAttackR = new Animation(temp, 20);
            animAttackR.setLooping(false);
        } catch (SlickException ex) {
            Logger.getLogger(RangedEnemy.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public float getProjectileSize() {
        return projectileSize;
    }

    public Animation getAnimProjectile() {
        return animProjectile;
    }

}
