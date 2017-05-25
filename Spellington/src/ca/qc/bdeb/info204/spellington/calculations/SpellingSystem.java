package ca.qc.bdeb.info204.spellington.calculations;

import ca.qc.bdeb.info204.spellington.GameCore;
import ca.qc.bdeb.info204.spellington.gameentities.GameAnimation;
import ca.qc.bdeb.info204.spellington.spell.ProjectileSpell;
import ca.qc.bdeb.info204.spellington.spell.Spell;
import ca.qc.bdeb.info204.spellington.gameentities.Projectile;
import ca.qc.bdeb.info204.spellington.gameentities.Spellington;
import ca.qc.bdeb.info204.spellington.gameentities.GameEntity.ElementalType;
import ca.qc.bdeb.info204.spellington.gameentities.enemies.Enemy;
import ca.qc.bdeb.info204.spellington.spell.BurstSpell;
import ca.qc.bdeb.info204.spellington.spell.ExplosionSpell;
import ca.qc.bdeb.info204.spellington.spell.HealingSpell;
import ca.qc.bdeb.info204.spellington.spell.PassiveSpell;
import ca.qc.bdeb.info204.spellington.spell.Potion;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.newdawn.slick.Animation;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;

/**
 * Class that manages the spell incantations and spell activation for the game.
 *
 * @author Celtis
 */
public class SpellingSystem {

    private static PassiveSpell passiveSpell;
    private static Spell activeSpell;
    private static Spell pastSpell;
    private static int nbSpellUses = 0;

    private static int nbPotionAcid = 5;
    private static int nbPotionHeal = 5;
    private static int nbPotionTime = 5;
    private static int nbPotionPast = 5;

    private static String incantationText = "";

    private static ArrayList<Spell> potionList = new ArrayList<>();
    private static ArrayList<Spell> allSpells = new ArrayList<>();
    private static ArrayList<Spell> noviceSpells = new ArrayList<>();
    private static ArrayList<Spell> adeptSpells = new ArrayList<>();
    private static ArrayList<Spell> masterSpells = new ArrayList<>();
    private static ArrayList<Spell> fireSpells = new ArrayList<>();
    private static ArrayList<Spell> iceSpells = new ArrayList<>();
    private static ArrayList<Spell> lightningSpells = new ArrayList<>();

    private static ArrayList<Spell> knownSpells = new ArrayList<>();

    private static Animation animFireBall;
    private static Animation animIceSpike;
    private static Animation animSpark;
    private static Animation animHeal;
    private static Animation animAscendingCurrent;
    private static Animation animFireResistance;
    private static Animation animIceResistance;
    private static Animation animLightningResistance;
    private static Animation animExplosiveBall;
    private static Animation animGiantFireBall;
    private static Animation animLightningSwarm;
    private static Animation animTeleportation;
    private static Animation animLightningBouncingBall;
    private static Animation animIceSpikeBall;
    private static Animation animRune;
    private static Animation animFireImmunity;
    private static Animation animMeteorShower;
    private static Animation animLightningImmunity;
    private static Animation animLightningSpear;
    private static Animation animIceStorm;
    private static Animation animIceImmunity;
    private static Animation animGreatHeal;
    private static Animation animAcid;
    private static Animation animExplosion;

    public static final int ID_FIRE_BALL = 1;
    public static final int ID_ICE_SPIKE = 2;
    public static final int ID_SPARK = 3;
    public static final int ID_HEAL = 4;
    public static final int ID_ASCENDING_CURRENT = 5;
    public static final int ID_FIRE_RES = 6;
    public static final int ID_ICE_RES = 7;
    public static final int ID_LIGHTNING_RES = 8;
    public static final int ID_EXPLOSIVE_BALL = 9;
    public static final int ID_FIRE_BREATH = 10;
    public static final int ID_GIANT_FIRE_BALL = 11;
    public static final int ID_LIGHTNING_SWARM = 12;
    public static final int ID_TELEPORTATION = 13;
    public static final int ID_LIGHTNING_BOUNCING_BALL = 14;
    public static final int ID_ICE_BREATH = 15;
    public static final int ID_ICE_SPIKE_BALL = 16;
    public static final int ID_RUNE = 17;
    public static final int ID_FIRE_IMMUNITY = 18;
    public static final int ID_METEOR_SHOWER = 19;
    public static final int ID_LIGHTNING_IMMUNITY = 20;
    public static final int ID_LIGHTNING_SPEAR = 21;
    public static final int ID_ICE_STORM = 22;
    public static final int ID_ICE_IMMUNITY = 23;
    public static final int ID_GREAT_HEAL = 24;
    public static final int ID_POTION_ACID = 25;
    public static final int ID_POTION_HEAL = 26;
    public static final int ID_POTION_TIME = 27;
    public static final int ID_POTION_PAST = 28;

