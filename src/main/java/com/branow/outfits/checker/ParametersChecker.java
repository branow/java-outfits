package com.branow.outfits.checker;

import java.util.Collection;
import java.util.function.BiPredicate;
import java.util.function.Predicate;

public class ParametersChecker {

    public static final String ARRAY_NULL_OR_EMPTY = "array is null or empty",
            COLLECTION_NULL_OR_EMPTY = "collection is null or empty",
            STRING_NULL_OR_EMPTY = "string is null or empty", OBJECT_NULL = "object is null";

    public static String toLowerCaseAndStrip(String string) {
        return string.toLowerCase().strip();
    }

    public static  <T> T isNullGetValue(T base, T other) {
        return base == null ? other : base;
    }


    public static void isNullThrow(Object object) {
        isNullThrow(object, OBJECT_NULL);
    }

    public static void isNullThrow(Object object, String message) {
        if (object == null) throw new IllegalArgumentException(message);
    }

    public static void isNullThrow(Object object, RuntimeException exception) {
        if (object == null) throw exception;
    }


    public static void isNullOrEmptyThrow(String object) {
        isNullOrEmptyThrow(object, STRING_NULL_OR_EMPTY);
    }

    public static void isNullOrEmptyThrow(String object, String message) {
        if (object == null || object.isEmpty()) throw new IllegalArgumentException(message);
    }

    public static void isNullOrEmptyThrow(String object, RuntimeException exception) {
        if (object == null || object.isEmpty()) throw exception;
    }


    public static void isNullOrEmptyThrow(Collection<?> collection) {
        isNullOrEmptyThrow(collection, COLLECTION_NULL_OR_EMPTY);
    }

    public static void isNullOrEmptyThrow(Collection<?> collection, String message) {
        if (collection == null || collection.isEmpty()) throw new IllegalArgumentException(message);
    }

    public static void isNullOrEmptyThrow(Collection<?> collection, RuntimeException exception) {
        if (collection == null || collection.isEmpty()) throw exception;
    }


    public static<E> void isNullOrEmptyThrow(E[] array) {
        isNullOrEmptyThrow(array, ARRAY_NULL_OR_EMPTY);
    }

    public static<E> void isNullOrEmptyThrow(E[] array, String message) {
        if (array == null || array.length == 0) throw new IllegalArgumentException(message);
    }

    public static<E> void isNullOrEmptyThrow(E[] array, RuntimeException exception) {
        if (array == null || array.length == 0) throw exception;
    }


    public static void isNullOrEmptyThrow(int[] array) {
        isNullOrEmptyThrow(array, ARRAY_NULL_OR_EMPTY);
    }

    public static void isNullOrEmptyThrow(int[] array, String message) {
        if (array == null || array.length == 0) throw new IllegalArgumentException(message);
    }

    public static void isNullOrEmptyThrow(int[] array, RuntimeException exception) {
        if (array == null || array.length == 0) throw exception;
    }

    public static void isNullOrEmptyThrow(char[] array) {
        isNullOrEmptyThrow(array, ARRAY_NULL_OR_EMPTY);
    }

    public static void isNullOrEmptyThrow(char[] array, String message) {
        if (array == null || array.length == 0) throw new IllegalArgumentException(message);
    }

    public static void isNullOrEmptyThrow(char[] array, RuntimeException exception) {
        if (array == null || array.length == 0) throw exception;
    }

    public static void isNullOrEmptyThrow(byte[] array) {
        isNullOrEmptyThrow(array, ARRAY_NULL_OR_EMPTY);
    }

    public static void isNullOrEmptyThrow(byte[] array, String message) {
        if (array == null || array.length == 0) throw new IllegalArgumentException(message);
    }

    public static void isNullOrEmptyThrow(byte[] array, RuntimeException exception) {
        if (array == null || array.length == 0) throw exception;
    }

    public static void isNullOrEmptyThrow(boolean[] array) {
        isNullOrEmptyThrow(array, ARRAY_NULL_OR_EMPTY);
    }

    public static void isNullOrEmptyThrow(boolean[] array, String message) {
        if (array == null || array.length == 0) throw new IllegalArgumentException(message);
    }

    public static void isNullOrEmptyThrow(boolean[] array, RuntimeException exception) {
        if (array == null || array.length == 0) throw exception;
    }

    public static void isNullOrEmptyThrow(long[] array) {
        isNullOrEmptyThrow(array, ARRAY_NULL_OR_EMPTY);
    }

    public static void isNullOrEmptyThrow(long[] array, String message) {
        if (array == null || array.length == 0) throw new IllegalArgumentException(message);
    }

    public static void isNullOrEmptyThrow(long[] array, RuntimeException exception) {
        if (array == null || array.length == 0) throw exception;
    }

    public static void isNullOrEmptyThrow(double[] array) {
        isNullOrEmptyThrow(array, ARRAY_NULL_OR_EMPTY);
    }

    public static void isNullOrEmptyThrow(double[] array, String message) {
        if (array == null || array.length == 0) throw new IllegalArgumentException(message);
    }

    public static void isNullOrEmptyThrow(double[] array, RuntimeException exception) {
        if (array == null || array.length == 0) throw exception;
    }

    public static<P> void isTrueThrow(boolean predicate) {
        if (predicate) throw new IllegalArgumentException();
    }

    public static<P> void isTrueThrow(boolean predicate, String message) {
        if (predicate) throw new IllegalArgumentException(message);
    }

    public static<P> void isTrueThrow(boolean predicate, RuntimeException exception) {
        if (predicate) throw exception;
    }

    public static<P> void isTrueThrow(P p, Predicate<P> func) {
        if (func.test(p)) throw new IllegalArgumentException();
    }

    public static<P> void isTrueThrow(P p, Predicate<P> func, String message) {
        if (func.test(p)) throw new IllegalArgumentException(message);
    }

    public static<P> void isTrueThrow(P p, Predicate<P> func, RuntimeException exception) {
        if (func.test(p)) throw exception;
    }

    public static<P1, P2> void isTrueThrow(P1 p1, P2 p2, BiPredicate<P1, P2> func, RuntimeException exception) {
        if (func.test(p1, p2)) throw exception;
    }

    public static<P> void isFalseThrow(P p, Predicate<P> func) {
        if (!func.test(p)) throw new IllegalArgumentException();
    }

    public static<P> void isFalseThrow(P p, Predicate<P> func, String message) {
        if (!func.test(p)) throw new IllegalArgumentException(message);
    }

    public static<P> void isFalseThrow(P p, Predicate<P> func, RuntimeException exception) {
        if (!func.test(p)) throw exception;
    }

    public static<P1, P2> void isFalseThrow(P1 p1, P2 p2, BiPredicate<P1, P2> func, RuntimeException exception) {
        if (!func.test(p1, p2)) throw exception;
    }

}
