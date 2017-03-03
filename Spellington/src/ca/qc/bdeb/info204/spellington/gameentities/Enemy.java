package ca.qc.bdeb.info204.spellington.gameentities;

import java.util.ArrayList;

/**
 * A LivingEntity opposing the player.
 *
 * @author Celtis
 * @see LivingEntity
 */
public abstract class Enemy extends LivingEntity {

    protected ArrayList<String> droppableSpells = new ArrayList<>();

    public Enemy(float x, float y, float width, float height, MouvementState mouvementState) {
        super(x, y, width, height, mouvementState);
    }

    public ArrayList<String> getDroppableSpells() {
        return droppableSpells;
    }

    public void setDroppableSpells(ArrayList<String> droppableSpells) {
        this.droppableSpells = droppableSpells;
    }

}
