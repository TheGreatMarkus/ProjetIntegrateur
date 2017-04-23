package ca.qc.bdeb.info204.spellington.spell;

import ca.qc.bdeb.info204.spellington.GameCore;
import ca.qc.bdeb.info204.spellington.calculations.GameAnimation;
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
 *
 * @author Fallen Angel
 */
public class ExplosionSpell extends Spell {

    private int damage;
    private float ray;

    public ExplosionSpell(int id, GameEntity.ElementalType element, String name, String shortDescription, int uses, Animation animation, int damage, float ray) {
        super(id, element, name, "Explosion", shortDescription, uses, animation, (int) (ray * 2), (int) (ray * 2));
        this.damage = damage;
        this.ray = ray;
    }

    @Override
    public void spellActivation(Spellington spellington, Input input, ArrayList<GameAnimation> activeAnimations, ArrayList<Projectile> activeProjectiles, ArrayList<Enemy> activeEnemy) {
        float renderMouseX = input.getMouseX() / GameCore.scale;
        float renderMouseY = input.getMouseY() / GameCore.scale;
        if (this.id == SpellingSystem.ID_SPARK || this.id == SpellingSystem.ID_LIGHTNING_SWARM || this.id == SpellingSystem.ID_ICE_RUNE) {
            exposionSpellOnMouse(input, activeEnemy);

        } else if (this.id == SpellingSystem.ID_METEOR_SWARM && this.id == SpellingSystem.ID_ICE_STORM) {
            explosionSpellGeneral(activeEnemy);
        }

        activeAnimations.add(new GameAnimation(renderMouseX - (width / 2), renderMouseY - (height / 2), width, height, animation, 30, 0));
    }

    private void exposionSpellOnMouse(Input input, ArrayList<Enemy> activeEnemy) {
        float renderMouseX = input.getMouseX() / GameCore.scale;
        float renderMouseY = input.getMouseY() / GameCore.scale;

        Circle explosion = new Circle(renderMouseX, renderMouseY, ray);
        for (int i = 0; i < activeEnemy.size(); i++) {
            if (explosion.intersects(activeEnemy.get(i))) {
                activeEnemy.get(i).subLifePoint(this.damage, this.element);
            }
        }
    }

    private void explosionSpellGeneral(ArrayList<Enemy> activeEnemy) {
        for (int i = 0; i < activeEnemy.size(); i++) {
            activeEnemy.get(i).subLifePoint(this.damage, this.element);
        }
    }

    @Override
    public void endOfActivation(Spellington spellington, ArrayList<GameAnimation> activeAnimations) {
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
