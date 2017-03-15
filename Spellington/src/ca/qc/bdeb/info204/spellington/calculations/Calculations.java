package ca.qc.bdeb.info204.spellington.calculations;

import ca.qc.bdeb.info204.spellington.gameentities.LivingEntity;
import ca.qc.bdeb.info204.spellington.gameentities.Tile;
import ca.qc.bdeb.info204.spellington.gamestates.PlayState;

/**
 * Class dedicated to performing long or complex calculations for the game.
 *
 * @author Cristian Aldea
 */
public class Calculations {

    public static int TargetI = 0;
    public static int TargetJ = 0;

    /**
     * @author Cristian Aldea
     * @param map
     * @param creature
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
            checkCollision(tempTile, creature);
        }
        for (int j = 0; j < map[0].length; j++) {
            Tile tempTile = map[TargetI][j];
            checkCollision(tempTile, creature);
        }
        for (int i = 0; i < map.length; i++) {
            for (int j = 0; j < map[i].length; j++) {
                Tile tempTile = map[i][j];
                checkCollision(tempTile, creature);

            }
        }
    }

    public static void checkCollision(Tile tile, LivingEntity creature) {
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
            if (heightIntersection < widthIntersection) {
                if (tile.getCenterY() < creature.getCenterY()) {
                    creature.setY(creature.getY() + heightIntersection);
                    creature.setCollisionTop(true);
                } else if (tile.getCenterY() > creature.getCenterY()) {
                    creature.setY(creature.getY() - (heightIntersection));
                    creature.setCollisionBottom(true);
                }
            } else if (widthIntersection < heightIntersection) {
                if (tile.getCenterX() < creature.getCenterX()) {
                    creature.setX(creature.getX() + widthIntersection);
                    creature.setCollisionLeft(true);
                } else if (tile.getCenterX() > creature.getCenterX()) {
                    creature.setX(creature.getX() - widthIntersection);
                    creature.setCollisionRight(true);
                }
            }
        }
    }
    
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
