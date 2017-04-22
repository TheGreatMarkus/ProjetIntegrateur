/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ca.qc.bdeb.info204.spellington.gameentities.enemies;

import java.awt.Dimension;
import org.newdawn.slick.Graphics;

/**
 *
 * @author 1522888
 */
public class BossEnemy extends Enemy {

    public BossEnemy(float x, float y, Dimension dim, MouvementState mouvementState, float GRAVITY_MODIFIER, EnemyType enemyType) {
        super(x, y, dim, mouvementState, GRAVITY_MODIFIER, enemyType);
    }

  

    @Override
    public void update(float time) {

    }

    @Override
    public void render(Graphics g) {

    }

}
