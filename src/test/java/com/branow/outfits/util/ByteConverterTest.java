package com.branow.outfits.util;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Stream;

public class ByteConverterTest {

    @ParameterizedTest
    @MethodSource("provide")
    public void intToByteArray(int seed) {
        Random random = new Random(seed);
        int val = random.nextInt();
        byte[] bytes = ByteConverter.intToByteArray(val);

        String expected = toBinaryString(val);
        String actual = toBinaryString(bytes);

        Assertions.assertEquals(expected, actual);
    }

    @ParameterizedTest
    @MethodSource("provide")
    public void byteArrayToInt(int seed) {
        Random random = new Random(seed);
        byte[] bytes = new byte[4];
        random.nextBytes(bytes);
        int val = ByteConverter.byteArrayToInt(bytes);
        String expected = toBinaryString(bytes);
        String actual = toBinaryString(val);
        Assertions.assertEquals(expected, actual);
    }


    @ParameterizedTest
    @MethodSource("provide")
    public void charToByteArray(int seed) {
        Random random = new Random(seed);
        char val = (char) random.nextInt();
        byte[] bytes = ByteConverter.charToByteArray(val);

        String expected = toBinaryString(val);
        String actual = toBinaryString(bytes);

        Assertions.assertEquals(expected, actual);
    }

    @ParameterizedTest
    @MethodSource("provide")
    public void bytesToChar(int seed) {
        Random random = new Random(seed);
        byte[] bytes = new byte[2];
        random.nextBytes(bytes);
        char val = ByteConverter.bytesToChar(bytes[0], bytes[1]);
        String expected = toBinaryString(bytes);
        String actual = toBinaryString(val);
        Assertions.assertEquals(expected, actual);
    }


    private static Stream<Arguments> provide() {
        return Stream.of(
                Arguments.of(-93036),
                Arguments.of(-79497),
                Arguments.of(-1),
                Arguments.of(0),
                Arguments.of(1),
                Arguments.of(30108),
                Arguments.of(92185)
        );
    }


    private String toBinaryString(byte[] bytes) {
        List<String> list = new ArrayList<>();
        for (byte b: bytes) {
            list.add(BitOperations.toBinaryString(b, true));
        }
        return String.join(" ", list);
    }

    private String toBinaryString(int val) {
        String bin = BitOperations.toBinaryString(val, true);
        return String.join(" ", List.of(bin.substring(0, 8), bin.substring(8, 16), bin.substring(16, 24), bin.substring(24, 32)));
    }

    private String toBinaryString(char val) {
        String bin = BitOperations.toBinaryString(val, true);
        return String.join(" ", bin.substring(16, 24), bin.substring(24, 32));
    }

}
