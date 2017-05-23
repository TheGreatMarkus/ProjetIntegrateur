package ca.qc.bdeb.info204.spellington.calculations;

import ca.qc.bdeb.info204.spellington.GameCore;
import static ca.qc.bdeb.info204.spellington.GameCore.DIM_MAP;
import ca.qc.bdeb.info204.spellington.gameentities.Chest;
import ca.qc.bdeb.info204.spellington.gameentities.MessageSign;
import ca.qc.bdeb.info204.spellington.gameentities.Spellington;
import ca.qc.bdeb.info204.spellington.gameentities.Tile;
import static ca.qc.bdeb.info204.spellington.gameentities.Tile.DIM_TILE;
import ca.qc.bdeb.info204.spellington.gameentities.Tile.TileEvent;
import ca.qc.bdeb.info204.spellington.gameentities.Tile.TileState;
import ca.qc.bdeb.info204.spellington.gameentities.Treasure;
import ca.qc.bdeb.info204.spellington.gameentities.enemies.DummyEnemy;
import ca.qc.bdeb.info204.spellington.gameentities.enemies.Enemy;
import ca.qc.bdeb.info204.spellington.gameentities.enemies.MageEnemy;
import ca.qc.bdeb.info204.spellington.gameentities.enemies.MeleeEnemy;
import ca.qc.bdeb.info204.spellington.gameentities.enemies.RangedEnemy;
import ca.qc.bdeb.info204.spellington.gamestates.LevelSelectionState;
import ca.qc.bdeb.info204.spellington.gamestates.PlayState;
import ca.qc.bdeb.info204.spellington.spell.Spell;
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
 * Class that manages the state of the game, such as deciding the map to play
 * and spawning enemies.
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

    //Chambers that will compose the 5 levels of the game.
    private static final ArrayList<TiledMap> TUTORIAL_ROOMS = new ArrayList();
    private static final ArrayList<TiledMap> DUNGEON_ROOMS = new ArrayList();
    private static final ArrayList<TiledMap> PLAINS_ROOMS = new ArrayList();
    private static final ArrayList<TiledMap> CASTLE_ROOMS = new ArrayList();
    private static final ArrayList<TiledMap> BOSS_ROOMS = new ArrayList();

    private static ArrayList<TiledMap> currentRooms = new ArrayList();

    private static ArrayList<Enemy> activeEnemies = new ArrayList<>();
    private static ArrayList<MessageSign> activeMessageSigns = new ArrayList<>();
    private static ArrayList<Treasure> activeTreasure = new ArrayList<>();
    //for testing
    private static final boolean ROOM_TESTING = false;
    private static final int ROOM_TESTING_INDEX = 5;

    /**
     * Initialises the GameManager.
     *
     * @param stateBasedGame
     */
    public static void initGameManager(StateBasedGame stateBasedGame) {
        GameManager.stateBasedGame = stateBasedGame;
        loadMaps();
        if (!loadGameSave()) {
            gameSave = new GameSave();
        }
    }

    /**
     * Starts a new game and puts the player in the tutorial.
     *
     * @throws SlickException General Slick Exception.
     */
    public static void newGame() throws SlickException {
        gameSave = new GameSave();
        saveGameSave();

        activeLevel = 1;
        activeMapIndex = 0;
        currentRooms = generateRooms(activeLevel);
        loadNextMap();
        extractMapInfo();
        ((PlayState) (stateBasedGame.getState(GameCore.PLAY_STATE_ID))).prepareLevel(activeMap, entryPoint.x, entryPoint.y);
        stateBasedGame.enterState(GameCore.PLAY_STATE_ID);

    }

    /**
     * Starts the game at the selected level.
     *
     * @param level The selected level.
     * @throws SlickException General Slick Exception.
     */
    public static void levelSelected(int level) throws SlickException {
        activeLevel = level;
        activeMapIndex = 0;
        currentRooms = generateRooms(activeLevel);
        loadNextMap();
    }

    /**
     * Loads a savefile of the game if one exists.
     *
     * @return The loaded save file.
     */
    public static boolean loadGameSave() {
        try {
            ObjectInputStream oos = new ObjectInputStream(new FileInputStream(GAME_SAVE_PATH));
            gameSave = (GameSave) oos.readObject();
            oos.close();
            System.out.println("Save file correctly loaded");
            System.out.println(gameSave.getKnownSpellsIDs());
            System.out.println(gameSave.getKnownEnemies());
            return true;
        } catch (FileNotFoundException ex) {
            System.out.println("Error loading save file. file not found.");
            return false;
        } catch (IOException | ClassNotFoundException ex) {
            System.out.println("Error loading save file. Error loading existing file");
            return false;
        }
    }

    /**
     * Saves the current savefile.
     *
     */
    public static void saveGameSave() {

        try {
            gameSave.setKnownSpellsIDs(new ArrayList<Integer>());
            for (Spell spell : SpellingSystem.getKnownSpells()) {
                gameSave.getKnownSpellsIDs().add(spell.getId());
            }
            ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(GAME_SAVE_PATH));
            oos.writeObject(gameSave);
            System.out.println("Save file succesfully saved.");
            oos.close();
        } catch (FileNotFoundException ex) {
            Logger.getLogger(GameManager.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(GameManager.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Loads all the maps of the game.
     */
    public static void loadMaps() {
        System.out.println("");
        System.out.println("Loading maps");
        try {
            for (int i = 0; i == i; i++) {
                TUTORIAL_ROOMS.add(new TiledMap("res/map/mapTuto" + (i + 1) + ".tmx"));
            }
        } catch (Exception ex) {
        }
        try {
            for (int i = 0; i == i; i++) {
                DUNGEON_ROOMS.add(new TiledMap("res/map/mapDungeon" + (i + 1) + ".tmx"));
            }
        } catch (Exception ex) {
        }
        try {
            for (int i = 0; i == i; i++) {
                PLAINS_ROOMS.add(new TiledMap("res/map/mapPlains" + (i + 1) + ".tmx"));
            }
        } catch (Exception ex) {
        }
        try {
            for (int i = 0; i == i; i++) {
                CASTLE_ROOMS.add(new TiledMap("res/map/mapCastle" + (i + 1) + ".tmx"));
            }
        } catch (Exception ex) {
        }
        try {
            for (int i = 0; i == i; i++) {
                BOSS_ROOMS.add(new TiledMap("res/map/mapBoss" + (i + 1) + ".tmx"));
            }
        } catch (Exception ex) {
        }

        System.out.println("Tutorial levels loaded : " + TUTORIAL_ROOMS.size());
        System.out.println("Dungeon levels loaded : " + DUNGEON_ROOMS.size());
        System.out.println("Plains levels loaded : " + PLAINS_ROOMS.size());
        System.out.println("Castle levels loaded : " + CASTLE_ROOMS.size());
        System.out.println("Boss levels loaded : " + BOSS_ROOMS.size());
        System.out.println("");
    }

    /**
     * Extract the informations from a TiledMap and converts it into a 2D array
     * of Tiles. Also, spawn the enemies of the game for that map.
     *
     * @see Tile
     */
    private static void extractMapInfo() {
        mapInformation = new Tile[DIM_MAP.height][DIM_MAP.width];
        activeEnemies = new ArrayList<>();
        activeMessageSigns = new ArrayList<>();
        activeTreasure = new ArrayList<>();
        for (int i = 0; i < activeMap.getHeight(); i++) {
            for (int j = 0; j < activeMap.getWidth(); j++) {
                TileState tempState = TileState.PASSABLE;
                TileEvent tempEvent;

                if (activeMap.getTileId(j, i, 1) == activeMap.getTileSet(1).firstGID + 11) {
                    tempState = TileState.PASSABLE;
                } else if (activeMap.getTileId(j, i, 1) == activeMap.getTileSet(1).firstGID + 3) {
                    tempState = TileState.IMPASSABLE;
                } else if (activeMap.getTileId(j, i, 1) == activeMap.getTileSet(1).firstGID) {
                    tempState = TileState.DAMAGE;
                } else if (activeMap.getTileId(j, i, 1) == activeMap.getTileSet(1).firstGID + 6) {
                    tempState = TileState.SLIPPERY;
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
                    activeEnemies.add(new DummyEnemy((DIM_TILE.width * j), (DIM_TILE.height * i), Enemy.EnemyType.DUMMY));
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
                    activeEnemies.add(new MeleeEnemy((DIM_TILE.width * j), (DIM_TILE.height * i) + 2 * Tile.DIM_TILE.height - Enemy.SLIME_SIZE.height, tempType));
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
                    activeEnemies.add(new MeleeEnemy((DIM_TILE.width * j), (DIM_TILE.height * i), tempType));
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
                    activeEnemies.add(new RangedEnemy((DIM_TILE.width * j), (DIM_TILE.height * i) + 2 * Tile.DIM_TILE.height - Enemy.RANGED_SIZE.height, tempType));
                } else if (activeMap.getTileId(j, i, 2) == tresureID) {
                    tempEvent = TileEvent.TREASURE_SPAWN;
                    //for now, all chest can give any adept spell.
                    activeTreasure.add(new Chest((DIM_TILE.width * j), (DIM_TILE.height * i), SpellingSystem.getAdeptSpells()));
//                    switch (activeLevel) {
//                        case 1:
//                            activeTreasure.add(new Chest(j, j, i, i, new ArrayList<>()));
//                            break;
//                        case 2:
//                            break;
//                        case 3:
//                            break;
//                        case 4:
//
//                            break;
//                        case 5:
//                            break;
//                    }
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
                    activeEnemies.add(new MageEnemy((DIM_TILE.width * j), (DIM_TILE.height * i), tempType));
                } else if (activeMap.getTileId(j, i, 2) == whatIsThis) {
                    tempEvent = TileEvent.WHAT_IS_THIS;
                } else if (activeMap.getTileId(j, i, 2) == message1ID) {
                    tempEvent = TileEvent.MESSAGE_1;
                    activeMessageSigns.add(new MessageSign((DIM_TILE.width * j),
                            (DIM_TILE.height * i), DIM_TILE.width, DIM_TILE.height, "Message 1"));
                } else if (activeMap.getTileId(j, i, 2) == message2ID) {
                    tempEvent = TileEvent.MESSAGE_2;
                    activeMessageSigns.add(new MessageSign((DIM_TILE.width * j),
                            (DIM_TILE.height * i), DIM_TILE.width, DIM_TILE.height, "Message 2"));
                } else if (activeMap.getTileId(j, i, 2) == message3ID) {
                    tempEvent = TileEvent.MESSAGE_3;
                    activeMessageSigns.add(new MessageSign((DIM_TILE.width * j),
                            (DIM_TILE.height * i), DIM_TILE.width, DIM_TILE.height, "Message 3"));
                } else {
                    tempEvent = TileEvent.NONE;
                }
                mapInformation[i][j] = new Tile(50 * j, 50 * i, 50, 50, tempState, tempEvent);
            }
        }
    }

    /**
     * Checks if the level has ended and the next should be loaded.
     *
     * @param spellington The protagonist of the game.
     * @throws SlickException General Slick Exception.
     */
    public static void checkEndOfLevel(Spellington spellington) throws SlickException {
        if (/*activeEnemies.isEmpty() && */spellington.getBounds().intersects(new Rectangle(exitPoint.x, exitPoint.y, Tile.DIM_TILE.width, Tile.DIM_TILE.height))) {
            loadNextMap();
        }
    }

    /**
     * Determines the next map to be played depending on the current map.
     *
     * @throws SlickException
     */
    private static void loadNextMap() throws SlickException {
        boolean endOfLevel = false;
        if (!ROOM_TESTING) {
            if (activeMapIndex < currentRooms.size()) {
                activeMap = currentRooms.get(activeMapIndex);
                activeMapIndex += 1;
            } else {
                endOfLevel = true;
            }
        } else if (ROOM_TESTING) {
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
                    activeMap = BOSS_ROOMS.get(ROOM_TESTING_INDEX);
                    break;
                default:
                    System.out.println("Invalid level number");
                    break;
            }
        }
        if (!endOfLevel) {
            extractMapInfo();
            ((PlayState) (stateBasedGame.getState(GameCore.PLAY_STATE_ID))).prepareLevel(activeMap, entryPoint.x, entryPoint.y);
            stateBasedGame.enterState(GameCore.PLAY_STATE_ID);
        } else {
            gameSave.completeLevel(activeLevel);
            saveGameSave();
            ((LevelSelectionState) (stateBasedGame.getState(GameCore.LEVEL_SELECTION_STATE_ID))).prepareLevel(gameSave);
            stateBasedGame.enterState(GameCore.LEVEL_SELECTION_STATE_ID);
        }
    }

    private static ArrayList<TiledMap> generateRooms(int activeLevel) {
        if (activeLevel == 1) {
            return TUTORIAL_ROOMS;
        } else if (activeLevel == 5) {
            return BOSS_ROOMS;
        }
        ArrayList<TiledMap> allRooms = new ArrayList();
        ArrayList<TiledMap> generatedRooms = new ArrayList();
        switch (activeLevel) {
            case 2:
                allRooms = (ArrayList<TiledMap>) DUNGEON_ROOMS.clone();
                break;
            case 3:
                allRooms = (ArrayList<TiledMap>) PLAINS_ROOMS.clone();
                break;
            case 4:
                allRooms = (ArrayList<TiledMap>) CASTLE_ROOMS.clone();
                break;
            default:
                System.out.println("Invalid level number");
                break;
        }
        ArrayList<TiledMap> usedRooms = (ArrayList<TiledMap>) (allRooms.clone());
        for (int i = 0; i < LEVEL_LENGTH; i++) {
            int index = GameCore.rand.nextInt(usedRooms.size());
            generatedRooms.add(usedRooms.get(index));
            usedRooms.remove(index);
        }
        System.out.println("Level " + activeLevel + " : ");
        for (int i = 0; i < generatedRooms.size(); i++) {
            for (int j = 0; j < allRooms.size(); j++) {
                if (allRooms.get(j).equals(generatedRooms.get(i))) {
                    System.out.println("Room " + (i + 1) + " index  : " + j);
                }
            }
        }

        return generatedRooms;
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

    public static ArrayList<MessageSign> getActiveMessageSigns() {
        return activeMessageSigns;
    }

    public static ArrayList<Treasure> getActiveTreasure() {
        return activeTreasure;
    }

}
