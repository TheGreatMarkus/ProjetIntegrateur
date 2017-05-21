package ca.qc.bdeb.info204.spellington.gameentities;

import ca.qc.bdeb.info204.spellington.GameCore;
import ca.qc.bdeb.info204.spellington.calculations.SpellingSystem;
import ca.qc.bdeb.info204.spellington.spell.Spell;
import java.util.ArrayList;
import org.newdawn.slick.Graphics;

/**
 *
 * @author Fallen Angel
 */
public class Chest extends Treasure {

    private boolean open;

    public Chest(float x, float y, float width, float height, ArrayList<Spell> droppableSpells) {
        super(x, y, width, height, droppableSpells);
        open = false;
    }

    @Override
    public void render(Graphics g) {
        g.drawRect(x, y, width, height);
        
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
