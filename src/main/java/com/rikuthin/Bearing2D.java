package com.rikuthin;

/**
 * Represents a bearing (direction) in 2D space, measured in degrees.
 * The bearing can be initialized using an angle or calculated between two points.
 */
public final class Bearing2D {

    private double degrees;

    /**
     * Creates a Bearing2D instance with a specified angle in degrees.
     * The angle is automatically normalized to the range [0, 360).
     * 
     * Values of note:
     *  - <value> = 0: NORTH (up)
     *  - <value> = 90: EAST (right)
     *  - <value> = 180: SOUTH (down)
     *  - <value> = 270: WEST (left)
     *  - <value> >= 360: <value> mod 360
     * 
     * @param degrees The initial bearing angle in degrees.
     */
    public Bearing2D(double degrees) {
        this.degrees = normalize(degrees);
    }

    /**
     * Creates a Bearing2D instance representing the angle from one point to another.
     * 
     * @param startX The x-coordinate of the starting point.
     * @param startY The y-coordinate of the starting point.
     * @param endX   The x-coordinate of the ending point.
     * @param endY   The y-coordinate of the ending point.
     */
    public Bearing2D(final int startX, final int startY, final int endX, final int endY) {
        calculateBearing(startX, startY, endX, endY);
    }

    /**
     * Returns the bearing angle in degrees.
     * 
     * @return The bearing angle, normalized to the range [0, 360).
     */
    public double getDegrees() {
        return degrees;
    }

    /**
     * Sets a new bearing angle in degrees.
     * The value is automatically normalized to the range [0, 360).
     * 
     * @param degrees The new bearing angle in degrees.
     */
    public void setDegrees(double degrees) {
        this.degrees = normalize(degrees);
    }

    /**
     * Calculates and sets the bearing angle between two points.
     * The angle is measured in degrees, relative to the positive x-axis.
     * 
     * @param startX The x-coordinate of the starting point.
     * @param startY The y-coordinate of the starting point.
     * @param endX   The x-coordinate of the ending point.
     * @param endY   The y-coordinate of the ending point.
     */
    public void calculateBearing(final int startX, final int startY, final int endX, final int endY) {
        int deltaX = endX - startX;
        int deltaY = endY - startY;

        double bearingRad = Math.atan2(deltaY, deltaX);
        double bearingDeg = Math.toDegrees(bearingRad);

        degrees = normalize(bearingDeg);
    }

    /**
     * Normalizes an angle to ensure it falls within the range [0, 360).
     * 
     * @param degrees The angle to normalize.
     * @return The normalized angle in degrees.
     */
    private double normalize(final double degrees) {
        double normalizedDegrees = degrees % 360;
        return (normalizedDegrees < 0) ? normalizedDegrees + 360 : normalizedDegrees;
    }

    /**
     * Compares this Bearing2D object with another object for equality.
     * 
     * @param obj The object to compare.
     * @return {@code true} if the object is a {@code Bearing2D} with the same angle, otherwise {@code false}.
     */
    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }

        if (!(obj instanceof Bearing2D)) {
            return false;
        }

        Bearing2D b = (Bearing2D) obj;
        return b.getDegrees() == degrees;
    }

    /**
     * Returns the hash code for this Bearing2D instance.
     * 
     * @return A hash code based on the bearing angle.
     */
    @Override
    public int hashCode() {
        return Double.hashCode(degrees);
    }
}
