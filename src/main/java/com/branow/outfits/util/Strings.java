package com.branow.outfits.util;

import java.util.*;
import java.util.stream.Stream;

import static com.branow.outfits.checker.ParametersChecker.isNullThrow;
import static java.lang.Math.*;

public class Strings {

    /**
     * Calculates the percentage of the strings' similarity with OSA distance algorithm.
     *
     * @param s1 The first string.
     * @param s2 The second string.
     * @return The percentage of the strings' similarity.
     * @see #calcOptimalStringAlignmentDistance(String, String)
     */
    public static double calcStringsSimilarityOSAD(String s1, String s2) {
        double len = max(s1.length(), s2.length());
        return 1 - calcOptimalStringAlignmentDistance(s1, s2) / len;
    }

    /**
     * Returns the number of operations that should be done on the second string to make both strings similar.
     *
     * @param s1 The first string.
     * @param s2 The second string.
     * @return The number of operations that includes: deletion, insertion, substitution, transposition.
     * @throws NullPointerException if either parameter is null.
     * @see <a href="https://en.wikipedia.org/wiki/Damerau%E2%80%93Levenshtein_distance">OSA algorithm</a>
     */
    public static int calcOptimalStringAlignmentDistance(String s1, String s2) {
        isNullThrow(s1, new NullPointerException("The first string mustn't be null"));
        isNullThrow(s2, new NullPointerException("The second string mustn't be null"));

        int[][] d = new int[s1.length() + 1][s2.length() + 1];

        for (int i = 0; i <= s1.length(); i++)
            d[i][0] = i;
        for (int j = 0; j <= s2.length(); j++)
            d[0][j] = j;

        for (int i = 1; i <= s1.length(); i++) {
            for (int j = 1; j <= s2.length(); j++) {
                int cost = s1.charAt(i - 1) == s2.charAt(j - 1) ? 0 : 1;
                d[i][j] = Stream.of(
                        d[i - 1][j] + 1,    //deletion
                        d[i][j - 1] + 1,            //insertion
                        d[i - 1][j - 1] + cost      //substitution
                ).min(Integer::compareTo).get();
                if (i > 1 && j > 1 && s1.charAt(i - 1) == s2.charAt(j - 2) &&
                        s1.charAt(i - 2) == s2.charAt(j - 1)) {
                    d[i][j] = min(d[i][j], d[i - 2][j - 2] + 1);     //transposition
                }
            }
        }
        return d[s1.length()][s2.length()];
    }

    public static String shift(String base, String start) {
        if (start.length() > base.length())
            start = start.substring(0, base.length() - 1);
        int index = base.indexOf(start);
        while (index == -1) {
            start = start.substring(0, start.length() - 1);
            if (start.isEmpty())
                throw new IllegalArgumentException("base doesn't contains start");
            index = base.indexOf(start);
        }
        return shift(base, index);
    }

    public static String shift(String base, int start) {
        return base.substring(start) + base.substring(0, start);
    }

    public static String getUnique(String string) {
        boolean equal = false;
        int next = string.indexOf(string.charAt(0), 1);
        while (next < string.length() && next > 0) {
            String unique = string.substring(0, next);
            String after = string.substring(next);
            if (unique.length() > after.length())
                equal = unique.substring(0, after.length()).equals(after);
            else if (unique.length() < after.length())
                equal = unique.equals(after.substring(0, unique.length()));
            else
                equal = unique.equals(after);
            if (equal)
                return unique;
            next = string.indexOf(string.charAt(0), next + 1);
        }
        return string;
    }

    public static String merge(List<String> phrases) {
        List<String> strings = new ArrayList<>(phrases);
        String result = strings.get(0);
        strings.remove(result);
        int iMax = 0;
        int cross = 0;
        while (!strings.isEmpty()) {
            for (int i = 0; i < strings.size(); i++) {
                int cCross = getCrossing(result, strings.get(i)).length();
                if (cCross > cross) {
                    cross = cCross;
                    iMax = i;
                }
            }
            result = merge(result, strings.get(iMax), getCrossing(result, strings.get(iMax)));
            strings.remove(iMax);
            iMax = 0;
            cross = 0;
        }
        return result;
    }

    public static String merge(String s1, String s2, String cross) {
        int i1 = s1.indexOf(cross);
        int i2 = s2.indexOf(cross);
        return i1 > i2 ?
                s1.substring(0, i1) + s2.substring(i2) :
                s2.substring(0, i2) + s1.substring(i1);
    }

    public static String getCrossing(String s1, String s2) {
        if (s1.contains(s2)) return s2;
        if (s2.contains(s1)) return s1;
        return getCrossingByMowing(s1, s2);
    }

    public static String getCrossingByMowing(String s1, String s2) {
        String cross1 = getCrossingByMowingOne(s1, s2);
        String cross2 = getCrossingByMowingOne(s2, s1);
        return cross1.length() > cross2.length() ? cross1 : cross2;
    }

    public static String getCrossingByMowingOne(String base, String moved) {
        while (!base.equals(moved)) {
            if (base.length() == 0 || moved.length() == 0)
                return "";
            if (base.length() > moved.length()) {
                base = base.substring(1);
            } else if (base.length() < moved.length()) {
                moved = moved.substring(0, moved.length() - 1);
            } else {
                base = base.substring(1);
                moved = moved.substring(0, moved.length() - 1);
            }
        }
        return base;
    }


}
