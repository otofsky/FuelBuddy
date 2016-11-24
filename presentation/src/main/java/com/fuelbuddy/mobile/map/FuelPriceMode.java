package com.fuelbuddy.mobile.map;

/**
 * Created by zjuroszek on 24.11.16.
 */

public enum FuelPriceMode {

    BENZIN_92("92"), BENZIN_95("95"), DIESEL("Diesel");

    String fuelType;

    FuelPriceMode(String fuelType) {
        this.fuelType = fuelType;
    }

    public String getFuelType() {
        return fuelType;
    }


}
