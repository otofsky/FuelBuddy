package com.fuelbuddy.mobile.map.controller;

import android.content.Context;

import com.fuelbuddy.data.FuelPriceMode;
import com.fuelbuddy.mobile.model.GasStationModel;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;

import java.util.List;

/**
 * Created by zjuroszek on 03.12.16.
 */

public interface MapInterface {

    void initMap(Context context, GoogleMap map, MapController.OnMarkerClickCallback onMarkerClickCallback);

    void clear();

    void showUserCurrentPosition(LatLng latLng);

    void showSelectedGasStation(String gasStationId);

    void setFuelStationsPositions(List<GasStationModel> gasStationModelList, FuelPriceMode fuelPriceMode);

    void setFuelStationsPositions();

    void centerOnPosition(boolean animate, LatLng latLng);

    void setClientPositions(LatLng latLng);

}
