package com.branow.outfits.util;

import java.util.ArrayList;
import java.util.List;

public class Strings {

    public static String shift(String base, String start) {
        if (start.length() > base.length())
            start = start.substring(0, base.length()-1);
        int index = base.indexOf(start);
        while (index == -1) {
            start = start.substring(0, start.length()-1);
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
            for (int i=0; i<strings.size(); i++) {
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
