package Game;

import Geometry.Ball;
import Geometry.Velocity;

/**
 * a BlockRemover is in charge of removing blocks from the game, as well as keeping count
 * of the number of blocks that remain.
 */
public class BlockRemover implements HitListener {
    private final Game game;
    private final Counter remainingBlocks;

    /**
     * a BlockRemover is in charge of removing blocks from the game, as well as keeping count
     * of the number of blocks that remain.
     *
     * @param game            game that is being played
     * @param remainingBlocks remaining blocks counter
     */
    public BlockRemover(Game game, Counter remainingBlocks) {
        this.game = game;
        this.remainingBlocks = remainingBlocks;
    }

    /**
     * Removing block from game.
     *
     * @param beingHit the block that is being hit
     * @param hitter   the ball that is hitting the block
     */
    public void hitEvent(Block beingHit, Ball hitter) {
        if (!beingHit.ballColorMatch(hitter)) {
            beingHit.removeHitListener(this);
            beingHit.removeFromGame(game);
            remainingBlocks.decrease(1);
            hitter.setColor(beingHit.getColor());
            Velocity v = Velocity.fromAngleAndSpeed(hitter.getVelocity().getAngle(),
                    hitter.getVelocity().getSpeed() + 0.2);
            hitter.setVelocity(v);
        }
    }
}
