package com.rikuthin.game_objects;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.geom.Ellipse2D;
import java.util.Objects;

import javax.swing.JPanel;

import com.rikuthin.App;
import com.rikuthin.utility.Bearing2D;

/**
 * Represents a bubble that moves within a JPanel. The bubble moves along a
 * specified bearing (angle in degrees) at a defined speed (pixels per tick) and
 * bounces off the edges of the panel.
 */
public class Bubble extends Ellipse2D.Double implements Runnable {

    private final JPanel panel;  // The panel on which the bubble moves
    private final Color colour;  // Colour of the bubble
    private boolean isMoving;    // Whether the bubble should move
    private Bearing2D bearing;   // Direction of movement (bearing)
    private double speed;        // Movement speed (in pixels per tick)

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
        this.isMoving = false;
        this.bearing = new Bearing2D(0);
        this.speed = 0;
    }

    public boolean isMoving() {
        return isMoving;
    }

    public Bearing2D getBearing() {
        return bearing;
    }

    public double getSpeed() {
        return speed;
    }

    public void setIsMoving(boolean isMoving) {
        this.isMoving = isMoving;
    }

    public void setBearing(Bearing2D bearing) {
        this.bearing = bearing;
    }

    public void setSpeed(double speed) {
        this.speed = Math.abs(speed);
    }

    /**
     * Moves the bubble based on its bearing and speed. The bubble bounces off
     * the edges of the panel. If it hits the top edge, it stops.
     */
    public void move() {
        if (!panel.isVisible()) {
            return;
        }

        // Conversion formula source: https://gis.stackexchange.com/questions/187286/how-to-convert-double-bearing-into-x-and-y-coordinate
        final double radians = Math.toRadians(bearing.getDegrees());
        final double newX = x + speed * Math.cos(radians);
        final double newY = y - speed * Math.sin(radians); // Inverted to convert Cartesian coordinates to screen coordinates

        Dimension panelSize = panel.getSize();

        // Bounce off left/right edges
        if (newX < 0) {
            x = 0;
            bearing.setDegrees(180 - bearing.getDegrees());  // Reverse X direction
        } else if (newX + width > panelSize.width) {
            x = panelSize.width - width;
            bearing.setDegrees(180 - bearing.getDegrees());  // Reverse X direction
        } else {
            x = newX;
        }

        // Stop moving at top edge
        if (newY < 0) {
            y = 0;
            isMoving = false;
        } else if (newY + height > panelSize.height) {
            y = panelSize.height - height;
            bearing.setDegrees(360 - bearing.getDegrees());  // Reverse Y direction (if bottom is enabled)
        } else {
            y = newY;
        }

        javax.swing.SwingUtilities.invokeLater(panel::repaint);  // Thread-safe repaint
    }

    /**
     * Draws the bubble onto the provided Graphics2D context.
     *
     * @param g2 The Graphics2D object used for rendering.
     */
    public void draw(Graphics2D g2) {
        g2.setColor(colour);
        g2.fill(this);
        g2.setColor(Color.BLACK);
        g2.draw(this);
    }

    /**
     * Runs the bubble's movement logic in a loop, moving the bubble
     * continuously while the isMoving flag is true.
     */
    @Override
    public void run() {
        while (isMoving) {
            move();
            try {
                Thread.sleep(App.TICK_SPEED_MS); // Controls the movement speed
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();  // Restore interrupted state
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
