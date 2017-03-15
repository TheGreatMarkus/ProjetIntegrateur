/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ca.qc.bdeb.info204.spellington.calculations;

import ca.qc.bdeb.info204.spellington.gameentities.GameEntity;
import ca.qc.bdeb.info204.spellington.gameentities.Projectile;
import ca.qc.bdeb.info204.spellington.gameentities.Spellington;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.scene.input.KeyCode;
import org.newdawn.slick.Animation;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.KeyListener;
import org.newdawn.slick.SlickException;

/**
 *
 * @author Celtis
 */
public class SpellingSystem {
    public enum SpellKind {PROJECTILE, HEALING, BREATH, EXPLOSION, PASSIVE}
    
   private static Spell passiveSpell;
   private static Spell activeSpell;
   private static int nbSpellUses = 0;
    
   private static String incantationText = "";
   
   private static ArrayList<Integer> letters = new ArrayList<>();
    
   private static ArrayList<Spell> knownSpell = new ArrayList<>();
   private static ArrayList<Spell> spellList = new ArrayList<>();
   private static ArrayList<Spell> tutoSpell = new ArrayList<>();
   private static ArrayList<Spell> adeptLavaSpell = new ArrayList<>();
   private static ArrayList<Spell> adeptIceSpell = new ArrayList<>();
   private static ArrayList<Spell> adeptChocSpell = new ArrayList<>();
   private static ArrayList<Spell> masterLavaSpell = new ArrayList<>();
   private static ArrayList<Spell> masterIceSpell = new ArrayList<>();
   private static ArrayList<Spell> masterChocSpell = new ArrayList<>();
   
   private static Animation animFireBall;
   private static Animation animIcePic;
   private static Animation animSparkle;
   private static Animation animHeal;
   private static Animation animUpStream;
   private static Animation animFireResistance;
   private static Animation animIceResistance;
   private static Animation animLightningResistance;
   private static Animation animExplosiveBall;
   private static Animation animFireBreath;
   private static Animation animGiantFireBall;
   private static Animation animLightningSwarm;
   private static Animation animTeleportation;
   private static Animation animLightningBouncingBall;
   private static Animation animIceBreath;
   private static Animation animIceSpikyBall;
   private static Animation animIceRune;
   private static Animation animFireImmunity;
   private static Animation animMeteorSwarm;
   private static Animation animLightningImmunity;
   private static Animation animLightningSpear;
   private static Animation animIceStorm;
   private static Animation animIceImmunity;
   private static Animation animMajorHealing;

