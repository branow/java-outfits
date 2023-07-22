package com.branow.outfits.print;

import java.util.Arrays;
import java.util.stream.Collectors;

public class Printer {

    public interface TextAttribute {
        String getCode();
    }

    private static final String PREFIX = "\033[";

    public static void printf(String message, Object[] objects, TextAttribute... attributes) {
        setAttributes(attributes);
        System.out.printf(message, objects);
        reset();
    }

    public static void print(String message, TextAttribute... attributes) {
        setAttributes(attributes);
        System.out.print(message);
        reset();
    }

    public static void println(String message, TextAttribute... attributes) {
        setAttributes(attributes);
        System.out.println(message);
        reset();
    }

    public static void reset() {
        setAttributes();
    }

    private static void setAttributes(TextAttribute... attributes) {
        System.out.print(compose(attributes));
    }

    private static String compose(TextAttribute... attributes) {
        return PREFIX + Arrays.stream(attributes)
                .map(TextAttribute::getCode)
                .collect(Collectors.joining(";")) + "m";
    }


    private final TextAttribute[] attributes;

    public Printer(TextAttribute... attributes) {
        this.attributes = attributes;
    }

    public void print(String message) {
        print(message, attributes);
    }

    public void println(String message) {
        println(message, attributes);
    }

    public void printf(String message, Object... objects) {
        printf(message, objects, attributes);
    }

}
