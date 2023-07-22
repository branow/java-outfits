package com.branow.outfits.print;

public enum Background implements Printer.TextAttribute {

    BORDER("51"), INVERT("7"),

    BLACK("40"), RED("41"), GREEN("42m"), YELLOW("43"), BLUE("44"),
    PURPLE("45"), CYAN("46"), WHITE("47"),

    BLACK_BRIGHT("100"), RED_BRIGHT("101"), GREEN_BRIGHT("102"), YELLOW_BRIGHT("103"),
    BLUE_BRIGHT("104"), PURPLE_BRIGHT("105"), CYAN_BRIGHT("106"), WHITE_BRIGHT("107");

    private final String code;

    Background(String code) {
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
