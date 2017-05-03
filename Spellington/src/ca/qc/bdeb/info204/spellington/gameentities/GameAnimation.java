package ca.qc.bdeb.info204.spellington.gameentities;

import org.newdawn.slick.Animation;
import org.newdawn.slick.Graphics;

/**
 * An animations that can be displayed independently from other Entities
 *
 * @author Celtis
 */
public class GameAnimation extends GameEntity {

    protected Animation animation;
    protected int yModifier;

    public GameAnimation(float x, float y, float width, float height, Animation animation, boolean looping, int yModifier) {
        super(x, y, width, height);

        this.animation = animation;
        this.animation.setLooping(looping);
        this.yModifier = yModifier;
        this.animation.restart();

    }

    public void render(Graphics g, Spellington spellington) {
        this.animation.draw(spellington.getX() - 20, spellington.getY() - (10 + yModifier), getWidth(), getHeight());

    }

    public Animation getAnimation() {
        return animation;
    }

}
