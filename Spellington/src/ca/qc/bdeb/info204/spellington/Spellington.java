package ca.qc.bdeb.info204.spellington;

import java.io.File;
import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.BasicGame;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;

/**
 *
 * @author Fallen Angel
 */
public class Spellington extends BasicGame {

    private static AppGameContainer appGameContainer;

    /**
     * @param args the command line arguments
     * @throws org.newdawn.slick.SlickException
     */
    public static void main(String[] args) throws SlickException {
        //Pour set les natives nécessaires aux libraires, don't touch.
        System.setProperty("java.library.path", "lib");
        System.setProperty("org.lwjgl.librarypath", new File("lib/natives").getAbsolutePath());
        System.setProperty("net.java.games.input.librarypath", new File("lib/natives").getAbsolutePath());

        appGameContainer = new AppGameContainer(new Spellington(), 500, 500, false);
        appGameContainer.setTargetFrameRate(60);
        appGameContainer.start();
        
    }

    public Spellington() {
        super("Exemple");
    }

    @Override
    public void init(GameContainer gc) throws SlickException {

    }

    @Override
    public void update(GameContainer gc, int i) throws SlickException {

    }

    @Override
    public void render(GameContainer gc, Graphics grphcs) throws SlickException {
        grphcs.drawString("fuck yes!!!!!!!!!!!", 10, 50);

    }

}
