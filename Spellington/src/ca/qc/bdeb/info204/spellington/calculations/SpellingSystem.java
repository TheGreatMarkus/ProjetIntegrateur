package ca.qc.bdeb.info204.spellington.calculations;

import ca.qc.bdeb.info204.spellington.spell.ProjectileSpell;
import ca.qc.bdeb.info204.spellington.spell.Spell;
import ca.qc.bdeb.info204.spellington.gameentities.Projectile;
import ca.qc.bdeb.info204.spellington.gameentities.Spellington;
import ca.qc.bdeb.info204.spellington.gameentities.GameEntity.ElementalType;
import ca.qc.bdeb.info204.spellington.gameentities.enemies.Enemy;
import ca.qc.bdeb.info204.spellington.spell.BreathSpell;
import ca.qc.bdeb.info204.spellington.spell.ExplosionSpell;
import ca.qc.bdeb.info204.spellington.spell.HealingSpell;
import ca.qc.bdeb.info204.spellington.spell.PassiveSpell;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;
import org.newdawn.slick.Animation;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;

/**
 *
 * @author Celtis
 */
public class SpellingSystem {

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
    private static Animation animIceSpike;
    private static Animation animSpark;
    private static Animation animHeal;
    private static Animation animAscendingCurrent;
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
    private static Animation animIceSpikeBall;
    private static Animation animIceRune;
    private static Animation animFireImmunity;
    private static Animation animMeteorSwarm;
    private static Animation animLightningImmunity;
    private static Animation animLightningSpear;
    private static Animation animIceStorm;
    private static Animation animIceImmunity;
    private static Animation animGreatHeal;

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
    public static final int ID_LIGHTNING_BALL = 14;
    public static final int ID_ICE_BREATH = 15;
    public static final int ID_ICE_SPIKE_BALL = 16;
    public static final int ID_ICE_RUNE = 17;
    public static final int ID_FIRE_IMMUNITY = 18;
    public static final int ID_METEOR_SWARM = 19;
    public static final int ID_LIGHTNING_IMMUNITY = 20;
    public static final int ID_LIGHTNING_SPEAR = 21;
    public static final int ID_ICE_STORM = 22;
    public static final int ID_ICE_IMMUNITY = 23;
    public static final int ID_GREAT_HEAL = 24;