    private static final String DESC_FIRE_BALL = "Boule en feu traditionnelle " + '\n' + "déployée à l'aide d'une trajectoire parabolique.";
    private static final String DESC_ICE_SPIKE = "Pic de glace suivant une " + '\n' + "trajectoire majoritairement linéaire.";
    private static final String DESC_SPARK = "Explosion d'étincelles " + '\n' + "engendrée par le clic de la souris.";
    private static final String DESC_HEAL = "Guérison de base.";
    private static final String DESC_ASCENDING_CURRENT = "Accorde des sauts " + '\n' + "supplémentaire à Spellington.";
    private static final String DESC_FIRE_RES = "Réduit les dégats infligés " + '\n' + "par les attaques de feu.";
    private static final String DESC_ICE_RES = "Réduit les dégats infligés " + '\n' + "par les attaques glaciales.";
    private static final String DESC_LIGHTNING_RES = "Réduit les dégats infligés " + '\n' + "par les attaques électriques.";
    private static final String DESC_EXPLOSIVE_BALL = "Boule de feu qui rebondie" + '\n' + " une fois avant d'exploser.";
    private static final String DESC_FIRE_BREATH = "Multiples boules de feu en " + '\n' + "arc de cercle.";
    private static final String DESC_GIANT_FIRE_BALL = "Boule de feu plus grande" + '\n' + "et plus dangereuse que les autres.";
    private static final String DESC_LIGHTNING_SWARM = "Grande explosion d'éclairs" + '\n' + " centrée sur la souris.";
    private static final String DESC_TELEPORTATION = "Sphère qui téléporte " + '\n' + "Spellington lors de la collision.";
    private static final String DESC_LIGHTNING_BOUNCING_BALL = "Sphère électrique" + '\n' + " qui rebondie à de nombreuses reprises.";
    private static final String DESC_ICE_BREATH = "Multiple pics de glace en arc" + '\n' + " de cercle.";
    private static final String DESC_ICE_SPIKE_BALL = "Boule de glace avec" + '\n' + " des pointes.";
    private static final String DESC_RUNE = "Rune de glace centrée sur la souris.";
    private static final String DESC_FIRE_IMMUNITY = "Immunité contre toute les " + '\n' + "attaques de feu.";
    private static final String DESC_METEOR_SHOWER = "Pluie de météorites qui " + '\n' + "touche tous les ennemis de l'écran.";
    private static final String DESC_LIGHTNING_IMMUNITY = "Immunité contre les " + '\n' + "attaques électriques.";
    private static final String DESC_LIGHTNING_SPEAR = "Lance de foudre qui " + '\n' + "fonce en ligne droite.";
    private static final String DESC_ICE_STORM = "Pluie de morceaux de glace " + '\n' + "qui touche tous les ennemis de l'écran.";
    private static final String DESC_ICE_IMMUNITY = "Immunité contre les attaques de glace.";
    private static final String DESC_GREAT_HEAL = "Sort de soin majeur qui guéris" + '\n' + " de toutes les blessures.";
    private static final String DESC_POTION_ACID = "Potion qui blesse l'ennemis" + '\n' + " touché.";
    private static final String DESC_POTION_HEAL = "Potion qui soigne immédiatement spellington.";
    private static final String DESC_POTION_TIME = "Potion qui ralentit le temps" + '\n' + "pour plus de temps de réflexion.";
    private static final String DESC_POTION_PAST = "Potion qui permet de réutiliser" + '\n' + " le dernier sort sans incantation.";

    static final boolean CHEAT_MODE = false;

