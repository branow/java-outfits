package com.branow.outfits.util;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

public class UniCharTest {

    @ParameterizedTest
    @MethodSource("provideConstructorByteArray")
    public void constructorByteArray(byte[] code, char[] chars, char c, int val, byte[] bytes) {
        test(new UniChar(code), chars, c, val, bytes);
    }

    @ParameterizedTest
    @MethodSource("provideConstructorChar")
    public void constructorChar(char code, char[] chars, char c, int val, byte[] bytes) {
        test(new UniChar(code), chars, c, val, bytes);
    }

    @ParameterizedTest
    @MethodSource("provideConstructorInt")
    public void constructorInt(int code, char[] chars, char c, int val, byte[] bytes) {
        test(new UniChar(code), chars, c, val, bytes);
    }

    public void test(UniChar uc, char[] chars, char c, int val, byte[] bytes) {
        Assertions.assertArrayEquals(chars, uc.toCharArray());
        Assertions.assertEquals(c, uc.toChar());
        Assertions.assertEquals(val, uc.toInt());
        Assertions.assertArrayEquals(bytes, uc.toBytes());
    }

    private static Stream<Arguments> provideConstructorByteArray () {
        return Stream.of(
                Arguments.of(new byte[] {}, new char[] {'\u0000', '\u0000'}, '\u0000', 0, new byte[] {}),
                Arguments.of(new byte[] {0, 0, 24}, new char[] {'\u0000', '\u0018'}, '\u0018', 24, new byte[] {24}),
                Arguments.of(new byte[] {1, -3, 4, 78, 12}, new char[] {'\u01FD', '\u044E'}, '\u044E', 0x01FD044E, new byte[] {1, -3, 4, 78})
        );
    }

    private static Stream<Arguments> provideConstructorChar () {
        return Stream.of(
                Arguments.of('\u0000', new char[] {'\u0000', '\u0000'}, '\u0000', 0, new byte[] {}),
                Arguments.of('\u0018', new char[] {'\u0000', '\u0018'}, '\u0018', 0x18, new byte[] {0x18}),
                Arguments.of('\u044E', new char[] {'\u0000', '\u044E'}, '\u044E', 0x044E, new byte[] {4, 78})
        );
    }

    private static Stream<Arguments> provideConstructorInt () {
        return Stream.of(
                Arguments.of(0, new char[] {'\u0000', '\u0000'}, '\u0000', 0, new byte[] {}),
                Arguments.of(24, new char[] {'\u0000', '\u0018'}, '\u0018', 24, new byte[] {24}),
                Arguments.of(0x01FD044E, new char[] {'\u01FD', '\u044E'}, '\u044E', 0x01FD044E, new byte[] {1, -3, 4, 78})
        );
    }
}
