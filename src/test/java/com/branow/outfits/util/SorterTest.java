package com.branow.outfits.util;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.Arrays;
import java.util.Comparator;
import java.util.stream.Stream;

public class SorterTest {

    @ParameterizedTest
    @MethodSource("provideTestSort")
    public <E> void sortQuick(Comparator<E> comparator, E[] actual) {
        sort(comparator, Sorter::sortQuick, actual);
    }

    @ParameterizedTest
    @MethodSource("provideTestSort")
    public <E> void sortMerge(Comparator<E> comparator, E[] actual) {
        sort(comparator, Sorter::sortMerge, actual);
    }

    @ParameterizedTest
    @MethodSource("provideTestSort")
    public <E> void sortInsertion(Comparator<E> comparator, E[] actual) {
        sort(comparator, Sorter::sortInsertion, actual);
    }

    @ParameterizedTest
    @MethodSource("provideTestSort")
    public <E> void sortSelection(Comparator<E> comparator, E[] actual) {
        sort(comparator, Sorter::sortSelection, actual);
    }

    @ParameterizedTest
    @MethodSource("provideTestSort")
    public <E> void sortBubble(Comparator<E> comparator, E[] actual) {
        sort(comparator, Sorter::sortBubble, actual);
    }

    public <E> void sort(Comparator<E> comparator, SorterInterface<E> algorithm, E[] actual) {
        E[] expected = (E[]) Arrays.stream(actual).sorted(comparator).toArray();
        algorithm.sort(comparator, actual);
        Assertions.assertArrayEquals(expected, actual);
    }


    private static Stream<Arguments> provideTestSort() {
        Comparator<Integer> comparator = Comparator.comparingInt(i -> i);
        return Stream.of(
                Arguments.of(
                        comparator,
                        new Integer[] {5, 1, 789, -5, 5, 0, 45}
                        )
        );
    }

    @FunctionalInterface
    private interface SorterInterface<E> {

        void sort(Comparator<E> comparator, E[] array);

    }

}
