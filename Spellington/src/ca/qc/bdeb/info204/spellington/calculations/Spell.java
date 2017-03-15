/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ca.qc.bdeb.info204.spellington.calculations;

import ca.qc.bdeb.info204.spellington.calculations.SpellingSystem;
import ca.qc.bdeb.info204.spellington.gameentities.GameEntity;
import ca.qc.bdeb.info204.spellington.gameentities.Projectile;

/**
 *
 * @author Celtis
 */
public class Spell {
    int id;
    int damage;
    Projectile.Trajectory trajectory;
    SpellingSystem.spellKind spellKind;
    GameEntity.Elements element;
    String name;
    String incantation;
    int speedModifier;
    int nbUses;
    int masse;
    

    public Spell(int id, int damage, Projectile.Trajectory trajectory, SpellingSystem.spellKind spellKind, GameEntity.Elements element, String name, int speedModifier, int nbUses) {
        this.id = id;
        this.damage = damage;
        this.trajectory = trajectory;
        this.spellKind = spellKind;
        this.element = element;
        this.name = name;
        this.speedModifier = speedModifier;
        this.nbUses = nbUses;
    }
    
    public void spellActivation () {
    
        // selon le type de sort
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

    public SpellingSystem.spellKind getSpellKind() {
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
