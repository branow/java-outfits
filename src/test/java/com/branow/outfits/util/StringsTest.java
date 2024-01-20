package com.branow.outfits.util;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

public class StringsTest {

    @ParameterizedTest
    @MethodSource("provide_calcStringsSimilarityOSAD_testStrings_validSimilarity")
    public void calcStringsSimilarityOSAD_testStrings_validSimilarity(String s1, String s2, double expected) {
        double actual = Strings.calcStringsSimilarityOSAD(s1, s2);
        Assertions.assertEquals(expected, actual, 0.001);
    }

    @ParameterizedTest
    @MethodSource("provide_calcOptimalStringAlignmentDistance_testStrings_validDistance")
    public void calcOptimalStringAlignmentDistance_testStrings_validDistance(String s1, String s2, int expected) {
        int actual = Strings.calcOptimalStringAlignmentDistance(s1, s2);
        Assertions.assertEquals(expected, actual);
    }

    private static Stream<Arguments> provide_calcStringsSimilarityOSAD_testStrings_validSimilarity() {
        return Stream.of(
                Arguments.of("cat", "cut", 0.6666),
                Arguments.of("cat", "act", 0.6666),
                Arguments.of("block", "blog", 0.6),
                Arguments.of("discussion", "dicusio", 0.7),
                Arguments.of("post", "calc", 0),
                Arguments.of("post", "", 0)
        );
    }

    private static Stream<Arguments> provide_calcOptimalStringAlignmentDistance_testStrings_validDistance() {
        return Stream.of(
                Arguments.of("cat", "cut", 1),
                Arguments.of("cat", "act", 1),
                Arguments.of("block", "blog", 2),
                Arguments.of("discussion", "dicusio", 3),
                Arguments.of("post", "calc", 4),
                Arguments.of("post", "", 4)
        );
    }


}
