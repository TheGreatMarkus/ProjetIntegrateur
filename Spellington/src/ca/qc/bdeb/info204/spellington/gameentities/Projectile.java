package ca.qc.bdeb.info204.spellington.gameentities;

import ca.qc.bdeb.info204.spellington.calculations.Vector2D;
import ca.qc.bdeb.info204.spellington.gamestates.PlayState;
import org.newdawn.slick.Animation;
import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;

/**
 * A DynamicEntity that can be fired by other entities in the game.
 *
 * @author Celtis
 * @see DynamicEntity
 */
public class Projectile extends DynamicEntity {

    public enum Trajectory {
        curved, strait
    };

    protected int Damage;
    protected Animation animation;

    public Projectile(float x, float y,int width ,int height , Vector2D speedVector, float GRAVITY_MODIFIER, Animation anim) {
        super(x, y, width, width, GRAVITY_MODIFIER);
        this.animation = anim;
        this.speedVector = speedVector;

    }

    public void update(float time) {
        this.speedVector.add(Vector2D.multVectorScalar(PlayState.GRAV_ACC, time * GRAVITY_MODIFIER));
        this.setX(this.getX() + this.getSpeedVector().getX() * time);
        this.setY(this.getY() + this.getSpeedVector().getY() * time);
    }

    public void render(Graphics g) {
        this.animation.draw(x, y, width, width);
    }
}
