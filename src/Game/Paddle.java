package Game;

import Geometry.Ball;
import Geometry.Point;
import Geometry.Rectangle;
import Geometry.Velocity;
import biuoop.DrawSurface;
import biuoop.KeyboardSensor;

/**
 * Game.Paddle class for the game. paddle is very fun.
 */
public class Paddle implements Sprite, Collidable {
    //fields
    private biuoop.KeyboardSensor keyboard;
    private final Block delegator;
    private final Game game;
    private final int speed = 5;

    // constructors

    /**
     * Construct paddle with given block and game instance.
     *
     * @param game      game instance that paddle will be placed in.
     * @param delegator block object that will represent the paddle.
     */
    public Paddle(Block delegator, Game game) {
        this.delegator = delegator;
        this.game = game;
        keyboard = game.getGui().getKeyboardSensor();
    }


    /**
     * Move the paddle to the left. motion is circular.
     */
    public void moveLeft() {
        double curX = this.delegator.getUpperLeft().getX();
        double curY = this.delegator.getUpperLeft().getY();
        double paddleWidth = this.delegator.getWidth();
        int gameWidth = this.game.getGameWidth();

        if (curX + paddleWidth - speed <= 0) {
            this.delegator.setUpperLeft(gameWidth - speed, curY);
        } else {
            this.delegator.setUpperLeft(curX - speed, curY);
        }
    }

    /**
     * Move the paddle to the right. motion is circular.
     */
    public void moveRight() {
        double curX = this.delegator.getUpperLeft().getX();
        double curY = this.delegator.getUpperLeft().getY();
        double paddleWidth = this.delegator.getWidth();
        int gameWidth = this.game.getGameWidth();

        if (curX + speed >= gameWidth) {
            this.delegator.setUpperLeft(-paddleWidth + speed, curY);
        } else {
            this.delegator.setUpperLeft(curX + speed, curY);
        }
    }

    // Game.Sprite

    /**
     * tell to object to think what he should do next frame, and do it.
     */
    public void timePassed() {
        if (keyboard.isPressed(KeyboardSensor.LEFT_KEY)) {
            moveLeft();
        }
        if (keyboard.isPressed(KeyboardSensor.RIGHT_KEY)) {
            moveRight();
        }
    }

    /**
     * Draw puddle on draw surface.
     *
     * @param d drawSurface to draw on.
     */
    public void drawOn(DrawSurface d) {
        delegator.drawOn(d);
    }

    // Game.Collidable

    /**
     * get the rectangle object that represents the paddle.
     *
     * @return rectangle object of paddle.
     */
    public Rectangle getCollisionRectangle() {
        return delegator.getCollisionRectangle();
    }

    /**
     * calculates the return velocity of given object after hitting the paddle.
     *
     * @param hitter ball that is hitting the paddle.
     * @param collisionPoint  point of collision.
     * @param currentVelocity velocity before hit.
     * @return expected velocity after hit.
     */
    public Velocity hit(Ball hitter, Point collisionPoint, Velocity currentVelocity) {
        //calculate in what segment the ball hit
        double relativeX = collisionPoint.getX() - this.delegator.getUpperLeft().getX();
        int segment = (int) Math.floor((relativeX / this.delegator.getWidth()) * 5);
        double currentSpeed = currentVelocity.getSpeed();
        switch (segment) {
            case 0:
                return Velocity.fromAngleAndSpeed(120, -currentSpeed);
            case 1:
                return Velocity.fromAngleAndSpeed(150, -currentSpeed);
            case 2:
                return Velocity.fromAngleAndSpeed(180, -currentSpeed);
            case 3:
                return Velocity.fromAngleAndSpeed(210, -currentSpeed);
            case 4:
                return Velocity.fromAngleAndSpeed(240, -currentSpeed);
            default:
                return currentVelocity;
        }

    }

    // Add this paddle to the game.

    /**
     * Adds the paddle to the game.
     *
     * @param g current game.
     */
    public void addToGame(Game g) {
        g.addSprite(this);
        g.addCollidable(this);
    }
}
