package com.fuelbuddy;

import com.fuelbuddy.data.FuelPricesUpdate;

import javax.inject.Inject;

/**
 * Created by zjuroszek on 11.01.17.
 */

public class FuelUpdateFactory {
    @Inject
    public FuelUpdateFactory() {
    }

    public FuelPricesUpdate createFuelUpdate(String iD, String userID, String fuel92, String fuel95, String diesel) {
        return new FuelPricesUpdate(iD, userID, Double.valueOf(fuel92), Double.valueOf(fuel95), Double.valueOf(diesel));
    }

}
