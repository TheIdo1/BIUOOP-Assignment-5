package Game;

import biuoop.DrawSurface;

import java.util.ArrayList;
import java.util.List;

/**
 * a class that holds collection of sprites and can operate actions on them.
 */
public class SpriteCollection {
    //fields
    private final List<Sprite> allSprites;

    //constructors

    /**
     * Create a new sprite collection.
     */
    public SpriteCollection() {
        allSprites = new ArrayList<Sprite>();
    }

    /**
     * Create a new sprite collection with given sprites list.
     *
     * @param sprites sprites list.
     */
    public SpriteCollection(List<Sprite> sprites) {
        allSprites = new ArrayList<Sprite>(sprites);
    }

    //Methods

    /**
     * Add given sprite to collection.
     *
     * @param s sprite to add.
     */
    public void addSprite(Sprite s) {
        allSprites.add(s);
    }


    /**
     * Call timePassed() on all sprites, if any sprite should do something linear with time, it will do it.
     */
    public void notifyAllTimePassed() {
        for (Sprite curSprite : allSprites) {
            curSprite.timePassed();
        }
    }

    /**
     * Draw all sprites on given draw surface.
     *
     * @param d draw surface to draw sprites on.
     */
    public void drawAllOn(DrawSurface d) {
        for (Sprite curSprite : allSprites) {
            curSprite.drawOn(d);
        }
    }
}
