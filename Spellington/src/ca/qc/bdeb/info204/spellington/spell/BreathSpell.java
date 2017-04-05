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
public class BreathSpell extends Spell {

    private float initSpeed;
    private float gravModifier;
    private int damage;
    private float angle;
    private int projectileQuantity;

    public BreathSpell(int id, GameEntity.ElementalType element, String name, int uses, Animation animation, int width, int height, float initSpeed, float gravModifier, int damage, float angle, int projectileQuantity) {
        super(id, element, name, uses, animation, width, height);
        this.initSpeed = initSpeed;
        this.gravModifier = gravModifier;
        this.damage = damage;
        this.angle = angle;
        this.projectileQuantity = projectileQuantity;
    }

    @Override
    public void spellActivation(Spellington spellington, Input input, ArrayList<GameAnimation> activeAnimations, ArrayList<Projectile> activeProjectiles) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void endOfActivation(Spellington spellington) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
