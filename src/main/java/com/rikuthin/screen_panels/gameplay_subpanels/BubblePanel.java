package com.rikuthin.screen_panels.gameplay_subpanels;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;

import javax.swing.JPanel;

import com.rikuthin.GameFrame;

public class BubblePanel extends JPanel {

    private BlasterPanel blasterPanel;
    private Point mousePointerCoord;

    public BubblePanel() {
        setBackground(new Color(200, 170, 170));
        setPreferredSize(new Dimension(GameFrame.FRAME_WIDTH, 590));

        addMouseMotionListener(new MouseMotionListener() {
            @Override
            public void mouseMoved(MouseEvent e) {
                mousePointerCoord = new Point(e.getX(), e.getY());

                if (blasterPanel != null) {
                    blasterPanel.updateMouseCoords(mousePointerCoord.x, mousePointerCoord.y);
                }
            }

            @Override
            public void mouseDragged(MouseEvent e) {
                // Not needed for now, but included for completeness
            }
        });
    }

    public void setBlasterPanel(BlasterPanel blasterPanel) {
        this.blasterPanel = blasterPanel;
    }
}
