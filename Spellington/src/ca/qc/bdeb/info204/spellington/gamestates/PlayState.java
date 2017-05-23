package ca.qc.bdeb.info204.spellington.gamestates;

import ca.qc.bdeb.info204.spellington.GameCore;
import ca.qc.bdeb.info204.spellington.gameentities.GameAnimation;
import ca.qc.bdeb.info204.spellington.calculations.Calculations;
import ca.qc.bdeb.info204.spellington.calculations.GameManager;
import ca.qc.bdeb.info204.spellington.calculations.SpellingSystem;
import ca.qc.bdeb.info204.spellington.calculations.Vector2D;
import ca.qc.bdeb.info204.spellington.gameentities.LivingEntity;
import ca.qc.bdeb.info204.spellington.gameentities.MessageSign;
import ca.qc.bdeb.info204.spellington.gameentities.PickUp;
import ca.qc.bdeb.info204.spellington.gameentities.Projectile;
import ca.qc.bdeb.info204.spellington.gameentities.Spellington;
import ca.qc.bdeb.info204.spellington.gameentities.Tile;
import ca.qc.bdeb.info204.spellington.gameentities.Treasure;
import ca.qc.bdeb.info204.spellington.gameentities.enemies.Enemy;
import java.awt.Font;
import java.util.ArrayList;
import org.newdawn.slick.Animation;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.UnicodeFont;
import org.newdawn.slick.font.effects.ColorEffect;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.tiled.TiledMap;

/**
 * A BasicGameState corresponding to the playing part of the game.
 *
 * @author Cristian Aldea
 */
public class PlayState extends BasicGameState {

    private UnicodeFont fontSpellChant;

    private Image IMG_GAME_CROSSHAIR;

    private TiledMap map;
    private Spellington spellington;

    public ArrayList<Projectile> activeProjectiles = new ArrayList<>();
    public ArrayList<GameAnimation> activeAnimations = new ArrayList<>();

    public static final Vector2D GRAV_ACC = new Vector2D(0, 0.0009f);
    private static float slowDownTime = 0;

    //debug variable
    public static boolean debugMode = false;
    private boolean displayHUD = true;

    private Image hud;

    /**
     * Initialises the BasicGameState
     *
     * @param gc the GameContainer
     * @param game the StateBasedGame
     * @throws SlickException General Slick exception
     */
    @Override
    public void init(GameContainer gc, StateBasedGame game) throws SlickException {
        spellington = new Spellington(0, 0, LivingEntity.AnimState.JUMP_R);
        fontSpellChant = new UnicodeFont(GameCore.getFontViking(Font.BOLD, 20 * GameCore.SCALE));
        fontSpellChant.addAsciiGlyphs();
        fontSpellChant.getEffects().add(new ColorEffect(java.awt.Color.white));
        fontSpellChant.loadGlyphs();

        //Loading crosshair image.
        IMG_GAME_CROSSHAIR = new Image("res/image/cursor/small_crosshair.png");
        //Loading test map information.

        //Loading HUD image
        this.hud = new Image("src/res/image/HUD/hud.png");
    }

    /**
     * Prepares the level by changing the map and the position of spellington.
     *
     * @param currentMap The map to be played.
     * @param spellingtonX The X position where Spellington should appear
     * @param spellingtonY The Y position where spellington sould appear.
     * @throws SlickException A general Slick Exception.
     */
    public void prepareLevel(TiledMap currentMap, int spellingtonX, int spellingtonY) throws SlickException {
        activeProjectiles = new ArrayList<>();
        activeAnimations = new ArrayList<>();

        spellington.setAnimState(LivingEntity.AnimState.STANDING_R);
        spellington.setX(spellingtonX);
        spellington.setY(spellingtonY);
        spellington.setAnimState(LivingEntity.AnimState.STANDING_R);
        spellington.setSpeedVector(new Vector2D(0, 0));
        map = currentMap;
    }

