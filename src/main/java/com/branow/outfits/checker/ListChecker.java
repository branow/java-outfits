package com.branow.outfits.checker;

import java.util.List;

public class ListChecker {

    public static<E> boolean equalsIgnoreOrder(List<E> l1, List<E> l2) {
        return isSubListIgnoreOrder(l1, l2) && isSubListIgnoreOrder(l2, l1);
    }

    public static<E> boolean isSubListIgnoreOrder(List<E> sub, List<E> base) {
        for (E e: sub)
            if (!base.contains(e)) return false;
        return true;
    }

}
