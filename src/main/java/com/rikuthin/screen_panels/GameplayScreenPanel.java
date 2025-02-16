package com.rikuthin.screen_panels;

import javax.swing.BoxLayout;

import com.rikuthin.GameFrame;
import com.rikuthin.screen_panels.gameplay_subpanels.BlasterPanel;
import com.rikuthin.screen_panels.gameplay_subpanels.BubblePanel;
import com.rikuthin.screen_panels.gameplay_subpanels.StatusPanel;

public final class GameplayScreenPanel extends ScreenPanel {

    private final StatusPanel statusPanel;
    private final BubblePanel bubblePanel;
    private final BlasterPanel blasterPanel;

    public GameplayScreenPanel(GameFrame gameFrame) {
        super(gameFrame);
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        statusPanel = new StatusPanel();
        bubblePanel = new BubblePanel();
        blasterPanel = new BlasterPanel(bubblePanel);
        
        bubblePanel.setBlasterPanel(blasterPanel);

        add(statusPanel);
        add(bubblePanel);
        add(blasterPanel);
    }

    public StatusPanel getStatusPanel() {
        return statusPanel;
    }

    public BubblePanel getBubblePanel() {
        return bubblePanel;
    }

    public BlasterPanel getBlasterPanel() {
        return blasterPanel;
    }
}
