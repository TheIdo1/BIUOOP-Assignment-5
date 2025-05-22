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
    private final double borderThickness = 20;
    private final Counter remainingBlocks;
    private final Counter remainingBalls;
    private final Counter scoreCounter;
    private List<HitListener> blockHitListeners;
    private final List<Ball> gameBalls;

    //constructors

    /**
     * creates game object.
     */
    public Game() {
        sprites = new SpriteCollection();
        environment = new GameEnvironment();
        remainingBlocks = new Counter();
        remainingBalls = new Counter();
        scoreCounter = new Counter();
        gameBalls = new ArrayList<>();
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
     * Remove collidable from the game environment.
     *
     * @param c collidable to remove
     */
    void removeCollidable(Collidable c) {
        environment.removeCollidable(c);
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
     * Remove sprite from the game's sprites collection.
     *
     * @param s sprite to remove
     */
    public void removeSprite(Sprite s) {
        sprites.removeSprite(s);
    }


    /**
     * Initializing game, making gui, paddle, ball, and borders.
     */
    public void initialize() {

        this.gui = new GUI("GTA VI : Early Edition", gameWidth, gameHeight);
        this.sleeper = new Sleeper();
        //background
        Color backgroundColor = Color.decode("#09b9f6");
        Block background = new Block(0, 0, gameWidth, gameHeight, backgroundColor);
        sprites.addSprite(background);

        //balls
        Ball gameBall = new Ball(300, 500, 5, Color.BLACK, new Velocity(0, -2));
        Ball gameBall2 = new Ball(300, 500, 5, Color.BLUE, new Velocity(1, -1));
        Ball gameBall3 = new Ball(300, 500, 5, Color.RED, new Velocity(-1, -1));
        this.addBall(gameBall);
        this.addBall(gameBall2);
        this.addBall(gameBall3);

        //paddle
        Paddle paddle = new Paddle(new Block((double) gameWidth / 2, gameHeight - borderThickness - 2,
                (double) gameWidth / 6, 2, Color.RED), this);
        paddle.addToGame(this);

        //add borders
        List<Block> borders = new ArrayList<Block>();
        borders.add(new Block(0, 0, gameWidth, borderThickness, Color.GRAY)); //top
        borders.add(new Block(gameWidth - borderThickness, 0, borderThickness, gameHeight, Color.GRAY)); //right
        borders.add(new Block(0, 0, borderThickness, gameHeight, Color.GRAY)); //left

        // bottom border. is not visible, and if being touched will lead to loss.
        Block bottomBorder = new Block(0, gameHeight, gameWidth, borderThickness, backgroundColor);
        HitListener deathListener = new BallRemover(this, remainingBalls);
        bottomBorder.addHitListener(deathListener);
        borders.add(bottomBorder);
        for (Block b : borders) {
            addSprite(b);
            addCollidable(b);
        }

        // Game Block Listeners.
        blockHitListeners = new ArrayList<>();
        blockHitListeners.add(new BlockRemover(this, remainingBlocks));
        blockHitListeners.add(new ScoreTrackingListener(scoreCounter));


        // score indicator
        Sprite scoreIndicator = new ScoreIndicator(scoreCounter, this);
        this.addSprite(scoreIndicator);
    }

    /**
     * run the animation loop. game will start to play until user closes the game or wins.
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


            // win event
            if (remainingBlocks.getValue() == 0) {
                scoreCounter.increase(100);
                System.out.println("You Win!\nYour score is: " + scoreCounter.getValue());
                gui.close();
                return;
            }

            // lose event
            if (remainingBalls.getValue() == 0) {
                System.out.println("Game Over.\nYour score is: " + scoreCounter.getValue());
                gui.close();
                return;
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
        // sizing and positioning
        double innerWidth = gameWidth - 2 * borderThickness;
        double innerHeight = gameHeight - 2 * borderThickness;

        double blockWidth = innerWidth / 15;
        double blockHeight = innerHeight / 26;

        double newX = borderThickness + x * blockWidth;
        double newY = borderThickness + y * blockHeight;

        Block toAdd = new Block(newX, newY, blockWidth, blockHeight, color);

        // listeners
        for (HitListener hl : blockHitListeners) {
            toAdd.addHitListener(hl);
        }

        // add to environments
        this.addBlock(toAdd);
    }

    /**
     * Add block to game.
     *
     * @param b block to add to game.
     */
    public void addBlock(Block b) {
        remainingBlocks.increase(1);
        addCollidable(b);
        addSprite(b);
    }

    /**
     * Add ball to game.
     *
     * @param b ball to add
     */
    public void addBall(Ball b) {
        remainingBalls.increase(1);
        b.setGameEnvironment(this.environment);
        sprites.addSprite(b);
        gameBalls.add(b);
    }


}