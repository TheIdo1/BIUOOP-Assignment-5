package Game;

import Geometry.Ball;
import Geometry.Line;
import Geometry.Point;
import Geometry.Rectangle;
import Geometry.Velocity;
import Utils.Utility;
import biuoop.DrawSurface;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

/**
 * class for game blocks, represented by rectangle.
 */
public class Block implements Collidable, Sprite, HitNotifier {
    //fields
    private final Rectangle delegator;
    private List<HitListener> hitListeners;

    // Constructors

    /**
     * Creates new block with given starting point and color.
     *
     * @param upperLeft top-left point of the block.
     * @param width     block width.
     * @param height    block height.
     * @param color     block color.
     */
    public Block(Point upperLeft, double width, double height, Color color) {
        delegator = new Rectangle(upperLeft, width, height, color);
        hitListeners = new ArrayList<>();
    }

    /**
     * Creates new block with given point and random color.
     *
     * @param upperLeft top-left point of the block.
     * @param width     block width.
     * @param height    block height.
     */
    public Block(Point upperLeft, double width, double height) {
        this(upperLeft, width, height, Utility.getRandomColor());
    }

    /**
     * Creates a new block with given x,y and color.
     *
     * @param x      top-left point X coordinate.
     * @param y      top-left point Y coordinate.
     * @param width  block width.
     * @param height block height.
     * @param color  block color.
     */
    public Block(double x, double y, double width, double height, Color color) {
        this(new Point(x, y), width, height, color);
    }

    /**
     * Creates a new block with given x,y and random color.
     *
     * @param x      top-left point X coordinate.
     * @param y      top-left point Y coordinate.
     * @param width  block width.
     * @param height block height.
     */
    public Block(double x, double y, double width, double height) {
        this(new Point(x, y), width, height);
    }

    /**
     * Creates a new block with given rectangle.
     *
     * @param rectangle rectangle to create block from.
     */
    public Block(Rectangle rectangle) {
        this(rectangle.getUpperLeft(), rectangle.getWidth(), rectangle.getHeight(), rectangle.getColor());
    }

    // Getters & Setters - adding as needed.

    /**
     * Sets the color of this object by delegating to the internal object.
     *
     * @param color the color to set
     */
    public void setColor(Color color) {
        this.delegator.setColor(color);
    }

    /**
     * Sets the upper-left point of this object by delegating to the internal object.
     *
     * @param upperLeft the new upper-left point
     */
    public void setUpperLeft(Point upperLeft) {
        this.delegator.setUpperLeft(upperLeft.copy());
    }

    /**
     * Sets the upper-left coordinates of this object by delegating to the internal object.
     *
     * @param x the x-coordinate of the upper-left point
     * @param y the y-coordinate of the upper-left point
     */
    public void setUpperLeft(double x, double y) {
        this.delegator.setUpperLeft(x, y);
    }

    /**
     * Get the width of the block.
     *
     * @return width of the block.
     */
    public double getWidth() {
        return this.delegator.getWidth();
    }

    /**
     * Get the height of the block.
     *
     * @return height of the block.
     */
    public double getHeight() {
        return this.delegator.getHeight();
    }

    /**
     * get the upper left point of the block.
     *
     * @return the upper-left point.
     */
    public Point getUpperLeft() {
        return this.delegator.getUpperLeft();
    }

    /**
     * Gets the color of the block and returns it.
     *
     * @return Color of the block
     */
    public Color getColor() {
        return delegator.getColor();
    }

    // Methods


    /**
     * Add hl to the list of listeners to hit event.
     *
     * @param hl hit listener to add
     */
    @Override
    public void addHitListener(HitListener hl) {
        hitListeners.add(hl);
    }

    /**
     * Remove hl from the list of listeners.
     *
     * @param hl hit listener to remove
     */
    @Override
    public void removeHitListener(HitListener hl) {
        hitListeners.remove(hl);
    }

    private void notifyHit(Ball hitter) {
        List<HitListener> listeners = new ArrayList<HitListener>(this.hitListeners);
        for (HitListener hl : listeners) {
            hl.hitEvent(this, hitter);
        }
    }


    /**
     * @return Geometry.Rectangle that is representing the block
     */
    public Rectangle getCollisionRectangle() {
        return delegator.copy();
    }

    /**
     * check if given point is colliding with the block and return expected velocity after hit.
     *
     * @param hitter          the ball that is hitting the block
     * @param collisionPoint  point of collision.
     * @param currentVelocity velocity before hit.
     * @return new velocity after hit.
     */
    public Velocity hit(Ball hitter, Point collisionPoint, Velocity currentVelocity) {
        if (currentVelocity == null) {
            return null;
        }
        //check if point is within the block.
        if (!delegator.contains(collisionPoint)) {
            return currentVelocity;
        }

        Velocity returnVelocity = currentVelocity.copy();
        //get sides of the rectangle top, right, bottom, left
        Line[] sides = delegator.getSides();

        // Check if hitting top or bottom sides of the block
        if (sides[0].isPointOnSegment(collisionPoint) || sides[2].isPointOnSegment(collisionPoint)) {
            returnVelocity.setDy(-returnVelocity.getDy());
        }

        // Check if hitting right or left sides of the block.
        if (sides[1].isPointOnSegment(collisionPoint) || sides[3].isPointOnSegment(collisionPoint)) {
            returnVelocity.setDx(-returnVelocity.getDx());
        }

        this.notifyHit(hitter);

        return returnVelocity;
    }

    /**
     * Checks if given ball's color is the same as block.
     *
     * @param ball ball to check colors with
     * @return true if same color, false otherwise.
     */
    public boolean ballColorMatch(Ball ball) {
        return this.delegator.getColor().equals(ball.getColor());
    }

    /**
     * Removes block from the game.
     *
     * @param game game to remove block from
     */
    public void removeFromGame(Game game) {
        game.removeCollidable(this);
        game.removeSprite(this);
    }

    /**
     * Add block to game.
     *
     * @param game game to add block to
     */
    public void addToGame(Game game) {
        game.addBlock(this);
    }

    /**
     * Draw block on given drawSurface.
     *
     * @param drawSurface drawSurface to draw on.
     */
    public void drawOn(DrawSurface drawSurface) {
        delegator.drawOn(drawSurface);
        drawSurface.setColor(Color.BLACK);
        drawSurface.drawRectangle((int) this.getUpperLeft().getX(), (int) this.getUpperLeft().getY(),
                (int) this.getWidth(), (int) this.getHeight());
    }


    /**
     * Notify block that time has passed, currently doing nothing.
     */
    public void timePassed() {
        return;
    }

}
