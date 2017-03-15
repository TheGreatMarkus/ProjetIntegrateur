package ca.qc.bdeb.info204.spellington.gameentities;

import ca.qc.bdeb.info204.spellington.calculations.Vector2D;
import ca.qc.bdeb.info204.spellington.gamestates.PlayState;
import org.newdawn.slick.Animation;

/**
 * A DynamicEntity that can be fired by other entities in the game.
 *
 * @author Celtis
 * @see DynamicEntity
 */
public class Projectile extends DynamicEntity {
    public enum Trajectory {curved, strait};


    protected int Damage;
    protected Animation animation;
    
    
    public Projectile(float x, float y, Animation anim,float GRAVITY_MODIFIER) {
        super(x, y, 20, 20, GRAVITY_MODIFIER);
        
        this.animation = anim;
        
        
        
    }
    
    public void update (float time) {
        
    this.speedVector.add(Vector2D.multVectorScalar(PlayState.GRAV_ACC, time * GRAVITY_MODIFIER));
    
    
    }
}
