package Geometry;

import Game.CollisionInfo;
import Utils.Utility;

import java.util.List;

/**
 * Represents a line segment between two points in 2D space.
 */
public class Line {
    //fields
    private final Point start;
    private final Point end;
    private final boolean isVertical;
    private final double freePart;
    private final Double slope;

    /**
     * Constructs a Geometry.Line given two Points, without reordering them.
     *
     * @param start the starting Geometry.Point
     * @param end   the ending Geometry.Point
     */
    public Line(Point start, Point end) {
        this.start = start.copy();
        this.end = end.copy();

        if (Utility.doubleEquals(start.getX(), end.getX())) {
            this.isVertical = true;
            this.slope = null;
            this.freePart = 0;
        } else {
            this.isVertical = false;
            this.slope = (end.getY() - start.getY()) / (end.getX() - start.getX());
            this.freePart = start.getY() - (start.getX() * slope);
        }
    }


    /**
     * Constructs a Geometry.Line from coordinates.
     *
     * @param x1 x of first point
     * @param y1 y of first point
     * @param x2 x of second point
     * @param y2 y of second point
     */
    public Line(double x1, double y1, double x2, double y2) {
        this(new Point(x1, y1), new Point(x2, y2));
    }


    // Getters & Setters of line Properties.

    /**
     * @return the length of the line
     */
    public double length() {
        return this.start.distance(end);
    }

    /**
     * @return the middle point of the line
     */
    public Point middle() {
        return new Point((this.start.getX() + this.end.getX()) / 2, (this.start.getY() + this.end.getY()) / 2);
    }

    /**
     * @return copy of start point
     */
    public Point start() {
        return (new Point(this.start.getX(), this.start.getY()));
    }

    /**
     * @return copy of end point
     */
    public Point end() {
        return (new Point(this.end.getX(), this.end.getY()));
    }

    /**
     * @return the slope of the line, or null if vertical
     */
    public Double slope() {
        return this.slope;
    }

    /**
     * @return true if the line is vertical
     */
    public boolean isVertical() {
        return this.isVertical;
    }

    /**
     * @return the y-intercept (free part) of the line
     */
    public double getFreePart() {
        return this.freePart;
    }

    // Intersection Methods

    /**
     * Checks if this line intersects another line.
     *
     * @param other the other line
     * @return true if they intersect
     */
    public boolean isIntersecting(Line other) {
        if (other == null) {
            return false;
        }

        if (!this.isVertical() && !other.isVertical()) {
            if (Utility.doubleEquals(this.slope, other.slope())) {
                if (Utility.doubleEquals(this.freePart, other.getFreePart())) {
                    return (this.start.getX() >= Math.min(other.start.getX(), other.end.getX())
                            && this.start.getX() <= Math.max(other.start.getX(), other.end.getX()))
                            || (this.end.getX() >= Math.min(other.start.getX(), other.end.getX())
                            && this.end.getX() <= Math.max(other.start.getX(), other.end.getX()))
                            || (other.end.getX() >= Math.min(this.start.getX(), this.end.getX())
                            && other.end.getX() <= Math.max(this.start.getX(), this.end.getX()))
                            || (other.start.getX() >= Math.min(this.start.getX(), this.end.getX())
                            && other.start.getX() <= Math.max(this.start.getX(), this.end.getX()));
                } else {
                    return false;
                }
            }
            //slope is not equal:
            double intersectionPointX = (other.getFreePart() - this.freePart) / (this.slope() - other.slope());
            return intersectionPointX >= Math.min(this.start.getX(), this.end.getX())
                    && intersectionPointX <= Math.max(this.start.getX(), this.end.getX())
                    && intersectionPointX >= Math.min(other.start.getX(), other.end.getX())
                    && intersectionPointX <= Math.max(other.start.getX(), other.end.getX());
        }

        // one line is vertical:
        if (this.isVertical() && !other.isVertical()) {
            double intersectionX = this.start.getX();
            double intersectionY = other.slope() * intersectionX + other.getFreePart();

            return intersectionX >= Math.min(other.start.getX(), other.end.getX())
                    && intersectionX <= Math.max(other.start.getX(), other.end.getX())
                    && intersectionY >= Math.min(this.start.getY(), this.end.getY())
                    && intersectionY <= Math.max(this.start.getY(), this.end.getY());
        }

        if (other.isVertical() && !this.isVertical()) {
            double intersectionX = other.start.getX();
            double intersectionY = this.slope() * intersectionX + this.freePart;

            return intersectionX >= Math.min(this.start.getX(), this.end.getX())
                    && intersectionX <= Math.max(this.start.getX(), this.end.getX())
                    && intersectionY >= Math.min(other.start.getY(), other.end.getY())
                    && intersectionY <= Math.max(other.start.getY(), other.end.getY());
        }

        //both lines are vertical:
        if (this.isVertical() && other.isVertical()) {
            if (Utility.doubleEquals(this.start.getX(), other.start.getX())) {
                return (this.start.getY() >= Math.min(other.start.getY(), other.end.getY())
                        && this.start.getY() <= Math.max(other.start.getY(), other.end.getY()))
                        || (this.end.getY() >= Math.min(other.start.getY(), other.end.getY())
                        && this.end.getY() <= Math.max(other.start.getY(), other.end.getY()))
                        || (other.end.getY() >= Math.min(this.start.getY(), this.end.getY())
                        && other.end.getY() <= Math.max(this.start.getY(), this.end.getY()))
                        || (other.start.getY() >= Math.min(this.start.getY(), this.end.getY())
                        && other.start.getY() <= Math.max(this.start.getY(), this.end.getY()));
            }
            return false;
        }
        return false;
    }


