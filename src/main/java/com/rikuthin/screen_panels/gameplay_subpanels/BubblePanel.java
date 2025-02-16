package com.rikuthin.screen_panels.gameplay_subpanels;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JLabel;
import javax.swing.JPanel;

import com.rikuthin.GameFrame;
import com.rikuthin.GameManager;
import com.rikuthin.game_objects.Bubble;

/**
 * The BubblePanel is responsible for displaying the bubbles in the game and
 * handling user interaction.
 * <p>
 * This panel allows the player to shoot bubbles by clicking on the screen, and
 * it manages the rendering of bubbles in the gameplay area.
 * </p>
 */
public class BubblePanel extends JPanel {

    /**
     * A list of bubbles contained within the panel
     */
    private final List<Bubble> bubbles;
    private final JLabel mouseLocationLabel;
    /**
     * Constructs the BubblePanel, initializing the list of bubbles and setting
     * up the background and mouse listener for bubble shooting.
     */
    public BubblePanel() {
        this.bubbles = new ArrayList<>();

        // Set panel background color and preferred size.
        setBackground(new Color(200, 170, 170));
        setPreferredSize(new Dimension(GameFrame.FRAME_WIDTH, 590));

        // Create and initialize the label to display mouse coordinates
        mouseLocationLabel = new JLabel();
        mouseLocationLabel.setBounds(10, 10, 200, 30);  // Set position of the label
        this.add(mouseLocationLabel);

        // Add the MouseMotionListener directly to the panel to track mouse movement
        this.addMouseMotionListener(new MouseMotionListener() {
            @Override
            public void mouseMoved(MouseEvent e) {
                // Get the mouse position
                int mouseX = e.getX();
                int mouseY = e.getY();

                // Update the label text with the new mouse coordinates
                mouseLocationLabel.setText("Mouse Location: (" + mouseX + ", " + mouseY + ")");
            }

            @Override
            public void mouseDragged(MouseEvent e) {
                // Optional: handle mouse dragging (not needed for this task)
            }
        });

        // Mouse click listener to shoot bubbles when clicked.
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (isVisible()) {
                    Point target = new Point(e.getX(), e.getY());
                    GameManager.getInstance().shootBubble(target);
                    repaint(); // Repaint the panel to update bubble positions.
                }
            }
        });
    }

    /**
     * Adds a bubble to the panel and triggers a repaint to display it.
     *
     * @param bubble The bubble to add to the panel.
     */
    public void addBubble(Bubble bubble) {
        bubbles.add(bubble);
        repaint(); // Repaint to show the new bubble.
    }

    /**
     * Paints the component by rendering all the bubbles in the list. This
     * method is automatically called by the Swing framework when the panel
     * needs to be redrawn.
     *
     * @param g The graphics context used for drawing.
     */
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;

        // Draw each bubble in the list.
        for (Bubble bubble : bubbles) {
            bubble.draw(g2);
        }
    }
}
