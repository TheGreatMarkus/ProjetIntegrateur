/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ca.qc.bdeb.info204.spellington.calculations;

import ca.qc.bdeb.info204.spellington.GameCore;
import ca.qc.bdeb.info204.spellington.calculations.SpellingSystem.SpellKind;
import ca.qc.bdeb.info204.spellington.gameentities.GameEntity;
import ca.qc.bdeb.info204.spellington.gameentities.Projectile;
import ca.qc.bdeb.info204.spellington.gameentities.Spellington;
import java.util.ArrayList;
import org.newdawn.slick.Animation;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;

/**
 *
 * @author Celtis
 */
public class Spell {

    private int id;
    private int damage;
    private Projectile.Trajectory trajectory;
    private SpellingSystem.SpellKind spellKind;
    private GameEntity.Elements element;
    private String name;
    private String incantation;
    private int speedModifier;
    private int nbUses;
    private Animation animation;
    private float GRAVITY_MODIFIER;
    private int height;
    private int width;
    private Image icon;

    public Spell(int id, int damage, Projectile.Trajectory trajectory, SpellKind spellKind, GameEntity.Elements element, String name, int speedModifier, int nbUses, float GRAVITY_MODIFIER, Animation animSpell, int width, int height) {
        this.id = id;
        this.damage = damage;
        this.trajectory = trajectory;
        this.spellKind = spellKind;
        this.element = element;
        this.name = name;
        this.speedModifier = speedModifier;
        this.nbUses = nbUses;
        this.GRAVITY_MODIFIER = GRAVITY_MODIFIER;
        this.animation = animSpell;
        this.height = height;
        this.width = width;

    }

    public void spellActivation(Spellington spellington, Input input, ArrayList<GameAnimation> activeAnimations, ArrayList<Projectile> activeProjectiles) {

        if (this.spellKind == SpellKind.HEALING) {
            int tempLife;
            if (this.id == 4) {
                tempLife = 20;

            } else {
                tempLife = spellington.getMaxLifePoint() - spellington.getLifePoint();
            }

            spellington.addLifePoint(tempLife);

        } else if (this.spellKind == SpellKind.EXPLOSION) {
            exposionSpellOnMouse(input);

        } else if (this.spellKind == SpellKind.PASSIVE) {
            if (this.id == 5) {
                spellington.setMAX_AIR_JUMPS(3);
            } else if (this.id == 6) {
                spellington.setResFire(10);
            } else if (this.id == 7) {
                spellington.setResIce(10);
            } else if (this.id == 8) {
                spellington.setResElectricity(10);
            } 

        } else if (this.spellKind == SpellKind.PROJECTILE) {
            if (this.getSpellKind() == SpellKind.PROJECTILE) {
                Projectile tempProjectile = this.createSpellProjectile(spellington, input);
                activeProjectiles.add(tempProjectile);
            }
        }

    }

    public void endOfActivation(Spellington spellington) {
        if (this.spellKind == SpellKind.PASSIVE) {
            if (this.id == 5) {
                spellington.setMAX_AIR_JUMPS(1);
            } else if (this.id == 6) {
                spellington.setResFire(0);
            } else if (this.id == 7) {
                spellington.setResIce(0);
            } else if (this.id == 8) {
                spellington.setResElectricity(0);
            } 

        }
    }

    public Projectile createSpellProjectile(Spellington spellington, Input input) {
        Projectile tempProj;
        float originX = spellington.getCenterX();
        float originY = spellington.getCenterY();
        float mouseX = (float) input.getMouseX() / GameCore.SCALE;
        float mouseY = (float) input.getMouseY() / GameCore.SCALE;
        float angle = Calculations.detAngle(mouseX - originX, mouseY - originY);
        Vector2D temp = new Vector2D(speedModifier, angle, true);
        System.out.println(temp.getX());
        System.out.println(temp.getY());
        tempProj = new Projectile(originX - width / 2, originY - height / 2, width, height, temp, GRAVITY_MODIFIER, animation);

        return tempProj;
    }

    public int getId() {
        return id;
    }

    public int getDamage() {
        return damage;
    }

    public Projectile.Trajectory getTrajectory() {
        return trajectory;
    }

    public SpellingSystem.SpellKind getSpellKind() {
        return spellKind;
    }

    public GameEntity.Elements getElement() {
        return element;
    }

    public String getName() {
        return name;
    }

    public String getIncantation() {
        return incantation;
    }

    public int getSpeedModifier() {
        return speedModifier;
    }

    public int getNbUses() {
        return nbUses;
    }

    public void setIncantation(String incantation) {
        this.incantation = incantation;
    }

    private void exposionSpellOnMouse(Input input) {

    }
}
