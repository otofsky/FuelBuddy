package com.fuelbuddy.mobile.map;

import com.fuelbuddy.mobile.model.GasStationModel;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;

import java.util.List;

/**
 * Created by zjuroszek on 03.12.16.
 */

public interface Map {


    public void adjustMap();

    public void initMap(GoogleMap map,GoogleMap.OnMarkerClickListener onMarkerClickListener);

    public void clear();

    public void seUserCurrentPosition(LatLng latLng);

    public void seFuelStationsPositions(List<GasStationModel> gasStationModelList);

    public GasStationModel getItem(int position);


}
