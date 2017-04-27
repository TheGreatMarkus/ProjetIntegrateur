package ca.qc.bdeb.info204.spellington.gameentities.enemies;

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
    private Animation animshootL;
    private Animation animshootR;

    public RangedEnemy(float x, float y, Dimension dim, MouvementState mouvementState, float GRAVITY_MODIFIER, EnemyType enemyType) {
        super(x, y, dim, mouvementState, GRAVITY_MODIFIER, enemyType);

    }

    @Override
    public void render(Graphics g) {
        g.setColor(Color.white);
        g.drawString("HP :" + this.lifePoint, x, y);
        g.setColor(Color.yellow);
        imgStandingLeft.draw(x - 68, y - 10);

    }

    @Override
    public void move(float time, Spellington spellington, ArrayList<Projectile> activeProjectiles, Tile[][] mapinfo) {
        //No moving
    }

    @Override
    public void attack(float time, Spellington spellington, ArrayList<Projectile> activeProjectiles, Tile[][] mapinfo) {
        if (i % 100 == 0) {
            tryToAttack(spellington, activeProjectiles, mapinfo);
        }
        i++;
    }

    public void tryToAttack(Spellington spellington, ArrayList<Projectile> activeProjectiles, Tile[][] mapinfo) {
        Float angle1 = 0f;
        Float angle2 = 0f;
        float v = 1f;
        float deltaX = this.getCenterX() - spellington.getCenterX();
        float deltaY = this.getCenterY() - spellington.getCenterY();
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

            activeProjectiles.add(new Projectile(this.getCenterX() - 15, this.getCenterY() - 15, 30, 30, tempVect1, 1, animProjectile, 5, ElementalType.FIRE, Projectile.SourceType.ENEMY));
            activeProjectiles.add(new Projectile(this.getCenterX() - 15, this.getCenterY() - 15, 30, 30, tempVect2, 1, animProjectile, 5, ElementalType.FIRE, Projectile.SourceType.ENEMY));
        }
    }

    @Override
    public void loadAnimations() {
        if (this.enemyType == EnemyType.ARCHER) {
            try {
                imgStandingRight = new Image("res/image/animation/enemies/archer/standingR.png");
                imgStandingLeft = new Image("res/image/animation/enemies/archer/standingL.png");
                Image[] temp = new Image[1];
                temp[0] = new Image("res/image/animation/enemies/archer/arrow.png");

                animProjectile = new Animation(temp, 10);
            } catch (SlickException ex) {
                Logger.getLogger(RangedEnemy.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else if (this.enemyType == EnemyType.CROSSBOWMAN) {
            try {
                imgStandingRight = new Image("res/image/animation/enemies/crossbowman/standingR.png");
                imgStandingLeft = new Image("res/image/animation/enemies/crossbowman/standingL.png");
                Image[] temp = new Image[1];
                temp[0] = new Image("res/image/animation/enemies/archer/arrow.png");

                animProjectile = new Animation(temp, 10);
            } catch (SlickException ex) {
                Logger.getLogger(RangedEnemy.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

}
