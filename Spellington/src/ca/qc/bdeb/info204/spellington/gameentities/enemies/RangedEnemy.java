package ca.qc.bdeb.info204.spellington.gameentities.enemies;

import ca.qc.bdeb.info204.spellington.GameCore;
import ca.qc.bdeb.info204.spellington.calculations.Calculations;
import ca.qc.bdeb.info204.spellington.calculations.Vector2D;
import ca.qc.bdeb.info204.spellington.gameentities.Projectile;
import ca.qc.bdeb.info204.spellington.gameentities.Spellington;
import ca.qc.bdeb.info204.spellington.gameentities.Tile;
import ca.qc.bdeb.info204.spellington.gamestates.PlayState;
import java.awt.Dimension;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.newdawn.slick.Animation;
import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

/**
 *
 * @author 1522888
 */
public class RangedEnemy extends Enemy {

    private Animation animProjectile;
    private Image imgStandingLeft;
    private Image imgStandingRight;
    private Animation animAttackL;
    private Animation animAttackR;

    private Dimension dimProjectile;

    public RangedEnemy(float x, float y, Dimension dim, MouvementState mouvementState, float gravMod, EnemyType enemyType) {
        super(x, y, dim, mouvementState, gravMod, enemyType);
        if (this.enemyType == EnemyType.ARCHER) {
            dimProjectile = new Dimension(30, 30);
        } else if (this.enemyType == EnemyType.CROSSBOWMAN) {
            dimProjectile = new Dimension(30, 30);
        }

    }

    @Override
    public void render(Graphics g) {
        g.setColor(Color.white);
        g.drawString("HP = " + this.lifePoint, x, y - 20);
        if (willDoAction) {
            g.setColor(new Color(255, 0, 0, 100));
            g.fillRect(x, y, width, height);
        }
        g.setColor(Color.orange);
        g.drawRect(x, y, width, height);
        float tempY = y - 30;
        float tempXLeft = x - 58;
        float tempXRight = x - 70;
        float tempWidth = 178;
        float tempHeight = 100;
        switch (this.mouvementState) {
            case STANDING_L:
                imgStandingLeft.draw(tempXLeft, tempY, tempWidth, tempHeight);
                break;
            case STANDING_R:
                imgStandingRight.draw(tempXRight, tempY, tempWidth, tempHeight);
                break;
            case ATTACK_L:
                animAttackL.draw(tempXLeft, tempY, tempWidth, tempHeight);
                if (animAttackL.isStopped()) {
                    animAttackL.restart();
                    this.mouvementState = MouvementState.STANDING_L;
                }
                break;
            case ATTACK_R:
                animAttackR.draw(tempXRight, tempY, tempWidth, tempHeight);
                if (animAttackR.isStopped()) {
                    animAttackR.restart();
                    this.mouvementState = MouvementState.STANDING_R;
                }
                break;

        }

    }

    @Override
    public void move(float time, Spellington spellington, ArrayList<Projectile> activeProjectiles, Tile[][] mapinfo) {
        if (spellington.getCenterX() <= this.getCenterX()) {
            this.setMouvementState(MouvementState.STANDING_L);
        } else if (spellington.getCenterX() > this.getCenterX()) {
            this.setMouvementState(MouvementState.STANDING_R);
        }

    }

    @Override
    public void attack(float time, Spellington spellington, ArrayList<Projectile> activeProjectiles, Tile[][] mapinfo) {
        if (attackCooldown == 0) {
            if (this.enemyType == EnemyType.ARCHER) {
                Calculations.EnemyTryToShootCurvedProjectile(this, spellington, activeProjectiles, mapinfo);
            } else if (this.enemyType == EnemyType.CROSSBOWMAN) {
                Calculations.EnemyTryToShootStraightProjectile(this, spellington, activeProjectiles, mapinfo);
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
                temp[j] = new Image("res/image/animation/enemies/" + tempString + "/attackL/ (" + (j + 1) + ").png");
            }
            animAttackL = new Animation(temp, 20);
            animAttackL.setLooping(false);

            temp = new Image[17];
            for (int j = 0; j < temp.length; j++) {
                temp[j] = new Image("res/image/animation/enemies/" + tempString + "/attackR/ (" + (j + 1) + ").png");
            }
            animAttackR = new Animation(temp, 20);
            animAttackR.setLooping(false);
        } catch (SlickException ex) {
            Logger.getLogger(RangedEnemy.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public Dimension getDimProjectile() {
        return dimProjectile;
    }

    public Animation getAnimProjectile() {
        return animProjectile;
    }

}
