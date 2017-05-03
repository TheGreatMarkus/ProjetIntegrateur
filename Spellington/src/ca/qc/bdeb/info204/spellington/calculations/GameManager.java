package ca.qc.bdeb.info204.spellington.calculations;

import ca.qc.bdeb.info204.spellington.GameCore;
import static ca.qc.bdeb.info204.spellington.GameCore.DIM_MAP;
import ca.qc.bdeb.info204.spellington.gameentities.LivingEntity;
import ca.qc.bdeb.info204.spellington.gameentities.Spellington;
import ca.qc.bdeb.info204.spellington.gameentities.Tile;
import ca.qc.bdeb.info204.spellington.gameentities.Tile.TileEvent;
import ca.qc.bdeb.info204.spellington.gameentities.Tile.TileState;
import ca.qc.bdeb.info204.spellington.gameentities.enemies.Enemy;
import ca.qc.bdeb.info204.spellington.gameentities.enemies.MageEnemy;
import ca.qc.bdeb.info204.spellington.gameentities.enemies.MeleeEnemy;
import ca.qc.bdeb.info204.spellington.gameentities.enemies.RangedEnemy;
import ca.qc.bdeb.info204.spellington.gamestates.PlayState;
import java.awt.Point;
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
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.tiled.TiledMap;

/**
 *
 * @author Cristian Aldea
 */
public class GameManager {

    private static final String GAME_SAVE_PATH = "save.data";
    private static int activeLevel;
    private static int activeMapIndex;
    private static GameSave gameSave;
    private static StateBasedGame stateBasedGame;

    private static TiledMap activeMap;
    private static Tile[][] mapInformation;
    private static Point entryPoint;
    private static Point exitPoint;
    private static final int LEVEL_LENGTH = 20;
    private static int remainingRooms = 0;

    //Chambers that will compose the 5 levels of the game.
    private static final ArrayList<TiledMap> TUTORIAL_ROOMS = new ArrayList();
    private static final ArrayList<TiledMap> DUNGEON_ROOMS = new ArrayList();
    private static final ArrayList<TiledMap> PLAINS_ROOMS = new ArrayList();
    private static final ArrayList<TiledMap> CASTLE_ROOMS = new ArrayList();
    private static final ArrayList<TiledMap> BOSS_ROOMS = new ArrayList();

    private static final int TUTORIAL_ROOM_NUMBER = 3;
    private static final int DUNGEON_ROOM_NUMBER = 20;
    private static final int PLAINS_ROOM_NUMBER = 10;
    private static final int CASTLE_ROOM_NUMBER = 10;
    private static final int BOSS_ROOM_NUMBER = 3;

    private static ArrayList<Enemy> activeEnemies = new ArrayList<>();
    //for testing
    private static final boolean ROOM_TESTING = false;
    private static final int ROOM_TESTING_INDEX = 1;

    public static void initGameManager(StateBasedGame stateBasedGame) {
        GameManager.stateBasedGame = stateBasedGame;
    }

    public static void newGame() throws SlickException {
        GameSave newSave = new GameSave("temp");
        gameSave = newSave;
        saveGameSave();
        activeLevel = 1;
        activeMapIndex = 0;
        activeMap = TUTORIAL_ROOMS.get(activeMapIndex);
        extractMapInfo();
        ((PlayState) (stateBasedGame.getState(GameCore.PLAY_STATE_ID))).prepareLevel(activeMap, entryPoint.x, entryPoint.y);
        stateBasedGame.enterState(GameCore.PLAY_STATE_ID);

    }

    public static void levelSelected(int level) throws SlickException {
        activeLevel = level;
        remainingRooms = LEVEL_LENGTH;
        loadNextMap();
    }