    /**
     * Renders the BasicGameState
     *
     * @param gc the GameContainer
     * @param game the StateBasedGame
     * @param g The Graphics component
     * @throws SlickException General Slick exception
     */
    @Override
    public void render(GameContainer gc, StateBasedGame game, Graphics g) throws SlickException {
        //Must be the first in the PlayState renger method.
        g.scale(GameCore.SCALE, GameCore.SCALE);

        g.setColor(Color.white);
        map.render(0, 0, 0);
        g.setColor(new Color(10, 10, 10, 80));
        for (Tile[] tile1 : GameManager.getMapInformation()) {
            for (Tile tile : tile1) {
                if (tile.getTileState() == Tile.TileState.PASSABLE) {
                    g.fillRect(tile.getX(), tile.getY(), tile.getWidth(), tile.getHeight());
                }
            }
        }
//        g.setColor(Color.white);
//        for (Tile[] tile1 : GameManager.getMapInformation()) {
//            for (Tile tile : tile1) {
//                if (tile.getTileState() == Tile.TileState.PASSABLE) {
//                    g.drawString(tile.getTileEvent().toString(), tile.getX(), tile.getY());
//                }
//            }
//        }

        for (Treasure treasure : GameManager.getActiveTreasure()) {
            treasure.render(g);
        }

        for (MessageSign sign : GameManager.getActiveMessageSigns()) {
            sign.render(g);
        }

        spellington.render(g);

        g.setColor(Color.white);
        g.drawString("ESC : Menu / F3 : TOGGLE DEBUG / F4 : TOGGLE HUD", 10, GameCore.PLAY_RENDER_SIZE.height - 40);

        //Render mouse cursor during gameplay.
        float renderMouseX = gc.getInput().getMouseX() / GameCore.SCALE;
        float renderMouseY = gc.getInput().getMouseY() / GameCore.SCALE;
        IMG_GAME_CROSSHAIR.draw(renderMouseX - 12, renderMouseY - 12, 25, 25);

        for (int i = 0; i < activeProjectiles.size(); i++) {
            activeProjectiles.get(i).render(g);
        }

        //Update of animations
        ArrayList<GameAnimation> animationsToBeRemoved = new ArrayList<>();
        for (GameAnimation anim : activeAnimations) {
            anim.render(g, spellington);
            if (anim.getAnimation().isStopped()) {
                animationsToBeRemoved.add(anim);
            }
        }
        activeAnimations.removeAll(animationsToBeRemoved);

        for (int i = 0; i < GameManager.getActiveEnemies().size(); i++) {
            GameManager.getActiveEnemies().get(i).render(g);
        }

        debugInfo(g, gc);

        displayHUD(g);

        Calculations.drawAimingHelp(g, gc.getInput(), SpellingSystem.getActiveSpell(), spellington);
    }

    /**
     * Updates the BasicGameState
     *
     * @param gc the GameContainer
     * @param game the StateBasedGame
     * @param delta the delta of the frame
     * @throws SlickException General Slick exception
     */
    @Override
    public void update(GameContainer gc, StateBasedGame game, int delta) throws SlickException {
        float deltaFloat = delta;
        if (slowDownTime > 0) {
            deltaFloat *= 0.1;
            slowDownTime -= delta;
        }
        if (slowDownTime < 0) {
            slowDownTime = 0;
        }
        //System.out.println("time passed = " + deltaFloat);

        if (deltaFloat > 40) {
            deltaFloat = 40;
        }
        //Update of the message signs if there are any
        for (MessageSign sign : GameManager.getActiveMessageSigns()) {
            sign.update(spellington);
        }

        ArrayList<Treasure> treasureToBeRemoved = new ArrayList<>();
        for (Treasure treasure : GameManager.getActiveTreasure()) {
            treasure.update(spellington, deltaFloat);
            if (treasure instanceof PickUp) {
                if (((PickUp) treasure).isPickedUp() && ((PickUp) treasure).getMessageDuration() == 0) {
                    treasureToBeRemoved.add(treasure);
                }
            }
        }
        GameManager.getActiveTreasure().removeAll(treasureToBeRemoved);

        //Update of Spellington
        spellington.update(gc.getInput(), deltaFloat);
        Calculations.checkMapCollision(GameManager.getMapInformation(), spellington);

        SpellingSystem.update(gc.getInput(), spellington, activeProjectiles, activeAnimations, GameManager.getActiveEnemies());

        //Update of projectiles
        ArrayList<Projectile> projectilesToBeRemoved = new ArrayList<>();
        for (int i = 0; i < activeProjectiles.size(); i++) {
            activeProjectiles.get(i).update((float) deltaFloat);
            if (Calculations.checkProjectileCollision(activeProjectiles.get(i), GameManager.getMapInformation(), GameManager.getActiveEnemies(), activeAnimations, spellington) != -1) {
                projectilesToBeRemoved.add(activeProjectiles.get(i));
            }
        }
        activeProjectiles.removeAll(projectilesToBeRemoved);

        //Update of enemies
        ArrayList<Enemy> enemiesToBeRemoved = new ArrayList<>();
        for (Enemy enemy : GameManager.getActiveEnemies()) {
            enemy.update(deltaFloat, spellington, activeProjectiles, GameManager.getMapInformation());
            Calculations.checkMapCollision(GameManager.getMapInformation(), enemy);
            if (enemy.getLifePoint() <= 0) {
                enemiesToBeRemoved.add(enemy);
                if (GameCore.rand.nextInt(10) == 0) {
                    GameManager.getActiveTreasure().add(new PickUp(enemy.getX(), enemy.getMaxY() - 50, SpellingSystem.getAdeptSpells()));
                }
                if (!GameManager.getGameSave().getKnownEnemies().contains(enemy.getEnemyType())) {
                    GameManager.getGameSave().getKnownEnemies().add(enemy.getEnemyType());
                }
            }
        }
        GameManager.getActiveEnemies().removeAll(enemiesToBeRemoved);

        GameManager.checkEndOfLevel(spellington);

        if (gc.getInput().isKeyPressed(Input.KEY_ESCAPE)) {
            game.enterState(GameCore.PAUSE_MENU_STATE_ID);
        }
        if (gc.getInput().isKeyPressed(Input.KEY_F3)) {
            debugMode = !debugMode;
        }
        if (gc.getInput().isKeyPressed(Input.KEY_F4)) {
            displayHUD = !displayHUD;
        }
        GameCore.clearInputRecord(gc);
    }

