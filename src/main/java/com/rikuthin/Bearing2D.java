package com.rikuthin;

/*
 * Calculates the bearing (in degrees) between two Cartesian points in 2D space
 */
public final class Bearing2D {

    private double degrees;

    public Bearing2D (double degrees) {
        this.degrees = normalize(degrees);
    }

    public Bearing2D(final int startX, final int startY, final int endX, final int endY) {
        calculateBearing(startX, startY, endX, endY);
    }

    public double getDegrees() {
        return degrees;
    }

    public void setDegrees(double degrees) {
        this.degrees = normalize(degrees);
    }

    public void calculateBearing(final int startX, final int startY, final int endX, final int endY) {
        int deltaX = endX - startX;
        int deltaY = endY - startY;

        double bearingRad = Math.atan2(deltaY, deltaX);
        double bearingDeg = Math.toDegrees(bearingRad);

        degrees = normalize(bearingDeg);
    }

    // Ensure the bearing in within the valid range of 0 to 360
    private double normalize(final double degrees) {
        double normalizedDegrees = degrees % 360;

        if (normalizedDegrees < 0) {
            normalizedDegrees += 360;
        }

        return normalizedDegrees;
    }

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

    @Override
    public int hashCode() {
        return Double.hashCode(degrees);
    }
}
