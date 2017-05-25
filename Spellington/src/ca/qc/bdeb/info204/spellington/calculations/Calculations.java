package ca.qc.bdeb.info204.spellington.calculations;

import ca.qc.bdeb.info204.spellington.GameCore;
import static ca.qc.bdeb.info204.spellington.GameCore.DIM_MAP;
import ca.qc.bdeb.info204.spellington.gameentities.DynamicEntity;
import ca.qc.bdeb.info204.spellington.gameentities.GameAnimation;
import ca.qc.bdeb.info204.spellington.gameentities.GameEntity;
import ca.qc.bdeb.info204.spellington.gameentities.GameEntity.ElementalType;
import ca.qc.bdeb.info204.spellington.gameentities.LivingEntity;
import ca.qc.bdeb.info204.spellington.gameentities.Projectile;
import ca.qc.bdeb.info204.spellington.gameentities.Spellington;
import ca.qc.bdeb.info204.spellington.gameentities.Tile;
import ca.qc.bdeb.info204.spellington.gameentities.enemies.Enemy;
import ca.qc.bdeb.info204.spellington.gameentities.enemies.RangedEnemy;
import ca.qc.bdeb.info204.spellington.gamestates.PlayState;
import ca.qc.bdeb.info204.spellington.spell.BurstSpell;
import ca.qc.bdeb.info204.spellington.spell.ExplosionSpell;
import ca.qc.bdeb.info204.spellington.spell.ProjectileSpell;
import ca.qc.bdeb.info204.spellington.spell.Spell;
import java.util.ArrayList;
import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.geom.Line;

/**
 * Class dedicated to performing long or complex calculations for the game.
 *
 * @author Cristian Aldea.
 */
public class Calculations {

    public static int TargetI = 0;
    public static int TargetJ = 0;

    /**
     * Checks collision between the map and a LivingEntity.
     *
     * @param map The collision and event information for the current map.
     * @param entity The LivingEntity that will be checked for collision.
     */
    public static void checkMapCollision(Tile[][] map, DynamicEntity entity) {
        TargetI = (int) (entity.getCenterY() / (float) Tile.DIM_TILE.width);
        TargetJ = (int) (entity.getCenterX() / (float) Tile.DIM_TILE.height);
        if (TargetI < 0 || TargetI >= DIM_MAP.height
                || TargetJ < 0 || TargetJ >= DIM_MAP.width) {
            TargetI = 0;
            TargetJ = 0;
        }

        for (int i = 0; i < map.length; i++) {
            Tile tempTile = map[i][TargetJ];
            checkTileAndEntityCollision(tempTile, entity);
        }
        for (int j = 0; j < map[0].length; j++) {
            Tile tempTile = map[TargetI][j];
            checkTileAndEntityCollision(tempTile, entity);
        }
        for (int i = 0; i < map.length; i++) {
            for (int j = 0; j < map[i].length; j++) {
                Tile tempTile = map[i][j];
                checkTileAndEntityCollision(tempTile, entity);

            }
        }
    }

    /**
     * Checks collision between a tile and a LivingEntity. Also does the
     * collision resolution after that collision is detected.
     *
     * @param tile The tile that will be checked for collision.
     * @param entity The LivingEntity that will be checked for collision.
     */
    public static void checkTileAndEntityCollision(Tile tile, DynamicEntity entity) {
        //If a collision is found and the tile is impassable
        if (entity.getBounds().intersects(tile.getBounds()) && tile.getTileState() != Tile.TileState.PASSABLE) {
            if (tile.getTileState() == Tile.TileState.DAMAGE && entity instanceof Spellington) {
                switch (GameManager.getActiveLevel()) {
                    case 1:
                    case 2:
                        ((LivingEntity) entity).subLifePoint(8, ElementalType.FIRE);
                        break;
                    case 3:
                        ((LivingEntity) entity).subLifePoint(8, ElementalType.ICE);
                        break;
                    case 4:
                        ((LivingEntity) entity).subLifePoint(8, ElementalType.LIGHTNING);
                        break;
                }
            }
            //To get the width and height of the intersaction
            float left = Float.max(tile.getX(), entity.getX());
            float right = Float.min(tile.getMaxX(), entity.getMaxX());
            float bottom = Float.min(tile.getMaxY(), entity.getMaxY());
            float top = Float.max(tile.getY(), entity.getY());

            float widthIntersection = Math.abs(right - left);
            float heightIntersection = Math.abs(bottom - top);

            /*The side of the correction is determined by calculating the 
             shallowest side of the intersection and the relative x and y positions
             of the entity to be moved*/
            if (heightIntersection < widthIntersection) {//collision with a wide object
                if (tile.getCenterY() < entity.getCenterY()) {//object over the creature
                    entity.setY(entity.getY() + heightIntersection);
                    entity.setCollisionTop(true);
                } else if (tile.getCenterY() > entity.getCenterY()) {//object under the creature
                    entity.setY(entity.getY() - (heightIntersection));
                    entity.setCollisionBottom(true);
                }
            } else if (widthIntersection < heightIntersection) { //collision with a tall object
                if (tile.getCenterX() < entity.getCenterX()) {// collision to the left 
                    entity.setX(entity.getX() + widthIntersection);
                    entity.setCollisionLeft(true);
                } else if (tile.getCenterX() > entity.getCenterX()) {//collision to the right
                    entity.setX(entity.getX() - widthIntersection);
                    entity.setCollisionRight(true);
                }
            }
        }
    }

