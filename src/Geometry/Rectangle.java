package Geometry;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

import Utils.Utility;
import biuoop.DrawSurface;

/**
 * The Geometry.Rectangle class represents a rectangle defined by its top-left corner (upperLeft),
 * width, height, and color. It can be drawn on a DrawSurface.
 */
public class Rectangle {

    // ---------------- Fields ----------------

    private Point upperLeft;
    private double height;
    private double width;
    private Color color;

    // ---------------- Constructors ----------------

    /**
     * Constructs a rectangle with a given top-left corner point, width, height, and color.
     *
     * @param upperLeft the top-left corner of the rectangle
     * @param width     the width of the rectangle
     * @param height    the height of the rectangle
     * @param color     the color of the rectangle
     */
    public Rectangle(Point upperLeft, double width, double height, Color color) {
        this.upperLeft = upperLeft;
        this.height = height;
        this.width = width;
        this.color = color;
    }

    /**
     * Constructs a random colored rectangle with a given top-left corner point, width and height.
     *
     * @param upperLeft the top-left corner of the rectangle
     * @param width     the width of the rectangle
     * @param height    the height of the rectangle
     */
    public Rectangle(Point upperLeft, double width, double height) {
        this.upperLeft = upperLeft;
        this.height = height;
        this.width = width;
        this.color = Utility.getRandomColor();
    }

    /**
     * Constructs a rectangle using x and y coordinates for the top-left corner,
     * with a specified width, height, and color.
     *
     * @param x      the x-coordinate of the top-left corner
     * @param y      the y-coordinate of the top-left corner
     * @param width  the width of the rectangle
     * @param height the height of the rectangle
     * @param color  the color of the rectangle
     */
    public Rectangle(double x, double y, double width, double height, Color color) {
        this(new Point(x, y), width, height, color);
    }

    /**
     * Constructs a random colored rectangle using x and y coordinates for the top-left corner,
     * with a specified width and height.
     *
     * @param x      the x-coordinate of the top-left corner
     * @param y      the y-coordinate of the top-left corner
     * @param width  the width of the rectangle
     * @param height the height of the rectangle
     */
    public Rectangle(double x, double y, double width, double height) {
        this(new Point(x, y), width, height, Utility.getRandomColor());
    }


    // ---------------- Accessors & Mutators ----------------

    /**
     * Returns the top-left point (upperLeft) of the rectangle.
     *
     * @return the top-left point
     */
    public Point getUpperLeft() {
        return upperLeft;
    }

    /**
     * Sets the top-left point (upperLeft) of the rectangle.
     *
     * @param upperLeft the new top-left point
     */
    public void setUpperLeft(Point upperLeft) {
        this.upperLeft = upperLeft.copy();
    }

    /**
     * Sets the top-left point of the rectangle.
     *
     * @param x x coordinate.
     * @param y y coordinate.
     */
    public void setUpperLeft(double x, double y) {
        this.upperLeft = new Point(x, y);
    }


    /**
     * Returns the height of the rectangle.
     *
     * @return the height
     */
    public double getHeight() {
        return height;
    }

    /**
     * Sets the height of the rectangle.
     *
     * @param height the new height
     */
    public void setHeight(double height) {
        this.height = height;
    }

    /**
     * Returns the width of the rectangle.
     *
     * @return the width
     */
    public double getWidth() {
        return width;
    }

    /**
     * Sets the width of the rectangle.
     *
     * @param width the new width
     */
    public void setWidth(double width) {
        this.width = width;
    }

    /**
     * Returns the color of the rectangle.
     *
     * @return the color
     */
    public Color getColor() {
        return color;
    }

    /**
     * Sets the color of the rectangle.
     *
     * @param color the new color
     */
    public void setColor(Color color) {
        this.color = color;
    }


    // Intersection Methods

    /**
     * returns a list of intersections between rectangle sides and given line.
     *
     * @param line line to intersect with rectangle.
     * @return Array List of intersection points of the line with sides. list can be empty if line isn't intersecting.
     */
    public java.util.List<Point> intersectionPoints(Line line) {
        List<Point> intersections = new ArrayList<>();
        Line[] sides = this.getSides();
        for (Line side : sides) {
            if (!line.isIntersecting(side)) {
                continue;
            }
            if (line.isContainedIn(side)) {
                addIfNotExists(intersections, line.start());
                addIfNotExists(intersections, line.end());
            } else if (line.contains(side)) {
                addIfNotExists(intersections, side.start());
                addIfNotExists(intersections, side.end());
            } else {
                Point intersectionPoint = line.intersectionWith(side);
                if (intersectionPoint != null) {
                    addIfNotExists(intersections, intersectionPoint);
                }
            }
        }
        return intersections;
    }

    /**
     * checks if Geometry.Point is in list, if is in list, do nothing, if isn't in list, add to list.
     *
     * @param list List of points.
     * @param p    Geometry.Point to add to the list.
     */
    private void addIfNotExists(List<Point> list, Point p) {
        if (!list.contains(p)) {
            list.add(p);
        }
    }


    // ---------------- Utils.Utility Methods ----------------

    /**
     * Returns an array of the four corners of the rectangle in the following order:
     * top-left, top-right, bottom-right, bottom-left.
     *
     * @return an array of 4 points representing the corners of the rectangle
     */
    public Point[] getCorners() {
        Point[] corners = new Point[4];
        corners[0] = this.upperLeft;
        corners[1] = new Point(this.upperLeft.getX() + width, this.upperLeft.getY());
        corners[2] = new Point(this.upperLeft.getX() + width, this.upperLeft.getY() + height);
        corners[3] = new Point(this.upperLeft.getX(), this.upperLeft.getY() + height);
        return corners;
    }

    /**
     * returns an array of the four sides of the rectangle in the following order:
     * top, right, bottom, left.
     *
     * @return an array of 4 lines representing the rectangle's sides.
     */
    public Line[] getSides() {
        Line[] sides = new Line[4];
        Point[] corners = this.getCorners();
        sides[0] = new Line(corners[0], corners[1]);
        sides[1] = new Line(corners[1], corners[2]);
        sides[2] = new Line(corners[2], corners[3]);
        sides[3] = new Line(corners[3], corners[0]);
        return sides;
    }

    /**
     * Draws the rectangle on the given DrawSurface.
     *
     * @param drawSurface the surface on which to draw the rectangle
     */
    public void drawOn(DrawSurface drawSurface) {
        if (drawSurface == null) {
            return;
        }
        drawSurface.setColor(color);
        drawSurface.fillRectangle((int) upperLeft.getX(), (int) upperLeft.getY(), (int) width, (int) height);
    }

    /**
     * creates a copy of current rectangle and returns it.
     * @return copy of this rectangle.
     */
    public Rectangle copy() {
        return new Rectangle(upperLeft, width, height, color);
    }

    /**
     * check if point is in the rectangle - including corners and vertices.
     *
     * @param point Geometry.Point to check if is in the rectangle.
     * @return true if is in the rectangle, false otherwise.
     */
    public boolean contains(Point point) {
        double x = point.getX();
        double y = point.getY();

        double left = upperLeft.getX();
        double right = upperLeft.getX() + width;
        double top = upperLeft.getY();
        double bottom = upperLeft.getY() + height;

        return Utility.doubleLE(left, x) && Utility.doubleLE(x, right)
                && Utility.doubleLE(top, y) && Utility.doubleLE(y, bottom);
    }
}
