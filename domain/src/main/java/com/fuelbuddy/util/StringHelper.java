package com.fuelbuddy.util;

/**
 * Created by zjuroszek on 21.07.16.
 */
public class StringHelper {

    public static String emptyToNull(String string) {
        return isNullOrEmpty(string) ? null : string;
    }

    public static boolean isNullOrEmpty(String string) {
        return string == null || string.isEmpty();
    }

}
