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
import org.newdawn.slick.Animation;
import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;

/**
 * A LivingEntity opposing the player. Will be spawned in the game in
 * GameManager
 *
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

    protected Image imgStandingLeft;
    protected Image imgStandingRight;
    protected Animation animAttackL;
    protected Animation animAttackR;

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
    protected float deltaXSpellington;
    protected float deltaYSpellington;

    protected int attackCooldown;
    protected int totalAttackCooldown;

    protected float aggroRange;
    protected boolean spellingtonInRange;
    protected boolean willDoAction;
    protected float distanceFromSpellington;
    protected boolean canSeeSpellington;

    public Enemy(float x, float y, EnemyType enemyType) {
        super(x, y, 0, 0, AnimState.STANDING_L, 1, 0);
        this.enemyType = enemyType;

        //Missing resistances
        if (this instanceof MeleeEnemy) {
            this.aggroRange = 500;
            switch (this.enemyType) {
                case KEEPER:
                    this.setHeight(HUMANOID_SIZE.height);
                    this.setWidth(HUMANOID_SIZE.width);
                    this.maxLifePoint = 20;
                    this.xpOnKill = 0;
                    this.damageType = ElementalType.NEUTRAL;
                    this.damage = 12;
                    break;
                case GUARD:
                    this.setHeight(HUMANOID_SIZE.height);
                    this.setWidth(HUMANOID_SIZE.width);
                    this.maxLifePoint = 50;
                    this.xpOnKill = 0;
                    this.damageType = ElementalType.LIGHTNING;
                    this.damage = 12;
                    break;
                case FIRE_SLIME:
                    this.setHeight(SLIME_SIZE.height);
                    this.setWidth(SLIME_SIZE.width);
                    this.maxLifePoint = 60;
                    this.xpOnKill = 0;
                    this.damageType = ElementalType.FIRE;
                    this.damage = 10;
                    break;
                case ICE_SLIME:
                    this.setHeight(SLIME_SIZE.height);
                    this.setWidth(SLIME_SIZE.width);
                    this.maxLifePoint = 60;
                    this.xpOnKill = 0;
                    this.damageType = ElementalType.ICE;
                    this.damage = 10;
                    break;
                case LIGHTNING_SLIME:
                    this.setHeight(SLIME_SIZE.height);
                    this.setWidth(SLIME_SIZE.width);
                    this.maxLifePoint = 60;
                    this.xpOnKill = 0;
                    this.damageType = ElementalType.LIGHTNING;
                    this.damage = 10;
                    break;
            }
        }
        if (this instanceof DummyEnemy) {
            this.setHeight(HUMANOID_SIZE.height);
            this.setWidth(HUMANOID_SIZE.width);
            this.maxLifePoint = 9999;
            this.xpOnKill = 0;
            this.damageType = ElementalType.LIGHTNING;
            this.damage = 10;

        }
        if (this instanceof RangedEnemy) {
            this.setHeight(RANGED_SIZE.height);
            this.setWidth(RANGED_SIZE.width);
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
        }
        if (this instanceof MageEnemy) {
            this.setHeight(HUMANOID_SIZE.height);
            this.setWidth(HUMANOID_SIZE.width);
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
        }
        if (this instanceof BossEnemy) {
            this.setHeight(HUMANOID_SIZE.height);
            this.setWidth(HUMANOID_SIZE.width);
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

    /**
     * Loads the animations for this enemy.
     */
    public abstract void loadAnimations();

    /**
     * General update function for all enemies that will update movement and
     * decisions.
     *
     * @param time The delta of the frame
     * @param spellington
     * @param activeProjectiles
     * @param mapinfo
     */
    public void update(float time, Spellington spellington, ArrayList<Projectile> activeProjectiles, Tile[][] mapinfo) {
        if (this.collisionBottom || this.collisionTop) {
            this.speedVector.setY(0);
        }
        if (this.collisionRight || this.collisionLeft) {
            this.speedVector.setX(0);
        }
        canSeeSpellington = Calculations.detEnemyCanSeeSpellington(this, spellington, mapinfo);
        deltaXSpellington = spellington.getCenterX() - this.getCenterX();
        deltaYSpellington = spellington.getCenterY() - this.getCenterY();
        distanceFromSpellington = (float) Math.sqrt(deltaXSpellington * deltaXSpellington + deltaYSpellington * deltaYSpellington);
        spellingtonInRange = Math.abs(distanceFromSpellington) < aggroRange;

        willDoAction = spellingtonInRange && canSeeSpellington;

        move(time, spellington, activeProjectiles, mapinfo);
        attack(time, spellington, activeProjectiles, mapinfo);

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

    /**
     * Renders general information about the enemy for testing purposes.
     *
     * @param g The Graphics component.
     */
    public void renderGeneralInfo(Graphics g) {
        g.setColor(Color.white);
        g.drawString("EnemyType : " + this.enemyType, getX(), getY() - 40);
        g.drawString("HP = " + this.lifePoint, getX(), getY() - 20);
        if (willDoAction) {
            g.setColor(new Color(255, 0, 0, 100));
            g.fillRect(getX(), getY(), getWidth(), getHeight());
        }
        g.drawRect(getX(), getY(), getWidth(), getHeight());

    }

    /**
     * Renders the enemy on the screen.
     *
     * @param g The Graphics component.
     */
    public abstract void render(Graphics g);

    /**
     * Determines what mouvement the enemy should be doing.
     *
     * @param time The delta of the frame.
     * @param spellington The playable protagonist.
     * @param activeProjectiles The list of active projectile in the game.
     * @param map The collision and event information for the current map.
     */
    public abstract void move(float time, Spellington spellington, ArrayList<Projectile> activeProjectiles, Tile[][] map);

    /**
     * Determines when and if the enemy should be attacking.
     *
     * @param time The delta of the frame.
     * @param spellington The playable protagonist.
     * @param activeProjectiles The list of active projectile in the game.
     * @param map The collision and event information for the current map.
     */
    public abstract void attack(float time, Spellington spellington, ArrayList<Projectile> activeProjectiles, Tile[][] map);

    public void slowDown(float time) {
        if (this.speedVector.getX() > 0) {
            this.speedVector.sub(Vector2D.multVectorScalar(X_ACC, time));
            if (this.speedVector.getX() < Vector2D.multVectorScalar(X_ACC, time).getX()) {
                this.speedVector.setX(0);
            }
        } else if (this.speedVector.getX() < 0) {
            this.speedVector.add(Vector2D.multVectorScalar(X_ACC, time));
            if (this.speedVector.getX() > -Vector2D.multVectorScalar(X_ACC, time).getX()) {
                this.speedVector.setX(0);
            }
        }

    }

    public float getDeltaXSpellington() {
        return deltaXSpellington;
    }

    public float getDeltaYSpellington() {
        return deltaYSpellington;
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

    public float getDistanceFromSpellington() {
        return distanceFromSpellington;
    }

    public ArrayList<String> getDroppableSpells() {
        return droppableSpells;
    }

    public void setDroppableSpells(ArrayList<String> droppableSpells) {
        this.droppableSpells = droppableSpells;
    }

    public int getDamage() {
        return damage;
    }

    public ElementalType getDamageType() {
        return damageType;
    }

}