    /**
     * Initiates the necessary components for the SpellingSystem.
     *
     * @author Celtis
     * @param gameSave The gameSave
     */
    public static void initSpellingSystem(GameSave gameSave) {
        initAnimation();

        Spell fireBall = new ProjectileSpell(ID_FIRE_BALL, ElementalType.FIRE, "Boule de feu", DESC_FIRE_BALL, 5, animFireBall, 30, 1, 1, 5);
        Spell iceSpike = new ProjectileSpell(ID_ICE_SPIKE, ElementalType.ICE, "Pic de glace", DESC_ICE_SPIKE, 5, animIceSpike, 10, 1, 0, 5);
        Spell spark = new ExplosionSpell(ID_SPARK, ElementalType.LIGHTNING, "Etincelle", DESC_SPARK, 2, animSpark, 5, 10);
        Spell heal = new HealingSpell(ID_HEAL, "Soin", DESC_HEAL, 3, animHeal, 100, 100, 10);
        Spell ascendingCurrent = new PassiveSpell(ID_ASCENDING_CURRENT, ElementalType.NEUTRAL, "Courant ascendant", DESC_ASCENDING_CURRENT, animAscendingCurrent, 100, 100, 0);
        Spell fireResistance = new PassiveSpell(ID_FIRE_RES, ElementalType.FIRE, "Résistance feu", DESC_FIRE_RES, animFireResistance, 100, 30, -40);
        Spell iceResistance = new PassiveSpell(ID_ICE_RES, ElementalType.ICE, "Résistance glace", DESC_ICE_RES, animIceResistance, 100, 30, -40);
        Spell lightningResistance = new PassiveSpell(ID_LIGHTNING_RES, ElementalType.LIGHTNING, "Résistance electrique", DESC_LIGHTNING_RES, animLightningResistance, 100, 30, -40);

        Spell explosiveBall = new ProjectileSpell(ID_EXPLOSIVE_BALL, ElementalType.FIRE, "Boule explosive", DESC_EXPLOSIVE_BALL, 2, animExplosiveBall, 70, 1, 1, 10);
        Spell fireBreath = new BurstSpell(ID_FIRE_BREATH, ElementalType.FIRE, "Soufle de feu", DESC_FIRE_BREATH, 3, animFireBall, 30, 1, 1, 10, 0.15f, 5);
        Spell giantFireBall = new ProjectileSpell(ID_GIANT_FIRE_BALL, ElementalType.FIRE, "Grosse boule de feu", DESC_GIANT_FIRE_BALL, 2, animGiantFireBall, 90, 1, 1, 20);
        Spell lightningSwarm = new ExplosionSpell(ID_LIGHTNING_SWARM, ElementalType.LIGHTNING, "Essain d'eclairs", DESC_LIGHTNING_SWARM, 1, animLightningSwarm, 3, 100);
        Spell teleportation = new ProjectileSpell(ID_TELEPORTATION, ElementalType.NEUTRAL, "Teleportation", DESC_TELEPORTATION, 1, animTeleportation, 50, 0.5f, 0, 0);
        Spell lightningBouncingBall = new ProjectileSpell(ID_LIGHTNING_BOUNCING_BALL, ElementalType.LIGHTNING, "Boule electrique rebondissante", DESC_LIGHTNING_BOUNCING_BALL, 2, animLightningBouncingBall, 30, 1, 1, 10);
        Spell iceBreath = new BurstSpell(ID_ICE_BREATH, ElementalType.ICE, "Souffle de glace", DESC_ICE_BREATH, 2, animIceSpike, 10, 1, 0, 1, 0.35f, 15);
        Spell iceSpikeBall = new ProjectileSpell(ID_ICE_SPIKE_BALL, ElementalType.ICE, "Boule a pointes de glace", DESC_ICE_SPIKE_BALL, 2, animIceSpikeBall, 50, 1, 1, 10);
        Spell iceRune = new ExplosionSpell(ID_RUNE, ElementalType.ICE, "Rune de glace", DESC_RUNE, 1, animRune, 20, 100);

        Spell fireImmunity = new PassiveSpell(ID_FIRE_IMMUNITY, ElementalType.FIRE, "Immunite feu", DESC_FIRE_IMMUNITY, animFireImmunity, 100, 100, 0);
        Spell meteorShower = new ExplosionSpell(ID_METEOR_SHOWER, ElementalType.FIRE, "Pluie de meteors", DESC_METEOR_SHOWER, 1, animMeteorShower, 30, 500);
        Spell lightningImmunity = new PassiveSpell(ID_LIGHTNING_IMMUNITY, ElementalType.LIGHTNING, "Immunite électrique", DESC_LIGHTNING_IMMUNITY, animLightningImmunity, 100, 100, 0);
        Spell lightningSpear = new ProjectileSpell(ID_LIGHTNING_SPEAR, ElementalType.LIGHTNING, "Lance de foudre", DESC_LIGHTNING_SPEAR, 1, animLightningSpear, 50, 1, 0, 60);
        Spell iceStorm = new ExplosionSpell(ID_ICE_STORM, ElementalType.ICE, "Tempete de glace", DESC_ICE_STORM, 1, animIceStorm, 30, 500);
        Spell iceImmunity = new PassiveSpell(ID_ICE_IMMUNITY, ElementalType.ICE, "Immunite glace", DESC_ICE_IMMUNITY, animIceImmunity, 100, 100, 0);
        Spell greatHeal = new HealingSpell(ID_GREAT_HEAL, "Soin majeur", DESC_GREAT_HEAL, 1, animGreatHeal, 100, 200, 999);

        Spell PotionAcid = new ProjectileSpell(ID_POTION_ACID, ElementalType.NEUTRAL, "Potion d'acide", DESC_POTION_ACID, 1, animAcid, 40, 1, 1, 20);
        Spell PotionHeal = new HealingSpell(ID_POTION_HEAL, "Potion de soin", DESC_POTION_HEAL, 1, animHeal, 100, 100, 20);
        Spell PotionTime = new Potion(ID_POTION_TIME, "Potion de ralentissement du temps", DESC_POTION_TIME, 1, new Animation(), 100, 100);
        Spell PotionPast = new Potion(ID_POTION_PAST, "Potion du passé", DESC_POTION_PAST, 1, new Animation(), 100, 100);

        potionList.add(PotionHeal);
        potionList.add(PotionAcid);
        potionList.add(PotionTime);
        potionList.add(PotionPast);

        allSpells.add(fireBall);
        allSpells.add(iceSpike);
        allSpells.add(spark);
        allSpells.add(heal);
        allSpells.add(ascendingCurrent);
        allSpells.add(fireResistance);
        allSpells.add(iceResistance);
        allSpells.add(lightningResistance);
        allSpells.add(explosiveBall);
        allSpells.add(fireBreath);
        allSpells.add(giantFireBall);
        allSpells.add(lightningSwarm);
        allSpells.add(teleportation);
        allSpells.add(lightningBouncingBall);
        allSpells.add(iceBreath);
        allSpells.add(iceSpikeBall);
        allSpells.add(iceRune);
        allSpells.add(fireImmunity);
        allSpells.add(meteorShower);
        allSpells.add(lightningImmunity);
        allSpells.add(lightningSpear);
        allSpells.add(iceStorm);
        allSpells.add(iceImmunity);
        allSpells.add(greatHeal);

        noviceSpells.add(fireBall);
        noviceSpells.add(iceSpike);
        noviceSpells.add(spark);
        noviceSpells.add(heal);
        noviceSpells.add(ascendingCurrent);
        noviceSpells.add(fireResistance);
        noviceSpells.add(iceResistance);
        noviceSpells.add(lightningResistance);

        adeptSpells.add(explosiveBall);
        adeptSpells.add(fireBreath);
        adeptSpells.add(giantFireBall);
        adeptSpells.add(lightningSwarm);
        adeptSpells.add(teleportation);
        adeptSpells.add(lightningBouncingBall);
        adeptSpells.add(iceBreath);
        adeptSpells.add(iceSpikeBall);
        adeptSpells.add(iceRune);

        masterSpells.add(fireImmunity);
        masterSpells.add(meteorShower);
        masterSpells.add(lightningImmunity);
        masterSpells.add(lightningSpear);
        masterSpells.add(iceStorm);
        masterSpells.add(iceImmunity);
        masterSpells.add(greatHeal);

        fireSpells.add(explosiveBall);
        fireSpells.add(fireBreath);
        fireSpells.add(giantFireBall);

        iceSpells.add(iceBreath);
        iceSpells.add(iceSpikeBall);
        iceSpells.add(iceRune);

        lightningSpells.add(lightningSwarm);
        lightningSpells.add(teleportation);
        lightningSpells.add(lightningBouncingBall);

        setSpellsIncantations();

    }

