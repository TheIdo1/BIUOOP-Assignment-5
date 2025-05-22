import Game.Game;

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
    }
}
