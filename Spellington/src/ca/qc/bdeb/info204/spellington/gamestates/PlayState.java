package ca.qc.bdeb.info204.spellington.gamestates;

import ca.qc.bdeb.info204.spellington.GameCore;
import ca.qc.bdeb.info204.spellington.calculations.GameAnimation;
import ca.qc.bdeb.info204.spellington.calculations.Calculations;
import ca.qc.bdeb.info204.spellington.calculations.GameManager;
import ca.qc.bdeb.info204.spellington.calculations.SpellingSystem;
import ca.qc.bdeb.info204.spellington.calculations.Vector2D;
import ca.qc.bdeb.info204.spellington.gameentities.LivingEntity;
import ca.qc.bdeb.info204.spellington.gameentities.Projectile;
import ca.qc.bdeb.info204.spellington.gameentities.Spellington;
import ca.qc.bdeb.info204.spellington.gameentities.Tile;
import ca.qc.bdeb.info204.spellington.gameentities.enemies.Enemy;
import ca.qc.bdeb.info204.spellington.spell.BreathSpell;
import ca.qc.bdeb.info204.spellington.spell.ExplosionSpell;
import ca.qc.bdeb.info204.spellington.spell.ProjectileSpell;
import ca.qc.bdeb.info204.spellington.spell.Spell;
import java.awt.Dimension;
import java.awt.Font;
import java.util.ArrayList;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.UnicodeFont;
import org.newdawn.slick.font.effects.ColorEffect;
import org.newdawn.slick.geom.Rectangle;
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

    public static final Vector2D GRAV_ACC = new Vector2D(0, 0.001f);
    public static final Dimension DIM_MAP = new Dimension(32, 18);

    //debug variable
    public static boolean debugMode = false;
    private static boolean displayHUD = true;

    //Variables and constants related to the rendering of the HUD
    private Image statsBarHUD, inputTextHUD, passiveSpellHUD, activeSpellHUD, redPotionHUD, greenPotionHUD, bluePotionHUD, icePotionHUD;
    private boolean drawAimingHelp;

    @Override
    public void init(GameContainer gc, StateBasedGame game) throws SlickException {
        spellington = new Spellington(0, 0, LivingEntity.MouvementState.JUMP_R);
        fontSpellChant = new UnicodeFont(GameCore.getFontViking(Font.BOLD, 25 * GameCore.scale));
        fontSpellChant.addAsciiGlyphs();
        fontSpellChant.getEffects().add(new ColorEffect(java.awt.Color.white));
        fontSpellChant.loadGlyphs();

        //Loading crosshair image.
        IMG_GAME_CROSSHAIR = new Image("res/image/cursor/small_crosshair.png");
        //Loading test map information.

        //Loading HUD image components
        this.statsBarHUD = new Image("src/res/image/HUD/statsBar.png");
        this.inputTextHUD = new Image("src/res/image/HUD/textRectangle.png");
        this.passiveSpellHUD = new Image("src/res/image/HUD/utilitySquare.png");
        this.activeSpellHUD = new Image("src/res/image/HUD/utilitySquare.png");
        this.redPotionHUD = new Image("src/res/image/HUD/redPotion.png");
        this.greenPotionHUD = new Image("src/res/image/HUD/greenPotion.png");
        this.bluePotionHUD = new Image("src/res/image/HUD/bluePotion.png");
        this.icePotionHUD = new Image("src/res/image/HUD/icePotion.png");
    }

    public void prepareLevel(TiledMap currentMap, int spellingtonX, int spellingtonY) throws SlickException {
        spellington.setMouvementState(LivingEntity.MouvementState.STANDING_R);
        spellington.setX(spellingtonX);
        spellington.setY(spellingtonY);
        map = currentMap;
    }

    @Override
    public void render(GameContainer gc, StateBasedGame game, Graphics g) throws SlickException {
        //Must be the first in the PlayState renger method.
        g.scale(GameCore.scale, GameCore.scale);
        //Currently broken, gonna look for a solution later.
        //g.translate(GameCore.screenTranslateX, GameCore.screenTranslateY);

        g.setColor(Color.white);
        map.render(0, 0, 0);

        g.setColor(Color.blue);
        spellington.render(g);

        g.setColor(Color.white);
        g.drawString("ESC : Menu / F3 : TOGGLE DEBUG / F4 : TOGGLE HUD", 10, GameCore.RENDER_SIZE.height - 40);

        //Render mouse cursor during gameplay.
        float renderMouseX = gc.getInput().getMouseX() / GameCore.scale;
        float renderMouseY = gc.getInput().getMouseY() / GameCore.scale;
        IMG_GAME_CROSSHAIR.draw(renderMouseX - 12, renderMouseY - 12, 25, 25);

        for (int i = 0; i < activeProjectiles.size(); i++) {
            activeProjectiles.get(i).render(g);
        }

        for (int i = 0; i < activeAnimations.size(); i++) {
            activeAnimations.get(i).render(g, spellington);
        }
        for (int i = 0; i < GameManager.getActiveEnemies().size(); i++) {

            GameManager.getActiveEnemies().get(i).render(g);
        }

        debugInfo(g, gc);

        displayHUD(g);

        drawAimingHelp(g, gc.getInput(), SpellingSystem.getActiveSpell(), spellington);

    }

    @Override
    public void update(GameContainer gc, StateBasedGame game, int delta) throws SlickException {
        if (delta > 40) {
            delta = 40;
        }
        if (gc.getInput().isKeyPressed(Input.KEY_ESCAPE)) {
            game.enterState(GameCore.PAUSE_MENU_STATE_ID);
        }
        if (gc.getInput().isKeyPressed(Input.KEY_F3)) {
            debugMode = !debugMode;
        }
        if (gc.getInput().isKeyPressed(Input.KEY_F4)) {
            displayHUD = !displayHUD;
        }
        //Update of Spellington
        spellington.update(gc.getInput(), delta);
        Calculations.checkMapCollision(GameManager.getMapInformation(), spellington);

        SpellingSystem.update(gc.getInput(), spellington, activeProjectiles, activeAnimations, GameManager.getActiveEnemies());

        //Update of projectiles
        ArrayList<Projectile> projectilesToBeRemoved = new ArrayList<>();
        for (int i = 0; i < activeProjectiles.size(); i++) {
            activeProjectiles.get(i).update((float) delta);
            if (Calculations.checkProjectileCollision(GameManager.getMapInformation(), GameManager.getActiveEnemies(), spellington, activeProjectiles.get(i))) {
                projectilesToBeRemoved.add(activeProjectiles.get(i));
            }
        }
        activeProjectiles.removeAll(projectilesToBeRemoved);

        //Update of enemies
        ArrayList<Enemy> enemiesToBeRemoved = new ArrayList<>();
        for (Enemy enemy : GameManager.getActiveEnemies()) {
            enemy.update(delta);
            Calculations.checkMapCollision(GameManager.getMapInformation(), enemy);
            if (enemy.getLifePoint() <= 0) {
                enemiesToBeRemoved.add(enemy);
            }
        }
        GameManager.getActiveEnemies().removeAll(enemiesToBeRemoved);

        //Update of animations
        ArrayList<GameAnimation> animationsToBeRemoved = new ArrayList<>();
        for (int j = 0; j < activeAnimations.size(); j++) {
            activeAnimations.get(j).update();
            if (activeAnimations.get(j).getVie() == 0) {
                animationsToBeRemoved.add(activeAnimations.get(j));
            }
        }

        activeAnimations.removeAll(animationsToBeRemoved);

        GameManager.checkEndOfLevel(spellington);
        GameCore.clearInputRecord(gc);
    }

    /**
     * Displays information about spellington for debug purposes
     *
     * @param g
     */
    private void debugInfo(Graphics g, GameContainer gc) {
        if (debugMode) {
            float actualMouseX = gc.getInput().getMouseX();
            float actualMouseY = gc.getInput().getMouseY();
            float renderMouseX = gc.getInput().getMouseX() / GameCore.scale;
            float renderMouseY = gc.getInput().getMouseY() / GameCore.scale;

            map.render(0, 0, 1);
            g.setColor(Color.red);

            int textY = (int) (80 * GameCore.scale);

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
            g.drawString("Current map : " + GameManager.getActiveMapIndex(), textX, textY);
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

    private void displayHUD(Graphics g) throws SlickException {
        if (displayHUD) {
            g.scale(1f / GameCore.scale, 1f / GameCore.scale);
            float statsBarOffSetX = 75 * GameCore.scale; //common X position of the stats bars
            float xGap = 5 * GameCore.scale;
            float barsY = 5 * GameCore.scale; //Universal Y position for most HUD components
            float healthBarY = (10f + barsY) * GameCore.scale; //Y position of the health bar
            float xpBarY = (54f + barsY) * GameCore.scale; //Y position of the xp bar
            float statBarWidth = 381 * GameCore.scale;
            float statBarHeight = 27 * GameCore.scale;
            float alpha = 0.5f; //50% color transparency
            final Color healthColor = new Color(1, 0, 0, alpha), xpColor = new Color(0, 0, 1, alpha), textColor = new Color(1, 1, 1, alpha);
            String incantationText = SpellingSystem.getIncantationText();

            float statsBarWidth = (float) statsBarHUD.getWidth() * GameCore.scale;
            float statsBarHeight = (float) statsBarHUD.getHeight() * GameCore.scale;
            float inputTextWidth = (float) inputTextHUD.getWidth() * GameCore.scale;
            float inputTextHeight = (float) inputTextHUD.getHeight() * GameCore.scale;
            float spellWidth = (float) activeSpellHUD.getWidth() * GameCore.scale;
            float spellHeight = (float) activeSpellHUD.getHeight() * GameCore.scale;
            float potionWidth = (float) redPotionHUD.getWidth() * GameCore.scale;
            float potionHeight = (float) redPotionHUD.getHeight() * GameCore.scale;

            float passiveX = GameCore.SCREEN_SIZE.width - xGap - spellWidth;
            float activeX = passiveX - xGap - spellWidth;
            float icePotionX = activeX - xGap - potionWidth;
            float bluePotionX = icePotionX - xGap - potionWidth;
            float greenPotionX = bluePotionX - xGap - potionWidth;
            float redPotionX = greenPotionX - xGap - potionWidth;

            this.statsBarHUD.draw(xGap, barsY, statsBarWidth, statsBarHeight);
            this.inputTextHUD.draw(((float) GameCore.SCREEN_SIZE.width / 2 - inputTextWidth / 2), barsY, inputTextWidth, inputTextHeight);
            this.passiveSpellHUD.draw(passiveX, barsY, spellWidth, spellHeight);
            this.activeSpellHUD.draw(activeX, barsY, spellWidth, spellHeight);
            this.redPotionHUD.draw(redPotionX, barsY, potionWidth, potionHeight);
            this.greenPotionHUD.draw(greenPotionX, barsY, potionWidth, potionHeight);
            this.bluePotionHUD.draw(bluePotionX, barsY, potionWidth, potionHeight);
            this.icePotionHUD.draw(icePotionX, barsY, potionWidth, potionHeight);

            g.setFont(fontSpellChant);
            g.setColor(textColor);
            g.drawString(incantationText, (GameCore.SCREEN_SIZE.width / 2) - (fontSpellChant.getWidth(incantationText) / 2), barsY + 8f * GameCore.scale);
            g.drawString("Passive", activeX, barsY + spellHeight);
            g.drawString("Active", passiveX, barsY + spellHeight);
            g.drawString("1", redPotionX + 3, barsY + potionHeight);
            g.drawString("2", greenPotionX + 3, barsY + potionHeight);
            g.drawString("3", bluePotionX + 3, barsY + potionHeight);
            g.drawString("4", icePotionX + 3, barsY + potionHeight);
            if (SpellingSystem.getActiveSpell() != null) {
                if (SpellingSystem.getActiveSpell().getAnimation() != null) {
                    SpellingSystem.getActiveSpell().getAnimation().draw(GameCore.SCREEN_SIZE.width - 100, 15, 80, 80);
                }
            }
            if (SpellingSystem.getPassiveSpell() != null) {
                if (SpellingSystem.getPassiveSpell().getAnimation() != null) {
                    SpellingSystem.getPassiveSpell().getAnimation().draw(GameCore.SCREEN_SIZE.width - 200, 15, 80, 80);
                }
            }
            g.setColor(healthColor);
            g.fillRect(statsBarOffSetX, healthBarY, ((float) spellington.getLifePoint() / (float) Spellington.INIT_MAX_LIFE) * (float) statBarWidth, statBarHeight);
            g.setColor(xpColor);
            g.fillRect(statsBarOffSetX, xpBarY, .5f * statBarWidth, statBarHeight);
            g.scale(GameCore.scale, GameCore.scale);//doit être la première ligne de render
        }
    }

    private void drawAimingHelp(Graphics g, Input input, Spell activeSpell, Spellington spellington) {
        float spellingtonX = spellington.getCenterX();
        float spellingtonY = spellington.getCenterY();
        float mouseX = input.getMouseX() / GameCore.scale;
        float mouseY = input.getMouseY() / GameCore.scale;

        if (activeSpell instanceof ProjectileSpell) {
            g.setColor(new Color(255, 255, 255));
            g.drawLine(spellingtonX, spellingtonY, mouseX, mouseY);
            float spellX = spellingtonX - activeSpell.getWidth() / 2;
            float spellY = spellingtonY - activeSpell.getHeight() / 2;
            float angle = Calculations.detAngle(mouseX - spellingtonX, mouseY - spellingtonY);
            float gravModifier = ((ProjectileSpell) activeSpell).getGravModifier();
            Vector2D tempSpeedVector = new Vector2D(((ProjectileSpell) activeSpell).getInitSpeed(), angle, true);
            float time = 16;
            boolean endLoop = false;
            boolean oob = false;
            g.setColor(new Color(255, 255, 255, 50));
            while (!endLoop && !oob) {
                g.drawOval(spellX, spellY, activeSpell.getWidth(), activeSpell.getHeight());
                tempSpeedVector.add(Vector2D.multVectorScalar(PlayState.GRAV_ACC, time * gravModifier));
                spellX += tempSpeedVector.getX() * time;
                spellY += tempSpeedVector.getY() * time;
                Rectangle tempRect = new Rectangle(spellX, spellY, activeSpell.getWidth(), activeSpell.getHeight());
                for (int i = 0; i < GameManager.getMapInformation().length; i++) {
                    for (int j = 0; j < GameManager.getMapInformation()[i].length; j++) {
                        if (tempRect.intersects(GameManager.getMapInformation()[i][j]) && GameManager.getMapInformation()[i][j].getTileState() == Tile.TileState.IMPASSABLE) {
                            endLoop = true;
                        }
                    }
                }
                if (spellX < 0 || spellX > 1600 || spellY < 0 || spellY > 900) {
                    oob = true;
                }
            }

        } else if (activeSpell instanceof BreathSpell) {

        } else if (activeSpell instanceof ExplosionSpell) {
            float ray = ((ExplosionSpell) activeSpell).getRay();
            float spellX = mouseX - ray;
            float spellY = mouseY - ray;
            g.setColor(new Color(255, 255, 255, 90));
            g.drawOval(spellX, spellY, ray * 2, ray * 2);
        }
    }

    @Override
    public int getID() {
        return GameCore.PLAY_STATE_ID;
    }

}