    public static void initSpellingSystem() {
        initAnimation();

//public                            Spell(int id, int damage, SpellKind spellKind                , GameEntity.Elements element, String name                         , int speedModifier, int nbUses, float GRAVITY_MODIFIER, Animation animSpell      , int width, int height) {    
//Spell fireBall = new              Spell(1     , 5         , SpellKind.PROJECTILE               , GameEntity.Elements.FIRE       , "Boule de feu"                  , 1                , 5         , 1                     , animFireBall             , 100      , 100       );
//Spell icePic = new                Spell(2     , 5         , SpellingSystem.SpellKind.PROJECTILE, GameEntity.Elements.ICE        , "Pic de glace"                  , 0                , 3         , 1f                    , animIcePic               , 100      , 100       );
//Spell sparkle = new               Spell(3     , 5         , SpellingSystem.SpellKind.EXPLOSION , GameEntity.Elements.ELECTRICITY, "Etincelle"                     , 0                , 2         , 1f                    , animSparkle              , 100      , 100       );
//Spell heal = new                  Spell(4     , 10        , SpellingSystem.SpellKind.HEALING   , GameEntity.Elements.NEUTRAL    , "Soin"                          , 0                , 1         , 1f                    , animHeal                 , 100      , 100       );
//Spell upStream = new              Spell(5     , 0         , SpellingSystem.SpellKind.PASSIVE   , GameEntity.Elements.NEUTRAL    , "Courant ascendant"             , 0                , 0         , 1f                    , animUpStream             , 100      , 100       );
//Spell fireResistance = new        Spell(6     , 10        , SpellingSystem.SpellKind.PASSIVE   , GameEntity.Elements.FIRE       , "Résistance feu"                , 0                , 0         , 1f                    , animFireResistance       , 100      , 30        );
//Spell iceResistance = new         Spell(7     , 10        , SpellingSystem.SpellKind.PASSIVE   , GameEntity.Elements.ICE        , "Résistance glace"              , 0                , 0         , 1f                    , animIceResistance        , 100      , 30        );
//Spell lightningResistance = new   Spell(8     , 10        , SpellingSystem.SpellKind.PASSIVE   , GameEntity.Elements.ELECTRICITY, "Résistance electrique"         , 0                , 0         , 1f                    , animLightningResistance  , 100      , 30        );
//Spell explosiveBall = new         Spell(9     , 10        , SpellingSystem.SpellKind.PROJECTILE, GameEntity.Elements.FIRE       , "Boule explosive"               , 0                , 2         , 1f                    , animExplosiveBall        , 100      , 100       );
//Spell fireBreath = new            Spell(10    , 1         , SpellingSystem.SpellKind.BREATH    , GameEntity.Elements.FIRE       , "Soufle de feu"                 , 0                , 300       , 1f                    , animFireBreath           , 100      , 100       );
//Spell giantFireBall = new         Spell(11    , 20        , SpellingSystem.SpellKind.PROJECTILE, GameEntity.Elements.FIRE       , "Grosse boule de feu"           , 0                , 2         , 1f                    , animGiantFireBall        , 200      , 200       );
//Spell lightningSwarm = new        Spell(12    , 3         , SpellingSystem.SpellKind.EXPLOSION , GameEntity.Elements.ELECTRICITY, "Essain d'eclairs"              , 0                , 1         , 1f                    , animLightningSwarm       , 100      , 100       );
//Spell teleportation = new         Spell(13    , 0         , SpellingSystem.SpellKind.PROJECTILE, GameEntity.Elements.NEUTRAL    , "Teleportation"                 , 0                , 1         , 1f                    , animTeleportation        , 100      , 100       );
//Spell lightningBouncingBall = new Spell(14    , 10        , SpellingSystem.SpellKind.PROJECTILE, GameEntity.Elements.ELECTRICITY, "Boule electrique rebondissante", 0                , 2         , 1                     , animLightningBouncingBall, 100      , 100       );
//Spell iceBreath = new             Spell(15    , 1         , SpellingSystem.SpellKind.BREATH    , GameEntity.Elements.ICE        , "Souffle de glace"              , 0                , 300       , 1f                    , animIceBreath            , 100      , 100       );
//Spell iceSpikyBall = new          Spell(16    , 10        , SpellingSystem.SpellKind.PROJECTILE, GameEntity.Elements.ICE        , "Boule a pointes de glace"      , 0                , 2         , 1f                    , animIceSpikyBall         , 100      , 100       );
//Spell iceRune = new               Spell(17    , 20        , SpellingSystem.SpellKind.EXPLOSION , GameEntity.Elements.ICE        , "Rune de glace"                 , 0                , 1         , 1f                    , animIceRune              , 100      , 100       );
//Spell fireImmunity = new          Spell(18    , 999       , SpellingSystem.SpellKind.PASSIVE   , GameEntity.Elements.FIRE       , "Immunite feu"                  , 0                , 0         , 1f                    , animFireImmunity         , 100      , 100       );
//Spell meteorSwarm = new           Spell(19    , 20        , SpellingSystem.SpellKind.EXPLOSION, GameEntity.Elements.FIRE        , "Pluie de meteors"              , 0                , 1         , 1f                    , animMeteorSwarm          , 100      , 100       );
//Spell lightningImmunity = new     Spell(20    , 999       , SpellingSystem.SpellKind.PASSIVE   , GameEntity.Elements.ELECTRICITY, "Immunite electrique"           , 0                , 0         , 1f                    , animLightningImmunity    , 100      , 100       );
//Spell lightningSpear = new        Spell(21    , 60        , SpellingSystem.SpellKind.PROJECTILE, GameEntity.Elements.ELECTRICITY, "Lance de foudre"               , 0                , 1         , 1f                    , animLightningSpear       , 100      , 100       );
//Spell iceStorm = new              Spell(22    , 20        , SpellingSystem.SpellKind.EXPLOSION , GameEntity.Elements.ICE        , "Tempete de glace"              , 0                , 1         , 1f                    , animIceStorm             , 100      , 100       );
//Spell iceImmunity = new           Spell(23    , 999       , SpellingSystem.SpellKind.PASSIVE   , GameEntity.Elements.ICE        , "Immunite glace"                , 0                , 0         , 1f                    , animIceImmunity          , 100      , 100       );
//Spell majorHealing = new          Spell(24    , 100       , SpellingSystem.SpellKind.HEALING   , GameEntity.Elements.NEUTRAL    , "Soin majeur"                   , 0                , 1         , 1f                    , animMajorHealing         , 100      , 100       );

        Spell fireBall = new ProjectileSpell(ID_FIRE_BALL, ElementalType.FIRE, "Boule de feu", 5, animFireBall, 20, 20, 1, 1, 5);
        Spell iceSpike = new ProjectileSpell(ID_ICE_SPIKE, ElementalType.ICE, "Pic de glace", 3, animIceSpike, 20, 20, 1, 0, 5);
        Spell spark = new ExplosionSpell(ID_SPARK, ElementalType.LIGHTNING, "Etincelle", 2, animSpark, 100, 100, 5, 5);
        Spell heal = new HealingSpell(ID_HEAL, "Soin", 1, animHeal, 100, 100, 10);
        Spell ascendingCurrent = new PassiveSpell(ID_ASCENDING_CURRENT, ElementalType.NEUTRAL, "Courant ascendant", animAscendingCurrent, 100, 100, 0);
        Spell fireResistance = new PassiveSpell(ID_FIRE_RES, ElementalType.FIRE, "Résistance feu", animFireResistance, 100, 30, -40);
        Spell iceResistance = new PassiveSpell(ID_ICE_RES, ElementalType.ICE, "Résistance glace", animIceResistance, 100, 30, -40);
        Spell lightningResistance = new PassiveSpell(ID_LIGHTNING_RES, ElementalType.LIGHTNING, "Résistance electrique", animLightningResistance, 100, 30, -40);
        Spell explosiveBall = new ProjectileSpell(ID_EXPLOSIVE_BALL, ElementalType.FIRE, "Boule explosive", 2, animExplosiveBall, 100, 100, 1, 1, 10);
        Spell fireBreath = new BreathSpell(ID_FIRE_BREATH, ElementalType.FIRE, "Soufle de feu", 300, animFireBreath, 100, 100, 1, 1, 1, 0.35f, 5);
        Spell giantFireBall = new ProjectileSpell(ID_GIANT_FIRE_BALL, ElementalType.FIRE, "Grosse boule de feu", 2, animGiantFireBall, 200, 200, 1, 1, 20);
        Spell lightningSwarm = new ExplosionSpell(ID_LIGHTNING_SWARM, ElementalType.LIGHTNING, "Essain d'eclairs", 1, animLightningSwarm, 100, 100, 3, 5);
        Spell teleportation = new ProjectileSpell(ID_TELEPORTATION, ElementalType.NEUTRAL, "Teleportation", 1, animTeleportation, 100, 100, 1, 1, 0);
        Spell lightningBouncingBall = new ProjectileSpell(ID_LIGHTNING_BALL, ElementalType.LIGHTNING, "Boule electrique rebondissante", 2, animLightningBouncingBall, 100, 100, 1, 1, 10);
        Spell iceBreath = new BreathSpell(ID_ICE_BREATH, ElementalType.ICE, "Souffle de glace", 300, animIceBreath, 100, 100, 1, 0, 1, 0.35f, 15);
        Spell iceSpikeBall = new ProjectileSpell(ID_ICE_SPIKE_BALL, ElementalType.ICE, "Boule a pointes de glace", 2, animIceSpikeBall, 100, 100, 1, 1, 10);
        Spell iceRune = new ExplosionSpell(ID_ICE_RUNE, ElementalType.ICE, "Rune de glace", 1, animIceRune, 100, 100, 20, 10);
        Spell fireImmunity = new PassiveSpell(ID_FIRE_IMMUNITY, ElementalType.FIRE, "Immunite feu", animFireImmunity, 100, 100, 0);
        Spell meteorSwarm = new ExplosionSpell(ID_METEOR_SWARM, ElementalType.FIRE, "Pluie de meteors", 1, animMeteorSwarm, 100, 100, 20, 9999);
        Spell lightningImmunity = new PassiveSpell(ID_LIGHTNING_IMMUNITY, ElementalType.LIGHTNING, "Immunite électrique", animLightningImmunity, 100, 100, 0);
        Spell lightningSpear = new ProjectileSpell(ID_LIGHTNING_SPEAR, ElementalType.LIGHTNING, "Lance de foudre", 1, animLightningSpear, 100, 100, 1, 1, 60);
        Spell iceStorm = new ExplosionSpell(ID_ICE_STORM, ElementalType.ICE, "Tempete de glace", 1, animIceStorm, 100, 100, 20, 9999);
        Spell iceImmunity = new PassiveSpell(ID_ICE_IMMUNITY, ElementalType.ICE, "Immunite glace", animIceImmunity, 100, 100, 0);
        Spell greatHeal = new HealingSpell(ID_GREAT_HEAL, "Soin majeur", 1, animGreatHeal, 100, 100, Integer.MAX_VALUE);

        spellList.add(fireBall);
        spellList.add(iceSpike);
        spellList.add(spark);
        spellList.add(heal);
        spellList.add(ascendingCurrent);
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
        spellList.add(iceSpikeBall);
        spellList.add(iceRune);
        spellList.add(fireImmunity);
        spellList.add(meteorSwarm);
        spellList.add(lightningImmunity);
        spellList.add(lightningSpear);
        spellList.add(iceStorm);
        spellList.add(iceImmunity);
        spellList.add(greatHeal);

        tutoSpell.add(fireBall);
        tutoSpell.add(iceSpike);
        tutoSpell.add(spark);
        tutoSpell.add(heal);
        tutoSpell.add(ascendingCurrent);
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
        adeptIceSpell.add(iceSpikeBall);
        adeptIceSpell.add(iceRune);

        masterLavaSpell.add(fireImmunity);
        masterLavaSpell.add(meteorSwarm);

        masterChocSpell.add(lightningImmunity);
        masterChocSpell.add(lightningSpear);

        masterIceSpell.add(iceStorm);
        masterIceSpell.add(iceImmunity);

        knownSpell.add(fireBall);
        knownSpell.add(iceSpike);
        knownSpell.add(spark);
        knownSpell.add(heal);
        knownSpell.add(ascendingCurrent);
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

        try {
            initSpellsIncantations();
        } catch (IOException ioe) {
        }
    }