    /**
     * The update method for the SpellingSystem
     *
     * @param input The Input classé
     * @param spellington The player object.
     * @param activeProjectiles The list of active projectiles
     * @param activeAnimations The list of active animations.
     * @param activeEnemy The list of active enemies.
     */
    public static void update(Input input, Spellington spellington, ArrayList<Projectile> activeProjectiles, ArrayList<GameAnimation> activeAnimations, ArrayList<Enemy> activeEnemy) {
        boolean clickedLeftMouse = false;
        if (input.isMousePressed(Input.MOUSE_LEFT_BUTTON)) {
            clickedLeftMouse = true;
        }

        if (clickedLeftMouse) {
            boolean newSpell = false;
            for (int i = 0; i < knownSpells.size(); i++) {
                if (incantationText.equalsIgnoreCase(knownSpells.get(i).getIncantation())) {
                    if (knownSpells.get(i) instanceof PassiveSpell) {
                        if (passiveSpell != null) {
                            passiveSpell.endOfActivation(spellington, activeAnimations);
                        }
                        passiveSpell = (PassiveSpell) knownSpells.get(i);
                        passiveSpell.spellActivation(spellington, input, activeAnimations, activeProjectiles, activeEnemy);
                        newSpell = true;
                    } else {
                        activeSpell = knownSpells.get(i);
                        nbSpellUses = knownSpells.get(i).getUses();
                        newSpell = true;
                    }
                }
            }
            if (!newSpell && activeSpell != null) {
                activeSpell.spellActivation(spellington, input, activeAnimations, activeProjectiles, activeEnemy);
                //pour tester
                if (!CHEAT_MODE) {
                    nbSpellUses--;
                }
            }
            if (nbSpellUses <= 0) {
                if (activeSpell != null) {
                    pastSpell = activeSpell;
                }
                activeSpell = null;
                nbSpellUses = 0;
            }
            incantationText = "";
        }

        //potions start-----------
        if (input.isKeyPressed(Input.KEY_1)) {
            if (nbPotionHeal > 0) {
                potionList.get(0).spellActivation(spellington, input, activeAnimations, activeProjectiles, activeEnemy);
                if (!CHEAT_MODE) {
                    nbPotionHeal--;
                }
            }
        }

        if (input.isKeyPressed(Input.KEY_2)) {
            if (nbPotionAcid > 0) {
                potionList.get(1).spellActivation(spellington, input, activeAnimations, activeProjectiles, activeEnemy);
                if (!CHEAT_MODE) {
                    nbPotionAcid--;
                }
            }
        }

        if (input.isKeyPressed(Input.KEY_3)) {
            if (nbPotionTime > 0) {
                potionList.get(2).spellActivation(spellington, input, activeAnimations, activeProjectiles, activeEnemy);
                if (!CHEAT_MODE) {
                    nbPotionTime--;
                }
            }
        }

        if (input.isKeyPressed(Input.KEY_4)) {
            if (nbPotionPast > 0) {
                potionList.get(3).spellActivation(spellington, input, activeAnimations, activeProjectiles, activeEnemy);
                if (!CHEAT_MODE) {
                    nbPotionPast--;
                }
            }
        }

        //potions end-----------
    }

