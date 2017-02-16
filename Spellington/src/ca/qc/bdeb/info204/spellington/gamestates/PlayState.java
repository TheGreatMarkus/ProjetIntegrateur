package ca.qc.bdeb.info204.spellington.gamestates;

import ca.qc.bdeb.info204.spellington.GameCore;
import ca.qc.bdeb.info204.spellington.gameentities.Spellington;
import ca.qc.bdeb.info204.spellington.gameentities.Tile;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
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
    private Tile[][] mapCollision;

    public static final float GRAVITY = 0.05f;

    @Override
    public void init(GameContainer gc, StateBasedGame game) throws SlickException {

        //Very bad implementation of 
        map = new TiledMap("src/resources/map/test_important.tmx");
        mapCollision = new Tile[18][32];
        for (int i = 0; i < map.getHeight(); i++) {
            for (int j = 0; j < map.getWidth(); j++) {
                if (map.getTileId(j, i, 0) != 0) {
                    mapCollision[i][j] = new Tile(50 * j, 50 * i, 50, 50, Tile.TileState.IMPASSABLE);
                } else {
                    mapCollision[i][j] = new Tile(50 * j, 50 * i, 50, 50, Tile.TileState.PASSABLE);
                }
            }
        }

        spellington = new Spellington(500, 500);
    }

    @Override
    public void render(GameContainer gc, StateBasedGame game, Graphics g) throws SlickException {
        g.scale(GameCore.SCALE, GameCore.SCALE);

        g.setColor(Color.white);
        map.render(0, 0, 0);
        for (int i = 0; i < mapCollision.length; i++) {
            for (int j = 0; j < mapCollision[i].length; j++) {
                g.drawRect(mapCollision[i][j].getX(), mapCollision[i][j].getY(), 50, 50);
            }
        }

        g.setColor(Color.blue);
        spellington.render(g);

        g.setColor(Color.red);
        g.drawString("Press Escape to leave the game and go to the menu", 20, 20);

        //Debug collision spellington
        g.setColor(Color.red);
        g.drawRect(100, 100, 25, 25);
        g.drawRect(100, 150, 25, 25);
        g.drawRect(125, 125, 25, 25);
        g.drawRect(75, 125, 25, 25);
        if (spellington.getCollisionTop()) {
            g.fillRect(100, 100, 25, 25);
        }
        if (spellington.getCollisionBottom()) {
            g.fillRect(100, 150, 25, 25);
        }
        if (spellington.getCollisionRight()) {
            g.fillRect(125, 125, 25, 25);
        }
        if (spellington.getCollisionLeft()) {
            g.fillRect(75, 125, 25, 25);
        }
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
                if (spellington.getBounds().intersects(mapCollision[i][j].getBounds()) && mapCollision[i][j].getTileState() == Tile.TileState.IMPASSABLE) {
                    //If a collision is found this frame,
                    float widthIntersection;
                    float heightIntersection;
                    //To get the width and height of the intersaction
                    if (mapCollision[i][j].getBounds().getCenterX() < spellington.getBounds().getCenterX()) {
                        widthIntersection = mapCollision[i][j].getX() + mapCollision[i][j].getWidth() - spellington.getX();
                    } else {
                        widthIntersection = spellington.getX() + spellington.getWidth() - mapCollision[i][j].getX();
                    }
                    if (mapCollision[i][j].getBounds().getCenterY() < spellington.getBounds().getCenterY()) {
                        heightIntersection = mapCollision[i][j].getY() + mapCollision[i][j].getHeight() - spellington.getY();
                    } else {
                        heightIntersection = spellington.getY() + spellington.getHeight() - mapCollision[i][j].getY();
                    }

                    //The side of the correction is determined by calculating the shallowest side of the intersection
                    if (heightIntersection < widthIntersection) {
                        if (mapCollision[i][j].getBounds().getCenterY() < spellington.getBounds().getCenterY()) {
                            spellington.setY(spellington.getY() + heightIntersection);
                            spellington.setCollisionTop(true);
                        } else if (mapCollision[i][j].getBounds().getCenterY() > spellington.getBounds().getCenterY()) {
                            spellington.setY(spellington.getY() - (heightIntersection));
                            spellington.setCollisionBottom(true);
                        }
                    } else if (widthIntersection < heightIntersection) {
                        if (mapCollision[i][j].getBounds().getCenterX() < spellington.getBounds().getCenterX()) {
                            spellington.setX(spellington.getX() + widthIntersection);
                            spellington.setCollisionLeft(true);
                        } else if (mapCollision[i][j].getBounds().getCenterX() > spellington.getBounds().getCenterX()) {
                            spellington.setX(spellington.getX() - widthIntersection);
                            spellington.setCollisionRight(true);
                        }
                    }
                }
            }
        }
    }

    public void extractMapInfo() {

    }

}
