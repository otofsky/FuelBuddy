package com.fuelbuddy.mobile.mapper;

import com.fuelbuddy.data.Position;
import com.fuelbuddy.data.User;
import com.fuelbuddy.mobile.model.UserModel;
import com.google.android.gms.maps.model.LatLng;

/**
 * Created by zjuroszek on 02.12.16.
 */

public class PositionMapper {
    public Position transformToPosition(LatLng latLng) {
        if (latLng == null) {
            throw new IllegalArgumentException("Cannot transformToPosition a null value");
        }
        Position position = new Position(latLng.latitude,latLng.longitude);
        return position;
    }
}
