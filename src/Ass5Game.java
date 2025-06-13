import Game.Game;
import Geometry.Ball;
import Geometry.Velocity;
import Game.Paddle;
import Game.Block;

import java.awt.Color;

/**
 * Name: Ido Itzhak
 * ID: 69420
 * class that holds the main methods, run it to enjoy the game.
 */
public class Ass5Game {

    /**
     * Initialize the game and run it.
     *
     * @param args doing absolutely nothing.
     */
    public static void main(String[] args) {
        Game game = new Game();
        game.initialize();
        addAss5Game(game);
        game.run();

    }

    private static void addAss5Game(Game g) {
        //background
        Color backgroundColor = Color.decode("#09b9f6");
        Block background = new Block(g.getBorderThickness(), g.getBorderThickness(),
                g.getGameWidth() - 2 * g.getBorderThickness(),
                g.getGameHeight() - g.getBorderThickness(), backgroundColor);
        g.addSprite(background);

        // blocks.
        Color[] colors = {
                Color.RED,
                Color.GREEN,
                Color.BLUE,
                Color.YELLOW,
                Color.ORANGE,
                Color.GRAY
        };
        for (int i = 0; i < 6; i++) {
            for (int j = i; j < 12; j++) {
                g.generateGameBlock(3 + j, 3 + i, colors[i]);
            }
        }

        //balls
        Ball gameBall = new Ball(300, 500, 5, Color.BLACK, new Velocity(0, -2));
        Ball gameBall2 = new Ball(300, 500, 5, Color.BLUE, new Velocity(1, -1));
        Ball gameBall3 = new Ball(300, 500, 5, Color.RED, new Velocity(-1, -1));
        g.addBall(gameBall);
        g.addBall(gameBall2);
        g.addBall(gameBall3);

        //Paddle
        Paddle paddle = new Paddle(new Block((double) g.getGameWidth() / 2, g.getGameHeight() - g.getBorderThickness() - 2,
                (double) g.getGameWidth() / 6, 2, Color.RED), g);
        paddle.addToGame(g);
    }
}