    /**
     * Initialises the animations for all the spells in the game.
     */
    private static void initAnimation() {
        try {
            Image[] tempImgFireBall = new Image[31];
            for (int i = 0; i < tempImgFireBall.length; i++) {
                tempImgFireBall[i] = new Image("res/image/animation/spells/fireBall/(" + (i + 1) + ").png");
            }
            animFireBall = new Animation(tempImgFireBall, 30);

            Image[] tempImgUpStream = new Image[19];
            for (int i = 18; i >= 0; i--) {
                tempImgUpStream[18 - i] = new Image("res/image/animation/spells/upStream/(" + (i + 1) + ").png");
            }
            animAscendingCurrent = new Animation(tempImgUpStream, 40);

            Image[] tempImgFireRes = new Image[19];
            for (int i = 0; i < tempImgFireRes.length; i++) {
                tempImgFireRes[i] = new Image("res/image/animation/spells/fireRes/(" + (i + 1) + ").png");
            }
            animFireResistance = new Animation(tempImgFireRes, 30);

            Image[] tempImgIceRes = new Image[19];
            for (int i = 0; i < tempImgIceRes.length; i++) {
                tempImgIceRes[i] = new Image("res/image/animation/spells/iceRes/(" + (i + 1) + ").png");
            }
            animIceResistance = new Animation(tempImgIceRes, 30);

            Image[] tempImgElectricRes = new Image[19];
            for (int i = 0; i < tempImgElectricRes.length; i++) {
                tempImgElectricRes[i] = new Image("res/image/animation/spells/electricRes/(" + (i + 1) + ").png");
            }
            animLightningResistance = new Animation(tempImgElectricRes, 30);

            Image[] tempImgElectricBolt = new Image[19];
            for (int i = 0; i < tempImgElectricBolt.length; i++) {
                tempImgElectricBolt[i] = new Image("res/image/animation/spells/lightningBolt/(" + (i + 1) + ").png");
            }
            animLightningBouncingBall = new Animation(tempImgElectricBolt, 15);

            Image[] tempImgHealSmall = new Image[19];
            for (int i = 0; i < tempImgHealSmall.length; i++) {
                tempImgHealSmall[i] = new Image("res/image/animation/spells/healSmall/(" + (i + 1) + ").png");
            }
            animHeal = new Animation(tempImgHealSmall, 30);

            Image[] tempImgHealBig = new Image[19];
            for (int i = 0; i < tempImgHealBig.length; i++) {
                tempImgHealBig[i] = new Image("res/image/animation/spells/healBig/(" + (i + 1) + ").png");
            }
            animGreatHeal = new Animation(tempImgHealBig, 30);

            Image[] tempImgFireImmu = new Image[19];
            for (int i = 0; i < tempImgFireImmu.length; i++) {
                tempImgFireImmu[i] = new Image("res/image/animation/spells/fireImmu/(" + (i + 1) + ").png");
            }
            animFireImmunity = new Animation(tempImgFireImmu, 30);

            Image[] tempImgIceImmu = new Image[19];
            for (int i = 0; i < tempImgIceImmu.length; i++) {
                tempImgIceImmu[i] = new Image("res/image/animation/spells/iceImmu/(" + (i + 1) + ").png");
            }
            animIceImmunity = new Animation(tempImgIceImmu, 30);

            Image[] tempImgElectricImmu = new Image[19];
            for (int i = 0; i < tempImgElectricImmu.length; i++) {
                tempImgElectricImmu[i] = new Image("res/image/animation/spells/electricImmu/(" + (i + 1) + ").png");
            }
            animLightningImmunity = new Animation(tempImgElectricImmu, 30);

            Image[] tempImgElectricSpark = new Image[19];
            for (int i = 0; i < tempImgElectricSpark.length; i++) {
                tempImgElectricSpark[i] = new Image("res/image/animation/spells/lightningSpark/(" + (i + 1) + ").png");
            }
            animSpark = new Animation(tempImgElectricSpark, 30);

            Image[] tempImgElectricSwarm = new Image[19];
            for (int i = 0; i < tempImgElectricSwarm.length; i++) {
                tempImgElectricSwarm[i] = new Image("res/image/animation/spells/lightningSwarm/(" + (i + 1) + ").png");
            }
            animLightningSwarm = new Animation(tempImgElectricSwarm, 30);

            Image[] tempImgElectricSpear = new Image[19];
            for (int i = 0; i < tempImgElectricSpear.length; i++) {
                tempImgElectricSpear[i] = new Image("res/image/animation/spells/lightningSpear/(" + (i + 1) + ").png");
            }
            animLightningSpear = new Animation(tempImgElectricSpear, 30);

            Image[] tempImgIceSpikeBall = new Image[19];
            for (int i = 0; i < tempImgIceSpikeBall.length; i++) {
                tempImgIceSpikeBall[i] = new Image("res/image/animation/spells/iceSpikeBall/(" + (i + 1) + ").png");
            }
            animIceSpikeBall = new Animation(tempImgIceSpikeBall, 30);

            Image[] tempImgExplosiveBall = new Image[20];
            for (int i = 0; i < tempImgExplosiveBall.length; i++) {
                tempImgExplosiveBall[i] = new Image("res/image/animation/spells/explosiveBall/(" + (i + 1) + ").png");
            }
            animExplosiveBall = new Animation(tempImgExplosiveBall, 30);

            Image[] tempImgIceSpike = new Image[19];
            for (int i = 0; i < tempImgIceSpike.length; i++) {
                tempImgIceSpike[i] = new Image("res/image/animation/spells/iceSpike/(" + (i + 1) + ").png");
            }
            animIceSpike = new Animation(tempImgIceSpike, 30);

            Image[] tempImgIceStorm = new Image[42];
            for (int i = 0; i < tempImgIceStorm.length; i++) {
                tempImgIceStorm[i] = new Image("res/image/animation/spells/iceStorm/(" + (i + 1) + ").png");
            }
            animIceStorm = new Animation(tempImgIceStorm, 30);

            Image[] tempImgMeteorSwarm = new Image[21];
            for (int i = 0; i < tempImgMeteorSwarm.length; i++) {
                tempImgMeteorSwarm[i] = new Image("res/image/animation/spells/meteorSwarm/(" + (i + 1) + ").png");
            }
            animMeteorShower = new Animation(tempImgMeteorSwarm, 30);

            Image[] tempImgteleportBall = new Image[1];
            for (int i = 0; i < tempImgteleportBall.length; i++) {
                tempImgteleportBall[i] = new Image("res/image/animation/spells/teleportBall.png");
            }
            animTeleportation = new Animation(tempImgteleportBall, 30);

            Image[] tempImgFireBallGiant = new Image[31];
            for (int i = 0; i < tempImgFireBallGiant.length; i++) {
                tempImgFireBallGiant[i] = new Image("res/image/animation/spells/fireBall/(" + (i + 1) + ").png");
            }
            animGiantFireBall = new Animation(tempImgFireBallGiant, 30);

            Image[] tempImgIceRune = new Image[20];
            for (int i = 0; i < tempImgIceRune.length; i++) {
                tempImgIceRune[i] = new Image("res/image/animation/spells/iceRune/(" + (i + 1) + ").png");
            }
            animRune = new Animation(tempImgIceRune, 30);

            //potion animation
            Image[] tempImgAcidPotion = new Image[1];
            tempImgAcidPotion[0] = new Image("res/image/acidProjectile.png");

            animAcid = new Animation(tempImgAcidPotion, 30);

            Image[] tempImgExplosion = new Image[21];
            for (int i = 0; i < tempImgExplosion.length; i++) {
                tempImgExplosion[i] = new Image("res/image/animation/spells/explosion/(" + (i + 1) + ").png");
            }
            animExplosion = new Animation(tempImgExplosion, 30);

        } catch (SlickException ex) {
            ex.printStackTrace();
        }
    }

