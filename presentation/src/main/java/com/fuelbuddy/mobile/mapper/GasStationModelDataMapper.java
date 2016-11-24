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
        if (gasStation != null) {
            gasStationModel = new GasStationModel();
            gasStationModel.setDistance(gasStation.getDistance());
            gasStationModel.setGasStationId(gasStation.getGasStationId());
            gasStationModel.setGasStationLatitude(gasStation.getGasStationLatitude());
            gasStationModel.setGasStationLongitude(gasStation.getGasStationLongitude());
            gasStationModel.setGasStationName(gasStation.getGasStationName());
            gasStationModel.setPrice92(gasStation.getPrice92());
            gasStationModel.setPrice95(gasStation.getPrice95());
            gasStationModel.setPriceDiesel(gasStation.getPriceDiesel());
            gasStationModel.setTimeUpdated(gasStation.getTimeUpdated());
        }
        return gasStationModel;
    }


    public List<GasStationModel> transform(Collection<GasStation> gasStationEntityCollection) {
        List<GasStationModel> userList = new ArrayList<>();
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
