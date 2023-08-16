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
import java.util.stream.Stream;

public class UniCharMapperTest {


    @ParameterizedTest
    @MethodSource("provideTest")
    public void test(String text, Charset charset) {
        ByteBuffer buffer = charset.encode(text);
        buffer = ByteBuffer.wrap(Arrays.copyOf(buffer.array(), buffer.limit()));

        UniCharMapper mapper = new UniCharMapper(buffer.duplicate(), charset);

        if (charset == StandardCharsets.UTF_16)
            buffer = mapper.getCharset().encode(text);

        List<String> expected = toCharsList(new String(buffer.array(), mapper.getCharset()));
        List<String> actual = new ArrayList<>();
        while (mapper.hasNext()) {
            UniChar uc = mapper.next();
            actual.add(uc.toString(mapper.getCharset()));
        }

        Assertions.assertEquals(expected, actual);
    }

    @ParameterizedTest
    @MethodSource("provideTest")
    public void testBytes(String text, Charset charset) {
        ByteBuffer buffer = charset.encode(text);
        buffer = ByteBuffer.wrap(Arrays.copyOf(buffer.array(), buffer.limit()));

        UniCharMapper mapper = new UniCharMapper(buffer.duplicate(), charset);

        if (charset == StandardCharsets.UTF_16)
            buffer = mapper.getCharset().encode(text);

        List<String> expected = toCharsList(new String(buffer.array(), mapper.getCharset()))
                .stream().map(e -> Arrays.toString(e.getBytes(mapper.getCharset()))).toList();
        List<String> actual = new ArrayList<>();
        while (mapper.hasNext()) {
            UniChar uc = mapper.next();
            actual.add(Arrays.toString(uc.toBytes()));
        }

        Assertions.assertEquals(expected, actual);
    }

    private static List<String> toCharsList(String text) {
        List<String> list = new ArrayList<>();
        for (int i = 0; i < text.length(); i++) {
            list.add(text.substring(i, i + 1));
        }
        return list;
    }

    private static Stream<Arguments> provideTest() {
        return Stream.of(
                Arguments.of(STR_1, StandardCharsets.ISO_8859_1),
                Arguments.of(STR_2, StandardCharsets.US_ASCII),
                Arguments.of(STR_3, StandardCharsets.UTF_8),
                Arguments.of(STR_4, StandardCharsets.UTF_16BE),
                Arguments.of(STR_5, StandardCharsets.UTF_16LE),
                Arguments.of(STR_5, StandardCharsets.UTF_16)
        );
    }

    private static final String STR_1 = "";
    private static final String STR_2 = "MIt[R\bVCMS}z\u0019Kxpyr,?bИpQzcyMu^M!\n";
    private static final String STR_3 = "ΚѵJ=^}eAglzt@ y\u0012E\u0002V<0T0]t81ɯT[%JCl\u001D^SY:3.T41awF*zsE9[\u000BA";
    private static final String STR_4 = "�qj+\u0015j[�t�T�D\u001Eca:\t&KW*$\u000FD>Y\u001AZa\u001Ac\u0005c,陡0\u001A" +
            "ňn`SL!4 erJs瞸\u000B\u0002\u001C=ş\u0002rz\u0002~Ǯ";
    private static final String STR_5 = "�d��5��\n\n\n�W�W3\\W��\u000B�W��\u0010����r\u0005�D6�d�\".\u001C��\u00046\u0015" +
            "\u0019\fH�t�e��'�{c\u000F\b��-n;��8!��`\u0002p\u0001l���Ψk\u0018��u�\u000F1VH�2P�<$z\u0011��|";

}
