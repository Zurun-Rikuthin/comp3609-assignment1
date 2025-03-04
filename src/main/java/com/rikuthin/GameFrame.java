package com.rikuthin;

import java.awt.CardLayout;

import javax.swing.JFrame;
import javax.swing.WindowConstants;

import com.rikuthin.screen_panels.GameplayScreenPanel;
import com.rikuthin.screen_panels.MainMenuScreenPanel;

public final class GameFrame extends JFrame {

    /**
     * Enum representing the different panel names used in the game.
     */
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

    /**
     * Constructor to initialize the game frame, set the size, title, and add
     * the main menu and gameplay panels. Also initializes the GameManager
     * instance and sets the blaster and bubble panels.
     */
    public GameFrame() {
        setTitle("Thread the Needle");
        setSize(FRAME_WIDTH, FRAME_HEIGHT);
        setResizable(false);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        cardLayout = new CardLayout();
        setLayout(cardLayout);

        mainMenuScreenPanel = new MainMenuScreenPanel(this);
        gameplayScreenPanel = new GameplayScreenPanel(this);

        // Initialize GameManager
        gameManager = GameManager.getInstance();
        gameManager.setBlasterPanel(gameplayScreenPanel.getBlasterPanel());
        gameManager.setBubblePanel(gameplayScreenPanel.getBubblePanel());
        gameManager.setStatusPanel(gameplayScreenPanel.getStatusPanel());

        add(mainMenuScreenPanel, PanelName.MAIN_MENU.name());
        add(gameplayScreenPanel, PanelName.GAMEPLAY.name());

        // Set the initial panel to the main menu
        switchToPanel(PanelName.MAIN_MENU);
        setVisible(true);
    }

    /**
     * Switches the view to the specified panel.
     *
     * @param panelName The panel to switch to (either MAIN_MENU or GAMEPLAY).
     */
    public void switchToPanel(final PanelName panelName) {
        if (panelName != null) {
            cardLayout.show(getContentPane(), panelName.name());
        } else {
            System.err.println("Warning: Attempted to switch to an invalid panel: " + panelName);
        }
    }

    /**
     * Returns the {@link MainMenuScreenPanel} associated with this game frame.
     *
     * @return The main menu screen panel.
     */
    public MainMenuScreenPanel getMainMenuScreenPanel() {
        return mainMenuScreenPanel;
    }

    /**
     * Returns the {@link GameplayScreenPanel} associated with this game frame.
     *
     * @return The gameplay screen panel.
     */
    public GameplayScreenPanel getGameplayScreenPanel() {
        return gameplayScreenPanel;
    }

    /**
     * Checks if the gameplay panel is currently active (visible).
     *
     * @return True if the gameplay screen is visible, otherwise false.
     */
    public boolean isGameplayActive() {
        return gameplayScreenPanel.isVisible();
    }

    /**
     * Checks if the main menu panel is currently active (visible).
     *
     * @return True if the main menu screen is visible, otherwise false.
     */
    public boolean isMainMenuActive() {
        return mainMenuScreenPanel.isVisible();
    }

    /**
     * Starts the game by initializing the game manager and setting up the
     * gameplay.
     */
    public void startGame() {
        gameManager.startGame();
    }
}
