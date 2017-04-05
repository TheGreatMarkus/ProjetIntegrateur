package ca.qc.bdeb.info204.spellington.spell;

import ca.qc.bdeb.info204.spellington.calculations.GameAnimation;
import ca.qc.bdeb.info204.spellington.calculations.SpellingSystem;
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
public class PassiveSpell extends Spell {

    public PassiveSpell(int id, GameEntity.ElementalType element, String name, Animation animation, int width, int height) {
        super(id, element, name, 0, animation, width, height);
    }

    @Override
    public void spellActivation(Spellington spellington, Input input, ArrayList<GameAnimation> activeAnimations, ArrayList<Projectile> activeProjectiles) {
        if (this.id == SpellingSystem.ID_ASCENDING_CURRENT) {
            spellington.setMAX_AIR_JUMPS(5);
        }

    }

    @Override
    public void endOfActivation(Spellington spellington) {
        if (this.id == SpellingSystem.ID_ASCENDING_CURRENT) {
            spellington.setMAX_AIR_JUMPS(1);
        }

    }

}
