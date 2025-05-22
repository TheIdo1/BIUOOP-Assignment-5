package Geometry;

import Game.CollisionInfo;
import Game.GameEnvironment;
import Game.Sprite;
import Game.Game;
import biuoop.DrawSurface;

import java.awt.Color;

/**
 * Represents a ball with a center point, radius (size), color, and velocity.
 * The ball can be drawn on a DrawSurface, move within a frame, bounce off walls,
 * and avoid entering a restricted inner rectangle.
 */
public class Ball implements Sprite {
    private Point center;
    private int size;
    private Color color;
    private Velocity velocity = new Velocity(0, 0);
    private GameEnvironment gameEnvironment;

    // ---------------- Constructors ----------------

    /**
     * Constructs a Geometry.Ball from a center point, radius, and color.
     *
     * @param center the center point of the ball
     * @param r      the radius of the ball
     * @param color  the color of the ball
     */
    public Ball(Point center, int r, Color color) {
        this.center = center.copy();
        this.size = Math.abs(r);
        this.color = color;
    }

    /**
     * Constructs a Geometry.Ball from x and y coordinates, radius, and color.
     *
     * @param x     the x-coordinate of the center
     * @param y     the y-coordinate of the center
     * @param r     the radius of the ball
     * @param color the color of the ball
     */
    public Ball(double x, double y, int r, Color color) {
        this(new Point(x, y), r, color);
    }


    /**
     * Constructs a new Geometry.Ball with a specified position, size, color, and velocity.
     *
     * @param x        the x-coordinate of the ball's center
     * @param y        the y-coordinate of the ball's center
     * @param r        the radius (size) of the ball
     * @param color    the color of the ball
     * @param velocity the velocity to assign to the ball (will be copied)
     */
    public Ball(double x, double y, int r, Color color, Velocity velocity) {
        this(x, y, r, color);
        this.velocity = velocity.copy();
    }

    /**
     * Constructs a new Geometry.Ball with a specified position, size, color,
     * and velocity defined by angle and speed.
     *
     * @param x      the x-coordinate of the ball's center
     * @param y      the y-coordinate of the ball's center
     * @param r      the radius (size) of the ball
     * @param color  the color of the ball
     * @param degree the angle of movement in degrees (0 is up, increasing clockwise)
     * @param speed  the speed of the ball (magnitude of velocity)
     */
    public Ball(double x, double y, int r, Color color, double degree, double speed) {
        this(x, y, r, color);
        this.velocity = Velocity.fromAngleAndSpeed(degree, speed);
    }


    // ---------------- Accessors ----------------

    /**
     * Returns a copy of the center point of the ball.
     *
     * @return a new Geometry.Point representing the center
     */
    public Point getCenter() {
        return center.copy();
    }

    /**
     * Returns the x-coordinate of the center.
     *
     * @return the x-coordinate as an int
     */
    public int getX() {
        return (int) center.getX();
    }

    /**
     * Returns the y-coordinate of the center.
     *
     * @return the y-coordinate as an int
     */
    public int getY() {
        return (int) center.getY();
    }

    /**
     * Returns the radius (size) of the ball.
     *
     * @return the size
     */
    public int getSize() {
        return size;
    }

    /**
     * Returns the color of the ball.
     *
     * @return the color
     */
    public Color getColor() {
        return color;
    }

    /**
     * Returns the current velocity of the ball.
     *
     * @return the velocity
     */
    public Velocity getVelocity() {
        return velocity;
    }

    /**
     * Sets the center point of the ball.
     *
     * @param center the new center point
     */
    public void setCenter(Point center) {
        this.center = center;
    }

    /**
     * Sets the x-coordinate of the center.
     *
     * @param x the new x-coordinate
     */
    public void setX(double x) {
        this.center.setX(x);
    }

    /**
     * Sets the y-coordinate of the center.
     *
     * @param y the new y-coordinate
     */
    public void setY(double y) {
        this.center.setY(y);
    }

    /**
     * Sets the radius (size) of the ball.
     *
     * @param size the new size
     */
    public void setSize(int size) {
        this.size = size;
    }

    /**
     * Sets the color of the ball.
     *
     * @param color the new color
     */
    public void setColor(Color color) {
        this.color = color;
    }

    /**
     * Sets the velocity of the ball.
     *
     * @param v the new velocity
     */
    public void setVelocity(Velocity v) {
        this.velocity = v;
    }

    /**
     * Sets the velocity of the ball using dx and dy values.
     *
     * @param dx the change in x per step
     * @param dy the change in y per step
     */
    public void setVelocity(double dx, double dy) {
        this.velocity = new Velocity(dx, dy);
    }

    /**
     * Sets the gameEnvironment of the ball (not a copy).
     *
     * @param gameEnvironment gameEnvironment to set.
     */
    public void setGameEnvironment(GameEnvironment gameEnvironment) {
        this.gameEnvironment = gameEnvironment;
    }

    // ---------------- Drawing & Time Behavior ----------------

    /**
     * Draws the ball on the provided DrawSurface.
     *
     * @param surface the surface to draw on
     */
    public void drawOn(DrawSurface surface) {
        if (surface == null) {
            return;
        }
        surface.setColor(this.color);
        Point renderPoint = this.center;
        surface.fillCircle((int) renderPoint.getX(), (int) renderPoint.getY(), this.size);
    }

    /**
     * Notify the ball that time has passed, move it a step.
     */
    public void timePassed() {
        this.moveOneStep();
    }

    // ---------------- Movement ----------------

    /**
     * Moves the ball one step arbitrary to collision in gameEnvironment.
     */
    public void moveOneStep() {
        Point curPosition = this.getCenter();
        Line trajectory = new Line(curPosition, this.velocity.applyToPoint(curPosition));
        CollisionInfo closestCollision = gameEnvironment.getClosestCollision(trajectory);
        if (closestCollision == null) {
            this.setCenter(this.velocity.applyToPoint(curPosition));
            return;
        }
        // move Geometry.Ball slightly before hitting the wall and update velocity.


        this.setCenter(trajectory.pointAtDistance(closestCollision.collisionPoint(), 0.1));
        this.setVelocity(closestCollision.collisionObject().hit(this, closestCollision.collisionPoint(),
                this.velocity));
    }


    // ---------------- Game Behavior ----------------

    /**
     * Removes ball from the game.
     * @param game game that is being played
     */
    public void removeFromGame(Game game) {
        game.removeSprite(this);
        this.setGameEnvironment(null);
    }
}
