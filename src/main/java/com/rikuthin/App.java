package com.rikuthin;

import javax.swing.SwingUtilities;

public class App {
    /**
     * How often the various threads should update themselves (milliseconds)
     */
    public static final int TICK_SPEED_MS = 20;

    public static void main(String[] args) {
        // Schedules GameFrame creation on the EDT
        SwingUtilities.invokeLater(App::launchGame);
    }

    public static void launchGame() {
        GameFrame gameFrame = new GameFrame();
        GameManager gameManager = new GameManager(gameFrame);
        gameFrame.setGameManager(gameManager);
        gameManager.startGame();
    }
}
