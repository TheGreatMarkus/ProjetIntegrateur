package ca.qc.bdeb.info204.spellington.gameentities.enemies;

import ca.qc.bdeb.info204.spellington.GameCore;
import ca.qc.bdeb.info204.spellington.calculations.Calculations;
import ca.qc.bdeb.info204.spellington.calculations.SpellingSystem;
import ca.qc.bdeb.info204.spellington.gameentities.GameAnimation;
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

/**
 * A ranged ennemy that will be able to teleport and throw varied projectile
 * depending on the level it spawns in.
 *
 * @author Cristian Aldea
 * @see RangedEnemy
 */
public class MageEnemy extends RangedEnemy {

    private Animation animTeleport;
    private float teleportTime;

    private static final int MAX_TELEPORT_TIME = 10000;

    public MageEnemy(float x, float y, EnemyType enemyType) {
        super(x, y, enemyType);
        teleportTime = MAX_TELEPORT_TIME;
        
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
    public void move(float time, Spellington spellington, ArrayList<Projectile> activeProjectiles, ArrayList<GameAnimation> activeAnimations, Tile[][] map) {
        if (canSeePlayer) {
            if (deltaXPlayer > 0) {
                this.animState = AnimState.STANDING_R;
            } else if (deltaXPlayer < 0) {
                this.animState = AnimState.STANDING_L;
            }
        }
        if (teleportTime == 0) {
            teleport(spellington, map, activeAnimations);
        }
        if (teleportTime > 0) {
            teleportTime -= time;
        }
        if (teleportTime < 0) {
            teleportTime = 0;
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
                        Calculations.enemyTryToShootStraightProjectile(this, spellington, activeProjectiles, mapinfo);
                        break;
                    case ELECTROMANCER:
                        Calculations.enemyTryToShootCurvedProjectile(this, spellington, activeProjectiles, mapinfo);
                        break;
                    default:
                        break;
                }
            }
        }
    }

    private void teleport(Spellington spellington, Tile[][] map, ArrayList<GameAnimation> activeAnimations) {
        int i;
        int j;
        Point temp;
        boolean teleport = false;
        do {
            i = GameCore.rand.nextInt(GameCore.DIM_MAP.height);
            j = GameCore.rand.nextInt(GameCore.DIM_MAP.width);
            if (i > 1) {
                if (map[i][j].getTileState() == Tile.TileState.IMPASSABLE
                        && map[i - 1][j].getTileState() == Tile.TileState.PASSABLE
                        && map[i - 2][j].getTileState() == Tile.TileState.PASSABLE) {
                    temp = new Point(j * Tile.DIM_TILE.width, (i - 2) * Tile.DIM_TILE.height);
                    float deltaX = spellington.getX() - temp.x;
                    float deltaY = spellington.getY() - temp.y;
                    if (Math.sqrt(deltaX * deltaX + deltaY * deltaY) > 500) {
                        activeAnimations.add(new GameAnimation(x - 25, y + 8, animTeleport.getWidth(), animTeleport.getHeight(), animTeleport.copy(), false, 0));
                        teleport = true;
                        this.setLocation(temp.x, temp.y);
                        teleportTime = MAX_TELEPORT_TIME;
                        activeAnimations.add(new GameAnimation(x - 25, y + 8, animTeleport.getWidth(), animTeleport.getHeight(), animTeleport.copy(), false, 0));
                    }
                }
            }
        } while (!teleport);
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
                animProjectile = SpellingSystem.getAnimIceSpike();
                break;
            case ELECTROMANCER:
                tempString = "electromancer";
                animProjectile = SpellingSystem.getAnimLightningBouncingBall();
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
