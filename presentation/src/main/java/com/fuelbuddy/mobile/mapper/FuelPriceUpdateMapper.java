package com.fuelbuddy.mobile.mapper;

import com.fuelbuddy.data.FuelPricesUpdate;
import com.fuelbuddy.data.GasStation;
import com.fuelbuddy.mobile.model.FuelPricesUpdateEntry;
import com.fuelbuddy.mobile.model.GasStationModel;

import javax.inject.Inject;

/**
 * Created by zjuroszek on 12.02.17.
 */

public class FuelPriceUpdateMapper {

    @Inject
    public FuelPriceUpdateMapper() {
    }

    public FuelPricesUpdateEntry transform(FuelPricesUpdate fuelPricesUpdate)
     {
        FuelPricesUpdateEntry fuelPricesUpdateEntry = null;
        if (fuelPricesUpdate != null) {
            fuelPricesUpdateEntry = new FuelPricesUpdateEntry();
            fuelPricesUpdateEntry.setPrice92(fuelPricesUpdate.getPrice92());
            fuelPricesUpdateEntry.setPrice95(fuelPricesUpdate.getPrice95());
            fuelPricesUpdateEntry.setPriceDiesel(fuelPricesUpdate.getPriceDiesel());
            fuelPricesUpdateEntry.setStationID(fuelPricesUpdate.getStationID());
            fuelPricesUpdateEntry.setUserID(fuelPricesUpdate.getUserID());
        }
        return fuelPricesUpdateEntry;
    }



}
