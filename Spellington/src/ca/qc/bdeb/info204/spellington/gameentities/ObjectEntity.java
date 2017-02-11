/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ca.qc.bdeb.info204.spellington.gameentities;

/**
 *
 * @author Celtis
 */
public abstract class ObjectEntity extends GameEntity{
    
   protected Boolean isPickUp = true;

    public Boolean getIsPickUp() {
        return isPickUp;
    }

    public void setIsPickUp(Boolean isPickUp) {
        this.isPickUp = isPickUp;
    }
    
}
