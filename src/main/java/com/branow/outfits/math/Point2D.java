package com.branow.outfits.math;

import java.util.Objects;

/**
 * A {@code Point2D} is an abstraction of a point in two-dimensional space.
 * Every instance of class keeps two double values: x and y.
 */
public class Point2D {

    private final double x;
    private final double y;

    public Point2D(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Point2D point2D = (Point2D) o;
        return Double.compare(point2D.x, x) == 0 && Double.compare(point2D.y, y) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }

    @Override
    public String toString() {
        return "(" + x + ", " + y + ")";
    }
}
