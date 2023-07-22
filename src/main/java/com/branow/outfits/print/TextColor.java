package com.branow.outfits.print;

public enum TextColor implements Printer.TextAttribute {

    BLACK("30"), RED("31"), GREEN("32"), YELLOW("33"), BLUE("34"),
    PURPLE("35"), CYAN("36"), WHITE("37"),

    BLACK_BRIGHT("90"), RED_BRIGHT("91"), GREEN_BRIGHT("92"), YELLOW_BRIGHT("93"),
    BLUE_BRIGHT("94"), PURPLE_BRIGHT("95"), CYAN_BRIGHT("96"), WHITE_BRIGHT("97");

    private final String code;

    TextColor(String code) {
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
