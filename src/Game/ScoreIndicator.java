package Game;

import biuoop.DrawSurface;

/**
 * In Charge of displaying the score.
 */
public class ScoreIndicator implements Sprite {
    private final Game game;
    private final Counter scoreCounter;

    /**
     * Creates ScoreIndicator.
     *
     * @param scoreCounter counter of score to display.
     * @param game the game that is being played
     */
    public ScoreIndicator(Counter scoreCounter, Game game) {
        this.scoreCounter = scoreCounter;
        this.game = game;
    }


    /**
     * Draws sprite on given drawSurface.
     *
     * @param d drawSurface to draw on.
     */
    @Override
    public void drawOn(DrawSurface d) {
        int x = (game.getGameWidth() / 2) - 50;
        int y = 17;
        d.drawText(x, y, "Score: " + scoreCounter.getValue(), 20);
    }

    /**
     * Notify the sprite that time has passed.
     */
    @Override
    public void timePassed() {

    }
}