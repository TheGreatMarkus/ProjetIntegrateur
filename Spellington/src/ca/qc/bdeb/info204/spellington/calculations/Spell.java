/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ca.qc.bdeb.info204.spellington.calculations;

import ca.qc.bdeb.info204.spellington.calculations.SpellingSystem;
import ca.qc.bdeb.info204.spellington.calculations.SpellingSystem.SpellKind;
import ca.qc.bdeb.info204.spellington.gameentities.GameEntity;
import ca.qc.bdeb.info204.spellington.gameentities.Projectile;
import ca.qc.bdeb.info204.spellington.gameentities.Spellington;
import org.newdawn.slick.Animation;

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
    private int GRAVITY_MODIFIER;

    public Spell(int id, int damage, Projectile.Trajectory trajectory, SpellKind spellKind, GameEntity.Elements element, String name, int speedModifier, int nbUses,int GRAVITY_MODIFIER) {
        this.id = id;
        this.damage = damage;
        this.trajectory = trajectory;
        this.spellKind = spellKind;
        this.element = element;
        this.name = name;
        this.speedModifier = speedModifier;
        this.nbUses = nbUses;
        this.GRAVITY_MODIFIER = GRAVITY_MODIFIER;
    }
    
    public void spellActivation () {
    
        // selon le type de sort
    }
    
    public Projectile createSpellProjectile (Spellington spellington) {
        Projectile tempProj;
        
        if (spellKind.equals(SpellKind.projectile)) {
     tempProj = new Projectile(spellington.getX(), spellington.getY(),new Animation(),GRAVITY_MODIFIER); 
        } else {
            tempProj = null;
        }
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

    
    
}
