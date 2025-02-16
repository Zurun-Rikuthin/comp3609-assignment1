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

    private static GameManager instance;

    private Bubble activeBubble;
    private BlasterPanel blasterPanel;
    private BubblePanel bubblePanel;
    private OptionalInt remainingBubbles; // Explicitly track uninitialized state
    private boolean canShootBlaster;
    private boolean gameActive;

    private GameManager() {
        remainingBubbles = OptionalInt.empty(); // Indicates game hasn't started
        gameActive = false;
    }

    /**
     * Returns the singleton instance of the GameManager.
     *
     * @return The single instance of GameManager.
     */
    public static GameManager getInstance() {
        if (instance == null) {
            instance = new GameManager();
        }
        return instance;
    }

    /**
     * Returns the remaining number of bubbles left that the player can shoot.
     *
     * @return OptionalInt containing remaining bubbles, or empty if startGame()
     * hasn't been called.
     */
    public OptionalInt getRemainingBubbles() {
        return remainingBubbles;
    }

    public BlasterPanel getBlasterPanel() {
        return blasterPanel;
    }

    public BubblePanel getBubblePanel() {
        return bubblePanel;
    }

    public void setBlasterPanel(BlasterPanel blasterPanel) {
        this.blasterPanel = blasterPanel;
    }

    public void setBubblePanel(BubblePanel bubblePanel) {
        this.bubblePanel = bubblePanel;
    }

    /**
     * Starts a new game by resetting/initialising game values/objects.
     */
    public void startGame() {
        if (blasterPanel == null || bubblePanel == null) {
            throw new IllegalStateException("Error: Game cannot start. BlasterPanel and BubblePanel must be set first.");
        }
        
        remainingBubbles = OptionalInt.of(50);
        blasterPanel.updateRemainingBubblesCounter(50);
        gameActive = true;
    }

    /**
     * Shoots a bubble towards a target point. Reduces the number of remaining
     * bubbles if possible.
     *
     * @param target The point to shoot the bubble towards.
     */
    public void shootBubble(Point target) {
        if (!gameActive) {
            System.err.println("Error: Cannot shoot bubble. Game has not started.");
            return;
        }

        remainingBubbles.ifPresent(bubbleCount -> {
            if (bubbleCount > 0) {
                Blaster blaster = blasterPanel.getBlaster();
                Bubble newBubble = blaster.shootBubble(target, nextBubbleColour());
                bubblePanel.addBubble(newBubble);

                remainingBubbles = OptionalInt.of(bubbleCount - 1);
                blasterPanel.updateRemainingBubblesCounter(bubbleCount - 1);
            } else {
                System.err.println("Warning: No more bubbles left to shoot.");
            }
        });
    }

    /**
     * Chooses the next bubble's color randomly.
     *
     * @return The next BubbleColour.
     */
    private Color nextBubbleColour() {
        if (!gameActive) {
            return null;
        }
        return BubbleColour.getRandomColour();
    }
}