    public static void newKnownSpell(Spell newSpell) {
        knownSpell.add(newSpell);
    }

    public static void update(Input input, Spellington spellington, ArrayList<Projectile> activeProjectiles, ArrayList<GameAnimation> activeAnimations, ArrayList<Enemy> activeEnemy) {

        for (int i = 0; i < letters.size(); i++) {
            if (input.isKeyPressed(letters.get(i))) {
                if (incantationText.length() < 30) {
                    incantationText = incantationText + Input.getKeyName(letters.get(i));
                }
            }
        }

        boolean tabUsed = false;
        if (input.isKeyPressed(Input.KEY_TAB)) {
            tabUsed = true;
        }

        if (tabUsed) {
            boolean newSpell = false;

            for (int i = 0; i < knownSpell.size(); i++) {
                if (incantationText.equals(knownSpell.get(i).getIncantation())) {
                    if (knownSpell.get(i) instanceof PassiveSpell) {
                        if (passiveSpell != null) {
                            passiveSpell.endOfActivation(spellington, activeAnimations);
                        }
                        passiveSpell = knownSpell.get(i);
                        passiveSpell.spellActivation(spellington, input, activeAnimations, activeProjectiles, activeEnemy);
                        newSpell = true;

                    } else {
                        activeSpell = knownSpell.get(i);
                        nbSpellUses = knownSpell.get(i).getUses();
                        newSpell = true;
                    }
                }

            }
            if (!newSpell && activeSpell != null) {
                activeSpell.spellActivation(spellington, input, activeAnimations, activeProjectiles, activeEnemy);
                nbSpellUses--;
            }
            if (nbSpellUses <= 0) {
                if (activeSpell != null) {
                    activeSpell.endOfActivation(spellington, activeAnimations);
                }
                activeSpell = null;
                nbSpellUses = 0;
            }
            incantationText = "";
        }

        //test start........................................................
        if (input.isKeyPressed(Input.KEY_EQUALS)) {
            incantationText = spellList.get(0).getIncantation();
            System.out.println(activeProjectiles.size());
        }

        if (input.isKeyPressed(Input.KEY_F2)) {
            incantationText = spellList.get(3).getIncantation();
        }

        if (input.isKeyPressed(Input.KEY_F9)) {
            incantationText = spellList.get(4).getIncantation();
        }
        
        if (input.isKeyPressed(Input.KEY_F8)) {
            incantationText = spellList.get(5).getIncantation();
        }
        
        if (input.isKeyPressed(Input.KEY_F7)) {
            incantationText = spellList.get(6).getIncantation();
        }
        
        if (input.isKeyPressed(Input.KEY_F6)) {
            incantationText = spellList.get(7).getIncantation();
        }

        if (input.isKeyPressed(Input.KEY_F1)) {
            spellington.subLifePoint(10, ElementalType.NEUTRAL);
            System.out.println(spellington.getLifePoint());
        }
        //test fin..........................................................
    }

