package com.branow.outfits.math;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.Random;
import java.util.stream.Stream;

import static java.lang.Math.*;

public class LagrangePolynomialTest {

    @ParameterizedTest
    @MethodSource("provideCalculate")
    public void calculate(Function function, double[] x) {
        Point2D[] points = points(x, function);
        LagrangePolynomial pol = new LagrangePolynomial(points);
        Polynomial polynomial = pol.calculate();
        for (Point2D point: points) {
            Assertions.assertEquals(function.calculate(point.getX()), polynomial.calculate(point.getX()), 0.000_000_1);
        }
    }

    private static Stream<Arguments> provideCalculate() {
        return Stream.of(
                Arguments.of(func1(), new double[] {-2, -1, 0, 1, 2}),
                Arguments.of(func2(), new double[] {-2, -1, 0, 1, 2}),
                Arguments.of(func3(), new double[] {-2, -1, 0, 1, 3}),
                Arguments.of(func4(), new double[] {-2, -1, 0, 1, 2}),
                Arguments.of(func5(), new double[] {-2, -1, 0, 1, 2}),
                Arguments.of(func6(), new double[] {-2, -1, 0, 1, 2}),
                Arguments.of(func1(), random()),
                Arguments.of(func2(), random()),
                Arguments.of(func3(), random()),
                Arguments.of(func4(), random()),
                Arguments.of(func5(), random()),
                Arguments.of(func6(), random())
        );
    }


    private static double[] random() {
        double[] x = new double[5];
        Random random = new Random();
        for (int i = 0; i < x.length; i++) {
            x[i] = ((double) random.nextInt()) / random.nextInt();
        }
        return x;
    }

    private static Point2D[] points(double[] x, Function function) {
        Point2D[] points = new Point2D[x.length];
        for (int i = 0; i < points.length; i++) {
            points[i] = new Point2D(x[i], function.calculate(x[i]));
        }
        return points;
    }

    private static Function func1() {
        return (x) -> 0;
    }

    private static Function func2() {
        return (x) -> x;
    }

    private static Function func3() {
        return (x) -> x / (x * x - 6 * x + 8);
    }

    private static Function func4() {
        return (x) -> (pow(cos(x), 2) - 3 + PI * cos(x) + 1) / (pow(cos(x), 4) - 3 + PI * sin(x) - 1);
    }

    private static Function func5() {
        return (x) -> (x * x + 3) / (sqrt(Math.abs(x * x * x + 2 * x - 2)));
    }

    private static Function func6() {
        return (x) -> log(pow(x * x + 1, 5) / (x * x + 9)) / log10((3 * x * x + 17) / (5 * x * x));
    }

}
