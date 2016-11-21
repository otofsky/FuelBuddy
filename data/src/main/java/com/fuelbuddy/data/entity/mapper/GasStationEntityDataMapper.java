package com.fuelbuddy.data.entity.mapper;

/**
 * Created by zjuroszek on 08.11.16.
 */

import com.fuelbuddy.data.GasStation;
import com.fuelbuddy.data.entity.GasStationEntity;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * Mapper class used to transformToUser {@link com.fuelbuddy.data.entity.GasStationEntity} (in the data layer) to {@link com.fuelbuddy.data.GasStation} in the
 * domain layer.
 */

@Singleton
public class GasStationEntityDataMapper {


    @Inject
    public GasStationEntityDataMapper() {
    }

    public GasStation transform(GasStationEntity gasStationEntity) {
        GasStation gasStation = null;
        if (gasStationEntity != null) {
            gasStation = new GasStation(gasStationEntity.getName());

        }
        return gasStation;
    }


    public List<GasStation> transform(Collection<GasStationEntity> userEntityCollection) {
        List<GasStation> userList = new ArrayList<>(20);
        GasStation user;
        for (GasStationEntity userEntity : userEntityCollection) {
            user = transform(userEntity);
            if (user != null) {
                userList.add(user);
            }
        }
        return userList;
    }


}