    /**
     * Sets the incantation key-words for all the spells
     */
    public static void setSpellsIncantations() {
        ArrayList<String> tempWord = new ArrayList<>();

        BufferedReader readerBuffer;
        String line;
        String filePath = new File("").getAbsolutePath();
        try {
            readerBuffer = new BufferedReader(new InputStreamReader(new FileInputStream(filePath + "\\src\\res\\wordbank\\noviceWord.txt"), "ISO-8859-1"));
            while ((line = readerBuffer.readLine()) != null) {
                tempWord.add(line);
            }
            readerBuffer.close();
        } catch (FileNotFoundException exc) {
            System.out.println("Erreur d'ouverture");
        } catch (IOException ex) {
            Logger.getLogger(SpellingSystem.class.getName()).log(Level.SEVERE, null, ex);
        }

        for (int i = 0; i < noviceSpells.size(); i++) {
            int tempdice = GameCore.rand.nextInt(tempWord.size());
            noviceSpells.get(i).setIncantation(tempWord.get(tempdice));
            if (tempWord.size() <= 1) {
                System.out.print("Erreur: il n'y a pas assez de mots dans noviceWord.txt");
            }
            tempWord.remove(tempdice);
        }
        tempWord.clear();

        BufferedReader readerBuffer2;
        String line2 = null;
        String filePath2 = new File("").getAbsolutePath();
        try {
            readerBuffer2 = new BufferedReader(new InputStreamReader(new FileInputStream(filePath2 + "\\src\\res\\wordbank\\adepteWord.txt"), "ISO-8859-1"));
            while ((line2 = readerBuffer2.readLine()) != null) {
                tempWord.add(line2);
            }
            readerBuffer2.close();
        } catch (FileNotFoundException exc) {
            System.out.println("Erreur d'ouverture");
        } catch (IOException ex) {
            Logger.getLogger(SpellingSystem.class.getName()).log(Level.SEVERE, null, ex);
        }

        for (int i = 0; i < adeptSpells.size(); i++) {
            int tempdice = GameCore.rand.nextInt(tempWord.size());
            adeptSpells.get(i).setIncantation(tempWord.get(tempdice));
            if (tempWord.size() <= 1) {
                System.out.print("Erreur: il n'y a pas assez de mots dans adepteWord.txt");
            }
            tempWord.remove(tempdice);
        }
        tempWord.clear();

        BufferedReader readerBuffer3 = null;
        String line3 = null;
        String filePath3 = new File("").getAbsolutePath();
        try {
            readerBuffer3 = new BufferedReader(new InputStreamReader(new FileInputStream(filePath3 + "\\src\\res\\wordbank\\masterWord.txt"), "ISO-8859-1"));
            while ((line3 = readerBuffer3.readLine()) != null) {
                tempWord.add(line3);
            }
            readerBuffer3.close();
        } catch (FileNotFoundException exc) {
            System.out.println("Erreur d'ouverture");
        } catch (IOException ex) {
            Logger.getLogger(SpellingSystem.class.getName()).log(Level.SEVERE, null, ex);
        }

        for (int i = 0; i < masterSpells.size(); i++) {
            int tempdice = GameCore.rand.nextInt(tempWord.size());
            masterSpells.get(i).setIncantation(tempWord.get(tempdice));
            if (tempWord.size() <= 1) {
                System.out.print("Erreur: il n'y a pas assez de mots dans masterWord.txt");
            }
            tempWord.remove(tempdice);
        }
        tempWord.clear();
    }

