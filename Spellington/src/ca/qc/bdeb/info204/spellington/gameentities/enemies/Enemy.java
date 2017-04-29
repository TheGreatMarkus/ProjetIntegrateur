package ca.qc.bdeb.info204.spellington.gameentities.enemies;

import ca.qc.bdeb.info204.spellington.calculations.Vector2D;
import ca.qc.bdeb.info204.spellington.gameentities.LivingEntity;
import ca.qc.bdeb.info204.spellington.gameentities.Projectile;
import ca.qc.bdeb.info204.spellington.gameentities.Spellington;
import ca.qc.bdeb.info204.spellington.gameentities.Tile;
import ca.qc.bdeb.info204.spellington.gamestates.PlayState;
import java.awt.Dimension;
import java.util.ArrayList;
import org.newdawn.slick.Graphics;

/**
 * A LivingEntity opposing the player.
 *
 * @author Celtis, Cristian
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
        DUMMY,
        BOSS
    }

    public static Dimension HUMANOID_SIZE = new Dimension(50, 100);
    public static Dimension RANGED_SIZE = new Dimension(50, 70);
    public static Dimension MAGE_SIZE = new Dimension(50, 50);
    public static Dimension SLIME_SIZE = new Dimension(50, 50);
    public static Dimension BOSS_SIZE = new Dimension(50, 50);

    protected ArrayList<String> droppableSpells = new ArrayList<>();
    protected int xpOnKill;
    protected ElementalType damageType;
    protected EnemyType enemyType;
    protected int damage;

    public Enemy(float x, float y, Dimension dim, MouvementState mouvementState, float gravModifier, EnemyType enemyType) {
        super(x, y, dim.width, dim.height, mouvementState, gravModifier, 0);
        this.enemyType = enemyType;
        //Missing resistances
        if (this instanceof MeleeEnemy) {
            switch (this.enemyType) {
                case KEEPER:

                    this.maxLifePoint = 20;
                    this.xpOnKill = 0;
                    this.damageType = ElementalType.NEUTRAL;
                    this.damage = 12;
                    break;
                case GUARD:
                    this.maxLifePoint = 50;
                    this.xpOnKill = 0;
                    this.damageType = ElementalType.LIGHTNING;
                    this.damage = 12;
                    break;
                case FIRE_SLIME:
                    this.maxLifePoint = 60;
                    this.xpOnKill = 0;
                    this.damageType = ElementalType.FIRE;
                    this.damage = 10;
                    break;
                case ICE_SLIME:
                    this.maxLifePoint = 60;
                    this.xpOnKill = 0;
                    this.damageType = ElementalType.ICE;
                    this.damage = 10;
                    break;
                case LIGHTNING_SLIME:
                    this.maxLifePoint = 60;
                    this.xpOnKill = 0;
                    this.damageType = ElementalType.LIGHTNING;
                    this.damage = 10;
                    break;
                case DUMMY:
                    this.maxLifePoint = 60;
                    this.xpOnKill = 0;
                    this.damageType = ElementalType.LIGHTNING;
                    this.damage = 10;
                    break;
            }
        } else if (this instanceof RangedEnemy) {
            switch (this.enemyType) {
                case ARCHER:
                    this.maxLifePoint = 10;
                    this.xpOnKill = 0;
                    this.damageType = ElementalType.NEUTRAL;
                    this.damage = 5;
                    break;
                case CROSSBOWMAN:
                    this.maxLifePoint = 20;
                    this.xpOnKill = 0;
                    this.damageType = ElementalType.LIGHTNING;
                    this.damage = 20;
                    break;
            }
        } else if (this instanceof MageEnemy) {
            switch (this.enemyType) {
                case PYROMANCER:
                    this.maxLifePoint = 30;
                    this.xpOnKill = 0;
                    this.damageType = ElementalType.FIRE;
                    this.damage = 30;
                    break;
                case CRYOMANCER:
                    this.maxLifePoint = 30;
                    this.xpOnKill = 0;
                    this.damageType = ElementalType.ICE;
                    this.damage = 20;
                    break;
                case ELECTROMANCER:
                    this.maxLifePoint = 30;
                    this.xpOnKill = 0;
                    this.damageType = ElementalType.LIGHTNING;
                    this.damage = 50;
                    break;
            }
        } else if (this instanceof BossEnemy) {
            switch (this.enemyType) {
                case BOSS:
                    this.maxLifePoint = 9999;
                    this.xpOnKill = 0;
                    this.damageType = ElementalType.LIGHTNING;
                    this.damage = 50;
                    break;
            }
        }
        this.gravModifier = 2;
        this.lifePoint = this.maxLifePoint;
        loadAnimations();
    }

    public ArrayList<String> getDroppableSpells() {
        return droppableSpells;
    }

    public void setDroppableSpells(ArrayList<String> droppableSpells) {
        this.droppableSpells = droppableSpells;
    }

    public abstract void loadAnimations();

    public void update(float time, Spellington spellington, ArrayList<Projectile> activeProjectiles, Tile[][] mapinfo) {
        if (this.collisionBottom || this.collisionTop) {
            this.speedVector.setY(0);
        }
        if (this.collisionRight || this.collisionLeft) {
            this.speedVector.setX(0);
        }
        move(time, spellington, activeProjectiles, mapinfo);
        attack(time, spellington, activeProjectiles, mapinfo);

        this.speedVector.add(Vector2D.multVectorScalar(PlayState.GRAV_ACC, time * gravModifier));
        this.setX(this.getX() + this.getSpeedVector().getX() * time);
        this.setY(this.getY() + this.getSpeedVector().getY() * time);
        this.resetCollisionState();
    }

    public abstract void render(Graphics g);

    public abstract void move(float time, Spellington spellington, ArrayList<Projectile> activeProjectiles, Tile[][] mapinfo);

    public abstract void attack(float time, Spellington spellington, ArrayList<Projectile> activeProjectiles, Tile[][] mapinfo);

}
