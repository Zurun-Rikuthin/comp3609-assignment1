package com.rikuthin;

import java.awt.Color;
import java.awt.Point;
import java.util.OptionalInt;

import com.rikuthin.game_objects.Blaster;
import com.rikuthin.game_objects.Bubble;
import com.rikuthin.screen_panels.gameplay_subpanels.BlasterPanel;
import com.rikuthin.screen_panels.gameplay_subpanels.BubblePanel;
import com.rikuthin.utility.BubbleColour;

public class GameManager {
    private final BlasterPanel blasterPanel;
    private final BubblePanel bubblePanel;
    
    private OptionalInt remainingBubbles; // Explicitly track uninitialized state

    public GameManager(BlasterPanel blasterPanel, BubblePanel bubblePanel) {
        this.blasterPanel = blasterPanel;
        this.bubblePanel = bubblePanel;
        this.remainingBubbles = OptionalInt.empty(); // Indicates game hasn't started
    }

    /**
     * Returns the remaining number of bubbles left that the player can shoot.
     * @return OptionalInt containing remaining bubbles, or empty if startGame() hasn't been called.
     */
    public OptionalInt getRemainingBubbles() {
        return remainingBubbles;
    }
    
    /**
     * Starts a new game by initializing the bubble count.
     * If the game is already running, it does nothing.
     */
    public void startGame() {
        if (remainingBubbles.isEmpty()) {
            remainingBubbles = OptionalInt.of(50);
            blasterPanel.updateRemainingBubblesCounter(50);
        } else {
            System.err.println("Warning: Attempted to start a game that is already running.");
        }
    }

    /**
     * Shoots a bubble towards a target point.
     * Reduces the number of remaining bubbles if possible.
     * 
     * @param target The point to shoot the bubble towards.
     */
    public void shootBubble(Point target) {
        if (remainingBubbles.isEmpty()) {
            System.err.println("Error: Cannot shoot bubble. Game has not started.");
            return;
        }

        if (remainingBubbles.getAsInt() > 0) {
            Blaster blaster = blasterPanel.getBlaster();
            Bubble newBubble = blaster.shootBubble(target, nextBubbleColour().getColour());
            bubblePanel.addBubble(newBubble);

            remainingBubbles = OptionalInt.of(remainingBubbles.getAsInt() - 1);
            blasterPanel.updateRemainingBubblesCounter(remainingBubbles.getAsInt());
        } else {
            System.err.println("Warning: No more bubbles left to shoot.");
        }
    }

    /**
     * Chooses the next bubble's color randomly.
     * @return The next BubbleColour.
     */
    private BubbleColour nextBubbleColour() {
        return BubbleColour.getRandomColourEnum();
    }
}
