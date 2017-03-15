package ca.qc.bdeb.info204.spellington.calculations;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.tiled.TiledMap;

/**
 *
 * @author Cristian Aldea
 */
public class GameManager {

    private static final String GAME_SAVE_PATH = "save.data";
    private static int activeLevel;
    private static GameSave gameSave;

    //Chambers that will compose the 5 levels of the game.
    private static ArrayList<TiledMap> TUTORIAL_ROOMS = new ArrayList();
    private static ArrayList<TiledMap> DUNGEON_ROOMS = new ArrayList();
    private static ArrayList<TiledMap> PLAINS_ROOMS = new ArrayList();
    private static ArrayList<TiledMap> CASTLE_ROOMS = new ArrayList();
    private static ArrayList<TiledMap> BOSS_ROOMS = new ArrayList();

    private static final int TUTORIAL_ROOMS_NUMBER = 10;
    private static final int DUNGEON_ROOMS_NUMBER = 10;
    private static final int PLAINS_ROOMS_NUMBER = 10;
    private static final int CASTLE_ROOMS_NUMBER = 10;
    private static final int BOSS_ROOMS_NUMBER = 10;

    public GameManager() {
    }

    public static GameSave loadGameSave() {
        FileInputStream fos = null;
        GameSave tempSaveGame = null;
        try {
            fos = new FileInputStream(GAME_SAVE_PATH);
            ObjectInputStream oos = new ObjectInputStream(fos);
            tempSaveGame = (GameSave) oos.readObject();
        } catch (FileNotFoundException ex) {
            Logger.getLogger(GameManager.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(GameManager.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(GameManager.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                fos.close();
            } catch (IOException ex) {
                Logger.getLogger(GameManager.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return tempSaveGame;
    }

    public static void saveGameSave() {
        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(GAME_SAVE_PATH);
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(gameSave);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(GameManager.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(GameManager.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                fos.close();
            } catch (IOException ex) {
                Logger.getLogger(GameManager.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public void loadChambers() {
        try {
            for (int i = 0; i < TUTORIAL_ROOMS_NUMBER; i++) {
                TUTORIAL_ROOMS.add(new TiledMap("res/map/level1/" + (i + 1) + ".tmx"));
            }
            for (int i = 0; i < DUNGEON_ROOMS_NUMBER; i++) {
                DUNGEON_ROOMS.add(new TiledMap("res/map/level2/" + (i + 1) + ".tmx"));
            }
            for (int i = 0; i < PLAINS_ROOMS_NUMBER; i++) {
                PLAINS_ROOMS.add(new TiledMap("res/map/level3/" + (i + 1) + ".tmx"));
            }
            for (int i = 0; i < CASTLE_ROOMS_NUMBER; i++) {
                CASTLE_ROOMS.add(new TiledMap("res/map/level4/" + (i + 1) + ".tmx"));
            }
            for (int i = 0; i < BOSS_ROOMS_NUMBER; i++) {
                BOSS_ROOMS.add(new TiledMap("res/map/level5/" + (i + 1) + ".tmx"));
            }
        } catch (SlickException ex) {
            Logger.getLogger(GameManager.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
}
