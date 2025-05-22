package Game;

import Geometry.Ball;
import Geometry.Point;
import Geometry.Rectangle;
import Geometry.Velocity;

/**
 * The Game.Collidable interface represents objects that can be collided with.
 * It provides methods to get the collision shape and to handle the collision response.
 */
public interface Collidable {

    //Methods

    /**
     * @return the collision shape of the object.
     */
    Rectangle getCollisionRectangle();

    /**
     * Notify the object that we collided with it at collisionPoint with a given velocity.
     *
     * @param hitter the ball that is hitting the collidable
     * @param collisionPoint  point of collision.
     * @param currentVelocity velocity before hit.
     * @return new velocity expected after the hit.
     */
    Velocity hit(Ball hitter, Point collisionPoint, Velocity currentVelocity);
}
