package ca.qc.bdeb.info204.spellington.textEntities;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;

/**
 *
 * @author Fallen Angel
 */
public class MenuItem {

    private String text;
    private float x;
    private float y;
    private float width;
    private float height;
    private boolean hoveredOver;

    public MenuItem(GameContainer gc, String text, boolean centerX, boolean centerY, float x, float y, float width, float height) {
        this.text = text;
        if (centerX) {
            this.x = gc.getWidth() / 2 - width / 2;
        } else {
            this.x = x;
        }
        if (centerY) {
            this.y = gc.getHeight() / 2 - height / 2;
        } else {
            this.y = y;
        }
        this.width = width;
        this.height = height;
    }

    public boolean detHoveredOver(float x, float y) {
        return (x >= this.x && x <= this.x + this.width) && (y >= this.y && y <= this.y + this.height);
    }

    public void render(Graphics g, GameContainer gc) {
        if (hoveredOver) {
            g.setColor(Color.yellow);
        } else {
            g.setColor(Color.white);
        }

        g.drawString(text, x, y);
        g.drawRect(x, y, width, height);
    }

    public boolean getHoveredOver() {
        return hoveredOver;
    }

    public void setHoveredOver(boolean hoveredOver) {
        this.hoveredOver = hoveredOver;
    }

}
