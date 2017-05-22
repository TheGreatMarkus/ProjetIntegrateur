package ca.qc.bdeb.info204.spellington.gameentities;

import ca.qc.bdeb.info204.spellington.GameCore;
import ca.qc.bdeb.info204.spellington.calculations.GameManager;
import ca.qc.bdeb.info204.spellington.calculations.SpellingSystem;
import ca.qc.bdeb.info204.spellington.spell.Spell;
import java.awt.Dimension;
import java.util.ArrayList;
import org.newdawn.slick.Graphics;

/**
 *
 * @author Cristian Aldea
 */
public class PickUp extends Treasure {

    private static final Dimension DIM_PICK_UP = new Dimension(50, 50);
    private boolean pickedUp;

    public PickUp(float x, float y, ArrayList<Spell> droppableSpells) {
        super(x, y, DIM_PICK_UP.width, DIM_PICK_UP.height, droppableSpells);
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
            droppableSpells.removeAll(GameManager.getGameSave().getKnownSpells());
            if (!droppableSpells.isEmpty()) {
                GameManager.getGameSave().getKnownSpells().add(droppableSpells.get(GameCore.rand.nextInt(droppableSpells.size())));
            }
        }
    }

    public boolean isPickedUp() {
        return pickedUp;
    }

}
