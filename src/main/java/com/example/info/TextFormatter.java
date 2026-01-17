package com.example.info;

public class TextFormatter {

    public static String color(String text) {
        return text.replaceAll("&([0-9a-fk-or])", "§$1");
    }
}
