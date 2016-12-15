package com.fuelbuddy.mobile.util;

import android.support.annotation.NonNull;

import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

/**
 * Created by zjuroszek on 15.12.16.
 */

public class MapUtil {


    public static final int ZOOM = 12;

    public enum MarkerType {
        STATION, USER
    }

    public static MarkerOptions initMarkerOptions(String title, LatLng currentPositionLatLng,MarkerType markerType, int icon) {
        return new MarkerOptions().position(currentPositionLatLng)
                .position(currentPositionLatLng)
                .title(title)
                .icon(getBitmapDescriptor(markerType,icon));
    }

    private static BitmapDescriptor getBitmapDescriptor(MarkerType markerType, int icon){
        BitmapDescriptor bitmapDescriptor = null;
        switch (markerType){
            case STATION:
                bitmapDescriptor =  BitmapDescriptorFactory.fromResource(icon);
                break;
            case USER:
                bitmapDescriptor = BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED);
                break;
        }
        return bitmapDescriptor;
    }
}
