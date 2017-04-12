package ca.qc.bdeb.info204.spellington.textEntities;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;

/**
 *
 * @author Fallen Angel
 */
public class MenuItem {

    public static enum MenuItemType {
        BUTTON,
        TEXT
    }

    private MenuItemType menuItemType;
    private String text;
    private float x;
    private float y;
    private float width;
    private float height;
    private boolean hoveredOver;

    public MenuItem(GameContainer gc, MenuItemType menuItemType, String text, boolean centerX, boolean centerY, float x, float y, float width, float height) {
        this.menuItemType = menuItemType;
        this.text = text;
        if (centerX) {
            this.x = gc.getWidth() / 2 - width / 2;
        } else {
            this.x = x;
        }
        this.x -= 10;
        if (centerY) {
            this.y = gc.getHeight() / 2 - height / 2;
        } else {
            this.y = y;
        }
        this.y -= 10;
        this.width = width;
        this.height = height;
        this.width += 20;
        this.height += 20;
    }

    public void detHoveredOver(float mouseX, float mouseY) {
        this.hoveredOver = (mouseX >= this.x && mouseX <= this.x + this.width) && (mouseY >= this.y && mouseY <= this.y + this.height);
    }

    public void render(Graphics g, GameContainer gc) {
        if (hoveredOver && this.menuItemType == MenuItemType.BUTTON) {
            g.setColor(new Color(1, 1, 1, 0.5f));
        } else {
            g.setColor(new Color(1, 1, 1, 1f));
        }

        g.fillRoundRect(x, y, width, height,20);
        g.drawString(text, x + 10, y + 10);
    }

    public boolean getHoveredOver() {
        return hoveredOver;
    }

    public void setHoveredOver(boolean hoveredOver) {
        this.hoveredOver = hoveredOver;
    }

    public float getX() {
        return x;
    }

    public void setX(float x) {
        this.x = x;
    }

    public float getY() {
        return y;
    }

    public void setY(float y) {
        this.y = y;
    }

    public float getWidth() {
        return width;
    }

    public void setWidth(float width) {
        this.width = width;
    }

    public float getHeight() {
        return height;
    }

    public void setHeight(float height) {
        this.height = height;
    }

}
