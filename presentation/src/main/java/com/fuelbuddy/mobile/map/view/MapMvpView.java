package com.fuelbuddy.mobile.map.view;

import com.fuelbuddy.data.GasStation;
import com.fuelbuddy.mobile.base.MvpView;
import com.fuelbuddy.mobile.model.GasStationModel;

import java.util.List;

/**
 * Created by zjuroszek on 25.10.16.
 */
public interface MapMvpView extends MvpView {

    public void showGasStations(List<GasStationModel> gasStationModelList);

    public void showSuccessMessage(String message);

    public void refreshFuelPrices();
}
