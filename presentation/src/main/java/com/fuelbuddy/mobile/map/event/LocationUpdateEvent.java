package com.fuelbuddy.mobile.map.event;

import com.google.android.gms.maps.model.LatLng;

/**
 * Created by zjuroszek on 02.12.16.
 */

public class LocationUpdateEvent {
    private LatLng mLatLng;

    public LocationUpdateEvent(LatLng latLng) {
        mLatLng = latLng;
    }

    public LatLng getLatLng() {
        return mLatLng;
    }
}
