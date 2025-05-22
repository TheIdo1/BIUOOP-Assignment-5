package Game;

import Geometry.Ball;

/**
 * HitListeners interface. being called when something is being hit.
 */
public interface HitListener {
    /**
     * This method is called whenever the beingHit object is hit.
     *
     * @param beingHit the block that is being hit
     * @param hitter   the ball that is hitting the block
     */
    void hitEvent(Block beingHit, Ball hitter);
}
