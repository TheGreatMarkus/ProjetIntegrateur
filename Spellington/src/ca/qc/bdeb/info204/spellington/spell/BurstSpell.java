package ca.qc.bdeb.info204.spellington.spell;

import ca.qc.bdeb.info204.spellington.GameCore;
import ca.qc.bdeb.info204.spellington.calculations.Calculations;
import ca.qc.bdeb.info204.spellington.gameentities.GameAnimation;
import ca.qc.bdeb.info204.spellington.gameentities.GameEntity.ElementalType;
import ca.qc.bdeb.info204.spellington.gameentities.Projectile;
import ca.qc.bdeb.info204.spellington.gameentities.Spellington;
import ca.qc.bdeb.info204.spellington.gameentities.enemies.Enemy;
import java.util.ArrayList;
import org.newdawn.slick.Animation;
import org.newdawn.slick.Input;

/**
 * A ProjectileSpell that shoots many projectiles per use.
 *
 * @author Cristian Aldea
 * @see ProjectileSpell
 */
public class BurstSpell extends ProjectileSpell {

    private float angleDeviation;
    private int projectileNumber;

    public BurstSpell(int id, ElementalType element, String name, String shortDescription, int uses, Animation animation, float size, float initSpeed, float gravModifier, int damage, float angle, int projectileNumber) {
        super(id, element, name, shortDescription, uses, animation, size, initSpeed, gravModifier, damage);
        this.angleDeviation = angle;
        this.projectileNumber = projectileNumber;
    }

    @Override
    public void spellActivation(Spellington spellington, Input input, ArrayList<GameAnimation> activeAnimations, ArrayList<Projectile> activeProjectiles, ArrayList<Enemy> activeEnemy) {
        for (int j = 0; j < projectileNumber; j++) {
            Projectile tempProjectile = this.createSpellProjectile(spellington, input);
            activeProjectiles.add(tempProjectile);
        }
    }

    @Override
    public void endOfActivation(Spellington spellington, ArrayList<GameAnimation> activeAnimations) {
    }

    @Override
    public float detAngle(Spellington spellington, Input input) {
        return Calculations.detAngle((float) input.getMouseX() / GameCore.SCALE - spellington.getCenterX(),
                (float) input.getMouseY() / GameCore.SCALE - spellington.getCenterY())
                + (this.angleDeviation * ((float) (Math.random() * 2.0) - 1.0f));
    }

    public float getAngleDeviation() {
        return angleDeviation;
    }

    public void setAngleDeviation(float angleDeviation) {
        this.angleDeviation = angleDeviation;
    }

    public int getProjectileNumber() {
        return projectileNumber;
    }

    public void setProjectileNumber(int projectileNumber) {
        this.projectileNumber = projectileNumber;
    }

}