    /**
     * Checks if this line intersects both other lines.
     *
     * @param other1 first line
     * @param other2 second line
     * @return true if intersects both
     */
    public boolean isIntersecting(Line other1, Line other2) {
        return (this.isIntersecting(other1) && this.isIntersecting(other2));
    }

    /**
     * Returns intersection point if lines intersect in exactly one point, null otherwise.
     *
     * @param other the other line
     * @return intersection Geometry.Point or null
     */
    public Point intersectionWith(Line other) {
        if (other == null || this.equals(other)) {
            return null;
        }
        if (!this.isIntersecting(other)) {
            return null;
        }

        // both lines are vertical
        if (this.isVertical() && other.isVertical()) {
            if (Utility.doubleEquals(this.start.getX(), other.start.getX())) {
                // check shared endpoints
                if (this.start.equals(other.start) || this.start.equals(other.end)) {
                    return this.start.copy();
                }
                if (this.end.equals(other.start) || this.end.equals(other.end)) {
                    return this.end.copy();
                }
            }
            return null;
        }

        // this is vertical, other is not
        if (this.isVertical() && !other.isVertical()) {
            double intersectionX = this.start.getX();
            double intersectionY = other.slope() * intersectionX + other.getFreePart();
            return new Point(intersectionX, intersectionY);
        }

        // other is vertical, this is not
        if (other.isVertical() && !this.isVertical()) {
            double intersectionX = other.start.getX();
            double intersectionY = this.slope * intersectionX + this.freePart;
            return new Point(intersectionX, intersectionY);
        }

        // both lines are not vertical and have same slope and free part (overlapping lines)
        if (Utility.doubleEquals(this.slope, other.slope())
                && Utility.doubleEquals(this.freePart, other.getFreePart())) {

            // check shared endpoints
            if (this.start.equals(other.start) || this.start.equals(other.end)) {
                return this.start;
            }
            if (this.end.equals(other.start) || this.end.equals(other.end)) {
                return this.end;
            }

            // overlapping but not touching at a single point
            return null;
        }

        // calculate intersection point, slope must be different
        double intersectionX = (other.getFreePart() - this.freePart) / (this.slope - other.slope());
        double intersectionY = this.slope * intersectionX + this.freePart;
        return new Point(intersectionX, intersectionY);
    }


    /**
     * Checks whether this line segment is fully contained within another line segment.
     * In other words, both endpoints of this line must lie on the other line segment.
     *
     * @param other the line segment to check against
     * @return true if this line is fully contained within the other, false otherwise
     */
    public boolean isContainedIn(Line other) {
        return other.isPointOnSegment(this.start) && other.isPointOnSegment(this.end);
    }

