package com.absolutecinema.utils;

public class DurationFormatter {
    public static String format(int duration) {
        int hours = duration / 60;
        int minutes = duration % 60;

        return hours + "h " + minutes + "m";
    }
}
