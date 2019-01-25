package com.memastick.backmem.main.util;

public class MathUtil {

    public static int rand(int from, int to) {
        return from + (int) (Math.random() * ((to - from) + 1));
    }

}