    public static String getIncantationText() {
        return incantationText;
    }

    private static void initAnimation() {
        try {

            Image[] tempImgFireBall = new Image[31];
            for (int i = 0; i < tempImgFireBall.length; i++) {
                tempImgFireBall[i] = new Image("res/image/animation/spells/tutoFireball/fireBall (" + (i + 1) + ").png");
            }
            animFireBall = new Animation(tempImgFireBall, 30);

            Image[] tempImgUpStream = new Image[19];
            for (int i = 18; i >= 0; i--) {
                tempImgUpStream[18-i] = new Image("res/image/animation/spells/upStream/upStream (" + (i + 1) + ").png");
            }
            animAscendingCurrent = new Animation(tempImgUpStream, 40);

            Image[] tempImgFireRes = new Image[19];
            for (int i = 0; i < tempImgFireRes.length; i++) {
                tempImgFireRes[i] = new Image("res/image/animation/spells/fireRes/fireRes (" + (i + 1) + ").png");
            }
            animFireResistance = new Animation(tempImgFireRes, 30);

            Image[] tempImgIceRes = new Image[19];
            for (int i = 0; i < tempImgIceRes.length; i++) {
                tempImgIceRes[i] = new Image("res/image/animation/spells/iceRes/iceRes (" + (i + 1) + ").png");
            }
            animIceResistance = new Animation(tempImgIceRes, 30);
            
            Image[] tempImgeElectricRes = new Image[19];
            for (int i = 0; i < tempImgeElectricRes.length; i++) {
                tempImgeElectricRes[i] = new Image("res/image/animation/spells/electricRes/electricRes (" + (i + 1) + ").png");
            }
            animLightningResistance = new Animation(tempImgeElectricRes, 30);

        } catch (SlickException ex) {
        }
    }

    private static void initSpellsIncantations() throws IOException {
        ArrayList<String> tempWord = new ArrayList<>();
        Random dice = new Random();

        BufferedReader readerBuffer = null;
        String line;
        String filePath = new File("").getAbsolutePath();
        try {
            readerBuffer = new BufferedReader(new FileReader(filePath + "\\src\\res\\wordbank\\noviceWord.txt"));
        } catch (FileNotFoundException exc) {
            System.out.println("Erreur d'ouverture");
        }
        while ((line = readerBuffer.readLine()) != null) {
            tempWord.add(line);
        }
        readerBuffer.close();

        for (int i = 0; i < spellList.size(); i++) {
            int tempdice = dice.nextInt(tempWord.size());
            spellList.get(i).setIncantation(tempWord.get(tempdice));
            if (tempWord.size() <= 1) {
                System.out.print("Erreur: il n'y a pas assez de mots dans noviceWord.txt");
            }
            tempWord.remove(tempdice);
        }

    }
}
