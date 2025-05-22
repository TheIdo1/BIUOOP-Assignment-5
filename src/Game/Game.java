package Game;

import Geometry.Ball;
import Geometry.Velocity;
import biuoop.DrawSurface;
import biuoop.GUI;
import biuoop.Sleeper;

import java.awt.Color;
import java.util.List;
import java.util.ArrayList;

/**
 * a class that is responsible for the game program, creates ball, paddle, and the sandbox for the level.
 */
public class Game {
    // fields
    private final SpriteCollection sprites;
    private final GameEnvironment environment;
    private GUI gui;
    private Sleeper sleeper;
    private int gameWidth = 800;
    private int gameHeight = 600;

    //constructors

    /**
     * creates game object.
     */
    public Game() {
        sprites = new SpriteCollection();
        environment = new GameEnvironment();
    }

    // Getters & Setters

    /**
     * Returns the width of the game screen.
     *
     * @return the width of the game in pixels
     */
    public int getGameWidth() {
        return gameWidth;
    }

    /**
     * Sets the width of the game screen.
     *
     * @param gameWidth the width to set, in pixels
     */
    public void setGameWidth(int gameWidth) {
        this.gameWidth = gameWidth;
    }

    /**
     * Returns the height of the game screen.
     *
     * @return the height of the game in pixels
     */
    public int getGameHeight() {
        return gameHeight;
    }

    /**
     * Sets the height of the game screen.
     *
     * @param gameHeight the height to set, in pixels
     */
    public void setGameHeight(int gameHeight) {
        this.gameHeight = gameHeight;
    }

    /**
     * get current game's GUI.
     *
     * @return GUI object of current game.
     */
    public GUI getGui() {
        return this.gui;
    }


    //Methods

    /**
     * Add collidable to the game environment.
     *
     * @param c collidable to add
     */
    public void addCollidable(Collidable c) {
        environment.addCollidable(c);
    }

    /**
     * Add sprite to the game's sprites collection.
     *
     * @param s sprite to add
     */
    public void addSprite(Sprite s) {
        sprites.addSprite(s);
    }


    /**
     * Initializing game, making gui, paddle, ball, and borders.
     */
    public void initialize() {

        this.gui = new GUI("GTA VI : Early Edition", gameWidth, gameHeight);
        this.sleeper = new Sleeper();
        Ball gameBall = new Ball(300, 500, 5, Color.BLACK, new Velocity(0, -2));
        gameBall.setGameEnvironment(this.environment);
        sprites.addSprite(gameBall);
        Ball gameBall2 = new Ball(300, 500, 5, Color.BLUE, new Velocity(0, -1));
        gameBall2.setGameEnvironment(this.environment);
        sprites.addSprite(gameBall2);
        Paddle paddle = new Paddle(new Block((double) gameWidth / 2, gameHeight - 3,
                (double) gameWidth / 6, 2, Color.RED), this);
        paddle.addToGame(this);
        List<Block> borders = new ArrayList<Block>();
        borders.add(new Block(0, -5, gameWidth, 5, Color.BLUE)); //top
        borders.add(new Block(gameWidth, 0, 5, gameHeight, Color.BLUE)); //right
        borders.add(new Block(0, gameHeight, gameWidth, 5, Color.BLUE)); //bot
        borders.add(new Block(-5, 0, 5, gameHeight, Color.BLUE)); //left
        for (Block b : borders) {
            addSprite(b);
            addCollidable(b);
        }
    }

    /**
     * run the animation loop. game will start to play untill user closes it.
     */
    public void run() {

        int framesPerSecond = 60;
        int millisecondsPerFrame = 1000 / framesPerSecond;
        while (true) {
            long startTime = System.currentTimeMillis(); // timing

            DrawSurface d = gui.getDrawSurface();
            this.sprites.drawAllOn(d);
            gui.show(d);
            this.sprites.notifyAllTimePassed();

            // timing
            long usedTime = System.currentTimeMillis() - startTime;
            long milliSecondLeftToSleep = millisecondsPerFrame - usedTime;
            if (milliSecondLeftToSleep > 0) {
                sleeper.sleepFor(milliSecondLeftToSleep);
            }
        }
    }

    /**
     * Generate and add to game a game-block. referring to game as board of 15x26 rectangles.
     *
     * @param x     x coordinate starting from 0.
     * @param y     y coordinate starting from 0.
     * @param color color of the block.
     */
    public void generateGameBlock(int x, int y, Color color) {
        double newX = (double) (gameWidth / 15) * x;
        double newY = (double) (gameHeight / 26) * y;
        Block toAdd = new Block(newX, newY, (double) gameWidth / 15, (double) gameHeight / 26, color);
        this.addCollidable(toAdd);
        this.addSprite(toAdd);
    }

}