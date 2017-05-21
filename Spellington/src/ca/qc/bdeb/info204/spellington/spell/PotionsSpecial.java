package ca.qc.bdeb.info204.spellington.spell;

import ca.qc.bdeb.info204.spellington.gameentities.GameAnimation;
import ca.qc.bdeb.info204.spellington.calculations.SpellingSystem;
import ca.qc.bdeb.info204.spellington.gameentities.GameEntity;
import ca.qc.bdeb.info204.spellington.gameentities.Projectile;
import ca.qc.bdeb.info204.spellington.gameentities.Spellington;
import ca.qc.bdeb.info204.spellington.gameentities.enemies.Enemy;
import java.util.ArrayList;
import org.newdawn.slick.Animation;
import org.newdawn.slick.Input;

/**
 * A spell that can be used on a limited basis by the player.
 *
 * @author Tarik
 * @see Spell
 */
public class PotionsSpecial extends Spell {

    public PotionsSpecial(int id, int uses, Animation animation, int width, int height) {
        super(id, GameEntity.ElementalType.NEUTRAL, uses, animation, width, height);
    }

    @Override
    public void spellActivation(Spellington spellington, Input input, ArrayList<GameAnimation> activeAnimations, ArrayList<Projectile> activeProjectiles, ArrayList<Enemy> activeEnemy) {
        if (this.id == SpellingSystem.ID_POTION_PAST) {
            SpellingSystem.pastSpellPotion(spellington, activeAnimations);
            activeAnimations.add(new GameAnimation(spellington.getX() - 20, spellington.getY() - 10, width, height, animation.copy(), false, 0));
        }

    }

    @Override
    public void endOfActivation(Spellington spellington, ArrayList<GameAnimation> activeAnimations) {

    }

}
