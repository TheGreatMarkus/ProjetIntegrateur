/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ca.qc.bdeb.info204.spellington.gameentities;

import ca.qc.bdeb.info204.spellington.calculations.Vector2D;
import org.newdawn.slick.Animation;

/**
 *
 * @author Fallen Angel
 */
public class EffectProjectile extends Projectile{
    
    public EffectProjectile(float x, float y, int width, int height, Vector2D speedVector, float GRAVITY_MODIFIER, Animation anim) {
        super(x, y, width, height, speedVector, GRAVITY_MODIFIER, anim);
    }
    
}
