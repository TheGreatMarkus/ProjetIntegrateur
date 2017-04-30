package ca.qc.bdeb.info204.spellington.calculations;

import ca.qc.bdeb.info204.spellington.GameCore;
import static ca.qc.bdeb.info204.spellington.GameCore.DIM_MAP;
import ca.qc.bdeb.info204.spellington.gameentities.GameEntity;
import ca.qc.bdeb.info204.spellington.gameentities.LivingEntity;
import ca.qc.bdeb.info204.spellington.gameentities.Projectile;
import ca.qc.bdeb.info204.spellington.gameentities.Projectile.SourceType;
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
     * @param map The map that will be checked for collision.
     * @param creature The LivingEntity that will be checked for collision.
     * @author Cristian Aldea.
     */
    public static void checkMapCollision(Tile[][] map, LivingEntity creature) {
        TargetI = (int) (creature.getCenterY() / (float) Tile.DIM_TILE.width);
        TargetJ = (int) (creature.getCenterX() / (float) Tile.DIM_TILE.height);
        if (TargetI >= DIM_MAP.height || TargetJ >= DIM_MAP.width) {
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
     * @author Cristian Aldea.
     */
    public static void checkTileAndLivingCollision(Tile tile, LivingEntity creature) {
        if (creature.intersects(tile) && tile.getTileState() == Tile.TileState.IMPASSABLE) {
            //If a collision is found and the tile is impassable

            //To get the width and height of the intersaction
            float left = Float.max(tile.getMinX(), creature.getMinX());
            float right = Float.min(tile.getMaxX(), creature.getMaxX());
            float bottom = Float.min(tile.getMaxY(), creature.getMaxY());
            float top = Float.max(tile.getMinY(), creature.getMinY());

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
     * @param map The map that will be checked for collision.
     * @param activeEnemies The enemies that will be checked for collision.
     * @param spellington Spellington that will be checked for collision.
     * @param projectile The projectile that will be checked for collision.
     * @return If the projectile should be removed from the activeProjectile
     * list.
     * @author Cristian Aldea.
     */
    public static int checkProjectileCollision(Projectile projectile, Tile[][] map, ArrayList<Enemy> activeEnemies, Spellington spellington) {
        for (int i = 0; i < map.length; i++) {
            for (int j = 0; j < map[i].length; j++) {
                if (projectile.intersects(map[i][j]) && map[i][j].getTileState() == Tile.TileState.IMPASSABLE) {
                    return 0;
                }
            }
        }

        if (projectile.intersects(spellington) && projectile.getSource() == SourceType.ENEMY) {
            spellington.subLifePoint(projectile.getDamage(), projectile.getDamageType());
            return 1;
        }

        for (Enemy activeEnemy : activeEnemies) {
            if (projectile.intersects(activeEnemy) && projectile.getSource() == SourceType.PLAYER) {
                activeEnemy.subLifePoint(projectile.getDamage(), projectile.getDamageType());
                return 2;
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
     * @author Cristian Aldea.
     */
    public static float detAngle(float x, float y) {
        //Calculate the base angle assuming the deltaX and DeltaY are positive
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

    public static boolean detEnemyCanSeeSpellington(Enemy enemy, Spellington spellington, Tile[][] mapinfo) {
        Line line = new Line(spellington.getCenterX(), spellington.getCenterY(), enemy.getCenterX(), enemy.getCenterY());
        for (int i = 0; i < mapinfo.length; i++) {
            for (int j = 0; j < mapinfo[i].length; j++) {
                if (line.intersects(mapinfo[i][j]) && mapinfo[i][j].getTileState() == Tile.TileState.IMPASSABLE) {
                    return false;
                }
            }
        }
        return true;
    }

    public static void EnemyTryToShootCurvedProjectile(RangedEnemy enemy, Spellington spellington, ArrayList<Projectile> activeProjectiles, Tile[][] mapinfo) {
        Float angle1;
        Float angle2;
        float deltaX = -enemy.getDeltaX();
        float deltaY = -enemy.getDeltaY();
        float v = 0.7f;
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
            Projectile test1 = new Projectile(enemy.getCenterX() - enemy.getDimProjectile().width / 2, enemy.getCenterY() - enemy.getDimProjectile().height / 2, enemy.getDimProjectile().width, enemy.getDimProjectile().height, new Vector2D(v, angle1, true), 1, null, 0, GameEntity.ElementalType.NEUTRAL, Projectile.SourceType.ENEMY);
            Projectile test2 = new Projectile(enemy.getCenterX() - enemy.getDimProjectile().width / 2, enemy.getCenterY() - enemy.getDimProjectile().height / 2, enemy.getDimProjectile().width, enemy.getDimProjectile().height, new Vector2D(v, angle2, true), 1, null, 0, GameEntity.ElementalType.NEUTRAL, Projectile.SourceType.ENEMY);
            int test1Result = -1;
            int test2Result = -1;
            while (test1Result == -1) {
                test1Result = Calculations.checkProjectileCollision(test1, mapinfo, new ArrayList<Enemy>(), spellington);
                test1.update(10);

            }
            while (test2Result == -1) {
                test2Result = Calculations.checkProjectileCollision(test2, mapinfo, new ArrayList<Enemy>(), spellington);
                test2.update(10);

            }
            if (test1Result == 1 || test2Result == 1) {
                float x = enemy.getCenterX() - enemy.getDimProjectile().width / 2;
                float y = enemy.getCenterY() - enemy.getDimProjectile().height / 2;
                int width = enemy.getDimProjectile().width;
                int height = enemy.getDimProjectile().height;
                if (test2Result == 1) {
                    //A changer
                    activeProjectiles.add(new Projectile(x, y, width, height, new Vector2D(v, angle2, true), 1, enemy.getAnimProjectile(), 5, GameEntity.ElementalType.FIRE, Projectile.SourceType.ENEMY));
                } else if (test1Result == 1) {
                    activeProjectiles.add(new Projectile(x, y, width, height, new Vector2D(v, angle1, true), 1, enemy.getAnimProjectile(), 5, GameEntity.ElementalType.FIRE, Projectile.SourceType.ENEMY));

                }
                enemy.setAttackCooldown(enemy.getTotalAttackCooldown());
                if (enemy.getMouvementState() == LivingEntity.MouvementState.STANDING_L) {
                    enemy.setMouvementState(LivingEntity.MouvementState.ATTACK_L);
                } else if (enemy.getMouvementState() == LivingEntity.MouvementState.STANDING_R) {
                    enemy.setMouvementState(LivingEntity.MouvementState.ATTACK_R);
                }
            }
        }
    }

    public static void EnemyTryToShootStraightProjectile(RangedEnemy enemy, Spellington spellington, ArrayList<Projectile> activeProjectiles, Tile[][] mapinfo) {
        float v = 0.5f;
        float angle = 1f;
        float deltaX = enemy.getDeltaX();
        float deltaY = enemy.getDeltaY();

        angle = Calculations.detAngle(deltaX, deltaY);

        Projectile test1 = new Projectile(enemy.getCenterX() - enemy.getDimProjectile().width / 2, enemy.getCenterY() - enemy.getDimProjectile().height / 2, enemy.getDimProjectile().width, enemy.getDimProjectile().height, new Vector2D(v, angle, true), 0, null, 0, GameEntity.ElementalType.NEUTRAL, Projectile.SourceType.ENEMY);

        int test1Result = -1;
        while (test1Result == -1) {
            test1Result = Calculations.checkProjectileCollision(test1, mapinfo, new ArrayList<Enemy>(), spellington);
            test1.update(10);

        }
        if (test1Result == 1) {
            enemy.setAttackCooldown(enemy.getTotalAttackCooldown());
            float x = enemy.getCenterX() - enemy.getDimProjectile().width / 2;
            float y = enemy.getCenterY() - enemy.getDimProjectile().height / 2;
            int width = enemy.getDimProjectile().width;
            int height = enemy.getDimProjectile().height;
            activeProjectiles.add(new Projectile(x, y, width, height, new Vector2D(v, angle, true), 0, enemy.getAnimProjectile(), 5, GameEntity.ElementalType.FIRE, Projectile.SourceType.ENEMY));
            if (enemy.getMouvementState() == LivingEntity.MouvementState.STANDING_L) {
                enemy.setMouvementState(LivingEntity.MouvementState.ATTACK_L);
            } else if (enemy.getMouvementState() == LivingEntity.MouvementState.STANDING_R) {
                enemy.setMouvementState(LivingEntity.MouvementState.ATTACK_R);
            }
        }

    }

}
