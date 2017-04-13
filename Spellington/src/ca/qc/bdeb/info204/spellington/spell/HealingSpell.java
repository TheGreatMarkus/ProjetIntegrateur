package ca.qc.bdeb.info204.spellington.spell;

import ca.qc.bdeb.info204.spellington.calculations.GameAnimation;
import ca.qc.bdeb.info204.spellington.gameentities.GameEntity;
import ca.qc.bdeb.info204.spellington.gameentities.Projectile;
import ca.qc.bdeb.info204.spellington.gameentities.Spellington;
import ca.qc.bdeb.info204.spellington.gameentities.enemies.Enemy;
import java.util.ArrayList;
import org.newdawn.slick.Animation;
import org.newdawn.slick.Input;

/**
 *
 * @author Fallen Angel
 */
public class HealingSpell extends Spell {

    private int healing;

    public HealingSpell(int id, String name, int uses, Animation animation, int width, int height, int healing) {
        super(id, GameEntity.ElementalType.NEUTRAL, name, uses, animation, width, height);
        this.healing = healing;
    }

    @Override
    public void spellActivation(Spellington spellington, Input input, ArrayList<GameAnimation> activeAnimations, ArrayList<Projectile> activeProjectiles, ArrayList<Enemy> activeEnemy) {
        spellington.addLifePoint(this.healing);
    }

    @Override
    public void endOfActivation(Spellington spellington, ArrayList<GameAnimation> activeAnimations) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