    /**
     * Displays information about spellington for debug purposes
     *
     * @param g The Graphics component.
     */
    private void debugInfo(Graphics g, GameContainer gc) {
        if (debugMode) {
            float actualMouseX = gc.getInput().getMouseX();
            float actualMouseY = gc.getInput().getMouseY();
            float renderMouseX = gc.getInput().getMouseX() / GameCore.SCALE;
            float renderMouseY = gc.getInput().getMouseY() / GameCore.SCALE;

            map.render(0, 0, 1);
            g.setColor(Color.red);

            int textY = (int) (80 * GameCore.SCALE);

            g.setColor(Color.lightGray);
            //int textY = 10;
            int textX = 10;
            int textYIncrement = 15;
            g.drawString("DEBUG", textX, textY);
            textY += textYIncrement;
            g.drawString("FPS : " + gc.getFPS(), textX, textY);
            textY += textYIncrement;
            g.drawString("Actual Mouse Position : (" + actualMouseX + "," + actualMouseY + ")", textX, textY);
            textY += textYIncrement;
            g.drawString("Render Mouse Position : (" + renderMouseX + "," + renderMouseY + ")", textX, textY);
            textY += textYIncrement;
            g.drawString("Spellington Position : (" + spellington.getX() + "," + spellington.getY() + ")", textX, textY);
            textY += textYIncrement;
            g.drawString("Spellington Speed : (" + spellington.getSpeedVector().getX() + "," + spellington.getSpeedVector().getY() + ")", textX, textY);
            textY += textYIncrement;
            g.drawString("Current max air jumps : " + spellington.getAirJumps(), textX, textY);
            textY += textYIncrement;
            textY += textYIncrement;
            g.drawString("Current level : " + GameManager.getActiveLevel(), textX, textY);
            textY += textYIncrement;
            g.drawString("Current room : " + GameManager.getActiveMapIndex(), textX, textY);
            textY += textYIncrement;
            g.drawString("Collision :", textX, textY);
            textY += textYIncrement;

            int startingX = 10;
            int startingY = textY + 10;
            int tempSize = 25;
            g.drawRect(startingX + tempSize, startingY, tempSize, tempSize); //Top
            g.drawRect(startingX + tempSize, startingY + tempSize * 2, tempSize, tempSize); //Bottom
            g.drawRect(startingX + tempSize * 2, startingY + tempSize, tempSize, tempSize); //Right
            g.drawRect(startingX, startingY + tempSize, tempSize, tempSize); //Left
            if (spellington.getCollisionTop()) {
                g.fillRect(startingX + tempSize, startingY, tempSize, tempSize);
            }
            if (spellington.getCollisionBottom()) {
                g.fillRect(startingX + tempSize, startingY + tempSize * 2, tempSize, tempSize);
            }
            if (spellington.getCollisionRight()) {
                g.fillRect(startingX + tempSize * 2, startingY + tempSize, tempSize, tempSize);
            }
            if (spellington.getCollisionLeft()) {
                g.fillRect(startingX, startingY + tempSize, tempSize, tempSize);
            }
            g.fillOval(renderMouseX - 1, renderMouseY - 1, 3, 3);
            g.setColor(Color.cyan);
            g.drawRect(GameManager.getExitPoint().x, GameManager.getExitPoint().y, Tile.DIM_TILE.width, Tile.DIM_TILE.height);
            g.setColor(Color.red);
            g.drawRect(GameManager.getEntryPoint().x, GameManager.getEntryPoint().y, Tile.DIM_TILE.width, Tile.DIM_TILE.height);
        }

    }

