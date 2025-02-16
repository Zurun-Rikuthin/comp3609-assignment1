package com.rikuthin.screen_panels.gameplay_subpanels;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;

import com.rikuthin.BubbleColour;
import com.rikuthin.GameFrame;
import com.rikuthin.game_objects.Blaster;

public class BlasterPanel extends JPanel {

    private final JLabel remainingBubblesCounterLabel;
    private final JPanel remainingBubblesPanel;
    private final JPanel heldBubblesPanel;
    private final JLabel mouseCoordsLabel;
    private final Blaster blaster;
    private final BubblePanel bubblePanel;

    private int remainingBubbles;

    public BlasterPanel(final BubblePanel bubblePanel) {
        this.bubblePanel = bubblePanel;
        remainingBubbles = 10;

        setBackground(new Color(159, 131, 131));
        setPreferredSize(new Dimension(GameFrame.FRAME_WIDTH, 110));
        setLayout(new BorderLayout());

        remainingBubblesPanel = new JPanel();
        remainingBubblesPanel.setLayout(new BoxLayout(remainingBubblesPanel, BoxLayout.Y_AXIS));

        remainingBubblesCounterLabel = new JLabel();

        heldBubblesPanel = new JPanel();
        add(remainingBubblesCounterLabel);

        mouseCoordsLabel = new JLabel("Mouse: ()");
        add(mouseCoordsLabel);

        // Blaster configuration
        final int shotSize = 30;
        final double shotSpeed = 10;
        final int blasterX = (GameFrame.FRAME_WIDTH / 2) - (shotSize / 2);
        final int blasterY = getPreferredSize().height - shotSize;

        blaster = new Blaster(
                blasterX,
                blasterY,
                shotSize,
                shotSpeed,
                new Color(2, 52, 54),
                bubblePanel
        );

        // Add mouse listener to shoot the blaster when clicked
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (bubblePanel != null) {
                    Point target = new Point(e.getX(), e.getY());
                    blaster.shootBubble(target, BubbleColour.getRandomColour());
                    remainingBubbles--;
                    updateRemainingBubblesCounter();
                    repaint();
                }
            }
        });
    }

    private void updateRemainingBubblesCounter() {
        remainingBubblesCounterLabel.setText(String.format("Remaining Bubbles: %d", remainingBubbles));
    }

    public void updateMouseCoords(int x, int y) {
        mouseCoordsLabel.setText(String.format("Mouse: (%d, %d)", x, y));
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        blaster.draw(g2);
    }
}
