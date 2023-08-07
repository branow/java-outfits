package com.branow.outfits.util;

public class BitOperations {

    public static int xor(int o1, int o2) {
        return (o1 & (~o2)) ^ ((~o1) & o2);
    }

    public static int xnor(int o1, int o2) {
        return ~xor(o1, o2);
    }

    /**
     * The method returns a binary string representation of gotten value. The length of returned string
     * is equals or less than 32 characters, for example (0 = 0, 12 = 1100,
     * -56 = 11111111 11111111 11111111 11001000).
     *
     * @param value an integer to be converted to binary string
     * @return a binary string representation of gotten value
     * @see Integer#toBinaryString(int)
     */
    public static String toBinaryString(int value) {
        return toBinaryString(value, false);
    }

    /**
     * The method returns a binary string representation of gotten value. The length of returned string
     * is equals or less than 32 characters, for example (0 = 0, 12 = 1100,
     * -56 = 11111111 11111111 11111111 11001000). But if {@code fillZeros} is {@code true}, it puts
     * enough numbers of zeros before number (0 = 00000000 00000000 00000000 00000000,
     * 12 = 00000000 00000000 00000000 00001100).
     *
     * @param value     a byte to be converted to binary string
     * @param fillZeros if it is {@code true}, the returned string is fitted to length 32 by adding zeros
     *                  in the beginning.
     * @return a binary string representation of gotten value
     * @see Integer#toBinaryString(int)
     */
    public static String toBinaryString(int value, boolean fillZeros) {
        return toBinaryString(value, 32, fillZeros);
    }

    /**
     * The method returns a binary string representation of gotten value. The length of returned string
     * is equals or less than 8 characters, for example (0 = 0, 12 = 1100, -56 = 11001000).
     *
     * @param value a byte to be converted to binary string
     * @return a binary string representation of gotten value
     * @see Integer#toBinaryString(int)
     */
    public static String toBinaryString(byte value) {
        return toBinaryString(value, false);
    }

    /**
     * The method returns a binary string representation of gotten value. The length of returned string
     * is equals or less than 8 characters, for example (0 = 0, 12 = 1100, -56 = 11001000). But if
     * {@code fillZeros} is {@code true}, it puts enough numbers of zeros before number (0 = 00000000,
     * 12 = 00001100).
     *
     * @param value     a byte to be converted to binary string
     * @param fillZeros if it is {@code true}, the returned string is fitted to length 8 by adding zeros
     *                  in the beginning.
     * @return a binary string representation of gotten value
     * @see Integer#toBinaryString(int)
     */
    public static String toBinaryString(byte value, boolean fillZeros) {
        return toBinaryString(value, 8, fillZeros);
    }


    private static String toBinaryString(int value, int length, boolean fill) {
        String binaryString = Integer.toBinaryString(value);
        if (binaryString.length() == length) {
            return binaryString;
        } else if (binaryString.length() > length) {
            return binaryString.substring(binaryString.length() - length);
        } else {
            if (fill) {
                if (value < 0)
                    return stringOfOnes(length - binaryString.length()) + binaryString;
                else
                    return stringOfZeros(length - binaryString.length()) + binaryString;
            } else {
                return binaryString;
            }
        }
    }

    private static String stringOfOnes(int count) {
        return "1".repeat(count);
    }

    private static String stringOfZeros(int count) {
        return "0".repeat(count);
    }
}
