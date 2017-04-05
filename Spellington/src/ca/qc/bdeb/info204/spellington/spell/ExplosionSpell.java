package ca.qc.bdeb.info204.spellington.spell;

import ca.qc.bdeb.info204.spellington.calculations.GameAnimation;
import ca.qc.bdeb.info204.spellington.gameentities.GameEntity;
import ca.qc.bdeb.info204.spellington.gameentities.Projectile;
import ca.qc.bdeb.info204.spellington.gameentities.Spellington;
import java.util.ArrayList;
import org.newdawn.slick.Animation;
import org.newdawn.slick.Input;

/**
 *
 * @author Fallen Angel
 */
public class ExplosionSpell extends Spell {

    private int damage;

    public ExplosionSpell(int id, GameEntity.ElementalType element, String name, int uses, Animation animation, int width, int height, int damage, float ray) {
        super(id, element, name, uses, animation, width, height);
        this.damage = damage;
    }

    @Override
    public void spellActivation(Spellington spellington, Input input, ArrayList<GameAnimation> activeAnimations, ArrayList<Projectile> activeProjectiles) {
        exposionSpellOnMouse(input);
    }

    private void exposionSpellOnMouse(Input input) {

    }

    @Override
    public void endOfActivation(Spellington spellington) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    

}
