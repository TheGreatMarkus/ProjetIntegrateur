package ca.qc.bdeb.info204.spellington.gameentities;

/**
 *
 * @author Celtis
 */
public abstract class ProjectileEntity {

    public static enum DamageType {
        FIRE,
        ICE,
        ELECTRICITY
    }

    protected float FlyingSpeed;

    protected int Damage;

    protected DamageType DamageType;
}
