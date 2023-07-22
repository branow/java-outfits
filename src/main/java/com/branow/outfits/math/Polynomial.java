package com.branow.outfits.math;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * An abstraction of math polynomial. The instance of class keeps coefficients in an array.
 * Position of value in the array equals degree of x with which this coefficient locates.
 * For example, polynomial: (1 + x^2 + 4*x^5 - 3*x) is kept as [1, -3, 1, 0, 4]
 * */
public class Polynomial {

    /**
     * The method adds two polynomials.
     * @param p1 the fist application
     * @param p2 the second application
     * @return a sum of applications
     * */
    public static Polynomial add(Polynomial p1, Polynomial p2) {
        int length = Math.max(p1.degree(), p2.degree());
        double[] pol = new double[length];
        for (int i=0; i<pol.length; i++) {
            pol[i] = p1.getCoefficient(i) + p2.getCoefficient(i);
        }
        return new Polynomial(pol);
    }

    /**
     * The method subtracts from polynomial other.
     * @param p1 a minuend
     * @param p2 a subtrahend
     * @return a difference
     * */
    public static Polynomial subtract(Polynomial p1, Polynomial p2) {
        int length = Math.max(p1.degree(), p2.degree());
        double[] pol = new double[length];
        for (int i=0; i<pol.length; i++) {
            pol[i] = p1.getCoefficient(i) - p2.getCoefficient(i);
        }
        return new Polynomial(pol);
    }

    /**
     * The method multiplies polynomials.
     * @param p1 the first multiplier
     * @param p2 the second multiplier
     * @return a product
     * */
    public static Polynomial multiply(Polynomial p1, Polynomial p2) {
        if (p1.degree() == 0 || p2.degree() == 0)
            return new Polynomial();

        Polynomial[] pols = new Polynomial[p1.degree()];
        for (int i=0; i<pols.length; i++) {
            double[] pol = new double[i + p2.degree()];
            for (int j=0; j<p2.degree(); j++) {
                pol[j + i] = p1.getCoefficient(i) * p2.getCoefficient(j);
            }
            pols[i] = new Polynomial(pol);
        }

        Polynomial sum = pols[0];
        for (int i=1; i<pols.length; i++) {
            sum = Polynomial.add(sum, pols[i]);
        }
        return sum;
    }


    private double[] coef;

    public Polynomial(double... coef) {
        if (coef == null)
            throw new NullPointerException("coef is null");
        this.coef = coef;
        trim();
    }

    /**
     * The method returns a coefficient at according position.
     * @param xDegree degree of x at which the coefficient is
     * @return value of the coefficient
     * */
    public double getCoefficient(int xDegree) {
        if (xDegree < 0 || xDegree >= coef.length)
            return 0;
        return coef[xDegree];
    }

    /**
     * The method returns coefficients.
     * @return a copied array of coefficients
     * */
    public double[] getCoefficients() {
        return Arrays.copyOf(coef, coef.length);
    }

    /**
     * return the highest degree of x
     * */
    public int degree() {
        return coef.length;
    }

    /**
     * The method adds a got polynomial to this.
     * @param pol the second application
     * @see Polynomial#add(Polynomial, Polynomial)
     * */
    public void add(Polynomial pol) {
        coef = Polynomial.add(this, pol).getCoefficients();
    }

    /**
     * The method subtracts a got polynomial of this.
     * @param pol s subtrahend
     * @see Polynomial#subtract(Polynomial, Polynomial)
     * */
    public void subtract(Polynomial pol) {
        coef = Polynomial.subtract(this, pol).getCoefficients();
    }

    /**
     * The method multiplies a got polynomial to this.
     * @param pol the second multiplier
     * @see Polynomial#multiply(Polynomial, Polynomial)
     * */
    public void multiply(Polynomial pol) {
        coef = Polynomial.multiply(this, pol).getCoefficients();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Polynomial that = (Polynomial) o;
        return Arrays.equals(coef, that.coef);
    }

    @Override
    public int hashCode() {
        return Arrays.hashCode(coef);
    }

    @Override
    public String toString() {
        List<String> values = new ArrayList<>();
        for (int i=0; i<coef.length; i++) {
            values.add("(" + coef[i] + " * " + "x^" + i + ")");
        }
        return String.join(" + ", values);
    }

    private void trim() {
        int newLength = coef.length;
        for (int i=coef.length - 1; i >= 0; i--) {
            if (coef[i] == 0)
                newLength--;
            else
                break;
        }
        if (newLength != coef.length) {
            coef = Arrays.copyOf(coef, newLength);
        }
    }
}
