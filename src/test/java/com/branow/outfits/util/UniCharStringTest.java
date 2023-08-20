package com.branow.outfits.util;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.stream.Stream;

public class UniCharStringTest {

    @ParameterizedTest
    @MethodSource("provideTest")
    public void testToUniChars(byte[] bytes, Charset charset) {
        ByteBuffer bb = charset.encode(new String(bytes, charset));
        byte[] data = Arrays.copyOf(bb.array(), bb.limit());
        String src = toString(data, charset);
        UniChar[] expected = toUniChars(data, charset);
        UniChar[] actual = UniCharString.toUniChars(src, charset);
        Assertions.assertArrayEquals(expected, actual);
    }

    @ParameterizedTest
    @MethodSource("provideTest")
    public void testToString(byte[] bytes, Charset charset) {
        ByteBuffer bb = charset.encode(new String(bytes, charset));
        byte[] data = Arrays.copyOf(bb.array(), bb.limit());
        UniChar[] src = toUniChars(data, charset);
        String expected = toString(data, charset);
        String actual = UniCharString.toString(src, charset);
        Assertions.assertEquals(expected, actual);
    }

    @ParameterizedTest
    @MethodSource("provideTest")
    public void testConverting(byte[] bytes, Charset charset) {
        ByteBuffer bb = charset.encode(new String(bytes, charset));
        byte[] data = Arrays.copyOf(bb.array(), bb.limit());

        UniChar[] uniChars1 = toUniChars(data, charset);
        String str1 = UniCharString.toString(uniChars1, charset);
        UniChar[] uniChars2 = UniCharString.toUniChars(str1, charset);
        String str2 = UniCharString.toString(uniChars2, charset);

        Assertions.assertArrayEquals(uniChars1, uniChars2);
        Assertions.assertEquals(str1, str2);
    }

    private static String toString(byte[] bytes, Charset charset) {
        return new String(bytes, charset);
    }

    private static UniChar[] toUniChars(byte[] bytes, Charset charset) {
        List<UniChar> uniChars = new ArrayList<>();
        UniCharMapper mapper = new UniCharMapper(ByteBuffer.wrap(bytes), charset);
        while (mapper.hasNext()) {
            uniChars.add(mapper.next());
        }
        return uniChars.toArray(UniChar[]::new);
    }


    private static Stream<Arguments> provideTest() {
        return Stream.of(
                Arguments.of(random(-115), StandardCharsets.ISO_8859_1),
                Arguments.of(random(654), StandardCharsets.US_ASCII),
                Arguments.of(random(1), StandardCharsets.UTF_8),
                Arguments.of(random(-98), StandardCharsets.UTF_16LE),
                Arguments.of(random(154), StandardCharsets.UTF_16BE),
                Arguments.of(random(0), StandardCharsets.UTF_16)
        );
    }

    private static byte[] random(int seed) {
        Random random = new Random(seed);
        int size = random.nextInt(0, 50);
        byte[] bytes = new byte[size];
        random.nextBytes(bytes);
        return bytes;
    }
}
