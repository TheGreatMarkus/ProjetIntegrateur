package ca.qc.bdeb.info204.spellington.gameentities;

import ca.qc.bdeb.info204.spellington.spell.Spell;
import java.util.ArrayList;
import org.newdawn.slick.Graphics;

/**
 * A StaticEntity from which the player will get a beneficial item.
 *
 * @author Cristian Aldea
 * @see StaticEntity
 */
public abstract class Treasure extends StaticEntity {

    protected ArrayList<Spell> droppableSpells;
    protected int messageDuration;
    protected Spell sortDonné;

    public Treasure(float x, float y, float width, float height, ArrayList<Spell> droppableSpells) {
        super(x, y, width, height);
        this.droppableSpells = droppableSpells;
        messageDuration = 5000;
        sortDonné = null;
    }

    /**
     * Renders the tresure on the screen.
     *
     * @param g The Graphics component
     */
    public abstract void render(Graphics g);

    /**
     * Updates the treasure.
     *
     * @param spellington The player object.
     * @param time The duration of the frame.
     */
    public abstract void update(Spellington spellington, float time);

    public int getMessageDuration() {
        return messageDuration;
    }

}
