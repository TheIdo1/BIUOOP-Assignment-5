package Utils;

import java.awt.Color;
import java.util.Random;

/**
 * Utils.Utility class providing helper methods for common operations.
 * This class cannot be instantiated or extended.
 */
public final class Utility {
    //fields
    private static final double EPSILON = 0.0000001;

    /**
     * Private constructor to prevent instantiation.
     */
    private Utility() {
        // prevents creating instances
    }


    // double comparators

    /**
     * Compares two double values for equality within a small EPSILON tolerance.
     *
     * @param a the first double value
     * @param b the second double value
     * @return true if numbers are closer than 0.0000001.
     */
    public static boolean doubleEquals(double a, double b) {
        return Math.abs(a - b) < EPSILON;
    }

    /**
     * Returns true if 'a' is less than or equal to 'b', allowing a small tolerance
     * (EPSILON) for floating-point imprecision.
     *
     * @param a the first number to compare
     * @param b the second number to compare
     * @return true if x is less than or approximately equal to y, false otherwise
     */
    public static boolean doubleLE(double a, double b) {
        return a < b || Math.abs(a - b) < EPSILON;
    }

    /**
     * Returns true if 'a' is greater than or equal to 'b', allowing a small tolerance (EPSILON) for floating-point
     * imprecision.
     *
     * @param a the first number to compare
     * @param b the second number to compare
     * @return true if x is greater than or approximately equal to y, false otherwise
     */
    public static boolean doubleGE(double a, double b) {
        return a > b || Math.abs(a - b) < EPSILON;
    }


    // Randomizers

    /**
     * Generates a random color and return its object.
     *
     * @return random color object.
     */
    public static Color getRandomColor() {
        Random random = new Random();
        return new Color(random.nextInt(255), random.nextInt(255), random.nextInt(255));
    }

    public static Color getDarkerColor(Color c){
        double r = c.getRed() * 0.7;
        double g = c.getGreen() * 0.7;
        double b = c.getBlue() * 0.7;
        return new Color((int) r, (int) g, (int) b);
    }


    public static Color getBrighterColor(Color c){
        double r = c.getRed() + ((255-c.getRed()) * 0.3);
        double g = c.getGreen() + ((255-c.getGreen()) * 0.3);
        double b = c.getBlue() + ((255-c.getBlue()) * 0.3);
        return new Color((int) r, (int) g, (int) b);
    }
}
