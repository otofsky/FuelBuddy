package com.fuelbuddy.data.repository.datasource;

import com.fuelbuddy.data.entity.GasStationEntity;

import java.util.List;

import rx.Observable;

/**
 * Created by zjuroszek on 08.11.16.
 */
public interface GasStationDataStore {


    Observable<List<GasStationEntity>> userEntityList();

    Observable<GasStationEntity> userEntityDetails(final int userId);

}
