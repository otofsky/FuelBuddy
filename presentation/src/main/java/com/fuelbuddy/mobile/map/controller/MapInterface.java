package com.fuelbuddy.mobile.map.controller;

import android.content.Context;

import com.fuelbuddy.mobile.model.GasStationModel;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;

import java.util.List;

/**
 * Created by zjuroszek on 03.12.16.
 */

public interface MapInterface {

    public void initMap(Context context, GoogleMap map, MapController.OnMarkerClickCallback onMarkerClickCallback);

    public void clear();

    public void showUserCurrentPosition(LatLng latLng);

    public void showSelectedGasStation(String gasStationId);

    public void setFuelStationsPositions(List<GasStationModel> gasStationModelList, String id);

    public void setFuelStationsPositions();

    public void centerOnGasStation(boolean animate, LatLng latLng);



}
