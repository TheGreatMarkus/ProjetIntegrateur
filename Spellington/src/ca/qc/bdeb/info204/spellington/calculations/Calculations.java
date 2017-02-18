package ca.qc.bdeb.info204.spellington.calculations;

import ca.qc.bdeb.info204.spellington.gameentities.LivingEntity;
import ca.qc.bdeb.info204.spellington.gameentities.Tile;

/**
 * Class dedicated to performing long or complex calculations for the game.
 *
 * @author Cristian Aldea
 */
public class Calculations {

    /**
     * @author Cristian Aldea
     * @param tile
     * @param creature
     */
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
            
            if (true) {
//                System.out.println("Width : " + widthIntersection);
//            System.out.println("Height: " + heightIntersection);
//            System.out.println("");
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
    }

}
