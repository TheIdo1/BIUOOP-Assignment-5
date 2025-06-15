package Game;

import java.awt.Color;

public class BlockFactory {

    public static Block gameBlock(double x, double y, double width, double height, Color color) {
        Block newBlock = new Block(x, y, width, height, color);
        newBlock.setDrawDecorations(true);
        newBlock.setDrawOutline(true);
        newBlock.setDrawShades(true);
        return newBlock;
    }

    public static Block borderBlock(double x, double y, double width, double height, Color color) {
        Block newBlock = new Block(x, y, width, height, color);
        newBlock.setDrawDecorations(false);
        newBlock.setDrawOutline(true);
        newBlock.setDrawShades(false);
        return newBlock;
    }

    public static Block backgroundBlock(double x, double y, double width, double height, Color color) {
        Block newBlock = new Block(x, y, width, height, color);
        newBlock.setDrawDecorations(false);
        newBlock.setDrawOutline(false);
        newBlock.setDrawShades(false);
        return newBlock;
    }

    public static Block paddleBlock(double x, double y, double width, double height, Color color) {
        Block newBlock = new Block(x, y, width, height, color);
        newBlock.setDrawDecorations(false);
        newBlock.setDrawOutline(true);
        newBlock.setDrawShades(false);
        return newBlock;

    }
}
