package ca.qc.bdeb.info204.spellington.spell;

import ca.qc.bdeb.info204.spellington.GameCore;
import ca.qc.bdeb.info204.spellington.calculations.Calculations;
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
public class BreathSpell extends ProjectileSpell {

    private float angleDeviation;
    private int projectileNumber;

    public BreathSpell(int id, GameEntity.ElementalType element, String name, String shortDescription, int uses, Animation animation, float size, float initSpeed, float gravModifier, int c, float angle, int projectileNumber) {
        super(id, element, name, shortDescription, uses, animation, size, initSpeed, gravModifier, projectileNumber);
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

    public int getDamage() {
        return damage;
    }

    public void setDamage(int damage) {
        this.damage = damage;

    }

    public float getInitSpeed() {
        return initSpeed;
    }

    public void setInitSpeed(float initSpeed) {
        this.initSpeed = initSpeed;
    }

    public float getGravModifier() {
        return gravModifier;
    }

    public void setGravModifier(float gravModifier) {
        this.gravModifier = gravModifier;
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
