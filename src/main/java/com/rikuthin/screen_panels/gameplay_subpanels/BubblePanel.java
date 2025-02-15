package com.rikuthin.screen_panels.gameplay_subpanels;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;

import javax.swing.JPanel;

import com.rikuthin.Bearing2D;
import com.rikuthin.GameFrame;
import com.rikuthin.game_objects.Bubble;

public class BubblePanel extends JPanel {

    private final Bubble bubble; // The bubble instance that will be added to the panel
    private BlasterPanel blasterPanel;

    public BubblePanel() {
        setBackground(new Color(200, 170, 170));
        setPreferredSize(new Dimension(GameFrame.FRAME_WIDTH, 590));
        
        // Initialize a bubble instance, example values for position, size, color
        bubble = new Bubble(300, 300, 50, Color.BLUE, this);
        bubble.setBearing(new Bearing2D(0));
        bubble.setPixelsPerTick(1);
        bubble.setIsMoving(true);

        // Add MouseMotionListener
        addMouseMotionListener(new MouseMotionListener() {
            @Override
            public void mouseMoved(MouseEvent e) {
                if (blasterPanel != null) {
                    blasterPanel.updateMouseCoords(e.getX(), e.getY());
                }
            }

            @Override
            public void mouseDragged(MouseEvent e) {
                // Not needed for now, but included for completeness
            }
        });

        startBubbleMovement();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        if (bubble != null) {
            bubble.draw((Graphics2D) g);
        }
    }

    public void setBlasterPanel (BlasterPanel blasterPanel) {
        this.blasterPanel = blasterPanel;
    }

    // Method to start the bubble movement (if needed)
    public final void startBubbleMovement() {
        Thread bubbleThread = new Thread(bubble);
        bubbleThread.start();
    }
}
