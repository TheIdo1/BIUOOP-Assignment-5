package Game;

import Geometry.Line;
import Geometry.Point;
import Geometry.Rectangle;
import Utils.Utility;
import Geometry.Velocity;
import biuoop.DrawSurface;

import java.awt.Color;

/**
 * class for game blocks, represented by rectangle.
 */
public class Block implements Collidable, Sprite {
    //fields
    private final Rectangle delegator;

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

    // Methods

    /**
     * @return Geometry.Rectangle that is representing the block
     */
    public Rectangle getCollisionRectangle() {
        return delegator.copy();
    }

    /**
     * check if given point is colliding with the block and return expected velocity after hit.
     *
     * @param collisionPoint  point of collision.
     * @param currentVelocity velocity before hit.
     * @return new velocity after hit.
     */
    public Velocity hit(Point collisionPoint, Velocity currentVelocity) {
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

        return returnVelocity;
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
