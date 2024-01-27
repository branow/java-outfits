package com.branow.outfits.util;

import java.util.Arrays;
import java.util.Comparator;
import java.util.Objects;

/**
 * Contains static methods for sorting arrays of Objects
 *
 * @author Orest Bodnar
 * @since 1.2.6
 */
public class Sorter {

    /**
     * Sort the given array by the <b>Bubble Sort Algorithm</b> O(n<sup>2</sup>)
     * (very inefficient way of sorting)
     *
     * @param comparator the comparator to compare elements of the array during sorting
     * @param array      the array to be sorted
     * @throws NullPointerException if the given array is null
     */
    @SafeVarargs
    public static <E> void sortBubble(Comparator<E> comparator, E... array) {
        checkArray(array);
        for (int i = 0; i < array.length - 1; i++) {
            for (int j = 0; j < array.length - 1 - i; j++) {
                if (comparator.compare(array[j], array[j + 1]) > 0) {
                    swap(array, j, j + 1);
                }
            }
        }
    }

    /**
     * Sort the given array by the <b>Selection Sort Algorithm</b> O(n<sup>2</sup>)
     * (better than bubble sort,
     * running time is independent of ordering of elements)
     *
     * @param comparator the comparator to compare elements of the array during sorting
     * @param array      the array to be sorted
     * @throws NullPointerException if the given array is null
     */
    @SafeVarargs
    public static <E> void sortSelection(Comparator<E> comparator, E... array) {
        checkArray(array);
        for (int i = 0; i < array.length - 1; i++) {
            int minIndex = i;
            for (int j = i + 1; j < array.length; j++) {
                if (comparator.compare(array[minIndex], array[j]) > 0) {
                    minIndex = j;
                }
            }
            swap(array, i, minIndex);
        }
    }


    /**
     * Sort the given array by the <b>Insertion Sort Algorithm</b> O(n<sup>2</sup>)
     * (relatively good for small lists,
     * relatively good for partially sorted lists)
     *
     * @param comparator the comparator to compare elements of the array during sorting
     * @param array      the array to be sorted
     * @throws NullPointerException if the given array is null
     */
    @SafeVarargs
    public static <E> void sortInsertion(Comparator<E> comparator, E... array) {
        checkArray(array);
        for (int i = 1; i < array.length; i++) {
            E current = array[i];
            int j = i;
            while (j > 0 && comparator.compare(array[j - 1], current) > 0) {
                array[j] = array[j - 1];
                j--;
            }
            array[j] = current;
        }
    }


    /**
     * Sort the given array by the <b>Merge Sort Algorithm</b> O(nlog<sub>2</sub>n)
     *
     * @param comparator the comparator to compare elements of the array during sorting
     * @param array      the array to be sorted
     * @throws NullPointerException if the given array is null
     */
    @SafeVarargs
    public static <E> void sortMerge(Comparator<E> comparator, E... array) {
        checkArray(array);
        sortMerge(comparator, array, 0, array.length);
    }

    private static <E> void sortMerge(Comparator<E> comparator, E[] array, int start, int end) {
        if (start < end - 1) {
            int mid = (start + end) / 2;
            sortMerge(comparator, array, start, mid);
            sortMerge(comparator, array, mid, end);
            merge(comparator, array, start, mid, end);
        }
    }

    private static <E> void merge(Comparator<E> comparator, E[] array, int start, int mid, int end) {
        E[] a1 = Arrays.copyOfRange(array, start, mid);
        E[] a2 = Arrays.copyOfRange(array, mid, end);
        for (int i = 0, j = 0, k = start; k < end; k++) {
            if (i >= a1.length) {
                array[k] = a2[j++];
            } else if (j >= a2.length) {
                array[k] = a1[i++];
            } else {
                array[k] = comparator.compare(a1[i], a2[j]) > 0 ? a2[j++] : a1[i++];
            }
        }
    }


    /**
     * Sort the given array by the <b>Quick Sort Algorithm</b><br/>
     * In worst case <b>O(n<sup>2</sup>)</b><br/>
     * In average case <b>O(nlog<sub>2</sub>n)</b><br/>
     *
     * @param comparator the comparator to compare elements of the array during sorting
     * @param array      the array to be sorted
     * @throws NullPointerException if the given array is null
     */
    @SafeVarargs
    public static <E> void sortQuick(Comparator<E> comparator, E... array) {
        checkArray(array);
        sortQuick(comparator, array, 0, array.length);
    }

    private static <E> void sortQuick(Comparator<E> comparator, E[] array, int start, int end) {
        if (start < end - 1) {
            int pivot = partition(comparator, array, start, end);
            sortQuick(comparator, array, start, pivot);
            sortQuick(comparator, array, pivot + 1, end);
        }
    }

    private static <E> int partition(Comparator<E> comparator, E[] array, int start, int end) {
        E pivot = array[end - 1];
        int i = start;
        for (int j = start; j < end; j++) {
            if (comparator.compare(pivot, array[j]) > 0) {
                swap(array, i++, j);
            }
        }
        swap(array, i, end - 1);
        return i;
    }


    private static void swap(Object[] array, int i, int j) {
        Object temp = array[i];
        array[i] = array[j];
        array[j] = temp;
    }

    private static <E> void checkArray(E[] array) {
        Objects.requireNonNull(array, "The given array must not be null");
    }

}
