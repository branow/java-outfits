package com.branow.outfits.util;

public class BitOperations {

    public static int xor(int o1, int o2) {
        return (o1 & (~o2)) ^ ((~o1) & o2);
    }

    public static int xnor(int o1, int o2) {
        return ~xor(o1, o2);
    }

    public static String toBinaryString(int value) {
        return Integer.toBinaryString(value);
    }

}
