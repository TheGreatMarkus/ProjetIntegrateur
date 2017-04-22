package ca.qc.bdeb.info204.spellington.calculations;

import ca.qc.bdeb.info204.spellington.gameentities.LivingEntity;
import ca.qc.bdeb.info204.spellington.gameentities.Projectile;
import ca.qc.bdeb.info204.spellington.gameentities.Spellington;
import ca.qc.bdeb.info204.spellington.gameentities.Tile;
import ca.qc.bdeb.info204.spellington.gameentities.enemies.Enemy;
import ca.qc.bdeb.info204.spellington.gamestates.PlayState;
import java.util.ArrayList;

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
        if (TargetI >= PlayState.DIM_MAP.height || TargetJ >= PlayState.DIM_MAP.width) {
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
    public static boolean checkProjectileCollision(Tile[][] map, ArrayList<Enemy> activeEnemies, Spellington spellington, Projectile projectile) {
        for (int i = 0; i < map.length; i++) {
            for (int j = 0; j < map[i].length; j++) {
                if (projectile.intersects(map[i][j]) && map[i][j].getTileState() == Tile.TileState.IMPASSABLE) {
                    return true;
                }
            }
        }
        for (Enemy activeEnemy : activeEnemies) {
            if (projectile.intersects(activeEnemy)) {
                activeEnemy.subLifePoint(projectile.getDamage(), projectile.getDamageType());
                return true;
            }
        }
        return false;
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

}
