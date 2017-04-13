package ca.qc.bdeb.info204.spellington.calculations;

import ca.qc.bdeb.info204.spellington.GameCore;
import ca.qc.bdeb.info204.spellington.gamestates.PlayState;
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
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.tiled.TiledMap;

/**
 *
 * @author Cristian Aldea
 */
public class GameManager {

    private static final String GAME_SAVE_PATH = "save.data";
    private static TiledMap activeMap;
    private static int activeLevel;
    private static GameSave gameSave;
    private static StateBasedGame stateBasedGame;

    //Chambers that will compose the 5 levels of the game.
    private static ArrayList<TiledMap> TUTORIAL_ROOMS = new ArrayList();
    private static ArrayList<TiledMap> DUNGEON_ROOMS = new ArrayList();
    private static ArrayList<TiledMap> PLAINS_ROOMS = new ArrayList();
    private static ArrayList<TiledMap> CASTLE_ROOMS = new ArrayList();
    private static ArrayList<TiledMap> BOSS_ROOMS = new ArrayList();

    private static final int TUTORIAL_ROOM_NUMBER = 3;
    private static final int DUNGEON_ROOM_NUMBER = 5;
    private static final int PLAINS_ROOM_NUMBER = 10;
    private static final int CASTLE_ROOM_NUMBER = 10;
    private static final int BOSS_ROOM_NUMBER = 10;

    public static void newGame() throws SlickException {
        GameSave newSave = new GameSave("temp");
        //Temp, à changer
        activeMap = TUTORIAL_ROOMS.get(0);
        ((PlayState) (stateBasedGame.getState(GameCore.PLAY_STATE_ID))).prepareLevel(activeMap, 100, 100);
    }

    public static void levelSelected(int level) throws SlickException {
        activeLevel = level;
        //Temp, à changer
        activeMap = TUTORIAL_ROOMS.get(0);
        ((PlayState) (stateBasedGame.getState(GameCore.PLAY_STATE_ID))).prepareLevel(activeMap, level, level);
    }

    public static void initGameManager(StateBasedGame stateBasedGame) {
        GameManager.stateBasedGame = stateBasedGame;
    }

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

    public static void loadMaps() {
        try {
            for (int i = 0; i < TUTORIAL_ROOM_NUMBER; i++) {
                TUTORIAL_ROOMS.add(new TiledMap("res/map/mapTuto" + (i + 1) + ".tmx"));
            }
            for (int i = 0; i < DUNGEON_ROOM_NUMBER; i++) {
                DUNGEON_ROOMS.add(new TiledMap("res/map/mapDungeon" + (i + 1) + ".tmx"));
            }
//            for (int i = 0; i < PLAINS_ROOM_NUMBER; i++) {
//                PLAINS_ROOMS.add(new TiledMap("res/map/level3/" + (i + 1) + ".tmx"));
//            }
//            for (int i = 0; i < CASTLE_ROOM_NUMBER; i++) {
//                CASTLE_ROOMS.add(new TiledMap("res/map/level4/" + (i + 1) + ".tmx"));
//            }
//            for (int i = 0; i < BOSS_ROOM_NUMBER; i++) {
//                BOSS_ROOMS.add(new TiledMap("res/map/level5/" + (i + 1) + ".tmx"));
//            }
        } catch (SlickException ex) {
            Logger.getLogger(GameManager.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
}
