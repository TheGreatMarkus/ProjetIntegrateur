package ca.qc.bdeb.info204.spellington.gameentities;

/**
 *
 * @author Celtis
 */
public abstract class ObjectEntity extends GameEntity {

    protected Boolean isPickUp = true;

    public Boolean getIsPickUp() {
        return isPickUp;
    }

    public void setIsPickUp(Boolean isPickUp) {
        this.isPickUp = isPickUp;
    }

}
