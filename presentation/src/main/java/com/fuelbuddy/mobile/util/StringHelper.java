package com.fuelbuddy.mobile.util;

import android.support.annotation.Nullable;

import com.google.android.gms.maps.model.LatLng;


/**
 * Created by zjuroszek on 21.07.16.
 */
public class StringHelper {

    private static String NAV_GOOGLE_PREFIX = "google.navigation:q=";


    @Nullable
    public static String emptyToNull(@Nullable String string) {
        return isNullOrEmpty(string) ? null : string;
    }


    public static boolean isNullOrEmpty(@Nullable String string) {
        return string == null || string.isEmpty();
    }


    public static String getNavigationUrl(LatLng latLng) {
        double lat = getLat(latLng);
        double lng = getLong(latLng);
        return getNavigationUrl(String.valueOf(lat), String.valueOf(lng));
    }

    public static String concat(String a, String b) {
        StringBuilder stringBuilder = new StringBuilder(a);
        stringBuilder.append(" ");
        stringBuilder.append(b);
        return stringBuilder.toString();
    }

    private static double getLong(LatLng latLng) {
        return latLng.longitude;
    }

    private static double getLat(LatLng latLng) {
        return latLng.latitude;
    }

    public static String getNavigationUrl(String lat, String lng) {
        StringBuilder stringBuilder = new StringBuilder(NAV_GOOGLE_PREFIX);
        stringBuilder.append(lat);
        stringBuilder.append(",");
        stringBuilder.append(lng);
        return stringBuilder.toString();

    }


}
