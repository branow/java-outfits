package com.branow.outfits.print;

public enum TextType implements Printer.TextAttribute {

    BOLD("1"), CROSS("9"), ITALICS("3"), UNDERLINED("4"), UNDERLINED_BOLD("21");

    private final String code;

    TextType(String code) {
        this.code = code;
    }

    @Override
    public String getCode() {
        return code;
    }

    @Override
    public String toString() {
        return getCode();
    }

}
