package ca.qc.bdeb.info204.spellington;

import java.io.File;
import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.BasicGame;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.tiled.TiledMap;

/**
 *
 * @author Fallen Angel
 */
public class Spellington extends BasicGame {

    private static AppGameContainer appGameContainer;
    private static Controller controller;
    GameContainer container;
    TiledMap map;

    /**
     * @param args the command line arguments
     * @throws org.newdawn.slick.SlickException
     */
    public static void main(String[] args) throws SlickException {
        //Pour set les natives nécessaires aux libraires, don't touch.
        System.setProperty("java.library.path", "lib");
        System.setProperty("org.lwjgl.librarypath", new File("lib/natives").getAbsolutePath());
        System.setProperty("net.java.games.input.librarypath", new File("lib/natives").getAbsolutePath());

        controller = new Controller();

        appGameContainer = new AppGameContainer(new Spellington(), 750, 750, false);
        appGameContainer.setTargetFrameRate(60);
        appGameContainer.start();

    }

    public Spellington() {
        super("Exemple");
    }

    @Override
    public void init(GameContainer gc) throws SlickException {
        container = gc;
        container.getInput().addKeyListener(controller);
         map = new TiledMap("src/ressources/map/grotte test.tmx");
    }

    @Override
    public void update(GameContainer gc, int i) throws SlickException {
    }

    @Override
    public void render(GameContainer gc, Graphics g) throws SlickException {
        map.render(0, 0);
        g.drawString("fuck yes bitch! GET THE DICK SLICK!", 10, 50);

    }

}
