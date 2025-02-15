package com.rikuthin;

import javax.swing.SwingUtilities;

public class App {

    public static void main(String[] args) {
        // Schedules GameFrame creation on the EDT
        SwingUtilities.invokeLater(GameFrame::new);
    }
}
