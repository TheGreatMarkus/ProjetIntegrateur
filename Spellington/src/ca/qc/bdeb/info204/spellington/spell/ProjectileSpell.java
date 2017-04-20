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
public class ProjectileSpell extends Spell {

    private float initSpeed;
    private float gravModifier;
    private int damage;

    public ProjectileSpell(int id, GameEntity.ElementalType element, String name, String shortDescription, int uses, Animation animation, int width, int height, float initSpeed, float gravModifier, int damage) {
        super(id, element, name, "Projectile", shortDescription, uses, animation, width, height);
        this.initSpeed = initSpeed;
        this.gravModifier = gravModifier;
        this.damage = damage;
    }

    public Projectile createSpellProjectile(Spellington spellington, Input input) {
        Projectile tempProj;
        float originX = spellington.getCenterX();
        float originY = spellington.getCenterY();
        float mouseX = (float) input.getMouseX() / GameCore.SCALE;
        float mouseY = (float) input.getMouseY() / GameCore.SCALE;
        float angle = Calculations.detAngle(mouseX - originX, mouseY - originY);
        Vector2D temp = new Vector2D(initSpeed, angle, true);
        tempProj = new Projectile(originX - width / 2, originY - height / 2, width, height, temp, gravModifier, animation, this.damage, this.element);

        return tempProj;
    }

    @Override
    public void spellActivation(Spellington spellington, Input input, ArrayList<GameAnimation> activeAnimations, ArrayList<Projectile> activeProjectiles, ArrayList<Enemy> activeEnemy) {
        Projectile tempProjectile = this.createSpellProjectile(spellington, input);
        activeProjectiles.add(tempProjectile);
    }

    @Override
    public void endOfActivation(Spellington spellington, ArrayList<GameAnimation> activeAnimations) {

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

    public void spellCollisionEffect(Spellington spellington, int x, int y, String direction) {
        if (this.id == SpellingSystem.ID_TELEPORTATION) {
            switch (direction) {
                case "top":
                    spellington.setLocation(x, y);
                    ;
                    break; //corriger les collisions selon la direction de la collision
                case "bottom":
                    spellington.setLocation(x, y);
                    ;
                    break;
                case "right":
                    spellington.setLocation(x, y);
                    ;
                    break;
                case "left":
                    spellington.setLocation(x, y);
                    ;
                    break;

            }

        }
    }

    public int getDamage() {
        return damage;
    }

    public void setDamage(int damage) {
        this.damage = damage;
    }

}