    public static boolean loadGameSave() {
        FileInputStream fos = null;
        try {
            fos = new FileInputStream(GAME_SAVE_PATH);
            ObjectInputStream oos = new ObjectInputStream(fos);
            gameSave = (GameSave) oos.readObject();
            fos.close();
            oos.close();
            return true;
        } catch (FileNotFoundException ex) {
            return false;
        } catch (IOException | ClassNotFoundException ex) {
            return false;
        }
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
//            for (int attackCooldown = 0; attackCooldown < PLAINS_ROOM_NUMBER; attackCooldown++) {
//                PLAINS_ROOMS.add(new TiledMap("res/map/level3/" + (attackCooldown + 1) + ".tmx"));
//            }
//            for (int attackCooldown = 0; attackCooldown < CASTLE_ROOM_NUMBER; attackCooldown++) {
//                CASTLE_ROOMS.add(new TiledMap("res/map/level4/" + (attackCooldown + 1) + ".tmx"));
//            }
//            for (int attackCooldown = 0; attackCooldown < BOSS_ROOM_NUMBER; attackCooldown++) {
//                BOSS_ROOMS.add(new TiledMap("res/map/level5/" + (attackCooldown + 1) + ".tmx"));
//            }
        } catch (SlickException ex) {
            Logger.getLogger(GameManager.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    /**
     *
     * @author Cristian Aldea
     */
    private static void extractMapInfo() {
        mapInformation = new Tile[DIM_MAP.height][DIM_MAP.width];
        activeEnemies = new ArrayList<>();
        for (int i = 0; i < activeMap.getHeight(); i++) {
            for (int j = 0; j < activeMap.getWidth(); j++) {
                TileState tempState;
                TileEvent tempEvent;

                if (activeMap.getTileId(j, i, 1) == activeMap.getTileSet(1).firstGID + 11) {
                    tempState = TileState.PASSABLE;
                } else {
                    tempState = TileState.IMPASSABLE;
                }
                int spellingtonExitID = activeMap.getTileSet(1).firstGID + 0;
                int spellingtonEntryID = activeMap.getTileSet(1).firstGID + 3;
                int dummyID = activeMap.getTileSet(1).firstGID + 6;
                int randomSlimeID = activeMap.getTileSet(1).firstGID + 9;
                int meleeEnemyID = activeMap.getTileSet(1).firstGID + 1;
                int rangedEnemyID = activeMap.getTileSet(1).firstGID + 4;
                int tresureID = activeMap.getTileSet(1).firstGID + 7;
                int mageSpawnID = activeMap.getTileSet(1).firstGID + 10;
                int whatIsThis = activeMap.getTileSet(1).firstGID + 2;
                int message1ID = activeMap.getTileSet(1).firstGID + 5;
                int message2ID = activeMap.getTileSet(1).firstGID + 8;
                int message3ID = activeMap.getTileSet(1).firstGID + 11;

                if (activeMap.getTileId(j, i, 2) == spellingtonExitID) {
                    tempEvent = TileEvent.SPELLINGTON_EXIT;
                    exitPoint = new Point(50 * j, 50 * i);
                } else if (activeMap.getTileId(j, i, 2) == spellingtonEntryID) {
                    tempEvent = TileEvent.SPELLINGTON_ENTRY;
                    entryPoint = new Point(50 * j, 50 * i);
                } else if (activeMap.getTileId(j, i, 2) == dummyID) {
                    tempEvent = TileEvent.DUMMY_SPAWN;
                    activeEnemies.add(new MeleeEnemy((float) (50 * j), (float) (50 * i), LivingEntity.MouvementState.STANDING_L, 1, Enemy.EnemyType.DUMMY));
                } else if (activeMap.getTileId(j, i, 2) == randomSlimeID) {
                    tempEvent = TileEvent.RANDOM_SLIME_SPAWN;
                    Enemy.EnemyType tempType = null;
                    switch (GameCore.rand.nextInt(3)) {
                        case 0:
                            tempType = Enemy.EnemyType.FIRE_SLIME;
                            break;
                        case 1:
                            tempType = Enemy.EnemyType.ICE_SLIME;
                            break;
                        case 2:
                            tempType = Enemy.EnemyType.LIGHTNING_SLIME;
                            break;
                    }
                    activeEnemies.add(new MeleeEnemy((float) (50 * j), (float) (50 * i), LivingEntity.MouvementState.STANDING_L, 1, tempType));
                } else if (activeMap.getTileId(j, i, 2) == meleeEnemyID) {
                    tempEvent = TileEvent.MELEE_ENEMY_SPAWN;
                    Enemy.EnemyType tempType = null;
                    switch (GameCore.rand.nextInt(2)) {
                        case 0:
                            tempType = Enemy.EnemyType.KEEPER;
                            break;
                        case 1:
                            tempType = Enemy.EnemyType.GUARD;
                            break;
                    }
                    activeEnemies.add(new MeleeEnemy((float) (50 * j), (float) (50 * i), LivingEntity.MouvementState.STANDING_L, 1, tempType));
                } else if (activeMap.getTileId(j, i, 2) == rangedEnemyID) {
                    tempEvent = TileEvent.RANGED_ENEMY_SPAWN;
                    Enemy.EnemyType tempType = null;
                    switch (GameCore.rand.nextInt(2)) {
                        case 0:
                            tempType = Enemy.EnemyType.ARCHER;
                            break;
                        case 1:
                            tempType = Enemy.EnemyType.CROSSBOWMAN;
                            break;
                    }
                    activeEnemies.add(new RangedEnemy((float) (50 * j), (float) (50 * i), LivingEntity.MouvementState.STANDING_L, 1, tempType));
                } else if (activeMap.getTileId(j, i, 2) == tresureID) {
                    tempEvent = TileEvent.TREASURE_SPAWN;
                } else if (activeMap.getTileId(j, i, 2) == mageSpawnID) {
                    tempEvent = TileEvent.MAGE_ENEMY_SPAWN;
                    Enemy.EnemyType tempType = null;
                    switch (activeLevel) {
                        case 2:
                            tempType = Enemy.EnemyType.PYROMANCER;
                            break;
                        case 3:
                            tempType = Enemy.EnemyType.CRYOMANCER;
                            break;
                        case 4:
                            tempType = Enemy.EnemyType.ELECTROMANCER;
                            break;
                    }
                    activeEnemies.add(new MageEnemy((float) (50 * j), (float) (50 * i), LivingEntity.MouvementState.STANDING_L, 1, tempType));
                } else if (activeMap.getTileId(j, i, 2) == whatIsThis) {
                    tempEvent = TileEvent.WHAT_IS_THIS;
                } else if (activeMap.getTileId(j, i, 2) == message1ID) {
                    tempEvent = TileEvent.MESSAGE_1;
                } else if (activeMap.getTileId(j, i, 2) == message2ID) {
                    tempEvent = TileEvent.MESSAGE_2;
                } else if (activeMap.getTileId(j, i, 2) == message3ID) {
                    tempEvent = TileEvent.MESSAGE_3;
                } else {
                    tempEvent = TileEvent.NONE;
                }
                mapInformation[i][j] = new Tile(50 * j, 50 * i, 50, 50, tempState, tempEvent);
            }
        }
    }

    public static void checkEndOfLevel(Spellington spellington) throws SlickException {
        if (/*activeEnemies.isEmpty() && */spellington.intersects(new Rectangle(exitPoint.x, exitPoint.y, Tile.DIM_TILE.width, Tile.DIM_TILE.height))) {
            loadNextMap();
        }
    }

    private static void loadNextMap() throws SlickException {
        boolean endOfLevel = false;
        if (ROOM_TESTING) {
            switch (activeLevel) {
                case 1:
                    activeMap = TUTORIAL_ROOMS.get(ROOM_TESTING_INDEX);
                    break;
                case 2:
                    activeMap = DUNGEON_ROOMS.get(ROOM_TESTING_INDEX);
                    break;
                case 3:
                    activeMap = PLAINS_ROOMS.get(ROOM_TESTING_INDEX);
                    break;
                case 4:
                    activeMap = CASTLE_ROOMS.get(ROOM_TESTING_INDEX);
                    break;
                case 5:
                    activeMap = BOSS_ROOMS.get(activeMapIndex);
                    break;
                default:
                    System.out.println("SECRET LEVEL");
                    break;
            }
        } else {
            switch (activeLevel) {
                case 1:
                    if (activeMapIndex < TUTORIAL_ROOM_NUMBER - 1) {
                        activeMapIndex += 1;
                        activeMap = TUTORIAL_ROOMS.get(activeMapIndex);
                    } else {
                        endOfLevel = true;
                    }
                    break;
                case 2:
                    if (remainingRooms > 0) {
                        activeMapIndex = GameCore.rand.nextInt(DUNGEON_ROOM_NUMBER);
                        activeMap = DUNGEON_ROOMS.get(activeMapIndex);
                        remainingRooms--;
                    } else {
                        endOfLevel = true;
                    }
                    break;
                case 3:
                    break;
                case 4:
                    break;
                case 5:
                    if (activeMapIndex < TUTORIAL_ROOM_NUMBER - 1) {
                        activeMapIndex += 1;
                        activeMap = TUTORIAL_ROOMS.get(activeMapIndex);
                    } else {
                        endOfLevel = true;
                    }
                    break;
                default:
                    System.out.println("SECRET LEVEL");
                    break;
            }
        }

        if (!endOfLevel) {
            extractMapInfo();
            ((PlayState) (stateBasedGame.getState(GameCore.PLAY_STATE_ID))).prepareLevel(activeMap, entryPoint.x, entryPoint.y);
            stateBasedGame.enterState(GameCore.PLAY_STATE_ID);
        } else {
            stateBasedGame.enterState(GameCore.LEVEL_SELECTION_STATE_ID);
        }
    }

    public static Tile[][] getMapInformation() {
        return mapInformation;
    }

    public static ArrayList<Enemy> getActiveEnemies() {
        return activeEnemies;
    }

    public static Point getExitPoint() {
        return exitPoint;
    }

    public static Point getEntryPoint() {
        return entryPoint;
    }

    public static StateBasedGame getStateBasedGame() {
        return stateBasedGame;
    }

    public static int getActiveLevel() {
        return activeLevel;
    }

    public static void setActiveLevel(int activeLevel) {
        GameManager.activeLevel = activeLevel;
    }

    public static int getActiveMapIndex() {
        return activeMapIndex;
    }

    public static void setActiveMapIndex(int activeMapIndex) {
        GameManager.activeMapIndex = activeMapIndex;
    }

    public static GameSave getGameSave() {
        return gameSave;
    }

    public static void setGameSave(GameSave gameSave) {
        GameManager.gameSave = gameSave;
    }

    public static TiledMap getActiveMap() {
        return activeMap;
    }

    public static void setActiveMap(TiledMap activeMap) {
        GameManager.activeMap = activeMap;
    }

}
