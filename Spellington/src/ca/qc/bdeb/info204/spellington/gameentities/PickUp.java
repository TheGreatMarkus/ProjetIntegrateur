package ca.qc.bdeb.info204.spellington.gameentities;

import ca.qc.bdeb.info204.spellington.GameCore;
import ca.qc.bdeb.info204.spellington.calculations.GameManager;
import ca.qc.bdeb.info204.spellington.calculations.SpellingSystem;
import ca.qc.bdeb.info204.spellington.spell.Spell;
import java.awt.Dimension;
import java.util.ArrayList;
import org.newdawn.slick.Color;
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
        sortDonné = null;
    }

    @Override
    public void render(Graphics g) {
        g.setColor(Color.white);
        g.drawRect(x, y, width, height);
        g.drawString("pickUp", x, y);
        if (pickedUp) {
            if (sortDonné != null) {
                g.drawString("Nouveau Sort! = " + sortDonné.getName(), x, y - 20);
            } else {
                g.drawString("Tous les sorts sont déja connus.", x, y - 20);
            }
        }

    }

    @Override
    public void update(Spellington spellington, float time) {
        if (spellington.getBounds().intersects(this.getBounds()) && !pickedUp) {
            pickedUp = true;
            //Exclude all droppable spells that the player already knows.
            droppableSpells.removeAll(SpellingSystem.getKnownSpells());
            if (!droppableSpells.isEmpty()) {
                sortDonné = droppableSpells.get(GameCore.rand.nextInt(droppableSpells.size()));
                SpellingSystem.getKnownSpells().add(sortDonné);
            }
        }

        if (pickedUp) {
            if (messageDuration > 0) {
                messageDuration -= time;
            }
            if (messageDuration < 0) {
                messageDuration = 0;
            }
        }

    }

    public boolean isPickedUp() {
        return pickedUp;
    }

}
