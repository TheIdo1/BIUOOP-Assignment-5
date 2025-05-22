package Game;

import Geometry.Line;
import Geometry.Point;

import java.util.ArrayList;
import java.util.List;

/**
 * a class that holds all the collidable of the game, and can operate actions on them.
 */
public class GameEnvironment {
    //fields
    private final List<Collidable> allCollideables;

    //constructor

    /**
     * Create Game.GameEnvironment object without any collideables.
     * this class holds all collideables in game.
     */
    public GameEnvironment() {
        allCollideables = new ArrayList<Collidable>();
    }

    /**
     * Create Game.GameEnvironment object with List of collideables to initiate.
     * this class holds all collideables in game.
     *
     * @param collideables list of collideables.
     */
    public GameEnvironment(List<Collidable> collideables) {
        allCollideables = new ArrayList<Collidable>(collideables);
    }


    // Methods

    /**
     * Add the given collidable to the environment.
     *
     * @param c Game.Collidable to add.
     */
    public void addCollidable(Collidable c) {
        allCollideables.add(c);
    }


    /**
     * Returns the closest collision that would occur along the given trajectory,
     * based on the current list of collidable objects.
     * If there are no collisions, returns null.
     *
     * @param trajectory the path the object is expected to move along
     * @return the Game.CollisionInfo of the closest collision, or null if no collisions are detected
     */
    public CollisionInfo getClosestCollision(Line trajectory) {
        List<CollisionInfo> occurringCollisions = new ArrayList<CollisionInfo>();

        for (Collidable curCol : allCollideables) {
            Point interPoint = trajectory.closestIntersectionToStartOfLine(curCol.getCollisionRectangle());
            if (interPoint != null) {
                occurringCollisions.add(new CollisionInfo(interPoint, curCol));
            }
        }
        if (occurringCollisions.isEmpty()) {
            return null;
        }
        return trajectory.closestIntersectionToStartOfLine(occurringCollisions);
    }

}