    /**
     * Displays the HUD.
     *
     * @param g The Graphics component.
     * @throws SlickException A gneneral Slick Exception.
     */
    private void displayHUD(Graphics g) throws SlickException {
        if (displayHUD) {
            g.scale(1f / GameCore.SCALE, 1f / GameCore.SCALE);
            float scale = GameCore.SCALE;
            String incantationText = SpellingSystem.getIncantationText();
            float hudWidth = (float) hud.getWidth() * scale;
            float hudHeight = (float) hud.getHeight() * scale;

            this.hud.draw(0, 0, hudWidth, hudHeight);

            g.setFont(fontSpellChant);
            g.setColor(Color.white);
            g.drawString(incantationText, (GameCore.SCREEN_SIZE.width / 2f) - (fontSpellChant.getWidth(incantationText) / 2f), 20f * GameCore.SCALE);
            g.drawString("Passive", 1510 * scale, 110 * scale);
            if (SpellingSystem.getNbSpellUses() > 0) {
                g.drawString("Charges : " + SpellingSystem.getNbSpellUses(), 1380 * scale, 110 * scale);
            }
            float x = 1105;
            float y = 70f * scale;
            g.drawString(SpellingSystem.getNbPotionHeal() + "", x * scale, y);
            x += 70;
            g.drawString(SpellingSystem.getNbPotionAcid() + "", x * scale, y);
            x += 70;
            g.drawString(SpellingSystem.getNbPotionTime() + "", x * scale, y);
            x += 70;
            g.drawString(SpellingSystem.getNbPotionPast() + "", x * scale, y);
            x = 1386 * scale;
            y = 17 * scale;

            if (SpellingSystem.getActiveSpell() != null) {
                if (SpellingSystem.getActiveSpell().getAnimation() != null) {
                    Animation temp = SpellingSystem.getActiveSpell().getAnimation();
                    float ratio = (float) temp.getWidth() / (float) temp.getHeight();
                    float size = 90f * GameCore.SCALE;
                    float height = size / ratio;
                    temp.draw(x, y + (45f * scale) - (height / 2), size, height);
                }
            }
            x = 1496 * scale;
            y = 17 * scale;
            if (SpellingSystem.getPassiveSpell() != null) {
                if (SpellingSystem.getPassiveSpell().getAnimation() != null) {
                    Animation temp = SpellingSystem.getPassiveSpell().getAnimation();
                    float ratio = (float) temp.getWidth() / (float) temp.getHeight();
                    float size = 90f * GameCore.SCALE;
                    float height = size / ratio;
                    temp.draw(x, y + (45f * scale) - (height / 2), size, height);
                }
            }
            g.setColor(new Color(1, 0, 0, 0.7f));
            g.fillRect(85f * scale, 22f * scale, (float) spellington.getLifePoint() * 420f * scale / (float) spellington.getMaxLifePoint(), 29f * scale);
            g.scale(GameCore.SCALE, GameCore.SCALE);//doit être la première ligne de render
        }
    }

    /**
     * @return The ID of this BasicGameState.
     */
    @Override
    public int getID() {
        return GameCore.PLAY_STATE_ID;
    }

    public static float getSlowDownTime() {
        return slowDownTime;
    }

    public static void setSlowDownTime(float slowDownTime) {
        PlayState.slowDownTime = slowDownTime;
    }

}
