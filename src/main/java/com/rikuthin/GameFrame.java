package com.rikuthin;

import java.awt.CardLayout;

import javax.swing.JFrame;
import javax.swing.WindowConstants;

import com.rikuthin.screen_panels.GameplayScreenPanel;
import com.rikuthin.screen_panels.MainMenuScreenPanel;
import com.rikuthin.screen_panels.gameplay_subpanels.StatusPanel;

public final class GameFrame extends JFrame {

    public static final int FRAME_WIDTH = 600;
    public static final int FRAME_HEIGHT = 800;

    public static final String MAIN_MENU_PANEL_NAME = "Main Menu";
    public static final String GAMEPLAY_PANEL_NAME = "Gameplay";
    public static final String BODY_TYPEFACE = "Cooper Black";

    private final MainMenuScreenPanel mainMenuScreenPanel;
    private final GameplayScreenPanel gameplayScreenPanel;
    private final CardLayout cardLayout;

    public GameFrame() {
        setTitle("Untitled Bubble Shooter");
        setSize(FRAME_WIDTH, FRAME_HEIGHT);
        setResizable(false);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        cardLayout = new CardLayout();
        setLayout(cardLayout);

        mainMenuScreenPanel = new MainMenuScreenPanel(this);
        gameplayScreenPanel = new GameplayScreenPanel(this);

        add(mainMenuScreenPanel, MAIN_MENU_PANEL_NAME);
        add(gameplayScreenPanel, GAMEPLAY_PANEL_NAME);

        switchToPanel(MAIN_MENU_PANEL_NAME);
        setVisible(true);
    }

    public void switchToPanel(final String panelName) {
        cardLayout.show(getContentPane(), panelName);
    }

    public boolean isGameplayActive() {
        return gameplayScreenPanel.isVisible();
    }

    public boolean isMainMenuActive() {
        return mainMenuScreenPanel.isVisible();
    }

    public StatusPanel getStatusPanel() {
        if (isGameplayActive()) {
            return gameplayScreenPanel.getStatusPanel();
        }
        return null;
    }
    
}
