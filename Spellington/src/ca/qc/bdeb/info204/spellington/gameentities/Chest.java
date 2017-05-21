package ca.qc.bdeb.info204.spellington.gameentities;

import ca.qc.bdeb.info204.spellington.GameCore;
import ca.qc.bdeb.info204.spellington.calculations.SpellingSystem;
import ca.qc.bdeb.info204.spellington.spell.Spell;
import java.awt.Dimension;
import java.util.ArrayList;
import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;

/**
 *
 * @author Fallen Angel
 */
public class Chest extends Treasure {

    private boolean open;
    private static final Dimension DIM_CHEST = new Dimension(50, 50);

    public Chest(float x, float y, ArrayList<Spell> droppableSpells) {
        super(x, y, DIM_CHEST.width, DIM_CHEST.height, droppableSpells);
        open = false;
    }

    @Override
    public void render(Graphics g) {
        g.setColor(Color.white);
        g.fillRect(x, y, width, height);
        if (open) {
            g.setColor(Color.black);
            g.drawString("OPEN", x, y);
        } else {
            g.setColor(Color.black);
            g.drawString("CLOSED", x, y);
        }

    }

    @Override
    public void update(Spellington spellington) {
        if (spellington.getBounds().intersects(this.getBounds())) {
            open = true;
            //Exclude all droppable spells that the player already knows.
            droppableSpells.removeAll(SpellingSystem.getKnownSpells());
            if (!droppableSpells.isEmpty()) {
                SpellingSystem.getKnownSpells().add(droppableSpells.get(GameCore.rand.nextInt(droppableSpells.size())));
            }
        }

    }

}
