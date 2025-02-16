package com.rikuthin;

import java.awt.Color;
import java.awt.Point;

import com.rikuthin.game_objects.Blaster;
import com.rikuthin.game_objects.Bubble;
import com.rikuthin.screen_panels.gameplay_subpanels.BlasterPanel;
import com.rikuthin.screen_panels.gameplay_subpanels.BubblePanel;
import com.rikuthin.utility.BubbleColour;

public class GameManager {
    private final BlasterPanel blasterPanel;
    private final BubblePanel bubblePanel;

    private int remainingBubbles;

    public GameManager(GameFrame gameFrame) {
        this.blasterPanel = gameFrame.getGameplayScreenPanel().getBlasterPanel();
        this.bubblePanel = gameFrame.getGameplayScreenPanel().getBubblePanel();
        this.remainingBubbles = 10;  // Initial number of bubbles
    }

    public int getRemainingBubbles() {
        return remainingBubbles;
    }
    
    public void startGame() {
        // Initialize game state, start game loop, etc.
        remainingBubbles = 50;
        blasterPanel.updateRemainingBubblesCounter(remainingBubbles);
    }

    public void shootBubble(Point target) {
        if (remainingBubbles > 0) {
            remainingBubbles--;

            Blaster blaster = blasterPanel.getBlaster();
            Bubble newBubble = blaster.shootBubble(target, nextBubbleColour());

            bubblePanel.addBubble(newBubble);
            blasterPanel.updateRemainingBubblesCounter(remainingBubbles);
        }
    }

    private Color nextBubbleColour() {
        return BubbleColour.getRandomColour();
    }
}
