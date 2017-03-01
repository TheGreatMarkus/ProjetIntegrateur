/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ca.qc.bdeb.info204.spellington.gameentities;

import java.util.ArrayList;
import javafx.scene.input.KeyCode;
import org.newdawn.slick.Input;
import org.newdawn.slick.KeyListener;

/**
 *
 * @author Celtis
 */
public class SpellingSystem {
    public enum spellKind {projectile, healing, breath, explosion, passive}
    
   private static Spell passiveSpell;
   private static Spell activeSpell;
   private static int nbSpellUses = 0;
    
   private static String incantationText;
    
   private static ArrayList<Spell> knownSpell = new ArrayList<>();
   private static ArrayList<Spell> Spells = new ArrayList<>();
   private static ArrayList<Spell> tutoSpell = new ArrayList<>();
   private static ArrayList<Spell> adeptLavaSpell = new ArrayList<>();
   private static ArrayList<Spell> adeptIceSpell = new ArrayList<>();
   private static ArrayList<Spell> adeptChocSpell = new ArrayList<>();
   private static ArrayList<Spell> masterLavaSpell = new ArrayList<>();
   private static ArrayList<Spell> masterIceSpell = new ArrayList<>();
   private static ArrayList<Spell> masterChocSpell = new ArrayList<>();

    public SpellingSystem() {
    }
    
    public static void init () {
        Spell fireBall = new Spell(1,5,Projectile.Trajectory.curved,SpellingSystem.spellKind.projectile,GameEntity.Elements.FIRE,"Boule de feu",0,4);
        Spell icePic = new Spell(2,5,Projectile.Trajectory.strait,SpellingSystem.spellKind.projectile,GameEntity.Elements.ICE,"Pic de glace",0,3);
        Spell sparkle = new Spell(3,5,Projectile.Trajectory.curved,SpellingSystem.spellKind.explosion,GameEntity.Elements.ELECTRICITY,"Etincelle",0,2);
        Spell heal = new Spell(4,10,Projectile.Trajectory.curved,SpellingSystem.spellKind.healing,GameEntity.Elements.NEUTRAL,"Soin",0,1);
        Spell upStream = new Spell(5,0,Projectile.Trajectory.curved,SpellingSystem.spellKind.passive,GameEntity.Elements.NEUTRAL,"Courant ascendant",0,0);
        Spell fireResistance = new Spell(6,10,Projectile.Trajectory.curved,SpellingSystem.spellKind.passive,GameEntity.Elements.FIRE,"Résistance feu",0,0);
        Spell iceResistance = new Spell(7,10,Projectile.Trajectory.curved,SpellingSystem.spellKind.passive,GameEntity.Elements.ICE,"Résistance glace",0,0);
        Spell lightningResistance = new Spell(8,10,Projectile.Trajectory.curved,SpellingSystem.spellKind.passive,GameEntity.Elements.ELECTRICITY,"Résistance electrique",0,0);
        Spell explosiveBall = new Spell(9,10,Projectile.Trajectory.curved,SpellingSystem.spellKind.projectile,GameEntity.Elements.FIRE,"Boule explosive",0,2);
        Spell fireBreath = new Spell(10,1,Projectile.Trajectory.curved,SpellingSystem.spellKind.breath,GameEntity.Elements.FIRE,"Soufle de feu",0,300);
        Spell giantFireBall = new Spell(11,20,Projectile.Trajectory.curved,SpellingSystem.spellKind.projectile,GameEntity.Elements.FIRE,"Grosse boule de feu",0,2);
        Spell lightningSwarm = new Spell(12,3,Projectile.Trajectory.curved,SpellingSystem.spellKind.explosion,GameEntity.Elements.ELECTRICITY,"Essain d'eclairs",0,1);
        Spell teleportation = new Spell(13,0,Projectile.Trajectory.curved,SpellingSystem.spellKind.projectile,GameEntity.Elements.NEUTRAL,"Teleportation",0,1);
        Spell lightningBouncingBall = new Spell(14,10,Projectile.Trajectory.curved,SpellingSystem.spellKind.projectile,GameEntity.Elements.ELECTRICITY,"Boule electrique rebondissante",0,2);
        Spell iceBreath = new Spell(15,1,Projectile.Trajectory.curved,SpellingSystem.spellKind.breath,GameEntity.Elements.ICE,"Souffle de glace",0,300);
        Spell iceSpikyBall = new Spell(16,10,Projectile.Trajectory.curved,SpellingSystem.spellKind.projectile,GameEntity.Elements.ICE,"Boule a pointes de glace",0,2);
        Spell iceRune = new Spell(17,20,Projectile.Trajectory.curved,SpellingSystem.spellKind.explosion,GameEntity.Elements.ICE,"Rune de glace",0,1);
        Spell fireImmunity = new Spell(18,999,Projectile.Trajectory.curved,SpellingSystem.spellKind.passive,GameEntity.Elements.FIRE,"Immunite feu",0,0);
        Spell meteorSwarm = new Spell(19,20,Projectile.Trajectory.curved,SpellingSystem.spellKind.explosion,GameEntity.Elements.FIRE,"Pluie de meteors",0,1);
        Spell lightningImmunity = new Spell(20,999,Projectile.Trajectory.curved,SpellingSystem.spellKind.passive,GameEntity.Elements.ELECTRICITY,"Immunite electrique",0,0);
        Spell lightningSpear = new Spell(21,60,Projectile.Trajectory.strait,SpellingSystem.spellKind.projectile,GameEntity.Elements.ELECTRICITY,"Lance de foudre",0,1);
        Spell iceStorm = new Spell(22,20,Projectile.Trajectory.curved,SpellingSystem.spellKind.explosion,GameEntity.Elements.ICE,"Tempete de glace",0,1);
        Spell iceImmunity = new Spell(23,999,Projectile.Trajectory.curved,SpellingSystem.spellKind.passive,GameEntity.Elements.ICE,"Immunite glace",0,0);
        Spell majorHealing = new Spell(24,100,Projectile.Trajectory.curved,SpellingSystem.spellKind.healing,GameEntity.Elements.NEUTRAL,"Soin majeur",0,1);
        
        Spells.add(fireBall);
        Spells.add(icePic);
        Spells.add(sparkle);
        Spells.add(heal);
        Spells.add(upStream);
        Spells.add(fireResistance);
        Spells.add(iceResistance);
        Spells.add(lightningResistance);
        Spells.add(explosiveBall);
        Spells.add(fireBreath);
        Spells.add(giantFireBall);
        Spells.add(lightningSwarm);
        Spells.add(teleportation);
        Spells.add(lightningBouncingBall);
        Spells.add(iceBreath);
        Spells.add(iceSpikyBall);
        Spells.add(iceRune);
        Spells.add(fireImmunity);
        Spells.add(meteorSwarm);
        Spells.add(lightningImmunity);
        Spells.add(lightningSpear);
        Spells.add(iceStorm);
        Spells.add(iceImmunity);
        Spells.add(majorHealing);
        
        tutoSpell.add(fireBall);
        tutoSpell.add(icePic);
        tutoSpell.add(sparkle);
        tutoSpell.add(heal);
        tutoSpell.add(upStream);
        tutoSpell.add(fireResistance);
        tutoSpell.add(iceResistance);
        tutoSpell.add(lightningResistance);
        
        adeptLavaSpell.add(explosiveBall);
        adeptLavaSpell.add(fireBreath);
        adeptLavaSpell.add(giantFireBall);
        
        adeptChocSpell.add(lightningSwarm);
        adeptChocSpell.add(teleportation);
        adeptChocSpell.add(lightningBouncingBall);
        
        adeptIceSpell.add(iceBreath);
        adeptIceSpell.add(iceSpikyBall);
        adeptIceSpell.add(iceRune);
        
        masterLavaSpell.add(fireImmunity);
        masterLavaSpell.add(meteorSwarm);
        
        masterChocSpell.add(lightningImmunity);
        masterChocSpell.add(lightningSpear);
        
        masterIceSpell.add(iceStorm);
        masterIceSpell.add(iceImmunity);
        
        knownSpell.add(fireBall);
        knownSpell.add(icePic);
        knownSpell.add(sparkle);
        knownSpell.add(heal);
        knownSpell.add(upStream);
        knownSpell.add(fireResistance);
        knownSpell.add(iceResistance);
        knownSpell.add(lightningResistance);
    }
    
