package com.rikuthin.utility;

/*
 * The original code for this class was written by Santiago Benoit
 * Source: https://stackoverflow.com/a/25796688
 */
import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Insets;
import java.awt.RenderingHints;
import java.awt.geom.RoundRectangle2D;

import javax.swing.border.AbstractBorder;

public class RoundedBorder extends AbstractBorder {

    private final Color borderColour;
    private final Color backgroundColour;
    private final int arcSize;

    public RoundedBorder(Color borderColour, Color backgroundColour, int arcSize) {
        this.borderColour = borderColour;
        this.backgroundColour = backgroundColour;
        this.arcSize = arcSize;
    }

    @Override
    public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {
        if (g instanceof Graphics2D g2d) {
            g2d = (Graphics2D) g.create();

            // Anti-aliasing for smoother edges
            g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            // Draw the background
            g2d.setColor(backgroundColour);
            g2d.fill(new RoundRectangle2D.Double(x, y, width, height, arcSize, arcSize));
            
            // Draw the border
            g2d.setColor(borderColour);
            g2d.draw(new RoundRectangle2D.Double(x + 0.5, y + 0.5, width - 1.0, height - 1.0, arcSize, arcSize));

            g2d.dispose();
        }
    }

    @Override
    public Insets getBorderInsets(Component c) {
        return (getBorderInsets(c, new Insets(arcSize / 2 , arcSize / 2, arcSize / 2, arcSize / 2)));
    }

    @Override
    public Insets getBorderInsets(Component c, Insets insets) {
        insets.left = insets.top = insets.right = insets.bottom = arcSize / 2;
        return insets;
    }

    @Override
    public boolean isBorderOpaque() {
        return true;
    }
}
