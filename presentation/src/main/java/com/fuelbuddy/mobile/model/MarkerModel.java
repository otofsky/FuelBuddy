package com.fuelbuddy.mobile.model;

import com.google.android.gms.maps.model.Marker;

/**
 * Created by zjuroszek on 23.12.16.
 */

public class MarkerModel {

    private GasStationModel gasStationModel;
    private Marker marker;

    public MarkerModel(GasStationModel gasStationModel, Marker marker) {
        this.gasStationModel = gasStationModel;
        this.marker = marker;
    }

    public GasStationModel getGasStationModel() {
        return gasStationModel;
    }

    public void setGasStationModel(GasStationModel gasStationModel) {
        this.gasStationModel = gasStationModel;
    }

    public Marker getMarker() {
        return marker;
    }

    public void setMarker(Marker marker) {
        this.marker = marker;
    }
}
