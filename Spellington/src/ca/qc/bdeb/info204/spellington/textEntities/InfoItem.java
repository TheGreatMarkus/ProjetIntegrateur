package ca.qc.bdeb.info204.spellington.textEntities;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;

/**
 *
 * @author 1553624
 */
public class InfoItem {
    
    public static enum InfoItemType {
        FULL,
        TRANSPARENT   
    }

    private InfoItemType infoItemType;
    private float x;
    private float y;
    private float width;
    private float height;
    private boolean hoveredOver;

    public InfoItem(GameContainer gc, InfoItemType infoItemType, float x, float y, float width, float height) {
        this.infoItemType = infoItemType;
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    public void detHoveredOver(float mouseX, float mouseY) {
        this.hoveredOver = (mouseX >= this.x && mouseX <= this.x + this.width) && (mouseY >= this.y && mouseY <= this.y + this.height);
    }

    public void renderTransparent(Graphics g, GameContainer gc) {
        if (hoveredOver && this.infoItemType == InfoItemType.TRANSPARENT) {
            g.setColor(new Color(128, 128, 128, 0.3f));
        } else {
            g.setColor(new Color(128, 128, 128, 0f));
        }
        
        g.fillRect(x, y, width, height);  
    }

//    public void renderFull(Graphics g, GameContainer gc) {
//        if (hoveredOver && this.infoItemType == InfoItemType.FULL) {
//            g.setColor(new Color(128, 128, 128, 0.3f));
//        } else {
//            g.setColor(new Color(128, 128, 128, 0.6f));
//        }
//        
//        g.fillRect(x, y, width, height);  
//    }
    
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

