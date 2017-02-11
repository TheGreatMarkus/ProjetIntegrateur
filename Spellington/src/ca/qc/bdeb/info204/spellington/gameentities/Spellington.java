package ca.qc.bdeb.info204.spellington.gameentities;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;

/**
 * Main protegonist of the game.
 *
 * @author Fallen Angel
 */
public class Spellington extends LivingEntity {

    private static Image IMG_SPELLINGTON;

    public Spellington() throws SlickException {
        LifePointMax = 100;
        LifePoint = LifePointMax;
        
        ResElectric = 0;
        ResIce = 0;
        ResFire = 0;
        
        MovementSpeed = 0.5f;
        
        IMG_SPELLINGTON = new Image("resources/images/spellington.png");
        this.setX(500);
        this.setY(500);

    }

    /**
     *
     * @param input
     * @param delta Delta of frame. To keep speed consistent regardless of frame
     * length.
     */
    public void update(Input input, int delta) {
        if (input.isKeyDown(Input.KEY_RIGHT) && input.isKeyDown(Input.KEY_LEFT)) {// à changer devrais suivre la souris
            
        } else if (input.isKeyDown(Input.KEY_D)) {
            this.setX(this.getX() + MovementSpeed * delta);
        } else if (input.isKeyDown(Input.KEY_A)) {
            this.setX(this.getX() - MovementSpeed * delta);
        }
        if (input.isKeyDown(Input.KEY_UP) && input.isKeyDown(Input.KEY_DOWN)) { // à changer pour pas voler
            
        } else if (input.isKeyDown(Input.KEY_W)) {
            this.setY(this.getY() - MovementSpeed * delta);
        } else if (input.isKeyDown(Input.KEY_S)) {
            this.setY(this.getY() + MovementSpeed * delta);
        }

    }

    public void render(Graphics g) {
        g.drawImage(IMG_SPELLINGTON, this.getX(), this.getY());
    }

}
