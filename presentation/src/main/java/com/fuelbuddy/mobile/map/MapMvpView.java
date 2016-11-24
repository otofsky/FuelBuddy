package com.fuelbuddy.mobile.map;

import com.fuelbuddy.data.GasStation;
import com.fuelbuddy.mobile.base.MvpView;
import com.fuelbuddy.mobile.model.GasStationModel;

import java.util.List;

/**
 * Created by zjuroszek on 25.10.16.
 */
public interface MapMvpView extends MvpView {

    public void removeMarkers();

    public void showInfoTest(String info);

    public void showMarkerAt(float latitude, float longitude);

    public void showMarkersAt(float latitude, float longitude);

    public void showFuelPriceBars(List<GasStationModel> gasStationModelList);

}
