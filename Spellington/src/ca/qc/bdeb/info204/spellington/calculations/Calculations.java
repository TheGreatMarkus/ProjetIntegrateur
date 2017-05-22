package ca.qc.bdeb.info204.spellington.calculations;

import static ca.qc.bdeb.info204.spellington.GameCore.DIM_MAP;
import ca.qc.bdeb.info204.spellington.gameentities.GameEntity;
import ca.qc.bdeb.info204.spellington.gameentities.GameEntity.ElementalType;
import ca.qc.bdeb.info204.spellington.gameentities.LivingEntity;
import ca.qc.bdeb.info204.spellington.gameentities.Projectile;
import ca.qc.bdeb.info204.spellington.gameentities.Projectile.ProjectileSourceType;
import ca.qc.bdeb.info204.spellington.gameentities.Spellington;
import ca.qc.bdeb.info204.spellington.gameentities.Tile;
import ca.qc.bdeb.info204.spellington.gameentities.enemies.Enemy;
import ca.qc.bdeb.info204.spellington.gameentities.enemies.RangedEnemy;
import ca.qc.bdeb.info204.spellington.gamestates.PlayState;
import java.util.ArrayList;
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
     * @param creature The LivingEntity that will be checked for collision.
     */
    public static void checkMapCollision(Tile[][] map, LivingEntity creature) {
        TargetI = (int) (creature.getCenterY() / (float) Tile.DIM_TILE.width);
        TargetJ = (int) (creature.getCenterX() / (float) Tile.DIM_TILE.height);
        if (TargetI < 0 || TargetI >= DIM_MAP.height
                || TargetJ < 0 || TargetJ >= DIM_MAP.width) {
            TargetI = 0;
            TargetJ = 0;
        }

        for (int i = 0; i < map.length; i++) {
            Tile tempTile = map[i][TargetJ];
            checkTileAndLivingCollision(tempTile, creature);
        }
        for (int j = 0; j < map[0].length; j++) {
            Tile tempTile = map[TargetI][j];
            checkTileAndLivingCollision(tempTile, creature);
        }
        for (int i = 0; i < map.length; i++) {
            for (int j = 0; j < map[i].length; j++) {
                Tile tempTile = map[i][j];
                checkTileAndLivingCollision(tempTile, creature);

            }
        }
    }

    /**
     * Checks collision between a tile and a LivingEntity. Also does the
     * collision resolution after that collision is detected.
     *
     * @param tile The tile that will be checked for collision.
     * @param creature The LivingEntity that will be checked for collision.
     */
    public static void checkTileAndLivingCollision(Tile tile, LivingEntity creature) {
        //If a collision is found and the tile is impassable
        if (creature.getBounds().intersects(tile.getBounds()) && tile.getTileState() != Tile.TileState.PASSABLE) {
            if (tile.getTileState() == Tile.TileState.LAVA) {
                creature.subLifePoint(5, ElementalType.FIRE);
            }
            //To get the width and height of the intersaction
            float left = Float.max(tile.getX(), creature.getX());
            float right = Float.min(tile.getMaxX(), creature.getMaxX());
            float bottom = Float.min(tile.getMaxY(), creature.getMaxY());
            float top = Float.max(tile.getY(), creature.getY());

            float widthIntersection = Math.abs(right - left);
            float heightIntersection = Math.abs(bottom - top);

            /*The side of the correction is determined by calculating the 
             shallowest side of the intersection and the relative x and y positions
             of the entity to be moved*/
            if (heightIntersection < widthIntersection) {//collision with a wide object
                if (tile.getCenterY() < creature.getCenterY()) {//object over the creature
                    creature.setY(creature.getY() + heightIntersection);
                    creature.setCollisionTop(true);
                } else if (tile.getCenterY() > creature.getCenterY()) {//object under the creature
                    creature.setY(creature.getY() - (heightIntersection));
                    creature.setCollisionBottom(true);
                }
            } else if (widthIntersection < heightIntersection) { //collision with a tall object
                if (tile.getCenterX() < creature.getCenterX()) {// collision to the left 
                    creature.setX(creature.getX() + widthIntersection);
                    creature.setCollisionLeft(true);
                } else if (tile.getCenterX() > creature.getCenterX()) {//collision to the right
                    creature.setX(creature.getX() - widthIntersection);
                    creature.setCollisionRight(true);
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
     * @param projectile The projectile that will be checked for collision.
     * @return If the projectile should be removed from the activeProjectile
     * list.
     */
    public static int checkProjectileCollision(Projectile projectile, Tile[][] map, ArrayList<Enemy> activeEnemies, Spellington spellington) {
        for (int i = 0; i < map.length; i++) {
            for (int j = 0; j < map[i].length; j++) {
                if (projectile.getBounds().intersects(map[i][j].getBounds()) && map[i][j].getTileState() != Tile.TileState.PASSABLE) {
                    return 0;//Collision with a tile.
                }
            }
        }

        if (projectile.getBounds().intersects(spellington.getBounds())) {
            if (projectile.getSource() != ProjectileSourceType.PLAYER) {
                if (projectile.getSource() == ProjectileSourceType.ENEMY) {
                    spellington.subLifePoint(projectile.getDamage(), projectile.getDamageType());
                }
                return 1;//Collision with Spellington.
            }
        }

        for (Enemy activeEnemy : activeEnemies) {
            if (projectile.getBounds().intersects(activeEnemy.getBounds())) {
                if (projectile.getSource() != ProjectileSourceType.ENEMY) {
                    if (projectile.getSource() == ProjectileSourceType.PLAYER) {
                        activeEnemy.subLifePoint(projectile.getDamage(), projectile.getDamageType());
                    }
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
            Projectile test1 = new Projectile(enemy.getCenterX() - enemy.getProjectileSize() / 2, enemy.getCenterY() - enemy.getProjectileSize() / 2, enemy.getProjectileSize(), new Vector2D(v, angle1, true), 1, null, 0, GameEntity.ElementalType.NEUTRAL, Projectile.ProjectileSourceType.TEST);
            Projectile test2 = new Projectile(enemy.getCenterX() - enemy.getProjectileSize() / 2, enemy.getCenterY() - enemy.getProjectileSize() / 2, enemy.getProjectileSize(), new Vector2D(v, angle2, true), 1, null, 0, GameEntity.ElementalType.NEUTRAL, Projectile.ProjectileSourceType.TEST);
            int test1Result = -1;
            int test2Result = -1;
            while (test1Result == -1) {
                test1Result = Calculations.checkProjectileCollision(test1, map, new ArrayList<Enemy>(), spellington);
                test1.update(10);

            }
            while (test2Result == -1) {
                test2Result = Calculations.checkProjectileCollision(test2, map, new ArrayList<Enemy>(), spellington);
                test2.update(10);

            }
            if (test1Result == 1 || test2Result == 1) {
                float x = enemy.getCenterX() - enemy.getProjectileSize() / 2;
                float y = enemy.getCenterY() - enemy.getProjectileSize() / 2;

                if (test1Result == 1) {
                    activeProjectiles.add(new Projectile(x, y, enemy.getProjectileSize(), new Vector2D(v, angle1, true),
                            1, enemy.getAnimProjectile(), 5, GameEntity.ElementalType.FIRE, Projectile.ProjectileSourceType.ENEMY));

                } else if (test2Result == 1) {
                    //A changer
                    activeProjectiles.add(new Projectile(x, y, enemy.getProjectileSize(), new Vector2D(v, angle2, true),
                            1, enemy.getAnimProjectile(), 5, GameEntity.ElementalType.FIRE, Projectile.ProjectileSourceType.ENEMY));
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

        Projectile test1 = new Projectile(x, y, enemy.getProjectileSize(),
                new Vector2D(v, angle, true), 0, null, 0, ElementalType.NEUTRAL, ProjectileSourceType.TEST);

        int test1Result = -1;
        while (test1Result == -1) {
            test1Result = Calculations.checkProjectileCollision(test1, map, new ArrayList<>(), spellington);
            test1.update(10);
        }

        if (test1Result == 1) {
            enemy.setAttackCooldown(enemy.getTotalAttackCooldown());

            activeProjectiles.add(new Projectile(x, y, enemy.getProjectileSize(),
                    new Vector2D(v, angle, true), 0, enemy.getAnimProjectile(),
                    enemy.getDamage(), enemy.getDamageType(), ProjectileSourceType.ENEMY));
            if (enemy.getAnimState() == LivingEntity.AnimState.STANDING_L) {
                enemy.setAnimState(LivingEntity.AnimState.ATTACK_L);
            } else if (enemy.getAnimState() == LivingEntity.AnimState.STANDING_R) {
                enemy.setAnimState(LivingEntity.AnimState.ATTACK_R);
            }
        }
    }
}