    /**
     * Checks the collisions for projectiles.
     *
     * @param map The collision and event information for the current map.
     * @param activeEnemies The enemies that will be checked for collision.
     * @param spellington Spellington that will be checked for collision.
     * @param activeAnimations
     * @param projectile The projectile that will be checked for collision.
     * @return If the projectile should be removed from the activeProjectile
     * list.
     */
    public static int checkProjectileCollision(Projectile projectile, Tile[][] map, ArrayList<Enemy> activeEnemies, ArrayList<GameAnimation> activeAnimations, Spellington spellington) {
        for (int i = 0; i < map.length; i++) {
            for (int j = 0; j < map[i].length; j++) {
                if (projectile.getBounds().intersects(map[i][j].getBounds()) && map[i][j].getTileState() != Tile.TileState.PASSABLE) {
                    if (projectile.getBounces() == 0) {
                        projectile.projectileEffet(spellington, activeEnemies, activeAnimations, map);
                        return 0;//Collision with a tile.
                    } else {
                        checkMapCollision(map, projectile);
                        projectile.bounce();
                        return -1;
                    }
                }
            }
        }

        if (projectile.getBounds().intersects(spellington.getBounds())) {
            if (projectile.getSourceID() < 0) {
                if (projectile.getSourceID() == -1) {
                    spellington.subLifePoint(projectile.getDamage(), projectile.getDamageType());
                }
                projectile.projectileEffet(spellington, activeEnemies, activeAnimations, map);
                return 1;//Collision with Spellington.
            }
        }

        for (Enemy activeEnemy : activeEnemies) {
            if (projectile.getBounds().intersects(activeEnemy.getBounds())) {
                if (projectile.getSourceID() != -1) {
                    if (projectile.getSourceID() > 0) {
                        activeEnemy.subLifePoint(projectile.getDamage(), projectile.getDamageType());
                    }
                    projectile.projectileEffet(spellington, activeEnemies, activeAnimations, map);
                    return 2;//Collision with an enemy.
                }
            }
        }
        return -1;
    }

    /**
     * Calculates angle (in radians) from a delta x and a delta y.
     *
     * @param x The delta x.
     * @param y The delta y.
     * @return The calculated angle.
     */
    public static float detAngle(float x, float y) {
        //Calculate the base angle (0 < theta < PI/2); 
        float tempAngle = (float) Math.atan(Math.abs(y) / Math.abs(x));
        if (x > 0) {
            if (y < 0) {
                tempAngle = -tempAngle;
            } else if (y == 0) {
                tempAngle = 0;
            }
        } else if (x < 0) {
            if (y > 0) {
                tempAngle = (float) Math.PI - tempAngle;
            } else if (y < 0) {
                tempAngle = (float) Math.PI + tempAngle;
            } else if (y == 0) {
                tempAngle = (float) Math.PI;
            }
        } else if (x == 0) {
            if (y > 0) {
                tempAngle = (float) Math.PI / 2f;
            } else if (y < 0) {
                tempAngle = (float) -Math.PI / 2f;
            } else if (y == 0) {
                tempAngle = 0;
            }
        }
        return tempAngle;
    }

    /**
     * Determines if an enemy has a line of sight to Spellington.
     *
     * @param enemy The enemy.
     * @param spellington The protagonist.
     * @param map The collision and event information for the current map.
     * @return If the enemy can see Spellington.
     */
    public static boolean detEnemyCanSeeSpellington(Enemy enemy, Spellington spellington, Tile[][] map) {
        Line line1 = new Line(spellington.getCenterX(), spellington.getY() + 10, enemy.getCenterX(), enemy.getY() + 10);
        Line line2 = new Line(spellington.getCenterX(), spellington.getMaxY() - 10, enemy.getCenterX(), enemy.getY() + 10);
        boolean test1 = true, test2 = true;
        for (int i = 0; i < map.length; i++) {
            for (int j = 0; j < map[i].length; j++) {
                if (line1.intersects(map[i][j].getBounds()) && map[i][j].getTileState() != Tile.TileState.PASSABLE) {
                    test1 = false;
                }
            }
        }
        for (int i = 0; i < map.length; i++) {
            for (int j = 0; j < map[i].length; j++) {
                if (line2.intersects(map[i][j].getBounds()) && map[i][j].getTileState() != Tile.TileState.PASSABLE) {
                    test2 = false;
                }
            }
        }
        return (test1 || test2);
    }

