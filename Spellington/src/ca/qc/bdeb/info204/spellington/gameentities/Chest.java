package ca.qc.bdeb.info204.spellington.gameentities;

import ca.qc.bdeb.info204.spellington.GameCore;
import ca.qc.bdeb.info204.spellington.calculations.SpellingSystem;
import ca.qc.bdeb.info204.spellington.spell.Spell;
import java.awt.Dimension;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

/**
 *
 * @author Fallen Angel
 */
public class Chest extends Treasure {

    private boolean open;
    private static final Dimension DIM_CHEST = new Dimension(50, 50);
    private Image imgChestClosed;
    private Image imgChestOpen;

    public Chest(float x, float y, ArrayList<Spell> droppableSpells, boolean isMasterChest) {
        super(x, y, DIM_CHEST.width, DIM_CHEST.height, droppableSpells);
        open = false;
        this.droppableSpells = droppableSpells;
        try {
            if (isMasterChest) {
                imgChestClosed = new Image("res/image/chest/masterClosed.png");
                imgChestOpen = new Image("res/image/chest/masterOpen.png");
            } else {
                imgChestClosed = new Image("res/image/chest/closed.png");
                imgChestOpen = new Image("res/image/chest/open.png");
            }
        } catch (SlickException ex) {
            Logger.getLogger(Chest.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void render(Graphics g) {
        g.setColor(Color.white);
        g.drawRect(x, y, width, height);
        if (open) {
            imgChestOpen.draw(x, y - 15, width, height + 15);
        } else {
            imgChestClosed.draw(x, y, width, height);
        }
        if (open && messageDuration > 0) {
            if (sortDonné != null) {
                g.drawString("Nouveau Sort! = " + sortDonné.getName(), x, y - 50);
            } else {
                g.drawString("Tous les sorts sont déja connus.", x, y - 50);
            }
        }

    }

    @Override
    public void update(Spellington spellington, float time) {
        if (spellington.getBounds().intersects(this.getBounds()) && !open) {
            open = true;
            //Exclude all droppable spells that the player already knows.
            droppableSpells.removeAll(SpellingSystem.getKnownSpells());
            if (!droppableSpells.isEmpty()) {
                sortDonné = droppableSpells.get(GameCore.rand.nextInt(droppableSpells.size()));
                SpellingSystem.getKnownSpells().add(sortDonné);
            }
        }

        if (open) {
            if (messageDuration > 0) {
                messageDuration -= time;
            }
            if (messageDuration < 0) {
                messageDuration = 0;
            }
        }

    }

    public boolean isOpen() {
        return open;
    }
    
    

}
