package com.branow.outfits.math;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

public class PolynomialTest {

    @ParameterizedTest
    @MethodSource("provideAdd")
    public void add(Polynomial p1, Polynomial p2, Polynomial expected) {
        Polynomial actual = Polynomial.add(p1, p2);
        Assertions.assertEquals(expected, actual);
    }

    @ParameterizedTest
    @MethodSource("provideSubtract")
    public void subtract(Polynomial p1, Polynomial p2, Polynomial expected) {
        Polynomial actual = Polynomial.subtract(p1, p2);
        Assertions.assertEquals(expected, actual);
    }

    @ParameterizedTest
    @MethodSource("provideMultiply")
    public void multiply(Polynomial p1, Polynomial p2, Polynomial expected) {
        Polynomial actual = Polynomial.multiply(p1, p2);
        Assertions.assertEquals(expected, actual);
    }

    private static Stream<Arguments> provideAdd() {
        return Stream.of(
                Arguments.of(new Polynomial(), new Polynomial(0, 2, 3), new Polynomial(0, 2, 3)),
                Arguments.of(new Polynomial(1, 8, 4, 7513), new Polynomial(), new Polynomial(1, 8, 4, 7513)),
                Arguments.of(new Polynomial(), new Polynomial(), new Polynomial()),
                Arguments.of(new Polynomial(7, -3, 4, 871, -6), new Polynomial(-7, 3, -4, -871, 6), new Polynomial()),
                Arguments.of(new Polynomial(215, -98, 21, 4, -98), new Polynomial(1, 546, -652, 12, 4, 2), new Polynomial(216, 448, -631, 16, -94, 2))
        );
    }

    private static Stream<Arguments> provideSubtract() {
        return Stream.of(
                Arguments.of(new Polynomial(), new Polynomial(0, 2, 3), new Polynomial(0, -2, -3)),
                Arguments.of(new Polynomial(1, 8, 4, 7513), new Polynomial(), new Polynomial(1, 8, 4, 7513)),
                Arguments.of(new Polynomial(), new Polynomial(), new Polynomial()),
                Arguments.of(new Polynomial(7, -3, 4, 871, -6), new Polynomial(7, -3, 4, 871, -6), new Polynomial()),
                Arguments.of(new Polynomial(215, -98, 21, 4, -98), new Polynomial(1, 546, -652, 12, 4, 2), new Polynomial(214, -644, 673, -8, -102, -2))
        );
    }

    private static Stream<Arguments> provideMultiply() {
        return Stream.of(
                Arguments.of(new Polynomial(), new Polynomial(0, 2, 3), new Polynomial()),
                Arguments.of(new Polynomial(1, 8, 4, 7513), new Polynomial(), new Polynomial()),
                Arguments.of(new Polynomial(), new Polynomial(), new Polynomial()),
                Arguments.of(new Polynomial(0, 0), new Polynomial(7, -3, 4), new Polynomial()),
                Arguments.of(new Polynomial(7, -3, 4), new Polynomial(0, 0, 0, 0, 0, 0), new Polynomial()),
                Arguments.of(new Polynomial(1, 0), new Polynomial(0, 2), new Polynomial(0, 2)),
                Arguments.of(new Polynomial(3, 1), new Polynomial(0, 2, 3), new Polynomial(0, 6, 11, 3)),
                Arguments.of(new Polynomial(0, 5, 0), new Polynomial(1, 2, 3, 4), new Polynomial(0, 5, 10, 15, 20)),
                Arguments.of(new Polynomial(1, 4, 2, -2, 3), new Polynomial(-1, 3, 0, 0, 2, 1), new Polynomial(-1, -1, 10, 8, -7, 18, 8, -2, 4, 3))
        );
    }


}