    /**
     * Tries to shoot a projetile to hit Spellington.
     *
     * @param enemy The enemy.
     * @param spellington The protagonist.
     * @param activeProjectiles The list of active projectiles in the game.
     * @param map The collision and event information for the current map.
     */
    public static void enemyTryToShootCurvedProjectile(RangedEnemy enemy, Spellington spellington, ArrayList<Projectile> activeProjectiles, Tile[][] map) {
        Float angle1;
        Float angle2;
        float deltaX = -enemy.getDeltaXPlayer();
        float deltaY = -enemy.getDeltaYPlayer();
        float v = 0.002f * enemy.getPlayerDistance();
        if (v < 0.5f) {
            v = 0.5f;
        }
        if (v > 1.2f) {
            v = 1.2f;
        }
        float g = PlayState.GRAV_ACC.getY();
        //Formule from https://en.wikipedia.org/wiki/Trajectory_of_a_projectile
        Float sqrt = (float) Math.sqrt((v * v * v * v) - (g * (g * deltaX * deltaX + (2 * deltaY * v * v))));
        if (!sqrt.isNaN()) {
            angle1 = (float) Math.atan((v * v + sqrt) / (g * deltaX));
            angle2 = (float) Math.atan((v * v - sqrt) / (g * deltaX));
            if (deltaX > 0) {
                angle1 = angle1 + (float) Math.PI;
                angle2 = angle2 + (float) Math.PI;
            }
            Projectile test1 = new Projectile(-2, enemy.getCenterX() - enemy.getProjectileSize() / 2,
                    enemy.getCenterY() - enemy.getProjectileSize() / 2, enemy.getProjectileSize(),
                    new Vector2D(v, angle1, true), 1, null, 0, GameEntity.ElementalType.NEUTRAL);
            Projectile test2 = new Projectile(-2, enemy.getCenterX() - enemy.getProjectileSize() / 2,
                    enemy.getCenterY() - enemy.getProjectileSize() / 2, enemy.getProjectileSize(),
                    new Vector2D(v, angle2, true), 1, null, 0, GameEntity.ElementalType.NEUTRAL);
            int test1Result = -1;
            int test2Result = -1;
            while (test1Result == -1) {
                test1Result = Calculations.checkProjectileCollision(test1, map, new ArrayList<>(), new ArrayList<>(), spellington);
                test1.update(10);

            }
            while (test2Result == -1) {
                test2Result = Calculations.checkProjectileCollision(test2, map, new ArrayList<>(), new ArrayList<>(), spellington);
                test2.update(10);

            }
            if (test1Result == 1 || test2Result == 1) {
                float x = enemy.getCenterX() - enemy.getProjectileSize() / 2;
                float y = enemy.getCenterY() - enemy.getProjectileSize() / 2;

                if (test1Result == 1) {
                    activeProjectiles.add(new Projectile(-1, x, y, enemy.getProjectileSize(), new Vector2D(v, angle1, true),
                            1, enemy.getAnimProjectile(), enemy.getDamage(), enemy.getDamageType()));

                } else if (test2Result == 1) {
                    //A changer
                    activeProjectiles.add(new Projectile(-1, x, y, enemy.getProjectileSize(), new Vector2D(v, angle2, true),
                            1, enemy.getAnimProjectile(), enemy.getDamage(), enemy.getDamageType()));
                }
                enemy.setAttackCooldown(enemy.getTotalAttackCooldown());
                if (enemy.getAnimState() == LivingEntity.AnimState.STANDING_L) {
                    enemy.setAnimState(LivingEntity.AnimState.ATTACK_L);
                } else if (enemy.getAnimState() == LivingEntity.AnimState.STANDING_R) {
                    enemy.setAnimState(LivingEntity.AnimState.ATTACK_R);
                }
            }
        }
    }

