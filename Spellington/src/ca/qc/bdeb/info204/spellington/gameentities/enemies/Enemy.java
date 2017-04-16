package ca.qc.bdeb.info204.spellington.gameentities.enemies;

import ca.qc.bdeb.info204.spellington.gameentities.LivingEntity;
import java.awt.Dimension;
import java.util.ArrayList;
import org.newdawn.slick.Animation;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;

/**
 * A LivingEntity opposing the player.
 *
 * @author Celtis
 * @see LivingEntity
 */
public abstract class Enemy extends LivingEntity {

    public static enum EnemyType {
        KEEPER,
        GUARD,
        ARCHER,
        CROSSBOWMAN,
        PYROMANCER,
        CRYOMANCER,
        ELECTROMANCER,
        FIRE_SLIME,
        ICE_SLIME,
        LIGHTNING_SLIME,
        BOSS
    }

    public static Dimension HUMANOID_SIZE = new Dimension(50, 50);
    public static Dimension MAGE_SIZE = new Dimension(50, 50);
    public static Dimension SLIME_SIZE = new Dimension(50, 50);
    public static Dimension BOSS_SIZE = new Dimension(50, 50);

    protected static Image imgJumpL;
    protected static Image imgJumpR;
    protected static Image imgStandingL;
    protected static Image imgStandingR;
    protected static Image imgWallL;
    protected static Image imgWallR;
    protected static Animation animWalkL;
    protected static Animation animWalkR;

    protected ArrayList<String> droppableSpells = new ArrayList<>();
    protected int xpOnKill;
    protected ElementalType damageType;
    protected EnemyType enemyType;
    protected int damage;

    public Enemy(float x, float y, Dimension dim, MouvementState mouvementState, float GRAVITY_MODIFIER, int maxLifePoint, EnemyType enemyType) {
        super(x, y, dim.width, dim.height, mouvementState, GRAVITY_MODIFIER, maxLifePoint);
        this.enemyType = enemyType;
        if (this instanceof MeleeEnemy) {
            switch (this.enemyType) {
                case KEEPER:
                    this.xpOnKill = 0;
                    this.damageType = ElementalType.NEUTRAL;
                    this.damage = 12;
                    break;
                case GUARD:
                    this.xpOnKill = 0;
                    this.damageType = ElementalType.LIGHTNING;
                    this.damage = 12;
                    break;
                case FIRE_SLIME:
                    this.xpOnKill = 0;
                    this.damageType = ElementalType.FIRE;
                    this.damage = 10;
                    break;
                case ICE_SLIME:
                    this.xpOnKill = 0;
                    this.damageType = ElementalType.ICE;
                    this.damage = 10;
                    break;
                case LIGHTNING_SLIME:
                    this.xpOnKill = 0;
                    this.damageType = ElementalType.LIGHTNING;
                    this.damage = 10;
                    break;
            }
        } else if (this instanceof RangedEnemy) {
            switch (this.enemyType) {
                case ARCHER:
                    this.xpOnKill = 0;
                    this.damageType = ElementalType.NEUTRAL;
                    this.damage = 5;
                    break;
                case CROSSBOWMAN:
                    this.xpOnKill = 0;
                    this.damageType = ElementalType.LIGHTNING;
                    this.damage = 20;
                    break;
            }
        } else if (this instanceof MageEnemy) {
            switch (this.enemyType) {
                case PYROMANCER:
                    this.xpOnKill = 0;
                    this.damageType = ElementalType.FIRE;
                    this.damage = 30;
                    break;
                case CRYOMANCER:
                    this.xpOnKill = 0;
                    this.damageType = ElementalType.ICE;
                    this.damage = 20;
                    break;
                case ELECTROMANCER:
                    this.xpOnKill = 0;
                    this.damageType = ElementalType.LIGHTNING;
                    this.damage = 50;
                    break;
            }
        } else if (this instanceof BossEnemy) {
            switch (this.enemyType) {
                case BOSS:
                    this.xpOnKill = 0;
                    this.damageType = ElementalType.LIGHTNING;
                    this.damage = 50;
                    break;
            }
        }
        //loadAnimations(this.enemyType);

    }

    public ArrayList<String> getDroppableSpells() {
        return droppableSpells;
    }

    public void setDroppableSpells(ArrayList<String> droppableSpells) {
        this.droppableSpells = droppableSpells;
    }

    private void loadAnimations(EnemyType enemyType) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public abstract void update(float time);

    public abstract void render(Graphics g);

}
