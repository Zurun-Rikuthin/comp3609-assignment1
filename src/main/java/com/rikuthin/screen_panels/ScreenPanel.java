package com.rikuthin.screen_panels;

import java.awt.Dimension;

import javax.swing.JPanel;

import com.rikuthin.GameFrame;
import com.rikuthin.GameManager;

public abstract class ScreenPanel extends JPanel {

    protected final GameFrame gameFrame;
    protected final GameManager gameManager;

    protected ScreenPanel(GameFrame gameFrame, GameManager gameManager) {
        this.gameFrame = gameFrame;
        this.gameManager = gameManager;
        Dimension panelSize = new Dimension(GameFrame.FRAME_WIDTH, GameFrame.FRAME_HEIGHT);

        setPreferredSize(panelSize);
        setMinimumSize(panelSize);
        setMaximumSize(panelSize);
    }    
}