    /**
     * Tries to shoot a projetile to hit Spellington.
     *
     * @param enemy The enemy.
     * @param spellington The protagonist.
     * @param activeProjectiles The list of active projectiles in the game.
     * @param map The collision and event information for the current map.
     */
    public static void enemyTryToShootStraightProjectile(RangedEnemy enemy, Spellington spellington, ArrayList<Projectile> activeProjectiles, Tile[][] map) {
        float v = 0.5f;
        float deltaX = enemy.getDeltaXPlayer();
        float deltaY = enemy.getDeltaYPlayer();
        float angle = Calculations.detAngle(deltaX, deltaY);
        float x = enemy.getCenterX() - enemy.getProjectileSize() / 2;
        float y = enemy.getCenterY() - enemy.getProjectileSize() / 2;

        Projectile test1 = new Projectile(-2, x, y, enemy.getProjectileSize(),
                new Vector2D(v, angle, true), 0, null, 0, ElementalType.NEUTRAL);

        int test1Result = -1;
        while (test1Result == -1) {
            test1Result = Calculations.checkProjectileCollision(test1, map, new ArrayList<>(), new ArrayList<>(), spellington);
            test1.update(10);
        }

        if (test1Result == 1) {
            enemy.setAttackCooldown(enemy.getTotalAttackCooldown());

            activeProjectiles.add(new Projectile(-1, x, y, enemy.getProjectileSize(),
                    new Vector2D(v, angle, true), 0, enemy.getAnimProjectile(),
                    enemy.getDamage(), enemy.getDamageType()));
            if (enemy.getAnimState() == LivingEntity.AnimState.STANDING_L) {
                enemy.setAnimState(LivingEntity.AnimState.ATTACK_L);
            } else if (enemy.getAnimState() == LivingEntity.AnimState.STANDING_R) {
                enemy.setAnimState(LivingEntity.AnimState.ATTACK_R);
            }
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
    public static void drawAimingHelp(Graphics g, Input input, Spell activeSpell, Spellington spellington) {
        float spellingtonX = spellington.getCenterX();
        float spellingtonY = spellington.getCenterY();
        float mouseX = input.getMouseX() / GameCore.SCALE;
        float mouseY = input.getMouseY() / GameCore.SCALE;
        float projectionPrecision = 50;
        if (activeSpell instanceof BurstSpell) {
            g.setColor(new Color(255, 255, 255));
            g.drawLine(spellingtonX, spellingtonY, mouseX, mouseY);
            Projectile temp1 = ((BurstSpell) activeSpell).createSpellProjectile(spellington, input, -2);
            Projectile temp2 = ((BurstSpell) activeSpell).createSpellProjectile(spellington, input, -2);
            Projectile temp3 = ((BurstSpell) activeSpell).createSpellProjectile(spellington, input, -2);
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
                if (Calculations.checkProjectileCollision(temp1, GameManager.getMapInformation(), GameManager.getActiveEnemies(), new ArrayList<>(), spellington) == 0) {
                    endLoop = true;
                    g.setColor(Color.cyan);
                    g.drawOval(temp1.getX(), temp1.getY(), temp1.getWidth(), temp1.getHeight());
                }
                if (Calculations.checkProjectileCollision(temp1, GameManager.getMapInformation(), GameManager.getActiveEnemies(), new ArrayList<>(), spellington) == 2) {
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
                if (Calculations.checkProjectileCollision(temp2, GameManager.getMapInformation(), GameManager.getActiveEnemies(), new ArrayList<>(), spellington) == 0) {
                    endLoop = true;
                    g.setColor(Color.cyan);
                    g.drawOval(temp2.getX(), temp2.getY(), temp2.getWidth(), temp2.getHeight());
                }
                if (Calculations.checkProjectileCollision(temp2, GameManager.getMapInformation(), GameManager.getActiveEnemies(), new ArrayList<>(), spellington) == 2) {
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
                if (Calculations.checkProjectileCollision(temp3, GameManager.getMapInformation(), GameManager.getActiveEnemies(), new ArrayList<>(), spellington) == 0) {
                    endLoop = true;
                    g.setColor(Color.cyan);
                    g.drawOval(temp3.getX(), temp3.getY(), temp3.getWidth(), temp3.getHeight());
                }
                if (Calculations.checkProjectileCollision(temp3, GameManager.getMapInformation(), GameManager.getActiveEnemies(), new ArrayList<>(), spellington) == 2) {
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
            Projectile temp = ((ProjectileSpell) activeSpell).createSpellProjectile(spellington, input, -2);
            temp.setDamage(0);

            boolean endLoop = false;
            g.setColor(new Color(255, 255, 255, 70));
            while (!endLoop) {
                float tempX = temp.getCenterX();
                float tempY = temp.getCenterY();
                temp.update(projectionPrecision);
                g.drawLine(temp.getCenterX(), temp.getCenterY(), tempX, tempY);
                if (Calculations.checkProjectileCollision(temp, GameManager.getMapInformation(), GameManager.getActiveEnemies(), new ArrayList<>(), spellington) == 0) {
                    endLoop = true;
                    g.setColor(Color.cyan);
                    g.drawOval(temp.getX(), temp.getY(), temp.getWidth(), temp.getHeight());
                }
                if (Calculations.checkProjectileCollision(temp, GameManager.getMapInformation(), GameManager.getActiveEnemies(), new ArrayList<>(), spellington) == 2) {
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
}
