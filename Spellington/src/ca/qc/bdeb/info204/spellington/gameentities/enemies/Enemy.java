package ca.qc.bdeb.info204.spellington.gameentities.enemies;

import ca.qc.bdeb.info204.spellington.GameCore;
import ca.qc.bdeb.info204.spellington.calculations.Calculations;
import ca.qc.bdeb.info204.spellington.calculations.Vector2D;
import ca.qc.bdeb.info204.spellington.gameentities.GameAnimation;
import ca.qc.bdeb.info204.spellington.gameentities.LivingEntity;
import ca.qc.bdeb.info204.spellington.gameentities.Projectile;
import ca.qc.bdeb.info204.spellington.gameentities.Spellington;
import ca.qc.bdeb.info204.spellington.gameentities.Tile;
import ca.qc.bdeb.info204.spellington.gamestates.PlayState;
import java.awt.Dimension;
import java.io.Serializable;
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

    public static enum EnemyType implements Serializable {
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
    public static Dimension SLIME_SIZE = new Dimension(50, 50);
    public static Dimension BOSS_SIZE = new Dimension(50, 50);

    protected ElementalType damageType;
    protected EnemyType enemyType;
    protected int damage;
    protected float deltaXPlayer;
    protected float deltaYPlayer;

    protected int attackCooldown;
    protected int totalAttackCooldown;

    protected float aggroRange;
    protected boolean spellingtonInRange;
    protected boolean canSeePlayer;
    protected float playerDistance;
    protected boolean playerInSight;

    public Enemy(float x, float y, EnemyType enemyType) {
        super(x, y, 0, 0, AnimState.STANDING_L, 1);
        this.enemyType = enemyType;
        this.maxXSpeed = 0f;
        this.xAcc = new Vector2D(0, 0);
        this.jumpVector = new Vector2D(0, 0);
        this.maxInvulnTime = 0;

        //Initialises the enemy according to his type.
        switch (this.enemyType) {
            case KEEPER:
            case GUARD:
                this.setDim(HUMANOID_SIZE);
                this.aggroRange = 400;
                switch (this.enemyType) {
                    case KEEPER:
                        this.maxLifePoint = 20;
                        this.damageType = ElementalType.NEUTRAL;
                        this.damage = 12;
                        this.maxXSpeed = 0.2f;
                        this.xAcc = new Vector2D(0.003f, 0);
                        this.jumpVector = new Vector2D(0, -0.45f);
                        this.resFire = 10;
                        this.resIce = 0;
                        this.resElectricity = 5;
                        break;
                    case GUARD:
                        this.maxLifePoint = 50;
                        this.damageType = ElementalType.LIGHTNING;
                        this.damage = 18;
                        this.maxXSpeed = 0.1f;
                        this.xAcc = new Vector2D(0.002f, 0);
                        this.jumpVector = new Vector2D(0, -0.45f);
                        this.resFire = 20;
                        this.resIce = 0;
                        this.resElectricity = -10;
                        break;
                }
                break;
            case FIRE_SLIME:
            case ICE_SLIME:
            case LIGHTNING_SLIME:
                this.setDim(SLIME_SIZE);
                this.aggroRange = 400;
                this.maxLifePoint = 60;
                this.damage = 10;
                this.maxXSpeed = 0.1f;
                this.xAcc = new Vector2D(0.002f, 0);
                this.jumpVector = new Vector2D(0, -0.45f);
                switch (this.enemyType) {
                    case FIRE_SLIME:
                        this.damageType = ElementalType.FIRE;
                        this.resFire = 999;
                        this.resIce = 0;
                        this.resElectricity = 0;
                        break;
                    case ICE_SLIME:
                        this.damageType = ElementalType.ICE;
                        this.resFire = 0;
                        this.resIce = 999;
                        this.resElectricity = 0;
                        break;
                    case LIGHTNING_SLIME:
                        this.damageType = ElementalType.LIGHTNING;
                        this.resFire = 0;
                        this.resIce = 0;
                        this.resElectricity = 999;
                        break;
                }
                break;
            case DUMMY:
                this.setDim(HUMANOID_SIZE);
                this.maxLifePoint = 25;
                this.damageType = ElementalType.NEUTRAL;
                this.damage = 0;
                break;
            case PYROMANCER:
            case CRYOMANCER:
            case ELECTROMANCER:
                this.setDim(HUMANOID_SIZE);
                this.aggroRange = 1000;
                this.maxLifePoint = 30;
                this.damage = 20;
                switch (this.enemyType) {
                    case PYROMANCER:
                        this.damageType = ElementalType.FIRE;
                        this.resFire = 999;
                        this.resIce = 0;
                        this.resElectricity = 0;
                        break;
                    case CRYOMANCER:
                        this.damageType = ElementalType.ICE;
                        this.resFire = 0;
                        this.resIce = 999;
                        this.resElectricity = 0;
                        break;
                    case ELECTROMANCER:
                        this.damageType = ElementalType.LIGHTNING;
                        this.resFire = 0;
                        this.resIce = 0;
                        this.resElectricity = 999;
                        break;
                }
                break;
            case ARCHER:
            case CROSSBOWMAN:
                this.setDim(RANGED_SIZE);
                this.damageType = ElementalType.NEUTRAL;
                switch (this.enemyType) {
                    case ARCHER:
                        this.maxLifePoint = 10;
                        this.damage = 5;
                        this.aggroRange = 9999;
                        this.resFire = -10;
                        this.resIce = 10;
                        this.resElectricity = 0;
                        break;
                    case CROSSBOWMAN:
                        this.maxLifePoint = 20;
                        this.damage = 10;
                        this.aggroRange = 1000;
                        this.resFire = 5;
                        this.resIce = 0;
                        this.resElectricity = 10;
                        break;
                }
                break;
            case BOSS:
                this.setDim(BOSS_SIZE);
                this.aggroRange = 9999;
                this.maxLifePoint = 9999;
                this.damageType = ElementalType.LIGHTNING;
                this.damage = 50;
                this.resFire = 0;
                this.resIce = 0;
                this.resElectricity = 0;
                break;
        }

        totalAttackCooldown = 1500;
        attackCooldown = 1500;
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
     * @param activeAnimations
     * @param map
     */
    public void update(float time, Spellington spellington, ArrayList<Projectile> activeProjectiles, ArrayList<GameAnimation> activeAnimations, Tile[][] map) {
        if (this.collisionBottom || this.collisionTop) {
            this.speedVector.setY(0);
        }
        if (this.collisionRight || this.collisionLeft) {
            this.speedVector.setX(0);
        }
        if (this.invulnTime > 0) {
            this.invulnTime -= time;
            if (invulnTime < 0) {
                invulnTime = 0;
            }
        }
        playerInSight = Calculations.detEnemyCanSeeSpellington(this, spellington, map);
        deltaXPlayer = spellington.getCenterX() - this.getCenterX();
        deltaYPlayer = spellington.getCenterY() - this.getCenterY();
        playerDistance = (float) Math.sqrt(deltaXPlayer * deltaXPlayer + deltaYPlayer * deltaYPlayer);
        spellingtonInRange = playerDistance < aggroRange;

        canSeePlayer = spellingtonInRange && playerInSight;

        move(time, spellington, activeProjectiles, activeAnimations, map);
        attack(time, spellington, activeProjectiles, map);

        if (attackCooldown > 0) {
            attackCooldown -= time;
        }
        if (attackCooldown < 0) {
            attackCooldown = 0;
        }

        this.speedVector.add(PlayState.GRAV_ACC.getMultScalar(time * gravModifier));
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
//        g.setColor(Color.white);
//        g.drawString("EnemyType : " + this.enemyType, getX(), getY() - 40);
        g.setColor(Color.red);
        g.fillRect(x, y - 10, width, 5);
        g.setColor(Color.green);
        g.fillRect(x, y - 10, width * (float) this.lifePoint / (float) this.maxLifePoint, 5);
//        if (canSeePlayer) {
//            g.setColor(new Color(255, 0, 0, 100));
//            g.fillRect(getX(), getY(), getWidth(), getHeight());
//        }
//        g.setColor(Color.white);
//        g.drawRect(getX(), getY(), getWidth(), getHeight());
//        if (invulnTime > 0) {
//            g.fillRect(x, y, width, height);
//        }

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
     * @param activeAnimations
     * @param map The collision and event information for the current map.
     */
    public abstract void move(float time, Spellington spellington, ArrayList<Projectile> activeProjectiles, ArrayList<GameAnimation> activeAnimations, Tile[][] map);

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
            this.speedVector.sub(xAcc.getMultScalar(time));
            if (this.speedVector.getX() < xAcc.getMultScalar(time).getX()) {
                this.speedVector.setX(0);
            }
        } else if (this.speedVector.getX() < 0) {
            this.speedVector.add(xAcc.getMultScalar(time));
            if (this.speedVector.getX() > -xAcc.getMultScalar(time).getX()) {
                this.speedVector.setX(0);
            }
        }

    }

    public Image getImgStandingLeft() {
        return imgStandingLeft;
    }

    public void setImgStandingLeft(Image imgStandingLeft) {
        this.imgStandingLeft = imgStandingLeft;
    }

    public ElementalType getDamageType() {
        return damageType;
    }

    public void setDamageType(ElementalType damageType) {
        this.damageType = damageType;
    }

    public EnemyType getEnemyType() {
        return enemyType;
    }

    public void setEnemyType(EnemyType enemyType) {
        this.enemyType = enemyType;
    }

    public int getDamage() {
        return damage;
    }

    public void setDamage(int damage) {
        this.damage = damage;
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

    public void setTotalAttackCooldown(int totalAttackCooldown) {
        this.totalAttackCooldown = totalAttackCooldown;
    }

    public float getAggroRange() {
        return aggroRange;
    }

    public void setAggroRange(float aggroRange) {
        this.aggroRange = aggroRange;
    }

    public float getDeltaXPlayer() {
        return deltaXPlayer;
    }

    public float getDeltaYPlayer() {
        return deltaYPlayer;
    }

    public float getPlayerDistance() {
        return playerDistance;
    }

}
