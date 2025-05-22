package Game;

import Geometry.Point;

/**
 * The Game.CollisionInfo class represents information about a collision event,
 * including the point where the collision occurred and the object involved in the collision.
 */
public class CollisionInfo {
    // fields

    /**
     * The point at which the collision occurs.
     */
    private final Point collisionPoint;

    /**
     * The collidable object involved in the collision.
     */
    private final Collidable collisionObject;

    // constructor

    /**
     * Constructs a Game.CollisionInfo instance with the given collision point and collidable object.
     *
     * @param collisionPoint  the point where the collision occurred
     * @param collisionObject the object that was involved in the collision
     */
    public CollisionInfo(Point collisionPoint, Collidable collisionObject) {
        this.collisionPoint = collisionPoint;
        this.collisionObject = collisionObject;
    }

    /**
     * Returns a copy of the point at which the collision occurs.
     *
     * @return the collision point
     */
    public Point collisionPoint() {
        return collisionPoint.copy();
    }

    /**
     * Returns the collidable object involved in the collision.
     *
     * @return the object involved in the collision
     */
    public Collidable collisionObject() {
        return collisionObject;
    }
}
