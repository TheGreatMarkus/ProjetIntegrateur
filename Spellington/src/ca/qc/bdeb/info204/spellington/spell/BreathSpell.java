package ca.qc.bdeb.info204.spellington.spell;

import ca.qc.bdeb.info204.spellington.GameCore;
import ca.qc.bdeb.info204.spellington.calculations.Calculations;
import ca.qc.bdeb.info204.spellington.calculations.GameAnimation;
import ca.qc.bdeb.info204.spellington.calculations.SpellingSystem;
import ca.qc.bdeb.info204.spellington.calculations.Vector2D;
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
    public void spellActivation(Spellington spellington, Input input, ArrayList<GameAnimation> activeAnimations, ArrayList<Projectile> activeProjectiles, ArrayList<Enemy> activeEnemy) {
        if (this.id == SpellingSystem.ID_FIRE_BREATH) {
            for (int j = 0; j < 5; j++) {
                Projectile tempProjectile = this.createSpellProjectileBreath(spellington, input);
                activeProjectiles.add(tempProjectile);
            }
        } else if (this.id == SpellingSystem.ID_ICE_BREATH) {
            for (int j = 0; j < 15; j++) {
                Projectile tempProjectile = this.createSpellProjectileBreath(spellington, input);
                activeProjectiles.add(tempProjectile);
            }
        }
    }

    @Override
    public void endOfActivation(Spellington spellington, ArrayList<GameAnimation> activeAnimations) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public Projectile createSpellProjectileBreath(Spellington spellington, Input input) {
        Projectile tempProj;
        float originX = spellington.getCenterX();
        float originY = spellington.getCenterY();
        float mouseX = (float) input.getMouseX() / GameCore.SCALE;
        float mouseY = (float) input.getMouseY() / GameCore.SCALE;
        float angle = Calculations.detAngle(mouseX - originX, mouseY - originY);
        Vector2D temp = new Vector2D(initSpeed, angle + (this.angle * (float)(Math.random()*2-1)), true);
        System.out.println(temp.getX());
        System.out.println(temp.getY());
        tempProj = new Projectile(originX - width / 2, originY - height / 2, width, height, temp, gravModifier, animation);

        return tempProj;
    }
}
