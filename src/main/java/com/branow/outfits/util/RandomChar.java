package com.branow.outfits.util;

public class RandomChar extends java.util.Random {

    public RandomChar() {
        super();
    }

    public RandomChar(long seed) {
        super(seed);
    }


    public char nextChar() {
        return (char) nextInt();
    }

    public void nextChars(char[] chars) {
        for (int i=0; i<chars.length; i++)
            chars[i] = nextChar();
    }

    public String nextString(int length) {
        char[] chars = new char[length];
        nextChars(chars);
        return new String(chars);
    }

}
