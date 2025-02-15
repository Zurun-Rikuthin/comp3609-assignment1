package com.rikuthin.screen_panels;

import java.awt.Dimension;

import javax.swing.JPanel;

import com.rikuthin.GameFrame;

public abstract class ScreenPanel extends JPanel {

    protected final GameFrame gameFrame;

    protected ScreenPanel(GameFrame gameFrame) {
        this.gameFrame = gameFrame;
        Dimension panelSize = new Dimension(GameFrame.FRAME_WIDTH, GameFrame.FRAME_HEIGHT);

        setPreferredSize(panelSize);
        setMinimumSize(panelSize);
        setMaximumSize(panelSize);
    }    
}
