/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ca.qc.bdeb.info204.spellington.gamestates;

import ca.qc.bdeb.info204.spellington.GameCore;
import ca.qc.bdeb.info204.spellington.gameentities.Spellington;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.state.transition.FadeInTransition;
import org.newdawn.slick.state.transition.FadeOutTransition;
import org.newdawn.slick.tiled.TiledMap;

/**
 *
 * @author Fallen Angel
 */
public class PlayState extends BasicGameState {

    private TiledMap map;
    private Spellington spellington;
    private Rectangle[][] mapCollision;

    @Override
    public void init(GameContainer container, StateBasedGame game) throws SlickException {
        //Init of the playState
        map = new TiledMap("src/resources/map/grotte test.tmx");
        mapCollision = new Rectangle[15][15];
        for (int i = 0; i < map.getHeight(); i++) {
            for (int j = 0; j < map.getWidth(); j++) {
                System.out.print(map.getTileId(j, i, 0));
                if (map.getTileId(j, i, 0) != 0) {
                    mapCollision[i][j] = new Rectangle(50 * j, 50 * i, 50, 50);
                } else {
                    mapCollision[i][j] = new Rectangle(9999, 9999, 50, 50);
                }

            }
            System.out.println("");
        }
        System.out.println("");
        spellington = new Spellington(500, 500);
    }

    @Override
    public void render(GameContainer gc, StateBasedGame game, Graphics g) throws SlickException {
        g.drawString("Press Escape to leave the game and go to the menu", 20, 40);
        g.setColor(Color.white);
        map.render(0, 0, 0);
        for (int i = 0; i < mapCollision.length; i++) {
            for (int j = 0; j < mapCollision[i].length; j++) {
                g.drawRect(mapCollision[i][j].getX(), mapCollision[i][j].getY(), 50, 50);
            }
        }
        spellington.render(g);
    }

    @Override
    public void update(GameContainer gc, StateBasedGame game, int delta) throws SlickException {
        if (gc.getInput().isKeyDown(Input.KEY_ESCAPE)) {
            game.enterState(GameCore.MAIN_MENU_STATE_ID, new FadeOutTransition(), new FadeInTransition());
        }
        spellington.update(gc.getInput(), delta);

        checkCollision();
    }

    @Override
    public int getID() {
        return GameCore.PLAY_STATE_ID;
    }

    private void checkCollision() {
        spellington.resetCollisionState();
        for (int i = 0; i < mapCollision.length; i++) {
            for (int j = 0; j < mapCollision[i].length; j++) {
                boolean collision = false;
                if (spellington.getBounds().intersects(mapCollision[i][j]) && !collision) {
                    //If a collision is found this frame,
                    float widthIntersection;
                    float heightIntersection;
                    //To get the width and height of the intersaction
                    if (mapCollision[i][j].getCenterX() < spellington.getBounds().getCenterX()) {
                        widthIntersection = mapCollision[i][j].getX() + mapCollision[i][j].getWidth() - spellington.getX();
                    } else {
                        widthIntersection = spellington.getX() + spellington.getWidth() - mapCollision[i][j].getX();
                    }
                    if (mapCollision[i][j].getCenterY() < spellington.getBounds().getCenterY()) {
                        heightIntersection = mapCollision[i][j].getY() + mapCollision[i][j].getHeight() - spellington.getY();

                    } else {
                        heightIntersection = spellington.getY() + spellington.getHeight() - mapCollision[i][j].getY();
                    }
                    //The side of the collision is determined by calculating the shallowest side of the intersection
                    if (widthIntersection < heightIntersection) {
                        if (mapCollision[i][j].getCenterX() < spellington.getBounds().getCenterX()) {
                            spellington.setX(spellington.getX() + (widthIntersection));
                            spellington.setCollisionLeft(true);
                        } else {
                            spellington.setX(spellington.getX() - (widthIntersection));
                            spellington.setCollisionRight(true);
                        }
                    } else if (heightIntersection < widthIntersection) {
                        if (mapCollision[i][j].getCenterY() < spellington.getBounds().getCenterY()) {
                            spellington.setY(spellington.getY() + (heightIntersection));
                            spellington.setCollisionTop(true);
                        } else {
                            spellington.setY(spellington.getY() - (heightIntersection));
                            spellington.setCollisionBottom(true);
                        }
                    }
                }else {
                }
            }
        }
    }

}
