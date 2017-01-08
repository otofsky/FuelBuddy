package com.fuelbuddy.mobile.mapper;

import com.fuelbuddy.mobile.model.FuelModel;
import com.fuelbuddy.mobile.model.GasStationModel;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

/**
 * Created by zjuroszek on 06.01.17.
 */

public class FuelMapper {

    @Inject
    public FuelMapper() {
    }

    public List<FuelModel> transferToFuelModelList(GasStationModel gasStationModel) {
        List<FuelModel> fuelModelList = new ArrayList<>();
        if (gasStationModel != null) {
            String price92 = gasStationModel.getPrice92();
            String price95 = gasStationModel.getPrice95();
            String priceDiesel = gasStationModel.getPriceDiesel();
            fuelModelList.add(new FuelModel(price92, FuelModel.Fuel.NATURAL92));
            fuelModelList.add(new FuelModel(price95, FuelModel.Fuel.NATURAL95));
            fuelModelList.add(new FuelModel(priceDiesel, FuelModel.Fuel.DIESEL));
        }
        return fuelModelList;
    }
}
