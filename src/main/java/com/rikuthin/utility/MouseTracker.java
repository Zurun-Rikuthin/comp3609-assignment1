package com.rikuthin.utility;

import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;

import javax.swing.JLabel;

public class MouseTracker implements MouseMotionListener {

    private JLabel mouseLocationLabel;  // Label to display the mouse location

    // Constructor to initialize the label
    public MouseTracker(JLabel label) {
        this.mouseLocationLabel = label;
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        // Get the mouse position and update the label text
        int mouseX = e.getX();
        int mouseY = e.getY();
        mouseLocationLabel.setText("Mouse Location: (" + mouseX + ", " + mouseY + ")");
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        // Optionally handle mouse dragging (not necessary for this case)
    }
}
