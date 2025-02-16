package com.rikuthin.screen_panels.gameplay_subpanels;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;

import com.rikuthin.GameFrame;
import com.rikuthin.GameManager;
import com.rikuthin.game_objects.Blaster;

public class BlasterPanel extends JPanel {

    private final GameManager gameManager;
    private final JLabel remainingBubblesCounterLabel;
    private final JPanel remainingBubblesPanel;
    private final JPanel heldBubblesPanel;
    private final Blaster blaster;

    private BubblePanel bubblePanel;

    public BlasterPanel(GameManager gameManager) {
        this.gameManager = gameManager;

        setBackground(new Color(159, 131, 131));
        setPreferredSize(new Dimension(GameFrame.FRAME_WIDTH, 110));
        setLayout(new BorderLayout());

        remainingBubblesPanel = new JPanel();
        remainingBubblesPanel.setLayout(new BoxLayout(remainingBubblesPanel, BoxLayout.Y_AXIS));

        remainingBubblesCounterLabel = new JLabel();

        heldBubblesPanel = new JPanel();
        add(remainingBubblesCounterLabel);

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
                new Color(2, 52, 54)
        );
    }

    public Blaster getBlaster() {
        return blaster;
    }


    public void updateRemainingBubblesCounter(final int value) {
        remainingBubblesCounterLabel.setText(String.format("Remaining Bubbles: %d", value));
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        blaster.draw(g2);
    }
}
