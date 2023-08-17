package com.branow.outfits.util;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Random;
import java.util.stream.Stream;

public class UniCharStreamMapperTest {

    @ParameterizedTest
    @MethodSource("provideTest")
    public void test(byte[] bytes, Charset charset) {
        ByteBuffer buffer = ByteBuffer.wrap(new String(bytes, charset).getBytes(charset));

        UniCharMapper mapper = new UniCharMapper(buffer, charset);
        UniCharStreamMapperImp streamMapper = new UniCharStreamMapperImp(buffer, charset);

        while (mapper.hasNext()) {
            Assertions.assertEquals(mapper.hasNext(), streamMapper.hasNext());
            UniChar expected = mapper.next();
            UniChar actual = streamMapper.next();
            Assertions.assertEquals(expected, actual);
        }
    }

    static class UniCharStreamMapperImp extends UniCharStreamMapper {
        private final ByteBuffer buffer;

        public UniCharStreamMapperImp(ByteBuffer buffer, Charset charset) {
            super(buffer.limit(), charset);
            this.buffer = buffer;
        }

        @Override
        protected ByteBuffer read(long pos, int size) {
            ByteBuffer src = buffer.slice((int) pos, size);
            return src.rewind();
        }
    }


    private static Stream<Arguments> provideTest() {
        return Stream.of(
                Arguments.of(randomBytes(0), StandardCharsets.US_ASCII),
                Arguments.of(randomBytes(-513), StandardCharsets.ISO_8859_1),
                Arguments.of(randomBytes(354), StandardCharsets.UTF_8),
                Arguments.of(randomBytes(-51354), StandardCharsets.UTF_16LE),
                Arguments.of(randomBytes(26589), StandardCharsets.UTF_16BE),
                Arguments.of(randomBytes(-798313), StandardCharsets.UTF_16)
        );
    }

    private static byte[] randomBytes(int seed) {
        Random random = new Random(seed);
        int size = random.nextInt(0, Integer.MAX_VALUE / 200);
        System.out.println(size);
        byte[] bytes = new byte[size];
        random.nextBytes(bytes);
        return bytes;
    }

}
