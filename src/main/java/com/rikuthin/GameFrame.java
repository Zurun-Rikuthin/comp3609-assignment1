package com.rikuthin;

import java.awt.CardLayout;

import javax.swing.JFrame;
import javax.swing.WindowConstants;

import com.rikuthin.screen_panels.GameplayScreenPanel;
import com.rikuthin.screen_panels.MainMenuScreenPanel;

public final class GameFrame extends JFrame {

    public enum PanelName {
        MAIN_MENU,
        GAMEPLAY
    }

    public static final int FRAME_WIDTH = 600;
    public static final int FRAME_HEIGHT = 800;

    public static final String BODY_TYPEFACE = "Cooper Black";

    private final GameManager gameManager;
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

        gameManager = GameManager.getInstance();
        gameManager.setBlasterPanel(gameplayScreenPanel.getBlasterPanel());
        gameManager.setBubblePanel(gameplayScreenPanel.getBubblePanel());

        add(mainMenuScreenPanel, PanelName.MAIN_MENU.name());
        add(gameplayScreenPanel, PanelName.GAMEPLAY.name());

        switchToPanel(PanelName.MAIN_MENU);
        setVisible(true);
    }

    public void switchToPanel(final PanelName panelName) {
        if (panelName != null) {
            cardLayout.show(getContentPane(), panelName.name());
        } else {
            System.err.println("Warning: Attempted to switch to an invalid panel: " + panelName);
        }
    }

    public MainMenuScreenPanel getMainMenuScreenPanel() {
        return mainMenuScreenPanel;
    }

    public GameplayScreenPanel getGameplayScreenPanel() {
        return gameplayScreenPanel;
    }

    public boolean isGameplayActive() {
        return gameplayScreenPanel.isVisible();
    }

    public boolean isMainMenuActive() {
        return mainMenuScreenPanel.isVisible();
    }

    public void startGame() {
        gameManager.startGame();
    }
}
