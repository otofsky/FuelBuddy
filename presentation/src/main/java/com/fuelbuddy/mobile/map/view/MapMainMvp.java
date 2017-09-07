package com.fuelbuddy.mobile.map.view;

import com.fuelbuddy.mobile.base.MvpView;
import com.fuelbuddy.mobile.model.GasStationModel;

import java.util.List;

/**
 * Created by zjuroszek on 25.10.16.
 */
public interface MapMainMvp extends MvpView {

    void showGasStations(List<GasStationModel> gasStationModelList);

    void showSuccessMessage(String message);

    void refreshFuelPrices();
}
