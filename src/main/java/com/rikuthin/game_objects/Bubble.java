package com.rikuthin.game_objects;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.geom.Ellipse2D;
import java.util.List;
import java.util.Objects;

import com.rikuthin.App;
import com.rikuthin.GameManager;
import com.rikuthin.screen_panels.gameplay_subpanels.BubblePanel;
import com.rikuthin.utility.Bearing2D;

/**
 * Represents a bubble that moves within a JPanel. The bubble moves along a
 * specified bearing (angle in degrees) at a defined speed (pixels per tick) and
 * bounces off the edges of the panel.
 */
public class Bubble extends Ellipse2D.Double implements Runnable {

    public static final double SIZE = 30; // Size of the bubble in pixels

    private final Color colour;  // Colour of the bubble
    private boolean isMoving;    // Whether the bubble should move
    private Bearing2D bearing;   // Direction of movement (bearing)
    private double speed;        // Movement speed (in pixels per tick)

    /**
     * Constructs a new Bubble.
     *
     * @param initialX The initial x-coordinate of the bubble.
     * @param initialY The initial y-coordinate of the bubble.
     * @param SIZE The diameter of the bubble.
     * @param colour The color of the bubble.
     */
    public Bubble(final int initialX, final int initialY, final Color colour) {
        super(initialX, initialY, SIZE, SIZE);

        if (GameManager.getInstance().getBubblePanel() == null) {
            throw new NullPointerException("BubblePanel must be instantiated before creating bubbles.");
        }

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

    public void setIsMoving(final boolean isMoving) {
        this.isMoving = isMoving;
    }

    public void setBearing(final Bearing2D bearing) {
        this.bearing = bearing;
    }

    public void setSpeed(final double speed) {
        this.speed = Math.abs(speed);
    }

    /**
     * Moves the bubble based on its bearing and speed.
     *
     * The bubble bounces off the sides of the panel edges and stops either when
     * hitting another bubble or the top of the panel.
     */
    public void move() {
        BubblePanel bubblePanel = GameManager.getInstance().getBubblePanel();
        if (!bubblePanel.isVisible()) {
            return;
        }
        final Dimension panelSize = bubblePanel.getSize();
        final double radians = Math.toRadians(bearing.getDegrees());

        double nextX = x + speed * Math.cos(radians);
        double nextY = y - speed * Math.sin(radians); // Inverted for screen coordinates        

        // Check for collision with another bubble
        if (checkCollision(nextX, nextY)) {
            isMoving = false;
            return;
        }

        // Handle Y-axis bouncing or stopping at the top
        if (nextY < 0) {
            y = 0;
            isMoving = false;
        } else {
            if (nextY + height > panelSize.height) {
                bearing.setDegrees(360 - bearing.getDegrees());  // Reverse Y direction
                nextY = panelSize.height - height;
            }
            y = nextY;
        }

        // Handle X-axis bouncing
        if (nextX < 0 || nextX + width > panelSize.width) {
            bearing.setDegrees(180 - bearing.getDegrees());  // Reverse X direction
            nextX = Math.clamp(nextX, 0, panelSize.width - width);
        }
        x = nextX;

        javax.swing.SwingUtilities.invokeLater(bubblePanel::repaint);  // Thread-safe repaint
    }

    /**
     * Draws the bubble onto the provided Graphics2D context.
     *
     * @param g2 The Graphics2D object used for rendering.
     */
    public void draw(final Graphics2D g2) {
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
     * considered equal if they have the same position, color, movement status,
     * bearing, and speed.
     *
     * @param obj The object to compare with.
     * @return {@code true} if the bubbles are equal, otherwise {@code false}.
     */
    @Override
    public boolean equals(final Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof Bubble)) {
            return false;
        }
        Bubble other = (Bubble) obj;
        return java.lang.Double.compare(x, other.x) == 0
                && java.lang.Double.compare(y, other.y) == 0
                && colour.equals(other.colour)
                && isMoving == other.isMoving
                && bearing.equals(other.bearing)
                && java.lang.Double.compare(speed, other.speed) == 0;
    }

    /**
     * Computes the hash code for this bubble using its position, color,
     * movement status, bearing, and speed.
     *
     * @return The hash code of the bubble.
     */
    @Override
    public int hashCode() {
        return Objects.hash(x, y, colour, isMoving, bearing, speed);
    }

    /**
     * Checks if moving into a new (x, y) poisiton would collide with another
     * bubble
     *
     * @param newX the newX position given the movement is successful
     * @param newY the newX position given the movement is successful
     *
     * @return Whether the bubbles collide or not
     */
    private boolean checkCollision(final double newX, final double newY) {
        List<Bubble> bubbles = GameManager.getInstance().getBubblePanel().getBubbles(); // Retrieve existing bubbles

        if (bubbles.isEmpty()) {
            return false; // No bubbles to check
        }

        final double thisRadius = width / 2.0;
        final double thisCentreX = newX + thisRadius;
        final double thisCentreY = newY + thisRadius;

        for (Bubble other : bubbles) {
            if (other.equals(this)) {
                continue; // Skip self-comparison
            }

            final double otherRadius = other.width / 2.0;
            final double otherCentreX = other.getCenterX();
            final double otherCentreY = other.getCenterY();

            final double distanceSquared = Math.pow(thisCentreX - otherCentreX, 2) + Math.pow(thisCentreY - otherCentreY, 2);
            final double radiusSumSquared = Math.pow(thisRadius + otherRadius, 2);

            if (distanceSquared <= radiusSumSquared) {
                return true; // Collision detected
            }
        }

        return false; // No collision
    }
}
