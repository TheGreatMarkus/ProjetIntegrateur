package ca.qc.bdeb.info204.spellington.gameentities;

/**
 *
 * @author Celtis
 */
public abstract class Projectile {

    public static enum DamageType {
        UTILITY,
        FIRE,
        ICE,
        ELECTRICITY
    }

    protected float FlyingSpeed;

    protected int Damage;

    protected DamageType DamageType;
}
