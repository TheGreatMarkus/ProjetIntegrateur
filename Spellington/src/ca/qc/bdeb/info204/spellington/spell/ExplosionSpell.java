package ca.qc.bdeb.info204.spellington.spell;

import ca.qc.bdeb.info204.spellington.GameCore;
import ca.qc.bdeb.info204.spellington.gameentities.GameAnimation;
import ca.qc.bdeb.info204.spellington.calculations.SpellingSystem;
import ca.qc.bdeb.info204.spellington.gameentities.GameEntity;
import ca.qc.bdeb.info204.spellington.gameentities.Projectile;
import ca.qc.bdeb.info204.spellington.gameentities.Spellington;
import ca.qc.bdeb.info204.spellington.gameentities.enemies.Enemy;
import java.util.ArrayList;
import org.newdawn.slick.Animation;
import org.newdawn.slick.Input;
import org.newdawn.slick.geom.Circle;

/**
 * A spell that creates explosions.
 *
 * @author Cristian Aldea
 * @see Spell
 */
public class ExplosionSpell extends Spell {

    private int damage;
    private float ray;

    public ExplosionSpell(int id, GameEntity.ElementalType element, String name, String desc, int uses, Animation animation, int damage, float ray) {
        super(id, element, name, desc, uses, animation, (int) (ray * 2), (int) (ray * 2));
        this.damage = damage;
        this.ray = ray;
    }

    @Override
    public void spellActivation(Spellington spellington, Input input, ArrayList<GameAnimation> activeAnimations, ArrayList<Projectile> activeProjectiles, ArrayList<Enemy> activeEnemy) {
        float renderMouseX = input.getMouseX() / GameCore.SCALE;
        float renderMouseY = input.getMouseY() / GameCore.SCALE;
        if (this.id == SpellingSystem.ID_SPARK || this.id == SpellingSystem.ID_LIGHTNING_SWARM || this.id == SpellingSystem.ID_RUNE) {
            exposionSpellOnMouse(input, activeEnemy);

        } else if (this.id == SpellingSystem.ID_METEOR_SHOWER || this.id == SpellingSystem.ID_ICE_STORM) {
            explosionSpellGeneral(activeEnemy);
        }

        activeAnimations.add(new GameAnimation(renderMouseX - (width / 2), renderMouseY - (height / 2), width, height, animation.copy(), false, 0));
    }

    /**
     * Creates an explosion at the position of the mouse that damages all nearby
     * ennemies.
     *
     * @param input The Slick class that handles input.
     * @param activeEnemy The list of active enemies.
     */
    private void exposionSpellOnMouse(Input input, ArrayList<Enemy> activeEnemy) {
        float renderMouseX = input.getMouseX() / GameCore.SCALE;
        float renderMouseY = input.getMouseY() / GameCore.SCALE;

        Circle explosion = new Circle(renderMouseX, renderMouseY, ray);
        for (int i = 0; i < activeEnemy.size(); i++) {
            if (explosion.intersects(activeEnemy.get(i).getBounds())) {
                activeEnemy.get(i).subLifePoint(this.damage, this.element);
            }
        }
    }

    /**
     * Damages all enemies in the game.
     *
     * @param activeEnemy The list of active enemies.
     */
    private void explosionSpellGeneral(ArrayList<Enemy> activeEnemy) {
        for (int i = 0; i < activeEnemy.size(); i++) {
            activeEnemy.get(i).subLifePoint(this.damage, this.element);
        }
    }


    public int getDamage() {
        return damage;
    }

    public void setDamage(int damage) {
        this.damage = damage;
    }

    public float getRay() {
        return ray;
    }

    public void setRay(float ray) {
        this.ray = ray;
    }

}
