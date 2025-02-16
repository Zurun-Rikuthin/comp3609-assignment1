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

    private BlasterPanel blasterPanel;
    private BubblePanel bubblePanel;

    private OptionalInt remainingBubbles; // Explicitly track uninitialized state

    private GameManager() {
        this.remainingBubbles = OptionalInt.empty(); // Indicates game hasn't started
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
     * Starts a new game by initializing the bubble count. If the game is
     * already running, it does nothing.
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
     * Resets the game to its initial state, allowing the player to start again.
     * If the game is already in progress, it will be reset.
     */
    public void resetGame() {
        remainingBubbles = OptionalInt.of(50);
        blasterPanel.updateRemainingBubblesCounter(50);
    }

    /**
     * Shoots a bubble towards a target point. Reduces the number of remaining
     * bubbles if possible.
     *
     * @param target The point to shoot the bubble towards.
     */
    public void shootBubble(Point target) {
        if (remainingBubbles.isEmpty()) {
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
        return BubbleColour.getRandomColour();
    }
}
