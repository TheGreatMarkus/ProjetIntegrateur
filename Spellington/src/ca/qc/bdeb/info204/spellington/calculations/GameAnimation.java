/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ca.qc.bdeb.info204.spellington.calculations;

import ca.qc.bdeb.info204.spellington.gameentities.GameEntity;
import ca.qc.bdeb.info204.spellington.gameentities.Spellington;
import org.newdawn.slick.Animation;
import org.newdawn.slick.Graphics;

/**
 *
 * @author 1544771
 */
public class GameAnimation extends GameEntity {

    protected Animation animation;
    protected int vie;
    protected int yModifier;

    public GameAnimation(float x, float y, float width, float height, Animation animation, int vie, int yModifier) {
        super(x, y, width, height);

        this.animation = animation;
        this.vie = vie;
        this.yModifier = yModifier;
        this.animation.restart();
    }

    public void update() {
        if(this.vie > 0) {
            this.vie--;
        }
    }

    public void render(Graphics g, Spellington spellington) {
        if (this.vie == -1) {
        this.animation.draw(spellington.getX()-20, spellington.getY()-(10+yModifier), width, height);
        } else {
        this.animation.draw(this.x, this.y, width, height);
        }
    }

    public int getVie() {
        return vie;
    }

}
