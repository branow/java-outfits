package com.branow.outfits.math;

/**
 * A {@code LagrangePolynomial} calculates a polynomial passing through the given points.
 * The calculation is based on Numerical interpolation using Lagrange's Interpolation formula
 */
public class LagrangePolynomial {

    private final Point2D[] points;

    /**
     * @param points the points used to construct the polynomial
     * */
    public LagrangePolynomial(Point2D... points) {
        this.points = points;
    }


    //formula
    //ln = sum(i=0, n) (((x-x0)(x-x1)...(x-x(i-1))(x-x(i+1))...(x-xn)) / ((xi-x0)(xi-x1)... (xi-x(i-1))(xi-x(i+1)) ...(xi-xn))) * yi
    /**
     * The method calculates polynomial using Lagrange's Interpolation formula.
     * @return a polynomial passing through the given points
     * */
    public Polynomial calculate() {
        Polynomial result = new Polynomial();
        for (int i = 0; i < points.length; i++) {
            Polynomial app = numerator(i);
            app.divide(new Polynomial(denominator(i)));
            app.multiply(new Polynomial(points[i].getY()));
            result.add(app);
        }
        return result;
    }


    //(((x-x0)(x-x1)...(x-xn))
    private Polynomial numerator(int i) {
        Polynomial polynomial = new Polynomial(1);
        for (int j = 0; j < points.length; j++) {
            if (i != j) {
                polynomial.multiply(new Polynomial(-points[j].getX(), 1));
            }
        }
        return polynomial;
    }

    //((xi-x0)(xi-x1)...(xi-xn)))
    private double denominator(int i) {
        double result = 1;
        for (int j = 0; j < points.length; j++) {
            if (i != j) {
                result *= (points[i].getX() - points[j].getX());
            }
        }
        return result;
    }

}
