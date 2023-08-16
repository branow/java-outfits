package com.branow.outfits.util;

import java.nio.BufferUnderflowException;
import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Objects;
import java.util.function.Supplier;

/**
 * The {@code UniCharMapper} is an adaptor between sequence of bytes
 * and instances of {@link UniChar}. It is going throw the byte buffer
 * and separating read bytes to exact {@link UniChar} according to given {@code charset}.
 * It has the same methods as {@link java.util.Iterator}, they are {@link UniCharMapper#hasNext()}
 * and {@link UniCharMapper#next()}.
 */
public class UniCharMapper {

    private final Charset charset;
    private final ByteBuffer buffer;
    private final Supplier<UniChar> next;

    /**
     * It uses constructor {@link UniCharMapper#UniCharMapper(ByteBuffer, Charset)}
     * with default charset gotten by {@link Charset#defaultCharset()}.
     *
     * @param buffer The buffer to read.
     */
    public UniCharMapper(ByteBuffer buffer) {
        this(buffer, Charset.defaultCharset());
    }

    /**
     * If the given {@code charset} is {@code UTF-16}, it tries to read first two bytes
     * to indicate which exactly coding it is {@code UTF-16BE} or {@code UTF-16LE} and exchange it.
     * If there is not an order mark, the exception is throw. Also, it throws exception
     * if the given charset is not a {@link StandardCharsets}.
     *
     * @param buffer  The buffer to read.
     * @param charset The charset to decode bytes to {@link  UniChar} representation.
     * @throws IllegalArgumentException if the given charset is not a {@link StandardCharsets}
     *                                  and if there is not an order mark in case {@code UTF-16} charset.
     */
    public UniCharMapper(ByteBuffer buffer, Charset charset) {
        this.buffer = buffer;
        this.charset = charset == StandardCharsets.UTF_16 ? determineUTF16() : charset;
        this.next = determineFunctionNext();
    }

    /**
     * Returns the {@code charset} of this object.
     *
     * @return The {@code charset} of this object.
     */
    public Charset getCharset() {
        return charset;
    }

    /**
     * Returns the {@code byte buffer} of this object.
     *
     * @return The {@code byte buffer} of this object.
     */
    public ByteBuffer getBuffer() {
        return buffer.duplicate();
    }

    /**
     * Tells weather there are ony elements {@link UniChar}. It considers that there is at least one
     * {@link UniChar} if there is at least one byte not read.
     *
     * @return {@code True} if, and only if, there is at least one element.
     */
    public boolean hasNext() {
        return buffer.hasRemaining();
    }

    /**
     * Returns next {@link UniChar} element.
     *
     * @return The next {@link UniChar} element.
     * @throws BufferUnderflowException if the byte buffer does not have remaining elements.
     */
    public UniChar next() {
        if (hasNext())
            return next.get();
        throw new BufferUnderflowException();
    }

    /**
     * Returns a string summarizing the state of this mapper.
     *
     * @return A summary string
     */
    @Override
    public String toString() {
        return this.getClass().getSimpleName() + "[" + charset + " " + buffer + "]";
    }

    /**
     * Tells whether this object equals the given.
     *
     * @return {@code True} if the charset and buffer of these two objects are equal.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UniCharMapper that = (UniCharMapper) o;
        return Objects.equals(charset, that.charset) && Objects.equals(buffer, that.buffer);
    }

    /**
     * Returns the hash code of this object. It uses {@link Objects#hash(Object...)} of these charset
     * and buffer.
     *
     * @return The hash code of this object.
     */
    @Override
    public int hashCode() {
        return Objects.hash(charset, buffer);
    }

    private UniChar nextUniChar() {
        return new UniChar(new byte[]{buffer.get()});
    }

    private UniChar nextUTF8() {
        byte start = buffer.get();
        int length = countUtf8char(start);
        byte[] array = new byte[length];
        array[0] = start;
        for (int i = 1; i < length; i++) {
            array[i] = buffer.hasRemaining() ? buffer.get() : 0;
        }
        return new UniChar(array);
    }

    private UniChar nextUTF16() {
        byte b1 = buffer.hasRemaining() ? buffer.get() : 0;
        byte b2 = buffer.hasRemaining() ? buffer.get() : 0;
        byte[] bytes = {b1, b2};
        int val = ByteConverter.byteArrayToInt(bytes);
        int c = (char) val;
        if (c > 0xD7FF && c < 0xE000) {
            byte b3 = buffer.hasRemaining() ? buffer.get() : 0;
            byte b4 = buffer.hasRemaining() ? buffer.get() : 0;
            bytes = new byte[]{b1, b2, b3, b4};
        }
        return new UniChar(bytes);
    }

    private static int countUtf8char(byte b) {
        if (b >= 0)
            return 1;
        String binary = BitOperations.toBinaryString(b, true);
        for (int i = 0; i < binary.length(); i++) {
            if (binary.charAt(i) == '0')
                return i;
        }
        return 0;
    }

    private Supplier<UniChar> determineFunctionNext() {
        if (charset == StandardCharsets.US_ASCII || charset == StandardCharsets.ISO_8859_1) {
            return this::nextUniChar;
        } else if (charset == StandardCharsets.UTF_8) {
            return this::nextUTF8;
        } else if (charset == StandardCharsets.UTF_16LE || charset == StandardCharsets.UTF_16BE) {
            return this::nextUTF16;
        }
        throw new IllegalArgumentException("Unknown charset: " + charset);
    }

    private Charset determineUTF16() {
        byte b1 = buffer.get();
        byte b2 = buffer.get();
        if (b1 == (byte) -1 && b2 == (byte) -2) {//little endian
            return StandardCharsets.UTF_16LE;
        } else if (b1 == (byte) -2 && b2 == (byte) -1) { //big endian
            return StandardCharsets.UTF_16BE;
        }
        throw new IllegalArgumentException("There is not the byte order mark");
    }
}

