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

    int i = 0;

    private Animation animProjectile;
    private Image imgStandingLeft;
    private Image imgStandingRight;
    private Animation animAttackL;
    private Animation animAttackR;

    public RangedEnemy(float x, float y, Dimension dim, MouvementState mouvementState, float GRAVITY_MODIFIER, EnemyType enemyType) {
        super(x, y, dim, mouvementState, GRAVITY_MODIFIER, enemyType);

    }

    @Override
    public void render(Graphics g) {
        g.setColor(Color.white);
        g.drawString(this.lifePoint + "", x, y);
        g.setColor(Color.yellow);
        g.drawRect(x, y, width, height);
        float tempY = y - 30;
        float tempXLeft = x - 58;
        float tempXRight = x - 70;
        switch (this.mouvementState) {
            case STANDING_L:
                imgStandingLeft.draw(tempXLeft, tempY, 178, 100);
                break;
            case STANDING_R:
                imgStandingRight.draw(tempXRight, tempY, 178, 100);
                break;
            case ATTACK_L:
                animAttackL.draw(tempXLeft, tempY, 178, 100);
                if (animAttackL.isStopped()) {
                    this.mouvementState = MouvementState.STANDING_L;
                }
                break;
            case ATTACK_R:
                animAttackR.draw(tempXRight, tempY, 178, 100);
                if (animAttackR.isStopped()) {
                    this.mouvementState = MouvementState.STANDING_R;
                }
                break;

        }

    }

    @Override
    public void move(float time, Spellington spellington, ArrayList<Projectile> activeProjectiles, Tile[][] mapinfo) {
        if (this.mouvementState != MouvementState.ATTACK_L && this.mouvementState != MouvementState.ATTACK_R) {
            if (spellington.getCenterX() <= this.getCenterX()) {
                this.setMouvementState(MouvementState.STANDING_L);
            } else if (spellington.getCenterX() > this.getCenterX()) {
                this.setMouvementState(MouvementState.STANDING_R);
            }
        }
    }

    @Override
    public void attack(float time, Spellington spellington, ArrayList<Projectile> activeProjectiles, Tile[][] mapinfo) {
        if (i > 100) {
            tryToAttack(spellington, activeProjectiles, mapinfo);
        }
        if (i < 10000) {
            i++;
        }
    }

    public void tryToAttack(Spellington spellington, ArrayList<Projectile> activeProjectiles, Tile[][] mapinfo) {
        if (this.enemyType == EnemyType.ARCHER) {
            Float angle1 = 0f;
            Float angle2 = 0f;
            float deltaX = this.getCenterX() - spellington.getCenterX();
            float deltaY = this.getCenterY() - spellington.getCenterY();
            float v = 0.7f;
            float g = PlayState.GRAV_ACC.getY();
            //Formule provenant de https://en.wikipedia.org/wiki/Trajectory_of_a_projectile
            Float sqrt = (float) Math.sqrt((v * v * v * v) - (g * (g * deltaX * deltaX + (2 * deltaY * v * v))));
            if (!sqrt.isNaN()) {
                try {
                    angle1 = (float) Math.atan((v * v + sqrt) / (g * deltaX));
                    angle2 = (float) Math.atan((v * v - sqrt) / (g * deltaX));
                } catch (Exception e) {
                    System.out.println("exception");

                }
                if (deltaX > 0) {
                    angle1 = angle1 + (float) Math.PI;
                    angle2 = angle2 + (float) Math.PI;
                }
                Vector2D tempVect1 = new Vector2D(v, angle1, true);
                Vector2D tempVect2 = new Vector2D(v, angle2, true);
                Projectile test1 = new Projectile(this.getCenterX() - 15, this.getCenterY() - 15, 30, 30, new Vector2D(v, angle1, true), 1, animProjectile, 0, ElementalType.FIRE, Projectile.SourceType.ENEMY);
                Projectile test2 = new Projectile(this.getCenterX() - 15, this.getCenterY() - 15, 30, 30, new Vector2D(v, angle2, true), 1, animProjectile, 0, ElementalType.FIRE, Projectile.SourceType.ENEMY);
                int test1Result = -1;
                int test2Result = -1;
                while (test1Result == -1) {
                    test1Result = Calculations.checkProjectileCollision(test1, mapinfo, new ArrayList<Enemy>(), spellington);
                    test1.update(10);

                }
                while (test2Result == -1) {
                    test2Result = Calculations.checkProjectileCollision(test2, mapinfo, new ArrayList<Enemy>(), spellington);
                    test2.update(10);

                }
                if (test1Result == 1 || test2Result == 1) {
                    if (test2Result == 1) {
                        activeProjectiles.add(new Projectile(this.getCenterX() - 15, this.getCenterY() - 15, 30, 30, tempVect2, 1, animProjectile, 5, ElementalType.FIRE, Projectile.SourceType.ENEMY));
                    } else if (test1Result == 1) {
                        activeProjectiles.add(new Projectile(this.getCenterX() - 15, this.getCenterY() - 15, 30, 30, tempVect1, 1, animProjectile, 5, ElementalType.FIRE, Projectile.SourceType.ENEMY));

                    }
                    i = 0;
                    if (this.mouvementState == MouvementState.STANDING_L) {
                        this.mouvementState = MouvementState.ATTACK_L;
                        animAttackL.restart();
                    } else if (this.mouvementState == MouvementState.STANDING_R) {
                        this.mouvementState = MouvementState.ATTACK_R;
                        animAttackR.restart();
                    }
                }
            }
        } else if (this.enemyType == EnemyType.CROSSBOWMAN) {
            float v = 0.5f;
            float angle = 1f;
            float deltaX = spellington.getCenterX() - this.getCenterX();
            float deltaY = spellington.getCenterY() - this.getCenterY();
            if (Math.abs(deltaX) < 800f * GameCore.scale) {
                angle = Calculations.detAngle(deltaX, deltaY);

                Projectile test1 = new Projectile(this.getCenterX() - 15, this.getCenterY() - 15, 30, 30, new Vector2D(v, angle, true), 0, animProjectile, 0, ElementalType.FIRE, Projectile.SourceType.ENEMY);
                int test1Result = -1;
                while (test1Result == -1) {
                    test1Result = Calculations.checkProjectileCollision(test1, mapinfo, new ArrayList<Enemy>(), spellington);
                    test1.update(10);

                }
                if (test1Result == 1) {
                    i = 0;
                    activeProjectiles.add(new Projectile(this.getCenterX() - 15, this.getCenterY() - 15, 30, 30, new Vector2D(v, angle, true), 0, animProjectile, 5, ElementalType.FIRE, Projectile.SourceType.ENEMY));
                    if (this.mouvementState == MouvementState.STANDING_L) {
                        this.mouvementState = MouvementState.ATTACK_L;
                        animAttackL.restart();
                    } else if (this.mouvementState == MouvementState.STANDING_R) {
                        this.mouvementState = MouvementState.ATTACK_R;
                        animAttackR.restart();
                    }
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

}
