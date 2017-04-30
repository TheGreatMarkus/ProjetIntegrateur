package ca.qc.bdeb.info204.spellington.textEntities;

import ca.qc.bdeb.info204.spellington.GameCore;
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

    private static final float TEXT_GAP = 10f * GameCore.SCALE;

    private MenuItemType menuItemType;
    private String text;
    private float x;
    private float y;
    private float width;
    private float height;
    private boolean hoveredOver;
    private boolean clickable;

    public MenuItem(GameContainer gc, MenuItemType menuItemType, String text, boolean centerX, boolean centerY, float x, float y, float width, float height) {
        this.menuItemType = menuItemType;
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
        this.width += TEXT_GAP * 2f;
        this.height += TEXT_GAP * 2f;
        if (this.menuItemType == MenuItemType.BUTTON) {
            this.clickable = true;
        } else {
            this.clickable = false;
        }
    }

    public void detHoveredOver(float mouseX, float mouseY) {
        this.hoveredOver = (mouseX >= this.x && mouseX <= this.x + this.width) && (mouseY >= this.y && mouseY <= this.y + this.height);
    }

    public void render(Graphics g, GameContainer gc) {
        if (hoveredOver && this.menuItemType == MenuItemType.BUTTON || !clickable && !(this.menuItemType == MenuItemType.TEXT)) {
            g.setColor(new Color(1, 1, 1, 0.5f));
        } else {
            g.setColor(new Color(1, 1, 1, 1f));
        }
        if (this.menuItemType == MenuItemType.BUTTON) {
            g.drawRoundRect(x, y, width, height, 12);
        }
        g.drawString(text, x + TEXT_GAP, y + TEXT_GAP);

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

    public boolean getClickable() {
        return clickable;
    }

    public void setClickable(boolean clickable) {
        this.clickable = clickable;
    }

}
