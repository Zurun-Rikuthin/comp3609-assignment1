package com.rikuthin.screen_panels;

import javax.swing.BoxLayout;

import com.rikuthin.GameFrame;
import com.rikuthin.GameManager;
import com.rikuthin.screen_panels.gameplay_subpanels.BlasterPanel;
import com.rikuthin.screen_panels.gameplay_subpanels.BubblePanel;
import com.rikuthin.screen_panels.gameplay_subpanels.StatusPanel;

public final class GameplayScreenPanel extends ScreenPanel {

    private final StatusPanel statusPanel;
    private final BubblePanel bubblePanel;
    private final BlasterPanel blasterPanel;

    public GameplayScreenPanel(GameFrame gameFrame, GameManager gameManager) {
        super(gameFrame, gameManager);

        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        statusPanel = new StatusPanel();
        bubblePanel = new BubblePanel(this.gameManager);
        blasterPanel = new BlasterPanel(this.gameManager);

        bubblePanel.setBlasterPanel(blasterPanel);
        blasterPanel.setBubblePanel(bubblePanel);

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
