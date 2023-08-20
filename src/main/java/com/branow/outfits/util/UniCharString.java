package com.branow.outfits.util;

import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * The {@code UniCharString} is an adaptable class between string and uni char array.
 * It has two static methods to convert string to uni char array and vice versa.
 * And it is also possible to create an instance of class that keeps uni char array
 * and lets to get string of it.
 * */
public class UniCharString {

    /**
     * Converts the given uni char array to string. It converts every uni char
     * calling {@link UniChar#toString(Charset)} and then connects them.
     *
     * @param uniChars The uni char array to convert.
     * @param charset The charset to decode uni char to string.
     * @return The string of the given uni char array.
     * */
    public static String toString(UniChar[] uniChars, Charset charset) {
        return Arrays.stream(uniChars)
                .map(uniChar -> uniChar.toString(charset))
                .collect(Collectors.joining());
    }

    /**
     * Converts the given string to uni char array. It uses {@link UniCharMapper}
     * to get uni chars from string.
     *
     * @param str The string to convert.
     * @param charset The charset to decode string to uni chars array.
     * @return The uni char array of the given string.
     * */
    public static UniChar[] toUniChars(String str, Charset charset) {
        List<UniChar> uniChars = new ArrayList<>();
        UniCharMapper mapper = new UniCharMapper(ByteBuffer.wrap(str.getBytes(charset)), charset);
        while (mapper.hasNext()) {
            uniChars.add(mapper.next());
        }
        return uniChars.toArray(UniChar[]::new);
    }


    private final UniChar[] uniChars;

    /**
     * Calls {@link UniCharString#UniCharString(UniChar[])}. It uses
     * {@link UniCharString#toUniChars(String, Charset)} to convert given
     * string to uni chars. It uses default charset - {@link Charset#defaultCharset()}.
     *
     * @param str The uni char array that this class keeps.
     * */
    public UniCharString(String str) {
        this(toUniChars(str, Charset.defaultCharset()));
    }

    /**
     * Calls {@link UniCharString#UniCharString(UniChar[])}. It uses
     * {@link UniCharString#toUniChars(String, Charset)} to convert given
     * string to uni chars.
     *
     * @param str The string to convert ot uni char array.
     * @param charset The charset to decode string to uni chars array.
     * */
    public UniCharString(String str, Charset charset) {
        this(toUniChars(str, charset));
    }

    /**
     * @param uniChars The uni char array that this class keeps.
     * */
    public UniCharString(UniChar[] uniChars) {
        this.uniChars = uniChars;
    }


    /**
     * Returns a copy of this uni char array.
     *
     * @return The copy of this uni char array.
     * */
    public UniChar[] toUniChars() {
        return Arrays.copyOf(uniChars, uniChars.length);
    }

    /**
     * Returns a string of this uni char array. It calls
     * {@link UniCharString#toUniChars(String, Charset)} with default
     * charset - {@link Charset#defaultCharset()}.
     *
     * @return The string of this uni char array.
     * */
    @Override
    public String toString() {
        return toString(uniChars, Charset.defaultCharset());
    }

    /**
     * Returns a string of this uni char array. It calls
     * {@link UniCharString#toUniChars(String, Charset)}.
     *
     * @param charset The charset to decode uni chars to string.
     * @return The string of this uni char array.
     * */
    public String toString(Charset charset) {
        return toString(uniChars, charset);
    }

    /**
     * Compares this {@code UniCharString} to the given object. These objects are equal
     * if their {@code uniChars} are equal.
     *
     * @param o The given object to compare.
     * @return {@code True} if this object equals to the given.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UniCharString that = (UniCharString) o;
        return Arrays.equals(uniChars, that.uniChars);
    }

    /**
     * Calculates a hash code of this object by {@code uniChars}.
     *
     * @return The hash code of this object.
     */
    @Override
    public int hashCode() {
        return Arrays.hashCode(uniChars);
    }
}
