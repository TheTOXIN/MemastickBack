package com.memastick.backmem.main.util;

public class TextUtil {

    public static String clearSpaces(String text) {
        return text
            .trim()
            .replaceAll("\n", " ")
            .replaceAll("\\s{2,}", " ");
    }
}
