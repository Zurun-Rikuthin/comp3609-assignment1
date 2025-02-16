package com.rikuthin;

import javax.swing.SwingUtilities;

public class App {

    /**
     * How often the various threads should update themselves (milliseconds).
     * The value is set to 20 milliseconds.
     */
    public static final int TICK_SPEED_MS = 20;

    /**
     * The entry point for the application. This method schedules the creation
     * of the {@link GameFrame} on the Event Dispatch Thread (EDT).
     *
     * @param args The command line arguments, though not used in this
     * application.
     */
    public static void main(String[] args) {
        // Schedules GameFrame creation on the EDT
        SwingUtilities.invokeLater(GameFrame::new);
    }
}
