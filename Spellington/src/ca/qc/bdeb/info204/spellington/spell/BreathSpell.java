package ca.qc.bdeb.info204.spellington.spell;

import ca.qc.bdeb.info204.spellington.GameCore;
import ca.qc.bdeb.info204.spellington.calculations.Calculations;
import ca.qc.bdeb.info204.spellington.calculations.GameAnimation;
import ca.qc.bdeb.info204.spellington.calculations.Vector2D;
import ca.qc.bdeb.info204.spellington.gameentities.GameEntity;
import ca.qc.bdeb.info204.spellington.gameentities.Projectile;
import ca.qc.bdeb.info204.spellington.gameentities.Projectile.SourceType;
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
    private float angleDeviation;
    private int projectileNumber;

    private static final float INIT_SPEED_MOD = 0.002f;
    private static final float INIT_SPEED_MIN = 0.2f;
    private static final float INIT_SPEED_MAX = 1.2f;

    public BreathSpell(int id, GameEntity.ElementalType element, String name, String shortDescription, int uses, Animation animation, int width, int height, float initSpeed, float gravModifier, int damage, float angle, int projectileNumber) {
        super(id, element, name, "Sort de souffle", shortDescription, uses, animation, width, height);
        this.initSpeed = initSpeed;
        this.gravModifier = gravModifier;
        this.damage = damage;
        this.angleDeviation = angle;
        this.projectileNumber = projectileNumber;
    }

    @Override
    public void spellActivation(Spellington spellington, Input input, ArrayList<GameAnimation> activeAnimations, ArrayList<Projectile> activeProjectiles, ArrayList<Enemy> activeEnemy) {
        for (int j = 0; j < projectileNumber; j++) {
            Projectile tempProjectile = this.createSpellProjectileBreath(spellington, input);
            activeProjectiles.add(tempProjectile);
        }
    }

    @Override
    public void endOfActivation(Spellington spellington, ArrayList<GameAnimation> activeAnimations) {
    }

    public Projectile createSpellProjectileBreath(Spellington spellington, Input input) {
        Projectile tempProj;
        float originX = spellington.getCenterX();
        float originY = spellington.getCenterY();
        float mouseX = (float) input.getMouseX() / GameCore.scale;
        float mouseY = (float) input.getMouseY() / GameCore.scale;
        float angle = Calculations.detAngle(mouseX - originX, mouseY - originY) + (this.angleDeviation * ((float) (Math.random() * 2.0) - 1.0f));
        float distance = Vector2D.distance(originX, originY, mouseX, mouseY);
        float speedMult = distance * (INIT_SPEED_MOD * GameCore.scale);
        if (speedMult < INIT_SPEED_MIN) {
            speedMult = INIT_SPEED_MIN;
        } else if (speedMult > INIT_SPEED_MAX) {
            speedMult = INIT_SPEED_MAX;
        }
        Vector2D temp = new Vector2D(initSpeed * speedMult, angle, true);
        tempProj = new Projectile(originX - width / 2, originY - height / 2, width, height, temp, gravModifier, animation, this.damage, element, SourceType.PLAYER);

        return tempProj;

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
