package com.fuelbuddy.mobile.map.controller;

import android.content.Context;

import com.fuelbuddy.mobile.model.GasStationModel;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;

import java.util.List;

/**
 * Created by zjuroszek on 03.12.16.
 */

public interface Map {



    public void initMap(Context context, GoogleMap map, GoogleMap.OnMarkerClickListener onMarkerClickListener);

    public void clear();

    public void showUserCurrentPosition(LatLng latLng);

    public void showSelectedGasStation(String gasStationId);

    public void seFuelStationsPositions(List<GasStationModel> gasStationModelList);




}
