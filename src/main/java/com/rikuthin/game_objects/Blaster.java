package com.rikuthin.game_objects;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.geom.Rectangle2D;
import java.util.Objects;

import com.rikuthin.screen_panels.gameplay_subpanels.BubblePanel;
import com.rikuthin.utility.Bearing2D;

/**
 * Represents a blaster that moves within a JPanel. The blaster moves along a
 * specified bearing (angle in degrees) at a defined speed (pixels per tick) and
 * bounces off the edges.
 */
public class Blaster extends Rectangle2D.Double implements Runnable {

    /**
     * The panel into which which the blaster shoots. *
     */
    private final BubblePanel bubblePanel;

    /**
     * The colour of the blaster
     */
    private Color colour;

    /**
     * The navigational bearing from the blaster's centre point to the current
     * location of the mouse pointer, represented as a {@link Bearing2D}.
     *
     * Defaults to 000 degrees construction.
     */
    private Bearing2D bearing;

    /**
     * The diameter (in pixels) of the bubbles shot from the blaster.
     */
    private int shotSize;

    /**
     * The speed (in pixels/tick) at which bubbles are shot from the blaster.
     */
    private double shotSpeed;

    /**
     * Constructs a new Blaster.
     *
     * @param initialX The x-coordinate of the blaster.
     *
     * @param initialY The y-coordinate of the blaster.
     * @param width The width of the blaster.
     * @param height The height of the blaster.
     * @param colour The colour of the blaster.
     * @param bubblePanel The BubblePanel into which the blaster shoots.
     */
    public Blaster(final int x, final int y, final int shotSize, final double shotSpeed, final Color colour, final BubblePanel bubblePanel) {
        super(x, y, shotSize, shotSize);
        this.colour = colour;
        this.bubblePanel = bubblePanel;
        bearing = new Bearing2D(0);
        this.shotSize = shotSize;
        this.shotSpeed = shotSpeed;
    }

    /**
     * Returns the colour of the blaster.
     *
     * @return The {@link Color} of the blaster.
     */
    public Color getColour() {
        return colour;
    }

    /**
     * Returns the current bearing (direction) of the blaster relative to the
     * mouse pointer.
     *
     * @return The {@link Bearing2D} representing the angle between the blaster
     * and the mouse pointer.
     */
    public Bearing2D getBearing() {
        return bearing;
    }

    /**
     * Returns the size (in pixels) of the bubbles that will be shot from the
     * blaster.
     *
     * @return The diamter of the {@link Bubble} shot from the blaster.
     */
    public int getShotSize() {
        return shotSize;
    }

    /**
     * Returns the speed (in pixels/tick) of the bubbles that will be shot from
     * the blaster.
     *
     * @return The speed the {@link Bubble} shot from the blaster will move at.
     */
    public double getShotSpeed() {
        return shotSpeed;
    }

    /**
     * Returns the colour of the blaster.
     *
     * @return The {@link Color} of the blaster.
     */
    public void setColour(Color colour) {
        this.colour = colour;
    }

    /**
     * Sets the bearing (direction) of the blaster's movement.
     *
     * @param bearing The new {@link Bearing2D} direction.
     */
    public void setBearing(Bearing2D bearing) {
        this.bearing = bearing;
    }

    /**
     * Sets the size (in pixels) of the bubbles that will be shot from the
     * blaster.
     *
     * @return The new {@link Bubble} size.
     */
    public void setShotSize(final int shotSize) {
        this.shotSize = shotSize;
    }

    /**
     * Returns the speed (in pixels/tick) of the bubbles that will be shot from
     * the blaster.
     *
     * @return The new {@link Bubble} speed.
     */
    public void setShotSpeed(double shotSpeed) {
        this.shotSpeed = shotSpeed;
    }

