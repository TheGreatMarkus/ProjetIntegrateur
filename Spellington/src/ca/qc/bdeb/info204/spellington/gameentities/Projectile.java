package ca.qc.bdeb.info204.spellington.gameentities;

/**
 * A DynamicEntity that can be fired by other entities in the game.
 *
 * @author Celtis
 * @see DynamicEntity
 */
public abstract class Projectile extends DynamicEntity {

    public static enum DamageType {
        UTILITY,
        FIRE,
        ICE,
        ELECTRICITY
    }

    protected float FlyingSpeed;

    protected int Damage;

    protected DamageType DamageType;

    public Projectile(float x, float y, float width, float height) {
        super(x, y, width, height);
    }
}
