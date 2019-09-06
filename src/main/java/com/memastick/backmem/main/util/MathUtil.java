package com.memastick.backmem.main.util;

public class MathUtil {

    public static int rand(int to) {
        return rand(0, to);
    }

    public static int rand(int from, int to) {
        return from + (int) (Math.random() * ((to - from) + 1));
    }

    public static boolean randBool() {
        return rand(0, 1) == 0;
    }

    public static int absents(int a, int b) {
        return Math.abs(a - b);
    }
}