    /**
     * Activates the last spell used.
     *
     * @param spellington The player objet
     */
    public static void pastSpellPotion(Spellington spellington) {
        if (pastSpell != null) {
            activeSpell = pastSpell;
            nbSpellUses = pastSpell.getUses();
        }
    }

    public static Spell getActiveSpell() {
        return activeSpell;
    }

    public static Spell getPassiveSpell() {
        return passiveSpell;
    }

    public static String getIncantationText() {
        return incantationText;
    }

    public static void setIncantationText(String incantationText) {
        SpellingSystem.incantationText = incantationText;
    }

    public static Animation getAnimFireBall() {
        return animFireBall;
    }

    public static ArrayList<Spell> getNoviceSpells() {
        return noviceSpells;
    }

    public static ArrayList<Spell> getAdeptSpells() {
        return adeptSpells;
    }

    public static ArrayList<Spell> getAllSpells() {
        return allSpells;
    }

    public static ArrayList<Spell> getPotionList() {
        return potionList;
    }

    public static int getNbPotionAcid() {
        return nbPotionAcid;
    }

    public static int getNbPotionHeal() {
        return nbPotionHeal;
    }

    public static int getNbPotionPast() {
        return nbPotionPast;
    }

    public static int getNbPotionTime() {
        return nbPotionTime;
    }

