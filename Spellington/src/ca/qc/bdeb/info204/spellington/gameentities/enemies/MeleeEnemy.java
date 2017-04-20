/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ca.qc.bdeb.info204.spellington.gameentities.enemies;

import ca.qc.bdeb.info204.spellington.GameCore;
import ca.qc.bdeb.info204.spellington.calculations.Vector2D;
import ca.qc.bdeb.info204.spellington.gamestates.PlayState;
import java.awt.Dimension;
import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;

/**
 *
 * @author 1522888
 */
public class MeleeEnemy extends Enemy {

    public MeleeEnemy(float x, float y, Dimension dim, MouvementState mouvementState, float GRAVITY_MODIFIER, EnemyType enemyType) {
        super(x, y, dim, mouvementState, GRAVITY_MODIFIER, enemyType);
    }

    @Override
    public void update(float time) {
        if (this.collisionBottom || this.collisionTop) {
            this.speedVector.setY(0);
        }
        if (this.collisionRight || this.collisionLeft) {
            this.speedVector.setX(0);
        }
        this.speedVector.setX((float) GameCore.rand.nextInt(4) * 0.01f - 0.02f);
        this.speedVector.add(Vector2D.multVectorScalar(PlayState.GRAV_ACC, time * GRAVITY_MODIFIER));
        this.setX(this.getX() + this.getSpeedVector().getX() * time);
        this.setY(this.getY() + this.getSpeedVector().getY() * time);
    }

    @Override
    public void render(Graphics g) {
        g.setColor(Color.yellow);
        g.drawRect(x, y, width, height);
    }

}
