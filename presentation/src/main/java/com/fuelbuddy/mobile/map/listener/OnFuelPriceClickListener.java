package com.fuelbuddy.mobile.map.listener;

import com.fuelbuddy.mobile.map.FuelPriceUpdate;
import com.fuelbuddy.mobile.model.GasStationModel;

/**
 * Created by zjuroszek on 10.12.16.
 */

public interface OnFuelPriceClickListener {

    void onFuelPriceClick(GasStationModel gasStationModel, FuelPriceUpdate fuelPriceUpdate);

}
