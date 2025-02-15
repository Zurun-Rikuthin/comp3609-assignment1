package com.rikuthin.game_objects;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.geom.Ellipse2D;
import java.util.Objects;

import javax.swing.JPanel;

import com.rikuthin.Bearing2D;

/**
 * Represents a bubble that moves within a JPanel. The bubble moves along a
 * specified bearing (angle in degrees) at a defined speed (pixels per tick) and
 * bounces off the edges.
 */
public class Bubble extends Ellipse2D.Double implements Runnable {

    /**
     * The panel within which the bubble moves.
     *
     */
    private final JPanel panel;

    /**
     * The colour of the bubble
     */
    private final Color colour;

    /**
     * Whether the bubble should start moving.
     * Set {@code False} by default upon construction.
     */
    private boolean isMoving;

    /**
     * The direction of movement, represented as a {@link Bearing2D}.
     * Set to {@code 0} degrees by default during construction.
     */
    private Bearing2D bearing;

    /**
     * The speed of the bubble's movement in pixels per tick.
     * * Set to {@code 0} degrees by default during construction.
     */
    private double pixelsPerTick;

    /**
     * Constructs a new Bubble.
     *
     * @param initialX The initial x-coordinate of the bubble.
     * @param initialY The initial y-coordinate of the bubble.
     * @param size The diameter of the bubble.
     * @param colour The color of the bubble.
     * @param panel The panel within which the bubble moves.
     */
    public Bubble(final int initialX, final int initialY, final double size, final Color colour, final JPanel panel) {
        super(initialX, initialY, size, size);
        this.panel = panel;
        this.colour = colour;
        isMoving = false;
        bearing = new Bearing2D(0);
        pixelsPerTick = 0;
    }

    /**
     * Returns whether the bubble is currently moving.
     *
     * @return {@code true} if the bubble is moving, otherwise {@code false}.
     */
    public boolean getIsMoving() {
        return isMoving;
    }

    /**
     * Returns the current bearing (direction) of the bubble.
     *
     * @return The {@link Bearing2D} representing the bubble's movement
     * direction.
     */
    public Bearing2D getBearing() {
        return bearing;
    }

    /**
     * Returns the movement speed of the bubble.
     *
     * @return The speed in pixels per tick.
     */
    public double getPixelsPerTick() {
        return pixelsPerTick;
    }

    /**
     * Sets whether the bubble should move.
     *
     * @param isMoving {@code true} to make the bubble move, {@code false} to
     * stop it.
     */
    public void setIsMoving(boolean isMoving) {
        this.isMoving = isMoving;
    }

    /**
     * Sets the bearing (direction) of the bubble's movement.
     *
     * @param bearing The new {@link Bearing2D} direction.
     */
    public void setBearing(Bearing2D bearing) {
        this.bearing = bearing;
    }

    /**
     * Sets the movement speed of the bubble to a new absolute (positive)
     * value).
     *
     * @param pixelsPerTick The new speed in pixels per tick.
     */
    public void setPixelsPerTick(double pixelsPerTick) {
        this.pixelsPerTick = Math.abs(pixelsPerTick);
    }

    /**
     * Moves the bubble according to its bearing (angle in degrees) and speed
     * (pixels per tick). If the bubble reaches the edges of the panel, it
     * bounces off and changes direction.
     */
    public void move() {
        if (!panel.isVisible()) {
            return;
        }

        double radians = Math.toRadians(bearing.getDegrees());
        double dx = pixelsPerTick * Math.cos(radians);
        double dy = pixelsPerTick * Math.sin(radians);

        x += dx;
        y += dy;

        Dimension panelSize = panel.getSize();

        // Bounce off left/right edges
        if (x < 0 || x + width > panelSize.width) {
            x = Math.clamp(x, 0, panelSize.width - width);
            bearing.setDegrees(180 - bearing.getDegrees()); // Reverse X direction
        }

        // Bounce off top/bottom edges
        if (y < 0 || y + height > panelSize.height) {
            y = Math.clamp(y, 0, panelSize.height - height);
            bearing.setDegrees(360 - bearing.getDegrees()); // Reverse Y direction
        }

        panel.repaint();
    }

    /**
     * Sets a new position for the bubble if it's within the panel boundaries.
     *
     * @param xPosition The new x-coordinate.
     * @param yPosition The new y-coordinate.
     */
    public void setLocation(final int xPosition, final int yPosition) {
        Dimension panelSize = panel.getSize();
        if (xPosition >= 0 && xPosition + width < panelSize.width
                && yPosition >= 0 && yPosition + height < panelSize.height) {
            x = xPosition;
            y = yPosition;
        }
    }

    /**
     * Draws the bubble onto the provided {@link Graphics2D} context.
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
     * Runs the bubble's movement logic in a loop until it is stopped. The
     * bubble will move continuously unless {@code isMoving} is set to false.
     */
    @Override
    public void run() {
        while (isMoving) {
            move();
            try {
                Thread.sleep(20); // Controls speed
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt(); // Restore interrupted state
                break;
            }
        }
    }

    /**
     * Checks if this bubble is equal to another object. Two bubbles are
     * considered equal if they have the same position, size, color, movement
     * status, bearing, and speed.
     *
     * @param obj The object to compare with.
     * @return {@code true} if the bubbles are equal, otherwise {@code false}.
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof Bubble)) {
            return false;
        }
        Bubble other = (Bubble) obj;
        return java.lang.Double.compare(x, other.x) == 0
                && java.lang.Double.compare(y, other.y) == 0
                && java.lang.Double.compare(width, other.width) == 0
                && java.lang.Double.compare(height, other.height) == 0 // Included for safety even though it's a circle
                && colour.equals(other.colour)
                && isMoving == other.isMoving
                && bearing.equals(other.bearing)
                && java.lang.Double.compare(pixelsPerTick, other.pixelsPerTick) == 0;

    }

    /**
     * Computes the hash code for this bubble using its position, size, color,
     * movement status, bearing, and speed.
     *
     * @return The hash code of the bubble.
     */
    @Override
    public int hashCode() {
        return Objects.hash(x, y, width, colour, isMoving, bearing, pixelsPerTick);
    }

}
