package ca.qc.bdeb.info204.spellington.gameentities;

import org.newdawn.slick.Animation;
import org.newdawn.slick.Graphics;

/**
 * An animations that can be displayed independently from other Entities
 *
 * @author Celtis
 */
public class GameAnimation extends GameEntity {

    private Animation animation;
    private int yModifier;
    private boolean looping;

    public GameAnimation(float x, float y, float width, float height, Animation animation, boolean looping, int yModifier) {
        super(x, y, width, height);
        this.looping = looping;
        this.animation = animation;
        this.animation.setLooping(looping);
        this.yModifier = yModifier;
        this.animation.restart();

    }

    public void render(Graphics g, Spellington spellington) {
        if (looping) {
            this.animation.draw(spellington.getX() - 20, spellington.getY() - (10 + yModifier), getWidth(), getHeight());
        } else {
            this.animation.draw(x, y, getWidth(), getHeight());
        }

    }

    public Animation getAnimation() {
        return animation;
    }

}
