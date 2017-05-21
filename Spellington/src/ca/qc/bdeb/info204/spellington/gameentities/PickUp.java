package ca.qc.bdeb.info204.spellington.gameentities;

import ca.qc.bdeb.info204.spellington.GameCore;
import ca.qc.bdeb.info204.spellington.calculations.SpellingSystem;
import ca.qc.bdeb.info204.spellington.spell.Spell;
import java.util.ArrayList;
import org.newdawn.slick.Graphics;

/**
 *
 * @author Cristian Aldea
 */
public class PickUp extends Treasure {

    private boolean pickedUp;

    public PickUp(float x, float y, float width, float height, ArrayList<Spell> droppableSpells) {
        super(x, y, width, height, droppableSpells);
        pickedUp = false;
    }

    @Override
    public void render(Graphics g) {
        g.drawRect(x, y, width, height);
        g.drawString("This is a pickUp", x, y);

    }

    @Override
    public void update(Spellington spellington) {
        if (spellington.getBounds().intersects(this.getBounds())) {
            pickedUp = true;
            //Exclude all droppable spells that the player already knows.
            droppableSpells.removeAll(SpellingSystem.getKnownSpells());
            if (!droppableSpells.isEmpty()) {
                SpellingSystem.getKnownSpells().add(droppableSpells.get(GameCore.rand.nextInt(droppableSpells.size())));
            }
        }
    }

    public boolean isPickedUp() {
        return pickedUp;
    }

}
