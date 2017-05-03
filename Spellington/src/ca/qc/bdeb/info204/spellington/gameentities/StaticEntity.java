package ca.qc.bdeb.info204.spellington.gameentities;

/**
 * A GameEntity that will not be able to move.
 *
 * @author Celtis
 * @see GameEntity
 */
public abstract class StaticEntity extends GameEntity {

    public StaticEntity(float x, float y, float width, float height) {
        super(x, y, width, height);
    }



}
