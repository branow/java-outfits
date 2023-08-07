package com.branow.outfits.util;

import java.util.stream.Stream;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

public class BitOperationsTest {

    @ParameterizedTest
    @MethodSource("provideToBinaryStringInt")
    public void toBinaryStringInt(int value, boolean fillZeros, String expected) {
        expected = expected.replaceAll(" ", "");
        String actual = BitOperations.toBinaryString(value, fillZeros);
        Assertions.assertEquals(expected, actual);
    }

    @ParameterizedTest
    @MethodSource("provideToBinaryStringByte")
    public void toBinaryStringByte(byte value, boolean fillZeros, String expected) {
        expected = expected.replaceAll(" ", "");
        String actual = BitOperations.toBinaryString(value, fillZeros);
        Assertions.assertEquals(expected, actual);
    }


    private static Stream<Arguments> provideToBinaryStringByte() {
        return Stream.of(
                Arguments.of((byte) 0, true, "0000 0000"),
                Arguments.of((byte) -1, false, "1111 1111"),
                Arguments.of((byte) 22, true, "0001 0110"),
                Arguments.of((byte) -78, true, "1011 0010"),
                Arguments.of((byte) 127, false, "111 1111"),
                Arguments.of((byte) -128, false, "1000 0000")
        );
    }

    private static Stream<Arguments> provideToBinaryStringInt() {
        return Stream.of(
                Arguments.of(0, true, "00000000 00000000 00000000 00000000"),
                Arguments.of(-1, false, "11111111 11111111 11111111 11111111"),
                Arguments.of(22, false, "10110"),
                Arguments.of(-78, false, "11111111 11111111 11111111 1011 0010"),
                Arguments.of(22156, true, "00000000 00000000 01010110 10001100"),
                Arguments.of(-7885, true, "11111111 11111111 11100001 00110011"),
                Arguments.of(2_147_483_647, false, "1111111 11111111 11111111 11111111"),
                Arguments.of(-2_147_483_648, false, "10000000 00000000 00000000 00000000")
        );
    }

}