    public static int getNbSpellUses() {
        return nbSpellUses;
    }

    public static ArrayList<Spell> getKnownSpells() {
        return knownSpells;
    }

    public static ArrayList<Spell> getFireSpells() {
        return fireSpells;
    }

    public static ArrayList<Spell> getIceSpells() {
        return iceSpells;
    }

    public static ArrayList<Spell> getLightningSpells() {
        return lightningSpells;
    }

    public static Animation getAnimExplosion() {
        return animExplosion;
    }

    public static void setActiveSpell(Spell activeSpell) {
        SpellingSystem.activeSpell = activeSpell;
    }

    public static void setPassiveSpell(PassiveSpell passiveSpell) {
        SpellingSystem.passiveSpell = passiveSpell;
    }

    public static void setNbPotionAcid(int nbPotionAcid) {
        SpellingSystem.nbPotionAcid = nbPotionAcid;
    }

    public static void setNbPotionHeal(int nbPotionHeal) {
        SpellingSystem.nbPotionHeal = nbPotionHeal;
    }

    public static void setNbPotionPast(int nbPotionPast) {
        SpellingSystem.nbPotionPast = nbPotionPast;
    }

    public static void setNbPotionTime(int nbPotionTime) {
        SpellingSystem.nbPotionTime = nbPotionTime;
    }

    public static void setNbSpellUses(int nbSpellUses) {
        SpellingSystem.nbSpellUses = nbSpellUses;
    }

    public static void setKnownSpells(ArrayList<Spell> knownSpells) {
        SpellingSystem.knownSpells = knownSpells;
    }

    public static Animation getAnimIceSpike() {
        return animIceSpike;
    }

    public static Animation getAnimLightningBouncingBall() {
        return animLightningBouncingBall;
    }

    public static ArrayList<Spell> getMasterSpells() {
        return masterSpells;
    }

}
