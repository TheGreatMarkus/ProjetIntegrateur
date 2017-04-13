package ca.qc.bdeb.info204.spellington.spell;

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
    
    public ExplosionSpell(int id, GameEntity.ElementalType element, String name, int uses, Animation animation, int width, int height, int damage, float ray) {
        super(id, element, name, uses, animation, width, height);
        this.damage = damage;
        this.ray = ray;
    }
    
    @Override
    public void spellActivation(Spellington spellington, Input input, ArrayList<GameAnimation> activeAnimations, ArrayList<Projectile> activeProjectiles, ArrayList<Enemy> activeEnemy) {
        if (this.id == SpellingSystem.ID_SPARK && this.id == SpellingSystem.ID_LIGHTNING_SWARM) {
            exposionSpellOnMouse(input, activeEnemy);
        } else if (this.id == SpellingSystem.ID_METEOR_SWARM && this.id == SpellingSystem.ID_ICE_STORM) {
            exposionSpellGeneral(activeEnemy);
        }
    }
    
    private void exposionSpellOnMouse(Input input, ArrayList<Enemy> activeEnemy) {
        Circle explo = new Circle(input.getMouseX(), input.getMouseY(), ray);
        for (int i = 0; i < activeEnemy.size(); i++) {
            if (explo.intersects(activeEnemy.get(i))) {
                activeEnemy.get(i).subLifePoint(this.damage, this.element);
            }
        }
    }
    
    private void exposionSpellGeneral(ArrayList<Enemy> activeEnemy) {
        for (int i = 0; i < activeEnemy.size(); i++) {
                activeEnemy.get(i).subLifePoint(this.damage, this.element);
        }
    }
    
    @Override
    public void endOfActivation(Spellington spellington, ArrayList<GameAnimation> activeAnimations) {
    }
    
}
