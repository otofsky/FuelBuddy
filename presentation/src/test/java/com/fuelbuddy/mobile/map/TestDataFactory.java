package com.fuelbuddy.mobile.map;

import com.fuelbuddy.data.FuelPriceMode;
import com.google.android.gms.maps.model.LatLng;

/**
 * Created by zjuroszek on 22.11.17.
 */

public class TestDataFactory {

    public static LatLng makeLatLng() {
        return new LatLng(27.66096, 83.52716);

    }
    public static FuelPriceMode makeFuelPriceMode(){
        return FuelPriceMode.BENZIN_92;
    }
}