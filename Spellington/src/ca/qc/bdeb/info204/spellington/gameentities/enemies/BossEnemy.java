package ca.qc.bdeb.info204.spellington.gameentities.enemies;

import ca.qc.bdeb.info204.spellington.gameentities.Projectile;
import ca.qc.bdeb.info204.spellington.gameentities.Spellington;
import ca.qc.bdeb.info204.spellington.gameentities.Tile;
import java.util.ArrayList;
import org.newdawn.slick.Graphics;

/**
 *
 * @author 1522888
 */
public class BossEnemy extends Enemy {

    public BossEnemy(float x, float y, MouvementState mouvementState, float GRAVITY_MODIFIER, EnemyType enemyType) {
        super(x, y, mouvementState, GRAVITY_MODIFIER, enemyType);
    }

    @Override
    public void render(Graphics g) {

    }

    @Override
    public void move(float time, Spellington spellington, ArrayList<Projectile> activeProjectiles, Tile[][] mapinfo) {
    }

    @Override
    public void attack(float time, Spellington spellington, ArrayList<Projectile> activeProjectiles, Tile[][] mapinfo) {
    }

    @Override
    public void loadAnimations() {
    }

}
