package com.fuelbuddy.mobile.map.event;

import com.fuelbuddy.mobile.model.GasStationModel;

/**
 * Created by zjuroszek on 07.01.17.
 */

public class OnPriceClickEvent extends Event{
    GasStationModel gasStationModel;

    public OnPriceClickEvent(GasStationModel gasStationModel) {
        this.gasStationModel = gasStationModel;
    }

    public GasStationModel getGasStationModel() {
        return gasStationModel;
    }
}
