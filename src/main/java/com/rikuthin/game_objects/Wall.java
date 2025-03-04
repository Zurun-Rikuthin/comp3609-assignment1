package com.rikuthin.game_objects;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.ThreadLocalRandom;

import com.rikuthin.App;
import com.rikuthin.GameManager;
import com.rikuthin.screen_panels.gameplay_subpanels.BubblePanel;
import com.rikuthin.utility.Bearing2D;

/**
 * Represents a wall that moves within a JPanel. The wall moves along a
 * specified bearing (angle in degrees) at a defined speed (pixels per tick) and
 * bounces off the edges of the panel.
 */
public class Wall extends Rectangle2D.Double implements Runnable {

    public static final double WIDTH = 30; // Size of the wall in pixels
    public static final double SIZE = 30; // Size of the wall in pixels

    private final Color colour;  // Colour of the wall
    private final double speed;        // Movement speed (in pixels per tick)

    private boolean isMoving;    // Whether the wall should move
    private Bearing2D bearing;   // Direction of movement (bearing)

    /**
     * Constructs a new Bubble.
     *
     * @param initialX The initial x-coordinate of the wall.
     * @param initialY The initial y-coordinate of the wall.
     * @param SIZE The diameter of the wall.
     * @param colour The color of the wall.
     */
    public Wall(final int initialX, final int initialY, final int width, final int height, final Color colour) {
        super(initialX, initialY, width, height);

        if (GameManager.getInstance().getBubblePanel() == null) {
            throw new NullPointerException("BubblePanel must be instantiated before creating walls.");
        }

        this.colour = colour;
        this.isMoving = true;
        this.speed = ThreadLocalRandom.current().nextInt(9) + 1.0;

        final boolean movesLeft = ThreadLocalRandom.current().nextBoolean();
        if (movesLeft) {
            this.bearing = new Bearing2D(0, 0, 100, 0);
        } else {
            this.bearing = new Bearing2D(100, 0, 0, 0);
        }
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

    /**
     * Moves the wall based on its bearing and speed.
     *
     * The wall bounces off the sides of the panel edges and phases through
     * other walls
     */
    public void move() {
        BubblePanel bubblePanel = GameManager.getInstance().getBubblePanel();
        if (!bubblePanel.isVisible()) {
            return;
        }
        final Dimension panelSize = bubblePanel.getSize();
        final double radians = Math.toRadians(bearing.getDegrees());

        double nextX = x + speed * Math.cos(radians);
        // Handle X-axis bouncing
        if (nextX < 0 || nextX + width > panelSize.width) {
            bearing.setDegrees(180 - bearing.getDegrees());  // Reverse X direction
            nextX = Math.clamp(nextX, 0, panelSize.width - width);
        }
        x = nextX;

        javax.swing.SwingUtilities.invokeLater(bubblePanel::repaint);  // Thread-safe repaint
    }

    /**
     * Draws the wall onto the provided Graphics2D context.
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
     * Runs the wall's movement logic
     */
    @Override
    public void run() {
        while (true) {
            if (isMoving) {
                if (!GameManager.getInstance().isPaused()) {
                    move();
                }
            }

            try {
                Thread.sleep(App.TICK_SPEED_MS);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                break;
            }
        }
    }

    /**
     * Checks if this wall is equal to another object. Two walls are considered
     * equal if they have the same position, size, colour, movement status,
     * bearing, and speed.
     *
     * @param obj The object to compare with.
     * @return {@code true} if the walls are equal, otherwise {@code false}.
     */
    @Override
    public boolean equals(final Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof Wall)) {
            return false;
        }
        Wall other = (Wall) obj;
        return java.lang.Double.compare(x, other.x) == 0
                && java.lang.Double.compare(y, other.y) == 0
                && java.lang.Double.compare(width, other.width) == 0
                && java.lang.Double.compare(height, other.height) == 0
                && colour.equals(other.colour)
                && isMoving == other.isMoving
                && bearing.equals(other.bearing)
                && java.lang.Double.compare(speed, other.speed) == 0;
    }

    /**
     * Computes the hash code for this wall using its position, colour, movement
     * status, bearing, and speed.
     *
     * @return The hash code of the wall.
     */
    @Override
    public int hashCode() {
        return Objects.hash(x, y, width, height, colour, isMoving, bearing, speed);
    }

    /**
     * Checks if moving into a new (x, y) poisiton would collide with another
     * wall
     *
     * @param nextX the next x position (given the movement is successful)
     * @param nextY the next y position (given the movement is successful)
     *
     * @return Whether the walls collide or not
     */
    private boolean checkCollision(final double nextX, final double nextY) {
        List<Wall> walls = GameManager.getInstance().getBubblePanel().getWalls(); // Retrieve existing walls

        if (walls.isEmpty()) {
            return false; // No walls to check
        }

        final double thisRadius = width / 2.0;
        final double thisCentreX = nextX + thisRadius;
        final double thisCentreY = nextY + thisRadius;

        for (Wall other : walls) {
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
