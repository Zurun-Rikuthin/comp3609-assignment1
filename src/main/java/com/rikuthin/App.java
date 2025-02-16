package com.rikuthin;

import javax.swing.SwingUtilities;

public class App {
    /**
     * How often the various threads should update themselves (milliseconds)
     */
    public static final int TICK_SPEED_MS = 20;

    public static void main(String[] args) {
        // Schedules GameFrame creation on the EDT
        SwingUtilities.invokeLater(GameFrame::new);
    }
}