    public static void initSpellingSystem() {
        
        Spell fireBall = new Spell(1,5,Projectile.Trajectory.curved,SpellKind.PROJECTILE,GameEntity.Elements.FIRE,"Boule de feu",0,4,1,animFireBall);
        Spell icePic = new Spell(2,5,Projectile.Trajectory.strait,SpellingSystem.SpellKind.PROJECTILE,GameEntity.Elements.ICE,"Pic de glace",0,3,1f,animIcePic);
        Spell sparkle = new Spell(3,5,Projectile.Trajectory.curved,SpellingSystem.SpellKind.EXPLOSION,GameEntity.Elements.ELECTRICITY,"Etincelle",0,2,1f,animSparkle);
        Spell heal = new Spell(4,10,Projectile.Trajectory.curved,SpellingSystem.SpellKind.HEALING,GameEntity.Elements.NEUTRAL,"Soin",0,1,1f,animHeal);
        Spell upStream = new Spell(5,0,Projectile.Trajectory.curved,SpellingSystem.SpellKind.PASSIVE,GameEntity.Elements.NEUTRAL,"Courant ascendant",0,0,1f,animUpStream);
        Spell fireResistance = new Spell(6,10,Projectile.Trajectory.curved,SpellingSystem.SpellKind.PASSIVE,GameEntity.Elements.FIRE,"Résistance feu",0,0,1f,animFireResistance);
        Spell iceResistance = new Spell(7,10,Projectile.Trajectory.curved,SpellingSystem.SpellKind.PASSIVE,GameEntity.Elements.ICE,"Résistance glace",0,0,1f,animIceResistance);
        Spell lightningResistance = new Spell(8,10,Projectile.Trajectory.curved,SpellingSystem.SpellKind.PASSIVE,GameEntity.Elements.ELECTRICITY,"Résistance electrique",0,0,1f,animLightningResistance);
        Spell explosiveBall = new Spell(9,10,Projectile.Trajectory.curved,SpellingSystem.SpellKind.PROJECTILE,GameEntity.Elements.FIRE,"Boule explosive",0,2,1f,animExplosiveBall);
        Spell fireBreath = new Spell(10,1,Projectile.Trajectory.curved,SpellingSystem.SpellKind.BREATH,GameEntity.Elements.FIRE,"Soufle de feu",0,300,1f,animFireBreath);
        Spell giantFireBall = new Spell(11,20,Projectile.Trajectory.curved,SpellingSystem.SpellKind.PROJECTILE,GameEntity.Elements.FIRE,"Grosse boule de feu",0,2,1f,animGiantFireBall);
        Spell lightningSwarm = new Spell(12,3,Projectile.Trajectory.curved,SpellingSystem.SpellKind.EXPLOSION,GameEntity.Elements.ELECTRICITY,"Essain d'eclairs",0,1,1f,animLightningSwarm);
        Spell teleportation = new Spell(13,0,Projectile.Trajectory.curved,SpellingSystem.SpellKind.PROJECTILE,GameEntity.Elements.NEUTRAL,"Teleportation",0,1,1f,animTeleportation);
        Spell lightningBouncingBall = new Spell(14,10,Projectile.Trajectory.curved,SpellingSystem.SpellKind.PROJECTILE,GameEntity.Elements.ELECTRICITY,"Boule electrique rebondissante",0,2,1,animLightningBouncingBall);
        Spell iceBreath = new Spell(15,1,Projectile.Trajectory.curved,SpellingSystem.SpellKind.BREATH,GameEntity.Elements.ICE,"Souffle de glace",0,300,1f,animIceBreath);
        Spell iceSpikyBall = new Spell(16,10,Projectile.Trajectory.curved,SpellingSystem.SpellKind.PROJECTILE,GameEntity.Elements.ICE,"Boule a pointes de glace",0,2,1f,animIceSpikyBall);
        Spell iceRune = new Spell(17,20,Projectile.Trajectory.curved,SpellingSystem.SpellKind.EXPLOSION,GameEntity.Elements.ICE,"Rune de glace",0,1,1f,animIceRune);
        Spell fireImmunity = new Spell(18,999,Projectile.Trajectory.curved,SpellingSystem.SpellKind.PASSIVE,GameEntity.Elements.FIRE,"Immunite feu",0,0,1f,animFireImmunity);
        Spell meteorSwarm = new Spell(19,20,Projectile.Trajectory.curved,SpellingSystem.SpellKind.EXPLOSION,GameEntity.Elements.FIRE,"Pluie de meteors",0,1,1f,animMeteorSwarm);
        Spell lightningImmunity = new Spell(20,999,Projectile.Trajectory.curved,SpellingSystem.SpellKind.PASSIVE,GameEntity.Elements.ELECTRICITY,"Immunite electrique",0,0,1f,animLightningImmunity);
        Spell lightningSpear = new Spell(21,60,Projectile.Trajectory.strait,SpellingSystem.SpellKind.PROJECTILE,GameEntity.Elements.ELECTRICITY,"Lance de foudre",0,1,1f,animLightningSpear);
        Spell iceStorm = new Spell(22,20,Projectile.Trajectory.curved,SpellingSystem.SpellKind.EXPLOSION,GameEntity.Elements.ICE,"Tempete de glace",0,1,1f,animIceStorm);
        Spell iceImmunity = new Spell(23,999,Projectile.Trajectory.curved,SpellingSystem.SpellKind.PASSIVE,GameEntity.Elements.ICE,"Immunite glace",0,0,1f,animIceImmunity);
        Spell majorHealing = new Spell(24,100,Projectile.Trajectory.curved,SpellingSystem.SpellKind.HEALING,GameEntity.Elements.NEUTRAL,"Soin majeur",0,1,1f,animMajorHealing);
        
        spellList.add(fireBall);
        spellList.add(icePic);
        spellList.add(sparkle);
        spellList.add(heal);
        spellList.add(upStream);
        spellList.add(fireResistance);
        spellList.add(iceResistance);
        spellList.add(lightningResistance);
        spellList.add(explosiveBall);
        spellList.add(fireBreath);
        spellList.add(giantFireBall);
        spellList.add(lightningSwarm);
        spellList.add(teleportation);
        spellList.add(lightningBouncingBall);
        spellList.add(iceBreath);
        spellList.add(iceSpikyBall);
        spellList.add(iceRune);
        spellList.add(fireImmunity);
        spellList.add(meteorSwarm);
        spellList.add(lightningImmunity);
        spellList.add(lightningSpear);
        spellList.add(iceStorm);
        spellList.add(iceImmunity);
        spellList.add(majorHealing);
        
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
        
        initAnimation();
        
       try{ 
        initSpellsIncantations();
       }catch (IOException ioe) {
       }
    }
    