    /**
     * Checks whether another line segment is fully contained within this line segment.
     * In other words, both endpoints of the other line must lie on this line segment.
     *
     * @param other the line segment to check
     * @return true if the other line is fully contained within this line, false otherwise
     */
    public boolean contains(Line other) {
        return this.isPointOnSegment(other.start) && this.isPointOnSegment(other.end);
    }

    /**
     * Checks whether a given point lies exactly on this line segment.
     *
     * @param p the point to check
     * @return true if the point lies on the segment (both on the infinite line and within bounds), false otherwise
     */
    public boolean isPointOnSegment(Point p) {
        // Check if the point lies on the infinite line defined by this segment
        boolean onLine;
        if (this.isVertical) {
            // For vertical lines, check X equality
            onLine = Utility.doubleEquals(p.getX(), this.start.getX());
        } else {
            // For non-vertical lines, calculate expected Y and compare
            double expectedY = this.slope * p.getX() + this.freePart;
            onLine = Utility.doubleEquals(expectedY, p.getY());
        }

        // Check if the point lies within the segment's bounding box
        boolean inBounds =
                Utility.doubleGE(p.getX(), Math.min(this.start.getX(), this.end.getX()))
                        && Utility.doubleLE(p.getX(), Math.max(this.start.getX(), this.end.getX()))
                        && Utility.doubleLE(p.getY(), Math.max(this.start.getY(), this.end.getY()))
                        && Utility.doubleGE(p.getY(), Math.min(this.start.getY(), this.end.getY()));

        return onLine && inBounds;
    }

    /**
     * Returns the closest intersection point between this line and a rectangle,
     * relative to the start point of this line.
     * If there are no intersection points, returns null.
     *
     * @param rect the rectangle to check intersections with
     * @return the closest intersection point to the start of the line, or null if there are none
     */
    public Point closestIntersectionToStartOfLine(Rectangle rect) {
        List<Point> intersections = rect.intersectionPoints(this);
        if (intersections.isEmpty()) {
            return null;
        }
        Point closest = intersections.get(0);
        double minDistance = closest.distance(this.start);
        for (Point point : intersections) {
            if (point.distance(this.start) < minDistance) {
                closest = point;
                minDistance = closest.distance(this.start);
            }
        }
        return closest;
    }

    /**
     * Returns the closest intersection point between this line and a given list of potential intersections,
     * relative to the start point of this line.
     * If there are no intersection points, returns null.
     *
     * @param intersections list of potential intersections.
     * @return the closest intersection collision info to the start of the line, or null if there are none
     */
    public CollisionInfo closestIntersectionToStartOfLine(List<CollisionInfo> intersections) {
        if (intersections.isEmpty()) {
            return null;
        }
        CollisionInfo closest = intersections.get(0);
        double minDistance = closest.collisionPoint().distance(this.start);

        for (CollisionInfo curCol : intersections) {
            if (curCol.collisionPoint().distance(this.start) < minDistance) {
                closest = curCol;
                minDistance = closest.collisionPoint().distance(this.start);
            }
        }
        return closest;
    }

    // Utils.Utility

    /**
     * Checks if this line equals another (same endpoints, order doesn't matter).
     *
     * @param other other line
     * @return true if equal
     */
    public boolean equals(Line other) {
        return (this.start.equals(other.start) && this.end.equals(other.end)
                || this.end.equals(other.start) && this.start.equals(other.end));
    }

    /**
     * @return deep copy of the line
     */
    public Line copy() {
        return new Line(this.start.copy(), this.end.copy());
    }


    /**
     * Returns a point located at a certain distance from a given point along the direction from start to end.
     * towards start.
     * The given point must lie on the current line segment.
     *
     * @param from     the point to start from
     * @param distance the distance to move along the line
     * @return a new point on the line at the specified distance from 'from', in the direction of starting point.
     */
    public Point pointAtDistance(Point from, double distance) {
        double dx = end.getX() - start.getX();
        double dy = end.getY() - start.getY();
        double length = Math.sqrt(dx * dx + dy * dy);

        double ux = dx / length;
        double uy = dy / length;

        double newX = from.getX() - ux * distance;
        double newY = from.getY() - uy * distance;

        return new Point(newX, newY);
    }


}
