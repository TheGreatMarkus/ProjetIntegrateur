package ca.qc.bdeb.info204.spellington.spell;

import ca.qc.bdeb.info204.spellington.gameentities.GameAnimation;
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

    public HealingSpell(int id, String name, String shortDescription, int uses, Animation animation, int width, int height, int healing) {
        super(id, GameEntity.ElementalType.NEUTRAL, name, shortDescription, uses, animation, width, height);
        this.healing = healing;
    }

    @Override
    public void spellActivation(Spellington spellington, Input input, ArrayList<GameAnimation> activeAnimations, ArrayList<Projectile> activeProjectiles, ArrayList<Enemy> activeEnemy) {
        spellington.addLifePoint(this.healing);
        activeAnimations.add(new GameAnimation(spellington.getX() - 20, spellington.getY() - 10, width, height, animation.copy(), false, 0));
    }

    @Override
    public void endOfActivation(Spellington spellington, ArrayList<GameAnimation> activeAnimations) {

    }

    public int getHealing() {
        return healing;
    }

    public void setHealing(int healing) {
        this.healing = healing;
    }
}
