/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ca.qc.bdeb.info204.spellington.gameentities;

import org.newdawn.slick.*;
/**
 *
 * @author Celtis
 */
public abstract class LivingEntity extends GameEntity{
    
    protected Animation animation;
    
    protected int LifePointMax;
    protected int LifePoint;
    
    protected int ResElectric;
    protected int ResIce;
    protected int ResFire;
    
    protected float MovementSpeed;

    public void setLifePoint(int LifePoint) {
        this.LifePoint = LifePoint;
    }

    public void setResElectric(int ResElectric) {
        this.ResElectric = ResElectric;
    }

    public void setResIce(int ResIce) {
        this.ResIce = ResIce;
    }

    public void setResFire(int ResFire) {
        this.ResFire = ResFire;
    }

    public void setMovementSpeed(float MovementSpeed) {
        this.MovementSpeed = MovementSpeed;
    }

    public int getLifePoint() {
        return LifePoint;
    }

    public int getResElectric() {
        return ResElectric;
    }

    public int getResIce() {
        return ResIce;
    }

    public int getResFire() {
        return ResFire;
    }

    public float getMovementSpeed() {
        return MovementSpeed;
    }

    public int getLifePointMax() {
        return LifePointMax;
    }

    public void setLifePointMax(int LifePointMax) {
        this.LifePointMax = LifePointMax;
    }
    
    
}