    /**
     * Moves the blaster according to its bearing (angle in degrees) and speed
     * (pixels per tick). If the blaster reaches the edges of the panel, it
     * bounces off and changes direction.
     */
    // public void rotate() {
    //     if (!panel.isVisible()) {
    //         return;
    //     }
    //     double radians = Math.toRadians(bearing.getDegrees());
    //     x += pixelsPerTick * Math.cos(radians);
    //     y += pixelsPerTick * Math.sin(radians);
    //     Dimension panelSize = panel.getSize();
    //     // Bounce off left/right edges
    //     if (x < 0) {
    //         x = 0;
    //         bearing.setDegrees(180 - bearing.getDegrees()); // Reverse X direction
    //     } else if (x + width > panelSize.width) {
    //         x = panelSize.width - width;
    //         bearing.setDegrees(180 - bearing.getDegrees()); // Reverse X direction
    //     }
    //     // Bounce off top/bottom edges
    //     if (y < 0) {
    //         y = 0;
    //         bearing.setDegrees(360 - bearing.getDegrees()); // Reverse Y direction
    //     } else if (y + height > panelSize.height) {
    //         y = panelSize.height - height;
    //         bearing.setDegrees(360 - bearing.getDegrees()); // Reverse Y direction
    //     }
    //     // Repaint the panel on the EDT to ensure thread safety
    //     javax.swing.SwingUtilities.invokeLater(panel::repaint);
    // }
    /**
     * Shoots a new Bubble instance towards the given target location.
     *
     * @param target The mouse position where the bubble should move.
     * @param bubbleColour The color of the bubble.
     * @param bubbleSize The size (diameter) of the bubble.
     * @param bubbleSpeed The speed at which the bubble moves.
     * @return The newly created bubble.
     */
    public Bubble shootBubble(final Point target, final Color bubbleColour) {
        // Ensure target is not null (failsafe)
        if (target == null) {
            throw new IllegalArgumentException("Target position cannot be null");
        }

        // Get the starting position of the bubble (center of the blaster)
        int startX = (int) Math.round(getCenterX());
        int startY = (int) Math.round(getCenterY());

        // Create the bubble
        Bubble bubble = new Bubble(startX, startY, shotSize, bubbleColour, bubblePanel);

        // Set the bubble's direction using the given target
        bubble.setBearing(new Bearing2D(startX, startY, target.x, target.y));

        // Set speed and movement
        bubble.setSpeed(shotSpeed);
        bubble.setIsMoving(true);

        bubblePanel.addBubble(bubble);
        new Thread(bubble).start();

        return bubble;
    }

    /**
     * Draws the blaster onto the provided {@link Graphics2D} context.
     *
     * @param g2 The {@link Graphics2D} object used for rendering.
     */
    public void draw(Graphics2D g2) {
        g2.setColor(colour);
        g2.fill(this);
        g2.setColor(Color.BLACK);
        g2.draw(this);
    }

    /**
     * Runs the blaster's movement logic in a loop until it is stopped.
     */
    @Override
    public void run() {
        while (true) {
            // rotate();
            try {
                Thread.sleep(20); // Controls speed
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt(); // Restore interrupted state
                break;
            }
        }
    }

    /**
     * Checks if this blaster is equal to another object. Two blasters are
     * considered equal if they have the same position, size, color, movement
     * status, bearing, and speed.
     *
     * @param obj The object to compare with.
     * @return {@code true} if the blasters are equal, otherwise {@code false}.
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof Blaster)) {
            return false;
        }
        Blaster other = (Blaster) obj;
        return java.lang.Double.compare(x, other.x) == 0
                && java.lang.Double.compare(y, other.y) == 0
                && java.lang.Double.compare(width, other.width) == 0
                && java.lang.Double.compare(height, other.height) == 0 // Included for safety even though it's a circle
                && colour.equals(other.getColour())
                && bearing.equals(other.getBearing())
                && java.lang.Integer.compare(shotSize, other.getShotSize()) == 0
                && java.lang.Double.compare(shotSpeed, other.getShotSpeed()) == 0;

    }

    /**
     * Computes the hash code for this blaster using its position, size, color,
     * movement status, bearing, and speed.
     *
     * @return The hash code of the blaster.
     */
    @Override
    public int hashCode() {
        return Objects.hash(x, y, width, height, colour, bearing, shotSize, shotSpeed);
    }
}
