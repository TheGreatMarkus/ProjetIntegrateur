/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ca.qc.bdeb.info204.spellington.gameentities.enemies;

import ca.qc.bdeb.info204.spellington.gameentities.Projectile;
import ca.qc.bdeb.info204.spellington.gameentities.Spellington;
import ca.qc.bdeb.info204.spellington.gameentities.Tile;
import java.util.ArrayList;
import org.newdawn.slick.Graphics;

/**
 *
 * @author Fallen Angel
 */
public class DummyEnemy extends Enemy {

    public DummyEnemy(float x, float y, EnemyType enemyType) {
        super(x, y, enemyType);
        
    }

    @Override
    public void loadAnimations() {

    }

    @Override
    public void render(Graphics g) {
        System.out.println("howwwww");
        renderGeneralInfo(g);
        

    }

    @Override
    public void move(float time, Spellington spellington, ArrayList<Projectile> activeProjectiles, Tile[][] map) {
        //No mouvement
        System.out.println("afdsaa");
    }

    @Override
    public void attack(float time, Spellington spellington, ArrayList<Projectile> activeProjectiles, Tile[][] map) {
        //No attacking
        System.out.println("asdfasdf");
    }

}
