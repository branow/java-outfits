package com.branow.outfits.util;

import java.util.List;
import java.util.stream.Collectors;

public class Lists {


    public static String join(String delimiter, List<?> list) {
        return list.stream().map(Object::toString).collect(Collectors.joining(delimiter));
    }

    public static<E> List<E> longer(List<E> l1, List<E> l2) {
        return l1.size() > l2.size() ? l1 : l2;
    }

}
