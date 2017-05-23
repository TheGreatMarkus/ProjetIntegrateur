package ca.qc.bdeb.info204.spellington.gameentities;

import ca.qc.bdeb.info204.spellington.spell.Spell;
import java.util.ArrayList;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.geom.Rectangle;

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

    public abstract void render(Graphics g);

    public abstract void update(Spellington spellington, float time);

    @Override
    public Rectangle getBounds() {
        return super.getBounds(); //To change body of generated methods, choose Tools | Templates.
    }

    public int getMessageDuration() {
        return messageDuration;
    }

}
