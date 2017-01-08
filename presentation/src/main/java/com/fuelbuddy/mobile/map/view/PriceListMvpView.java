package com.fuelbuddy.mobile.map.view;

import com.fuelbuddy.mobile.base.MvpView;
import com.fuelbuddy.mobile.model.GasStationModel;

import java.util.List;

/**
 * Created by zjuroszek on 25.12.16.
 */

public interface PriceListMvpView extends MvpView {

    public void showFuelPriceBars(List<GasStationModel> gasStationModelList);

}
