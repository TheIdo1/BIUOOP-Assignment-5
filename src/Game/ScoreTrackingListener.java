package Game;

import Geometry.Ball;

/**
 * Tracker for the game score.
 */
public class ScoreTrackingListener implements HitListener {
    private final Counter currentScore;

    /**
     * Initializing Score Tracker.
     *
     * @param scoreCounter Score Counter of the game.
     */
    public ScoreTrackingListener(Counter scoreCounter) {
        this.currentScore = scoreCounter;
    }

    /**
     * Increasing the score accordingly.
     *
     * @param beingHit the block that is being hit
     * @param hitter   the ball that is hitting the block
     */
    public void hitEvent(Block beingHit, Ball hitter) {
        currentScore.increase(5);
    }
}
