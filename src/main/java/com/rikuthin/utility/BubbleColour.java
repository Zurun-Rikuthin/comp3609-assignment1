package com.rikuthin.utility;

import java.awt.Color;
import java.util.concurrent.ThreadLocalRandom;

public enum BubbleColour {
    MURRAY_RED(new Color(87, 0, 58)),
    PANTONE_ORANGE(new Color(242, 100, 25)),
    HUNYADI_YELLOW(new Color(246, 174, 45)),
    TEA_GREEN(new Color(200, 216, 175)),
    LAPIS_LAZULI_BLUE(new Color(51, 101, 138)),
    ULTRAVIOLET(new Color(77, 83, 130)),
    MAUVINE_PURPLE(new Color(144, 4, 159));

    private static final BubbleColour[] VALUES = values(); // Cache for efficiency
    private static final int SIZE = VALUES.length;

    private final Color colour;

    BubbleColour(Color colour) {
        this.colour = colour;
    }

    public Color getColour() {
        return colour;
    }

    public static BubbleColour getRandomBubbleColour() {
        return VALUES[ThreadLocalRandom.current().nextInt(SIZE)];
    }

    public static Color getRandomColour() {
        return getRandomBubbleColour().getColour();
    }
}
