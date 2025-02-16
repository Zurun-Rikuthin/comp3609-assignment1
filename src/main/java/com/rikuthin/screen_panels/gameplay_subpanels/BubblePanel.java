package com.rikuthin.screen_panels.gameplay_subpanels;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JPanel;

import com.rikuthin.GameFrame;
import com.rikuthin.GameManager;
import com.rikuthin.game_objects.Bubble;

public class BubblePanel extends JPanel {

    private final GameManager gameManager;
    private final List<Bubble> bubbles;

    public BubblePanel(GameManager gameManager) {
        this.gameManager = gameManager;
        bubbles = new ArrayList<>();
        setBackground(new Color(200, 170, 170));
        setPreferredSize(new Dimension(GameFrame.FRAME_WIDTH, 590));

        // Shoot the blaster when clicked
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (isVisible()) {
                    Point target = new Point(e.getX(), e.getY());
                    gameManager.shootBubble(target);
                    repaint();
                }
            }
        });
    }

    public void addBubble(Bubble bubble) {
        bubbles.add(bubble);
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;

        for (Bubble bubble : bubbles) {
            bubble.draw(g2);
        }
    }
}
