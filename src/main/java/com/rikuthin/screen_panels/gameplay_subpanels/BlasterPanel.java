package com.rikuthin.screen_panels.gameplay_subpanels;

import java.awt.Color;
import java.awt.Dimension;

import javax.swing.JLabel;
import javax.swing.JPanel;

public class BlasterPanel extends JPanel {

    private final JLabel remainingBubblesCounterLabel;

    private int remainingBubbles;

    public BlasterPanel() {
        remainingBubbles = 0;

        setBackground(new Color(159, 131, 131));
        setPreferredSize(new Dimension(580, 110));

        remainingBubblesCounterLabel = new JLabel("Remaining Bubbles: N/A");

        add(remainingBubblesCounterLabel);
    }
}
