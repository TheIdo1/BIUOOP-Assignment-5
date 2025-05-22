package Game;

import biuoop.DrawSurface;

/**
 * an interface for all the sprites object that can be made in the game.
 */
public interface Sprite {
    //fields
    //constructors

    //Methods

    /**
     * Draws sprite on given drawSurface.
     * @param d drawSurface to draw on.
     */
    void drawOn(DrawSurface d);

    /**
     * Notify the sprite that time has passed.
     */
    void timePassed();
}
