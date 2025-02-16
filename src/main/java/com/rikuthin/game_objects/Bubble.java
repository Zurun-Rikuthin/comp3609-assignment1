package com.rikuthin.game_objects;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.geom.Ellipse2D;
import java.util.Objects;

import javax.swing.JPanel;

import com.rikuthin.App;
import com.rikuthin.Bearing2D;

/**
 * Represents a bubble that moves within a JPanel. The bubble moves along a
 * specified bearing (angle in degrees) at a defined speed (pixels per tick) and
 * bounces off the edges.
 */
public class Bubble extends Ellipse2D.Double implements Runnable {

    /**
     * The panel within which the bubble moves.
     */
    private final JPanel panel;

    /**
     * The colour of the bubble
     */
    private final Color colour;

    /**
     * Whether the bubble should start moving. Set {@code False} by default upon
     * construction.
     */
    private boolean isMoving;

    /**
     * The direction of movement, represented as a {@link Bearing2D}. Set to
     * {@code 0} degrees by default during construction.
     */
    private Bearing2D bearing;

    /**
     * The speed of the bubble's movement in pixels per tick. * Set to {@code 0}
     * by default during construction.
     */
    private double speed;

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
        speed = 0;
    }

    /**
     * Returns whether the bubble is currently moving.
     *
     * @return {@code true} if the bubble is moving, otherwise {@code false}.
     */
    public boolean isMoving() {
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
    public double getSpeed() {
        return speed;
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
    public void setSpeed(double speed) {
        this.speed = Math.abs(speed);
    }

    /**
     * Moves the bubble according to its bearing (angle in degrees) and speed
     * (pixels per tick).
     * 
     * If the bubble hits the side edges of the panel, it bounces off and changes direction.
     * If the bubble hits the top of the panel or collides with another bubble, it stops moving.
     */
    public void move() {
        if (!panel.isVisible()) {
            return;
        }

        // https://gis.stackexchange.com/questions/187286/how-to-convert-double-bearing-into-x-and-y-coordinate

        final double radians = Math.toRadians(bearing.getDegrees());
        final double newX = x + speed * Math.cos(radians);
        final double newY = y - speed * Math.sin(radians); // Inverted because +y is downward in Swing compared to normal Cartesian +y

        Dimension panelSize = panel.getSize();

        // Bounce off left/right edges
        if (newX < 0) {
            x = 0;
            bearing.setDegrees(180 - bearing.getDegrees()); // Reverse X direction
        } else if (newX + width > panelSize.width) {
            x = panelSize.width - width;
            bearing.setDegrees(180 - bearing.getDegrees()); // Reverse X direction
        }

        // Stop moving at top edge
        if (newY < 0) {
            y = 0;
            isMoving = false;
        }

        // Bounce off top/bottom edges
        // if (y < 0) {
        //     y = 0;
        //     bearing.setDegrees(360 - bearing.getDegrees()); // Reverse Y direction
        // } else if (y + height > panelSize.height) {
        //     y = panelSize.height - height;
        //     bearing.setDegrees(360 - bearing.getDegrees()); // Reverse Y direction
        // }

        // Repaint the panel on the EDT to ensure thread safety
        javax.swing.SwingUtilities.invokeLater(panel::repaint);
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
                Thread.sleep(App.TICK_SPEED_MS); // Controls speed
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
                && java.lang.Double.compare(speed, other.speed) == 0;

    }

    /**
     * Computes the hash code for this bubble using its position, size, color,
     * movement status, bearing, and speed.
     *
     * @return The hash code of the bubble.
     */
    @Override
    public int hashCode() {
        return Objects.hash(x, y, width, colour, isMoving, bearing, speed);
    }

}
