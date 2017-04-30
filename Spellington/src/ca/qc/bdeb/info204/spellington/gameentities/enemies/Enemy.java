package ca.qc.bdeb.info204.spellington.gameentities.enemies;

import ca.qc.bdeb.info204.spellington.GameCore;
import ca.qc.bdeb.info204.spellington.calculations.Calculations;
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
    protected static final float MAX_X_SPEED = 0.2f;
    protected static final Vector2D X_ACC = new Vector2D(0.001f, 0);
    protected static final float INIT_JUMP_SPEED = -0.5f;

    protected ArrayList<String> droppableSpells = new ArrayList<>();
    protected int xpOnKill;
    protected ElementalType damageType;
    protected EnemyType enemyType;
    protected int damage;
    protected boolean canSeeSpellington;
    protected float distanceFromSpellington;
    protected float deltaX;
    protected float deltaY;
    protected boolean willDoAction;
    protected float aggroRange;
    protected int attackCooldown;
    protected int totalAttackCooldown;

    public Enemy(float x, float y, Dimension dim, MouvementState mouvementState, float gravModifier, EnemyType enemyType) {
        super(x, y, dim.width, dim.height, mouvementState, gravModifier, 0);
        this.enemyType = enemyType;

        //Missing resistances
        if (this instanceof MeleeEnemy) {
            this.aggroRange = 500;
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
            this.aggroRange = 1000;
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
            this.aggroRange = 1000;
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
            this.aggroRange = 9999;
            switch (this.enemyType) {
                case BOSS:
                    this.maxLifePoint = 9999;
                    this.xpOnKill = 0;
                    this.damageType = ElementalType.LIGHTNING;
                    this.damage = 50;
                    break;
            }
        }
        totalAttackCooldown = 100;
        attackCooldown = 0;
        this.aggroRange *= GameCore.SCALE;
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
        canSeeSpellington = Calculations.detEnemyCanSeeSpellington(this, spellington, mapinfo);
        deltaX = spellington.getCenterX() - this.getCenterX();
        deltaY = spellington.getCenterY() - this.getCenterY();
        distanceFromSpellington = (float) Math.sqrt(deltaX * deltaX + deltaY * deltaY);

        willDoAction = Math.abs(distanceFromSpellington) < aggroRange && canSeeSpellington;
        if (willDoAction) {
            move(time, spellington, activeProjectiles, mapinfo);
            attack(time, spellington, activeProjectiles, mapinfo);
        }
        if (!willDoAction && this.speedVector.getX() > 0) {
            this.speedVector.sub(Vector2D.multVectorScalar(X_ACC, time));
            if (this.speedVector.getX() < Vector2D.multVectorScalar(X_ACC, time).getX()) {
                this.speedVector.setX(0);
            }
        } else if (!willDoAction && this.speedVector.getX() < 0) {
            this.speedVector.add(Vector2D.multVectorScalar(X_ACC, time));
            if (this.speedVector.getX() > -Vector2D.multVectorScalar(X_ACC, time).getX()) {
                this.speedVector.setX(0);
            }
        }

        if (attackCooldown > 0) {
            attackCooldown--;
        }
        if (attackCooldown < 0) {
            attackCooldown = 0;
        }

        this.speedVector.add(Vector2D.multVectorScalar(PlayState.GRAV_ACC, time * gravModifier));
        this.setX(this.getX() + this.getSpeedVector().getX() * time);
        this.setY(this.getY() + this.getSpeedVector().getY() * time);
        this.resetCollisionState();
    }

    public abstract void render(Graphics g);

    public abstract void move(float time, Spellington spellington, ArrayList<Projectile> activeProjectiles, Tile[][] mapinfo);

    public abstract void attack(float time, Spellington spellington, ArrayList<Projectile> activeProjectiles, Tile[][] mapinfo);

    public float getDeltaX() {
        return deltaX;
    }

    public float getDeltaY() {
        return deltaY;
    }

    public int getAttackCooldown() {
        return attackCooldown;
    }

    public void setAttackCooldown(int attackCooldown) {
        this.attackCooldown = attackCooldown;
    }

    public int getTotalAttackCooldown() {
        return totalAttackCooldown;
    }

}