    public static void newKnownSpell (Spell newSpell) {
    knownSpell.add(newSpell);   
    }
    
    public static void update (Input input, Spellington mainMage, ArrayList<Projectile> projectileList) {
        
        ArrayList<Integer> letters = new ArrayList<>();
        letters.add(Input.KEY_A);
        letters.add(Input.KEY_B);
        letters.add(Input.KEY_C);
        letters.add(Input.KEY_D);
        letters.add(Input.KEY_E);
        letters.add(Input.KEY_F);
        letters.add(Input.KEY_G);
        letters.add(Input.KEY_H);
        letters.add(Input.KEY_I);
        letters.add(Input.KEY_J);
        letters.add(Input.KEY_K);
        letters.add(Input.KEY_L);
        letters.add(Input.KEY_M);
        letters.add(Input.KEY_N);
        letters.add(Input.KEY_O);
        letters.add(Input.KEY_P);
        letters.add(Input.KEY_Q);
        letters.add(Input.KEY_R);
        letters.add(Input.KEY_S);
        letters.add(Input.KEY_T);
        letters.add(Input.KEY_U);
        letters.add(Input.KEY_V);
        letters.add(Input.KEY_W);
        letters.add(Input.KEY_X);
        letters.add(Input.KEY_Y);
        letters.add(Input.KEY_Z);
        
                
        for (int i = 0; i < letters.size() ; i++) {
            if (input.isKeyPressed(letters.get(i))) {
                incantationText = incantationText + Input.getKeyName(letters.get(i));
            }
        }
        
        boolean tabUsed = false;
        if(input.isKeyPressed(Input.KEY_TAB)){
            tabUsed = true;
        }
        
        if(tabUsed) {
        
        boolean newSpell = false;
        
            for (int i = 0; i < knownSpell.size(); i++) {
                if (incantationText.equals(knownSpell.get(i).getIncantation())) {
                    if(knownSpell.get(i).getSpellKind() == spellKind.passive) {
                    
                        passiveSpell = knownSpell.get(i);
                        newSpell = true;
                        
                    } else {
                    activeSpell = knownSpell.get(i);
                    nbSpellUses = knownSpell.get(i).getNbUses();
                    newSpell = true;
                    }
                }

            }
            
            incantationText = "";
            
            if(!newSpell && activeSpell != null) {
            activeSpell.spellActivation();
            nbSpellUses--;
            }
            if(nbSpellUses <= 0) {
            activeSpell = null;
            nbSpellUses =0;
            }
        }
        
    }
   
    public static void spellActivation () {
    }
    
    
}