    public static void newKnownSpell (Spell newSpell) {
    knownSpell.add(newSpell);   
    }
    
    public static void update (Input input,Spellington spellington, ArrayList<Projectile> projectileList) {
        


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
                    if(knownSpell.get(i).getSpellKind() == SpellKind.PASSIVE) {
                        
                        knownSpell.get(i).spellActivation();
                        passiveSpell.endOfActivation();
                        passiveSpell = knownSpell.get(i);
                        newSpell = true;
                        
                    } else {
                        if(knownSpell.get(i).getSpellKind().equals(SpellKind.PROJECTILE)){
                        Projectile tempProjectile = knownSpell.get(i).createSpellProjectile(spellington);
                        projectileList.add(tempProjectile);
                        }
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
        
        //test start........................................................
        if(input.isKeyPressed(Input.KEY_EQUALS)){
            incantationText = spellList.get(0).getIncantation();
            System.out.println(projectileList.size());
        }
        //test fin..........................................................
    }
   
    public static void spellActivation () {
        
    }

    public static String getIncantationText() {
        return incantationText;
    }
    
    private static void initAnimation() {
        try {

            Image[] tempImgFireBall = new Image[31];
            for (int i = 0; i < tempImgFireBall.length; i++) {
                tempImgFireBall[i] = new Image("res/image/animation/spells/tuto_fireball/" + (i + 1) + ".png");
            }
            animFireBall = new Animation(tempImgFireBall, 15);
            
            
            
            
            
            
            
        } catch (SlickException ex) {
            Logger.getLogger(SpellingSystem.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private static void initSpellsIncantations () throws IOException{
    ArrayList<String> tempWord = new ArrayList<>();
    Random dice = new Random();
    
    BufferedReader readerBuffer = null;
    String line;
    String filePath = new File("").getAbsolutePath();
    try {
	readerBuffer = new BufferedReader(new FileReader(filePath+"\\src\\res\\wordbank\\noviceWord.txt"));
      } catch(FileNotFoundException exc) {
	System.out.println("Erreur d'ouverture");
      }
    while ((line = readerBuffer.readLine()) != null){
      tempWord.add(line);
    }
    readerBuffer.close();
    
        for (int i = 0; i < tutoSpell.size(); i++) {
            tutoSpell.get(i).setIncantation(tempWord.get(dice.nextInt(tempWord.size())));
        }
    
    }
}
