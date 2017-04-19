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

/**
 *
 * @author Fallen Angel
 */
public class PassiveSpell extends Spell {

    protected GameAnimation PassiveSpellAnimation;

    public PassiveSpell(int id, GameEntity.ElementalType element, String name, String shortDescription, Animation animation, int width, int height, int yModifier) {
        super(id, element, name, "Passif", shortDescription, 0, animation, width, height);
        PassiveSpellAnimation = new GameAnimation(-200, -200, width, height, animation, -1, yModifier);

    }

    @Override
    public void spellActivation(Spellington spellington, Input input, ArrayList<GameAnimation> activeAnimations, ArrayList<Projectile> activeProjectiles, ArrayList<Enemy> activeEnemy) {
        if (this.id == SpellingSystem.ID_ASCENDING_CURRENT) {
            spellington.setMAX_AIR_JUMPS(5);
        } else if (this.id == SpellingSystem.ID_FIRE_RES) {
            spellington.setResFire(spellington.getResFire() + 5);
        } else if (this.id == SpellingSystem.ID_ICE_RES) {
            spellington.setResIce(spellington.getResIce() + 5);
        } else if (this.id == SpellingSystem.ID_LIGHTNING_RES) {
            spellington.setResElectricity(spellington.getResElectricity() + 5);
        } else if (this.id == SpellingSystem.ID_FIRE_IMMUNITY) {
            spellington.setResFire(spellington.getResFire() + 999);
        } else if (this.id == SpellingSystem.ID_ICE_IMMUNITY) {
            spellington.setResIce(spellington.getResIce() + 999);
        } else if (this.id == SpellingSystem.ID_LIGHTNING_IMMUNITY) {
            spellington.setResElectricity(spellington.getResElectricity() + 999);
        }

        activeAnimations.add(PassiveSpellAnimation);

    }

    @Override
    public void endOfActivation(Spellington spellington, ArrayList<GameAnimation> activeAnimations) {
        if (this.id == SpellingSystem.ID_ASCENDING_CURRENT) {
            spellington.setMAX_AIR_JUMPS(1);
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
