package ca.qc.bdeb.info204.spellington.spell;

import ca.qc.bdeb.info204.spellington.gameentities.GameAnimation;
import ca.qc.bdeb.info204.spellington.calculations.SpellingSystem;
import ca.qc.bdeb.info204.spellington.gameentities.GameEntity.ElementalType;
import ca.qc.bdeb.info204.spellington.gameentities.Projectile;
import ca.qc.bdeb.info204.spellington.gameentities.Spellington;
import ca.qc.bdeb.info204.spellington.gameentities.enemies.Enemy;
import ca.qc.bdeb.info204.spellington.gamestates.PlayState;
import java.util.ArrayList;
import org.newdawn.slick.Animation;
import org.newdawn.slick.Input;

/**
 * A spell that can be used on a limited basis by the player.
 *
 * @author Tarik
 * @see Spell
 */
public class Potion extends Spell {

    public Potion(int id, String name, String desc, int uses, Animation animation, int width, int height) {
        super(id, ElementalType.NEUTRAL, name, desc, uses, animation, width, height);
    }

    @Override
    public void spellActivation(Spellington spellington, Input input, ArrayList<GameAnimation> activeAnimations, ArrayList<Projectile> activeProjectiles, ArrayList<Enemy> activeEnemy) {
        if (this.id == SpellingSystem.ID_POTION_TIME) {
            PlayState.setSlowDownTime(5000);
        } else if (this.id == SpellingSystem.ID_POTION_PAST) {
            SpellingSystem.pastSpellPotion(spellington, activeAnimations);
        }

    }

    public void endOfActivation(Spellington spellington, ArrayList<GameAnimation> activeAnimations) {

    }

}
