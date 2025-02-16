package com.rikuthin.utility;

import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Insets;
import java.awt.RenderingHints;
import java.awt.geom.RoundRectangle2D;

import javax.swing.border.AbstractBorder;

/**
 * Custom border with rounded corners for Swing components.
 *
 * The original code for this class was written by Santiago Benoit. Source:
 * https://stackoverflow.com/a/25796688
 */
public class RoundedBorder extends AbstractBorder {

    private final Color borderColour;
    private final Color backgroundColour;
    private final int arcSize;

    /**
     * Constructs a RoundedBorder with specified border color, background color,
     * and corner radius.
     *
     * @param borderColour The color of the border.
     * @param backgroundColour The background color of the border.
     * @param arcSize The arc size (radius of rounded corners).
     */
    public RoundedBorder(Color borderColour, Color backgroundColour, int arcSize) {
        this.borderColour = borderColour;
        this.backgroundColour = backgroundColour;
        this.arcSize = arcSize;
    }

    /**
     * Paints the rounded border on the given component.
     *
     * @param c The component on which to paint the border.
     * @param g The graphics context to use for painting.
     * @param x The x coordinate of the top-left corner.
     * @param y The y coordinate of the top-left corner.
     * @param width The width of the component.
     * @param height The height of the component.
     */
    @Override
    public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {
        if (g instanceof Graphics2D) {
            Graphics2D g2d = (Graphics2D) g.create();

            // Anti-aliasing for smoother edges
            g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            // Draw the background with rounded corners
            g2d.setColor(backgroundColour);
            g2d.fill(new RoundRectangle2D.Double(x, y, width, height, arcSize, arcSize));

            // Draw the border with rounded corners
            g2d.setColor(borderColour);
            g2d.draw(new RoundRectangle2D.Double(x + 0.5, y + 0.5, width - 1.0, height - 1.0, arcSize, arcSize));

            g2d.dispose();
        }
    }

    /**
     * Returns the insets (padding) of the border.
     *
     * @param c The component for which the insets are being calculated.
     * @return The insets of the border.
     */
    @Override
    public Insets getBorderInsets(Component c) {
        return new Insets(arcSize / 2, arcSize / 2, arcSize / 2, arcSize / 2);
    }

    /**
     * Returns the insets (padding) of the border.
     *
     * @param c The component for which the insets are being calculated.
     * @param insets The insets to be updated with border values.
     * @return The updated insets of the border.
     */
    @Override
    public Insets getBorderInsets(Component c, Insets insets) {
        insets.left = insets.top = insets.right = insets.bottom = arcSize / 2;
        return insets;
    }

    /**
     * Indicates whether the border is opaque (fully filled).
     *
     * @return true, since the border is opaque.
     */
    @Override
    public boolean isBorderOpaque() {
        return true;
    }
}
