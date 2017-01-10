package com.fuelbuddy.mobile.util;

import android.content.Context;
import android.location.LocationManager;

import static android.content.Context.LOCATION_SERVICE;

/**
 * Created by zjuroszek on 18.12.16.
 */

public class LocationUtil {

    public static boolean isLocationEnabled(Context context) {
        LocationManager service = (LocationManager) context.getSystemService(LOCATION_SERVICE);
        boolean enabled = service.isProviderEnabled(LocationManager.GPS_PROVIDER);
        return enabled;

    }
}

