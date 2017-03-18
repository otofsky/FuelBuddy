package com.fuelbuddy.mobile.map.event;

import com.fuelbuddy.mobile.base.Event;
import com.fuelbuddy.mobile.model.GasStationModel;

/**
 * Created by zjuroszek on 07.01.17.
 */

public class OnPriceClickEvent extends Event {
    GasStationModel gasStationModel;
    String selectedGasStationId;

    public OnPriceClickEvent(GasStationModel gasStationModel) {
        this.gasStationModel = gasStationModel;
    }

    public OnPriceClickEvent( String selectedGasStationId) {
        this.selectedGasStationId = selectedGasStationId;
    }

    public GasStationModel getGasStationModel() {
        return gasStationModel;
    }

    public String getSelectedGasStationId() {
        return selectedGasStationId;
    }
}
