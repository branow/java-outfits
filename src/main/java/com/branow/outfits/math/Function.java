package com.branow.outfits.math;

/**
 * A {@code Function} is a dependence one variable of another.
 */
public interface Function {

    /**
     * The method calculates some variable {@code y} for every variable {@code x}.
     *
     * @param x an argument
     * @return a value of function
     */
    double calculate(double x);

}
