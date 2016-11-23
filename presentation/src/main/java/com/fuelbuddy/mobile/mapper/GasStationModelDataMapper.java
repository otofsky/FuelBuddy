package com.fuelbuddy.mobile.mapper;

/**
 * Created by zjuroszek on 08.11.16.
 */

import com.fuelbuddy.data.GasStation;
import com.fuelbuddy.data.entity.GasStationEntity;
import com.fuelbuddy.mobile.model.GasStationModel;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;



@Singleton
public class GasStationModelDataMapper {


    @Inject
    public GasStationModelDataMapper() {
    }

    public GasStationModel transform(GasStation gasStation) {
        GasStationModel gasStationModel = null;
        if (gasStationModel != null) {
            gasStationModel = new GasStationModel();
            gasStationModel.setDistance(gasStationModel.getDistance());
            gasStationModel.setGasStationId(gasStationModel.getGasStationId());
            gasStationModel.setGasStationLatitude(gasStationModel.getGasStationLatitude());
            gasStationModel.setGasStationLongitude(gasStationModel.getGasStationLongitude());
            gasStationModel.setGasStationName(gasStationModel.getGasStationName());
            gasStationModel.setPrice92(gasStationModel.getPrice92());
            gasStationModel.setPrice95(gasStationModel.getPrice95());
            gasStationModel.setPriceDiesel(gasStationModel.getPriceDiesel());
            gasStationModel.setTimeUpdated(gasStationModel.getTimeUpdated());
        }
        return gasStationModel;
    }


    public List<GasStationModel> transform(Collection<GasStation> gasStationEntityCollection) {
        List<GasStationModel> userList = new ArrayList<>(20);
        GasStationModel gasStationModel;
        for (GasStation gasStation : gasStationEntityCollection) {
            gasStationModel = transform(gasStation);
            if (gasStationModel != null) {
                userList.add(gasStationModel);
            }
        }
        return userList;
    }


}
