package ca.qc.bdeb.info204.spellington.gameentities;

/**
 * A DynamicEntity that can be fired by other entities in the game.
 *
 * @author Celtis
 * @see DynamicEntity
 */
public abstract class Projectile extends DynamicEntity {

    protected float FlyingSpeed;

    protected int Damage;

    public Projectile(float x, float y, float width, float height) {
        super(x, y, width, height);
    }
}
