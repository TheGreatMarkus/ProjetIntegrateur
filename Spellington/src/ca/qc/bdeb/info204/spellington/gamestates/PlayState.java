package ca.qc.bdeb.info204.spellington.gamestates;

import ca.qc.bdeb.info204.spellington.GameCore;
import ca.qc.bdeb.info204.spellington.gameentities.GameAnimation;
import ca.qc.bdeb.info204.spellington.calculations.Calculations;
import ca.qc.bdeb.info204.spellington.calculations.GameManager;
import ca.qc.bdeb.info204.spellington.calculations.SpellingSystem;
import ca.qc.bdeb.info204.spellington.calculations.Vector2D;
import ca.qc.bdeb.info204.spellington.gameentities.LivingEntity;
import ca.qc.bdeb.info204.spellington.gameentities.Projectile;
import ca.qc.bdeb.info204.spellington.gameentities.Spellington;
import ca.qc.bdeb.info204.spellington.gameentities.Tile;
import ca.qc.bdeb.info204.spellington.gameentities.enemies.Enemy;
import ca.qc.bdeb.info204.spellington.spell.BurstSpell;
import ca.qc.bdeb.info204.spellington.spell.ExplosionSpell;
import ca.qc.bdeb.info204.spellington.spell.ProjectileSpell;
import ca.qc.bdeb.info204.spellington.spell.Spell;
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

    //debug variable
    public static boolean debugMode = false;
    private static boolean displayHUD = true;

    //Variables and constants related to the rendering of the HUD
    private Image statsBarHUD, inputTextHUD, passiveSpellHUD, activeSpellHUD, redPotionHUD, greenPotionHUD, bluePotionHUD, icePotionHUD;

    /**
     * Initialises the BasicGameState
     *
     * @param gc the GameContainer
     * @param game the StateBasedGame
     * @throws SlickException General Slick exception
     */
    @Override
    public void init(GameContainer gc, StateBasedGame game) throws SlickException {
        spellington = new Spellington(0, 0, LivingEntity.MouvementState.JUMP_R);
        fontSpellChant = new UnicodeFont(GameCore.getFontViking(Font.BOLD, 25 * GameCore.SCALE));
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

    /**
     * Prepares the level by changing the map and the position of spellington.
     *
     * @param currentMap The map to be played.
     * @param spellingtonX The X position where Spellington should appear
     * @param spellingtonY The Y position where spellington sould appear.
     * @throws SlickException A general Slick Exception.
     */
    public void prepareLevel(TiledMap currentMap, int spellingtonX, int spellingtonY) throws SlickException {
        spellington.setMouvementState(LivingEntity.MouvementState.STANDING_R);
        spellington.setX(spellingtonX);
        spellington.setY(spellingtonY);
        spellington.setMouvementState(LivingEntity.MouvementState.STANDING_R);
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

        drawAimingHelp(g, gc.getInput(), SpellingSystem.getActiveSpell(), spellington);

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
        deltaFloat *= 1;
        if (deltaFloat > 40) {
            deltaFloat = 40;
        }
        //System.out.println("time passed = " + deltaFloat);

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
        spellington.update(gc.getInput(), deltaFloat);
        Calculations.checkMapCollision(GameManager.getMapInformation(), spellington);

        SpellingSystem.update(gc.getInput(), spellington, activeProjectiles, activeAnimations, GameManager.getActiveEnemies());

        //Update of projectiles
        ArrayList<Projectile> projectilesToBeRemoved = new ArrayList<>();
        for (int i = 0; i < activeProjectiles.size(); i++) {
            activeProjectiles.get(i).update((float) deltaFloat);
            if (Calculations.checkProjectileCollision(activeProjectiles.get(i), GameManager.getMapInformation(), GameManager.getActiveEnemies(), spellington) != -1) {
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
            }
        }
        GameManager.getActiveEnemies().removeAll(enemiesToBeRemoved);

        GameManager.checkEndOfLevel(spellington);
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

    /**
     * Displays the HUD.
     *
     * @param g The Graphics component.
     * @throws SlickException A gneneral Slick Exception.
     */
    private void displayHUD(Graphics g) throws SlickException {
        if (displayHUD) {
            g.scale(1f / GameCore.SCALE, 1f / GameCore.SCALE);
            float statsBarOffSetX = 75 * GameCore.SCALE; //common X position of the stats bars
            float xGap = 5 * GameCore.SCALE;
            float barsY = 5 * GameCore.SCALE; //Universal Y position for most HUD components
            float healthBarY = (10f + barsY) * GameCore.SCALE; //Y position of the health bar
            float xpBarY = (54f + barsY) * GameCore.SCALE; //Y position of the xp bar
            float statBarWidth = 381 * GameCore.SCALE;
            float statBarHeight = 27 * GameCore.SCALE;
            float alpha = 0.5f; //50% color transparency
            final Color healthColor = new Color(1, 0, 0, alpha), xpColor = new Color(0, 0, 1, alpha), textColor = new Color(1, 1, 1, alpha);
            String incantationText = SpellingSystem.getIncantationText();

            float statsBarWidth = (float) statsBarHUD.getWidth() * GameCore.SCALE;
            float statsBarHeight = (float) statsBarHUD.getHeight() * GameCore.SCALE;
            float inputTextWidth = (float) inputTextHUD.getWidth() * GameCore.SCALE;
            float inputTextHeight = (float) inputTextHUD.getHeight() * GameCore.SCALE;
            float spellWidth = (float) activeSpellHUD.getWidth() * GameCore.SCALE;
            float spellHeight = (float) activeSpellHUD.getHeight() * GameCore.SCALE;
            float potionWidth = (float) redPotionHUD.getWidth() * GameCore.SCALE;
            float potionHeight = (float) redPotionHUD.getHeight() * GameCore.SCALE;

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
            g.drawString(incantationText, (GameCore.SCREEN_SIZE.width / 2) - (fontSpellChant.getWidth(incantationText) / 2), barsY + 8f * GameCore.SCALE);
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
            g.scale(GameCore.SCALE, GameCore.SCALE);//doit être la première ligne de render
        }
    }

    /**
     * Draws the aiming assitance if the current active spell is offensive.
     *
     * @param g The Graphics component.
     * @param input The input class where input to the program is handled.
     * @param activeSpell The currently active spellé
     * @param spellington The playable protagonist.
     */
    private void drawAimingHelp(Graphics g, Input input, Spell activeSpell, Spellington spellington) {
        float spellingtonX = spellington.getCenterX();
        float spellingtonY = spellington.getCenterY();
        float mouseX = input.getMouseX() / GameCore.SCALE;
        float mouseY = input.getMouseY() / GameCore.SCALE;
        float projectionPrecision = 15;
        if (activeSpell instanceof BurstSpell) {
            g.setColor(new Color(255, 255, 255));
            g.drawLine(spellingtonX, spellingtonY, mouseX, mouseY);
            Projectile temp1 = ((BurstSpell) activeSpell).createSpellProjectile(spellington, input);
            Projectile temp2 = ((BurstSpell) activeSpell).createSpellProjectile(spellington, input);
            Projectile temp3 = ((BurstSpell) activeSpell).createSpellProjectile(spellington, input);
            temp1.setDamage(0);
            temp2.setDamage(0);
            temp3.setDamage(0);
            float tempAngle1 = Calculations.detAngle(mouseX - spellingtonX, mouseY - spellingtonY) + ((BurstSpell) activeSpell).getAngleDeviation();
            float tempAngle2 = Calculations.detAngle(mouseX - spellingtonX, mouseY - spellingtonY) - ((BurstSpell) activeSpell).getAngleDeviation();
            float tempAngle3 = Calculations.detAngle(mouseX - spellingtonX, mouseY - spellingtonY);
            temp1.setSpeedVector(new Vector2D(temp1.getSpeedVector().vectorLength(), tempAngle1, true));
            temp2.setSpeedVector(new Vector2D(temp2.getSpeedVector().vectorLength(), tempAngle2, true));
            temp3.setSpeedVector(new Vector2D(temp3.getSpeedVector().vectorLength(), tempAngle3, true));

            boolean endLoop = false;
            g.setColor(new Color(255, 255, 255, 70));
            while (!endLoop) {
                float tempX = temp1.getCenterX();
                float tempY = temp1.getCenterY();
                temp1.update(projectionPrecision);
                g.drawLine(temp1.getCenterX(), temp1.getCenterY(), tempX, tempY);
                if (Calculations.checkProjectileCollision(temp1, GameManager.getMapInformation(), GameManager.getActiveEnemies(), spellington) == 0) {
                    endLoop = true;
                    g.setColor(Color.cyan);
                    g.drawOval(temp1.getX(), temp1.getY(), temp1.getWidth(), temp1.getHeight());
                }
                if (Calculations.checkProjectileCollision(temp1, GameManager.getMapInformation(), GameManager.getActiveEnemies(), spellington) == 2) {
                    endLoop = true;
                    g.setColor(Color.red);
                    g.drawOval(temp1.getX(), temp1.getY(), temp1.getWidth(), temp1.getHeight());
                }

                if (temp1.getX() < 0 || temp1.getX() > 1600 || temp1.getY() < 0 || temp1.getY() > 900) {
                    endLoop = true;
                }
            }
            endLoop = false;
            g.setColor(new Color(255, 255, 255, 70));
            while (!endLoop) {
                float tempX = temp2.getCenterX();
                float tempY = temp2.getCenterY();
                temp2.update(projectionPrecision);
                g.drawLine(temp2.getCenterX(), temp2.getCenterY(), tempX, tempY);
                if (Calculations.checkProjectileCollision(temp2, GameManager.getMapInformation(), GameManager.getActiveEnemies(), spellington) == 0) {
                    endLoop = true;
                    g.setColor(Color.cyan);
                    g.drawOval(temp2.getX(), temp2.getY(), temp2.getWidth(), temp2.getHeight());
                }
                if (Calculations.checkProjectileCollision(temp2, GameManager.getMapInformation(), GameManager.getActiveEnemies(), spellington) == 2) {
                    endLoop = true;
                    g.setColor(Color.red);
                    g.drawOval(temp2.getX(), temp2.getY(), temp2.getWidth(), temp2.getHeight());
                }

                if (temp2.getX() < 0 || temp2.getX() > 1600 || temp2.getY() < 0 || temp2.getY() > 900) {
                    endLoop = true;
                }
            }
            endLoop = false;
            g.setColor(new Color(255, 255, 255, 70));
            while (!endLoop) {
                float tempX = temp3.getCenterX();
                float tempY = temp3.getCenterY();
                temp3.update(projectionPrecision);
                g.drawLine(temp3.getCenterX(), temp3.getCenterY(), tempX, tempY);
                if (Calculations.checkProjectileCollision(temp3, GameManager.getMapInformation(), GameManager.getActiveEnemies(), spellington) == 0) {
                    endLoop = true;
                    g.setColor(Color.cyan);
                    g.drawOval(temp3.getX(), temp3.getY(), temp3.getWidth(), temp3.getHeight());
                }
                if (Calculations.checkProjectileCollision(temp3, GameManager.getMapInformation(), GameManager.getActiveEnemies(), spellington) == 2) {
                    endLoop = true;
                    g.setColor(Color.red);
                    g.drawOval(temp3.getX(), temp3.getY(), temp3.getWidth(), temp3.getHeight());
                }

                if (temp3.getX() < 0 || temp3.getX() > 1600 || temp3.getY() < 0 || temp3.getY() > 900) {
                    endLoop = true;
                }
            }

        } else if (activeSpell instanceof ProjectileSpell) {
            g.setColor(new Color(255, 255, 255));
            g.drawLine(spellingtonX, spellingtonY, mouseX, mouseY);
            Projectile temp = ((ProjectileSpell) activeSpell).createSpellProjectile(spellington, input);
            temp.setDamage(0);

            boolean endLoop = false;
            g.setColor(new Color(255, 255, 255, 70));
            while (!endLoop) {
                float tempX = temp.getCenterX();
                float tempY = temp.getCenterY();
                temp.update(projectionPrecision);
                g.drawLine(temp.getCenterX(), temp.getCenterY(), tempX, tempY);
                if (Calculations.checkProjectileCollision(temp, GameManager.getMapInformation(), GameManager.getActiveEnemies(), spellington) == 0) {
                    endLoop = true;
                    g.setColor(Color.cyan);
                    g.drawOval(temp.getX(), temp.getY(), temp.getWidth(), temp.getHeight());
                }
                if (Calculations.checkProjectileCollision(temp, GameManager.getMapInformation(), GameManager.getActiveEnemies(), spellington) == 2) {
                    endLoop = true;
                    g.setColor(Color.red);
                    g.drawOval(temp.getX(), temp.getY(), temp.getWidth(), temp.getHeight());
                }

                if (temp.getX() < 0 || temp.getX() > 1600 || temp.getY() < 0 || temp.getY() > 900) {
                    endLoop = true;
                }
            }

        } else if (activeSpell instanceof ExplosionSpell) {
            float ray = ((ExplosionSpell) activeSpell).getRay();
            float spellX = mouseX - ray;
            float spellY = mouseY - ray;
            g.setColor(new Color(255, 255, 255, 90));
            g.drawOval(spellX, spellY, ray * 2, ray * 2);
        }
    }

    /**
     * @return The ID of this BasicGameState.
     */
    @Override
    public int getID() {
        return GameCore.PLAY_STATE_ID;
    }

}
