package ca.qc.bdeb.info204.spellington.gameentities;

import org.newdawn.slick.Animation;
import org.newdawn.slick.Graphics;

/**
 * An animation that can be displayed independently from other Entities
 *
 * @author Celtis
 */
public class GameAnimation extends GameEntity {

    private Animation animation;
    private int yModifier;
    private boolean onPlayer;

    public GameAnimation(float x, float y, float width, float height, Animation animation, boolean onPlayer, int onPlayerYMod) {
        super(x, y, width, height);
        this.onPlayer = onPlayer;
        this.animation = animation;
        this.animation.setLooping(this.onPlayer);
        this.yModifier = onPlayerYMod;

    }

    /**
     * Renders the Gameanimation of the screen
     *
     * @param g The Graphics component
     * @param spellington The player object.
     */
    public void render(Graphics g, Spellington spellington) {
        if (onPlayer) {
            this.animation.draw(spellington.getX() - 20, spellington.getY() - (10 + yModifier), getWidth(), getHeight());
        } else {
            this.animation.draw(x, y, getWidth(), getHeight());
        }

    }

    public Animation getAnimation() {
        return animation;
    }

    public boolean isOnPlayer() {
        return onPlayer;
    }

}
