package com.rikuthin.screen_panels.gameplay_subpanels;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;

import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class BlasterPanel extends JPanel {

    private final JLabel remainingBubblesCounterLabel;
    private final JPanel remainingBubblesPanel;
    private final JPanel heldBubblesPanel;

    private int remainingBubbles;

    public BlasterPanel() {
        remainingBubbles = -1;

        setBackground(new Color(159, 131, 131));
        setPreferredSize(new Dimension(580, 110));
        setLayout(new BorderLayout());

        remainingBubblesPanel = new JPanel();
        remainingBubblesPanel.setLayout(new BoxLayout(remainingBubblesPanel, BoxLayout.Y_AXIS));

        remainingBubblesCounterLabel = new JLabel();

        heldBubblesPanel = new JPanel();
        add(remainingBubblesCounterLabel);

        updateRemainingBubblesCounter(remainingBubbles);
    }

    private void updateRemainingBubblesCounter(final int newValue) {
        remainingBubblesCounterLabel.setText(String.format("Remaining Bubbles: %d", newValue));
        remainingBubbles = newValue;
    }
}
