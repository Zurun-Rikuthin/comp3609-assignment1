package com.rikuthin.screen_panels.gameplay_subpanels;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JPanel;

import com.rikuthin.Bearing2D;
import com.rikuthin.game_objects.Bubble;

public class BubblePanel extends JPanel {

    private Bubble bubble; // The bubble instance that will be added to the panel

    public BubblePanel() {
        setBackground(new Color(200, 170, 170));
        setPreferredSize(new Dimension(580, 590));
        
        // Initialize a bubble instance, example values for position, size, color
        bubble = new Bubble(100, 100, 50, Color.BLUE, this);
        bubble.setBearing(new Bearing2D(30));
        bubble.setPixelsPerTick(10);
        bubble.setIsMoving(true);

        startBubbleMovement();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g); // Always call the superclass method

        if (bubble != null) {
            bubble.draw((Graphics2D) g); // Draw the bubble onto the panel
        }
    }

    // Method to start the bubble movement (if needed)
    public final void startBubbleMovement() {
        Thread bubbleThread = new Thread(bubble);
        bubbleThread.start();
    }
}
