package com.rikuthin.utility;

import java.awt.Color;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Represents a set of predefined bubble colours for the game.
 */
public enum BubbleColour {
    MURRAY_RED(new Color(87, 0, 58)),
    PANTONE_ORANGE(new Color(242, 100, 25)),
    HUNYADI_YELLOW(new Color(246, 174, 45)),
    TEA_GREEN(new Color(200, 216, 175)),
    LAPIS_LAZULI_BLUE(new Color(51, 101, 138)),
    ULTRAVIOLET(new Color(77, 83, 130)),
    MAUVINE_PURPLE(new Color(144, 4, 159));

    private static final BubbleColour[] VALUES = values(); // Cached values for efficiency
    private static final int SIZE = VALUES.length;

    private final Color colour;

    /**
     * Constructs a BubbleColour enum with a specific colour.
     *
     * @param colour The colour associated with this BubbleColour.
     */
    BubbleColour(Color colour) {
        this.colour = colour;
    }

    /**
     * Returns the colour associated with this BubbleColour.
     *
     * @return The colour of this bubble.
     */
    public Color getColour() {
        return colour;
    }

    /**
     * Returns a randomly selected BubbleColour.
     *
     * @return A random BubbleColour.
     */
    public static BubbleColour getRandomBubbleColour() {
        return VALUES[ThreadLocalRandom.current().nextInt(SIZE)];
    }

    /**
     * Returns a randomly selected colour from the available BubbleColours.
     *
     * @return A random colour.
     */
    public static Color getRandomColour() {
        return getRandomBubbleColour().getColour();
    }
}
