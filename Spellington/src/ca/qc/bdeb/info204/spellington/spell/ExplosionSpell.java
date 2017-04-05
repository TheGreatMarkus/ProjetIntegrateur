package ca.qc.bdeb.info204.spellington.spell;

import ca.qc.bdeb.info204.spellington.calculations.GameAnimation;
import ca.qc.bdeb.info204.spellington.gameentities.Enemy;
import ca.qc.bdeb.info204.spellington.gameentities.GameEntity;
import ca.qc.bdeb.info204.spellington.gameentities.Projectile;
import ca.qc.bdeb.info204.spellington.gameentities.Spellington;
import java.util.ArrayList;
import org.newdawn.slick.Animation;
import org.newdawn.slick.Input;
import org.newdawn.slick.geom.Circle;
import org.newdawn.slick.geom.Rectangle;

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
        exposionSpellOnMouse(input, activeEnemy);
    }
    
    private void exposionSpellOnMouse(Input input, ArrayList<Enemy> activeEnemy) {
        Circle explo = new Circle(input.getMouseX(), input.getMouseY(), ray);
        for (int i = 0; i < activeEnemy.size(); i++) {
            if (explo.intersects(activeEnemy.get(i))) {
                activeEnemy.get(i).subLifePoint(this.damage, this.element);
            }
        }
    }
    
    @Override
    public void endOfActivation(Spellington spellington) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
