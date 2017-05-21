package ca.qc.bdeb.info204.spellington.spell;

import ca.qc.bdeb.info204.spellington.gameentities.GameAnimation;
import ca.qc.bdeb.info204.spellington.calculations.SpellingSystem;
import ca.qc.bdeb.info204.spellington.gameentities.GameEntity.ElementalType;
import ca.qc.bdeb.info204.spellington.gameentities.Projectile;
import ca.qc.bdeb.info204.spellington.gameentities.Spellington;
import ca.qc.bdeb.info204.spellington.gameentities.enemies.Enemy;
import java.util.ArrayList;
import org.newdawn.slick.Animation;
import org.newdawn.slick.Input;

/**
 * A Spell that grants passive benefits to the player.
 *
 * @author Cristian Aldea
 * @see Spell
 */
public class PassiveSpell extends Spell {

    protected GameAnimation PassiveSpellAnimation;
    private static int airJumps = 4;
    private static int resistance = 5;
    private static int immunity = 999;

    public PassiveSpell(int id, ElementalType element, String name, String desc, Animation animation, int width, int height, int yModifier) {
        super(id, element, name, desc, 0, animation, width, height);
        PassiveSpellAnimation = new GameAnimation(-200, -200, width, height, animation.copy(), true, yModifier);

    }

    @Override
    public void spellActivation(Spellington spellington, Input input, ArrayList<GameAnimation> activeAnimations, ArrayList<Projectile> activeProjectiles, ArrayList<Enemy> activeEnemy) {
        if (this.id == SpellingSystem.ID_ASCENDING_CURRENT) {
            spellington.setMaxAirJumps(spellington.getAirJumps() + airJumps);
        } else if (this.id == SpellingSystem.ID_FIRE_RES) {
            spellington.setResFire(spellington.getResFire() + resistance);
        } else if (this.id == SpellingSystem.ID_ICE_RES) {
            spellington.setResIce(spellington.getResIce() + resistance);
        } else if (this.id == SpellingSystem.ID_LIGHTNING_RES) {
            spellington.setResElectricity(spellington.getResElectricity() + resistance);
        } else if (this.id == SpellingSystem.ID_FIRE_IMMUNITY) {
            spellington.setResFire(spellington.getResFire() + immunity);
        } else if (this.id == SpellingSystem.ID_ICE_IMMUNITY) {
            spellington.setResIce(spellington.getResIce() + immunity);
        } else if (this.id == SpellingSystem.ID_LIGHTNING_IMMUNITY) {
            spellington.setResElectricity(spellington.getResElectricity() + immunity);
        }

        activeAnimations.add(PassiveSpellAnimation);

    }

    @Override
    public void endOfActivation(Spellington spellington, ArrayList<GameAnimation> activeAnimations) {
        if (this.id == SpellingSystem.ID_ASCENDING_CURRENT) {
            spellington.setMaxAirJumps(spellington.getAirJumps() - airJumps);
        } else if (this.id == SpellingSystem.ID_FIRE_RES) {
            spellington.setResFire(spellington.getResFire() - 5);
        } else if (this.id == SpellingSystem.ID_ICE_RES) {
            spellington.setResIce(spellington.getResIce() - 5);
        } else if (this.id == SpellingSystem.ID_LIGHTNING_RES) {
            spellington.setResElectricity(spellington.getResElectricity() - 5);
        } else if (this.id == SpellingSystem.ID_FIRE_IMMUNITY) {
            spellington.setResFire(spellington.getResFire() - 999);
        } else if (this.id == SpellingSystem.ID_ICE_IMMUNITY) {
            spellington.setResIce(spellington.getResIce() - 999);
        } else if (this.id == SpellingSystem.ID_LIGHTNING_IMMUNITY) {
            spellington.setResElectricity(spellington.getResElectricity() - 999);
        }

        activeAnimations.remove(PassiveSpellAnimation);
    }

}
