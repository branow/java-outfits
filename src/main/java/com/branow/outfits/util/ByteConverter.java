package com.branow.outfits.util;

import java.util.Arrays;

/**
 * The {@code ByteConverter} contains different methods to convert primitive
 * data types to a sequence of bytes and vice versa.
 * */
public class ByteConverter {

    /**
     * The method converts the given integer value to a byte array.
     * The byte array length is always equal four.
     *
     * @param value The given integer value to convert.
     * @return The byte array representation of the given value.
     * */
    public static byte[] intToByteArray(int value) {
        return new byte[]{
                (byte) (value >>> 24),
                (byte) (value >>> 16),
                (byte) (value >>> 8),
                (byte) value};
    }

    /**
     * The method converts the byte array value to an integer value.
     * If the byte array length is longer than four bytes, all the bytes after
     * third position are not used to work on integer value.
     *
     * @param bytes The byte array to convert.
     * @return The integer value representation of the given byte array.
     * */
    public static int byteArrayToInt(byte[] bytes) {
        int length = Math.min(bytes.length, 4);
        byte[] array = Arrays.copyOf(bytes, length);
        for (int i = array.length - 1; i > 0; i--) {
            if (array[i] < 0) {
                array[i - 1] += 1;
            }
        }
        int sum = 0;
        for (int i = array.length - 1, shift = 0; i >= 0; i--, shift += 8) {
            sum += array[i] << shift;
        }
        return sum;
    }

    /**
     * The method converts the given character value to a byte array.
     * The byte array length is always equal two bytes.
     *
     * @param c The given character value to convert.
     * @return The byte array representation of the given value.
     * */
    public static byte[] charToByteArray(char c) {
        return new byte[]{
                (byte) (c >>> 8),
                (byte)c};
    }

    /**
     * The method converts the two bytes to a character value.
     *
     * @param higher The first byte to convert.
     * @param lower The second byte to convert.
     * @return The character value representation of the given bytes.
     * */
    public static char bytesToChar(byte higher, byte lower) {
        if (lower < 0) higher += 1;
        return (char) ((higher << 8) + lower);
    }
}
