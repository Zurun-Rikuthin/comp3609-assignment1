package com.rikuthin;

import java.awt.Color;
import java.awt.Point;

import com.rikuthin.game_objects.Blaster;
import com.rikuthin.game_objects.Bubble;
import com.rikuthin.screen_panels.gameplay_subpanels.BlasterPanel;
import com.rikuthin.screen_panels.gameplay_subpanels.BubblePanel;
import com.rikuthin.utility.RandomColour;

public class GameManager {

    private static GameManager instance;

    private BlasterPanel blasterPanel;
    private BubblePanel bubblePanel;
    private int remainingBubbles;
    private boolean canShootBlaster;
    private boolean gameActive;

    private GameManager() {
        remainingBubbles = 0;
        gameActive = false;
        canShootBlaster = false;
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
     * @return The remaining number of bubbles
     */
    public int getRemainingBubbles() {
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

        remainingBubbles = 100;
        blasterPanel.updateRemainingBubblesCounter(remainingBubbles);
        bubblePanel.initialiseWalls();
        gameActive = true;
        canShootBlaster = true;
    }

    /**
     * Shoots a bubble towards a target point. Reduces the number of remaining
     * bubbles if possible.
     *
     * @param target The point to shoot the bubble towards.
     */
    public void shootBubble(Point target) {
        if (!gameActive) {
            throw new IllegalStateException("Error: Cannot shoot bubble. Game has not started.");
        }
        
        if (remainingBubbles > 0) {
            if(canShootBlaster) {
                canShootBlaster = false;

                Blaster blaster = blasterPanel.getBlaster();
                Bubble newBubble = blaster.shootBubble(target, nextRandomColour());
                
                bubblePanel.addBubble(newBubble);
                
                remainingBubbles--;
                blasterPanel.updateRemainingBubblesCounter(remainingBubbles);
            } else {
                System.err.println("Warning: Bubble already fired. Wait for it to stop moving.");
            }
        } else {
            System.err.println("Warning: No more bubbles left to shoot.");
        }
    }

    public void onBubbleMovementComplete() {
        canShootBlaster = true;
    }

    /**
     * Chooses the next bubble's color randomly.
     *
     * @return The next random colour.
     */
    private Color nextRandomColour() {
        if (!gameActive) {
            throw new IllegalStateException("Cannot select color when game is not active.");
        }
        return RandomColour.getRandomColour();
    }
    
}
