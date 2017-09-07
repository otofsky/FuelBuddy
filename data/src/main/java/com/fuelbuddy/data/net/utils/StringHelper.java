package com.fuelbuddy.data.net.utils;

import android.support.annotation.Nullable;


/**
 * Created by zjuroszek on 21.07.16.
 */
public class StringHelper {

    @Nullable
    public static String emptyToNull(@Nullable String string) {
        return isNullOrEmpty(string) ? null : string;
    }


    public static boolean isNullOrEmpty(@Nullable String string) {
        return string == null || string.isEmpty();
    }

}
