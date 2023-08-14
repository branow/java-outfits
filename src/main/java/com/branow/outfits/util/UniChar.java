package com.branow.outfits.util;

import java.util.Arrays;

/**
 * The UniChar is a sequence of four or fewer bytes that codes a matching character.
 * The class keeps a code of a character in a byte array but there are also methods
 * to transform it to an integer value, a char array, a char or a string.
 */
public class UniChar {

    private final byte[] bytes;

    /**
     * It writes only first four bytes all others is skipped. If array hava less length than four bytes,
     * zero bytes are written at the beginning.
     *
     * @param code The byte representation of a character.
     */
    public UniChar(byte[] code) {
        this.bytes = normalize(code);
    }

    /**
     * @param c The character.
     * @see ByteConverter#intToByteArray(int)
     */
    public UniChar(char c) {
        this(ByteConverter.intToByteArray(c));
    }

    /**
     * @param code The integer representation of a character.
     * @see ByteConverter#intToByteArray(int)
     */
    public UniChar(int code) {
        this(ByteConverter.intToByteArray(code));
    }


    /**
     * Converts this byte sequence to a new character array, where the
     * first two bytes form the first character, the second tho bytes - second character.
     *
     * @return The character array with length equaling two chars.
     * @see ByteConverter#bytesToChar(byte, byte)
     */
    public char[] toCharArray() {
        byte[] res = new byte[4];
        if (bytes.length < 4)
            System.arraycopy(bytes, 0, res, 4 - bytes.length, bytes.length);
        else
            res = bytes;
        return new char[] {
                ByteConverter.bytesToChar(res[0], res[1]),
                ByteConverter.bytesToChar(res[2], res[3])
        };
    }

    /**
     * Converts this last two bytes of bytes sequence to a new character.
     *
     * @return The character formed of the last two bytes.
     * @see ByteConverter#bytesToChar(byte, byte)
     */
    public char toChar() {
        byte higher = bytes.length - 2 >= 0 ? bytes[bytes.length - 2] : 0;
        byte lower = bytes.length - 1 >= 0 ? bytes[bytes.length - 1] : 0;
        return ByteConverter.bytesToChar(higher, lower);
    }

    /**
     * Converts this byte sequence to a new integer value.
     *
     * @return The integer value formed of these bytes.
     * @see ByteConverter#byteArrayToInt(byte[])
     */
    public int toInt() {
        return ByteConverter.byteArrayToInt(bytes);
    }

    /**
     * Returns copy of these byte array.
     *
     * @return The copy of these byte array.
     */
    public byte[] toBytes() {
        return Arrays.copyOf(bytes, bytes.length);
    }


    @Override
    public String toString() {
        return new String(bytes);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UniChar uniChar = (UniChar) o;
        return Arrays.equals(bytes, uniChar.bytes);
    }

    @Override
    public int hashCode() {
        return Arrays.hashCode(bytes);
    }


    private static byte[] normalize(byte[] code) {
        int end = Math.min(code.length, 4);
        int start = 0;
        for (int i=0; i<end; i++) {
            if (code[i] == 0)
                start++;
            else
                break;
        }
        return Arrays.copyOfRange(code, start, end);
    }
}
