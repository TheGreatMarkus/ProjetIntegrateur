package ca.qc.bdeb.slick2dtuto;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.BasicGame;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.tiled.TiledMap;

/**
 *
 */
public class Main extends BasicGame {

    GameContainer container;
    TiledMap map;

    public Main() {
        super("Premier exemple");
    }

    public static void main(String args[]) throws SlickException {
        new AppGameContainer(new Main(), 750, 750, false).start();
    }

    @Override
    public void init(GameContainer gc) throws SlickException {
        container = gc;
        map = new TiledMap("src\\ressources\\map\\grotte test.tmx");
    }

    @Override
    public void update(GameContainer gc, int i) throws SlickException {

    }

    @Override
    public void render(GameContainer gc, Graphics grphcs) throws SlickException {
       map.render(0, 0);
    }

    @Override
    public void keyReleased(int key, char c) {
        if (Input.KEY_ESCAPE == key) {
            container.exit();
        }
    }
    

}
