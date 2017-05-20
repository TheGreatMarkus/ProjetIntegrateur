package ca.qc.bdeb.info204.spellington.gameentities.enemies;

import ca.qc.bdeb.info204.spellington.calculations.Calculations;
import ca.qc.bdeb.info204.spellington.calculations.SpellingSystem;
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
 * A ranged ennemy that will be able to teleport and throw varied projectile
 * depending on the level it spawns in.
 *
 * @author Cristian Aldea
 * @see RangedEnemy
 */
public class MageEnemy extends RangedEnemy {

    private Animation animTeleport;

    public MageEnemy(float x, float y, EnemyType enemyType) {
        super(x, y, enemyType);
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
                break;
            case STANDING_R:
                imgStandingRight.draw(tempX, tempY, tempWidth, tempHeight);
                break;
        }
    }

    @Override
    public void move(float time, Spellington spellington, ArrayList<Projectile> activeProjectiles, Tile[][] mapinfo) {
        if (canSeePlayer) {
            if (deltaXPlayer > 0) {
                this.animState = AnimState.STANDING_R;
            } else if (deltaXPlayer < 0) {
                this.animState = AnimState.STANDING_L;
            }
        }
    }

    @Override
    public void attack(float time, Spellington spellington, ArrayList<Projectile> activeProjectiles, Tile[][] mapinfo) {
        if (spellingtonInRange) {
            if (attackCooldown == 0) {
                switch (this.enemyType) {
                    case PYROMANCER:
                        Calculations.enemyTryToShootCurvedProjectile(this, spellington, activeProjectiles, mapinfo);
                        break;
                    case CRYOMANCER:
                        break;
                    case ELECTROMANCER:
                        break;
                    default:
                        break;
                }
            }
        }
    }

    @Override
    public void loadAnimations() {
        String tempString = "";
        switch (this.enemyType) {
            case PYROMANCER:
                tempString = "pyromancer";
                animProjectile = SpellingSystem.getAnimFireBall();
                break;
            case CRYOMANCER:
                tempString = "cryomancer";
                break;
            case ELECTROMANCER:
                tempString = "electromancer";
                break;
        }
        try {
            imgStandingRight = new Image("res/image/animation/enemies/" + tempString + "/standingR.png");
            imgStandingLeft = new Image("res/image/animation/enemies/" + tempString + "/standingL.png");

            Image[] temp = new Image[20];
            for (int j = 0; j < temp.length; j++) {
                temp[j] = new Image("res/image/animation/enemies/teleport/(" + (j + 1) + ").png");
            }
            animTeleport = new Animation(temp, 20);
            animTeleport.setLooping(false);
        } catch (SlickException ex) {
            Logger.getLogger(MageEnemy.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
