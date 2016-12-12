package com.fuelbuddy.data.repository.datasource.GasStationDataStore;

import com.fuelbuddy.data.Position;
import com.fuelbuddy.data.entity.GasStationEntity;
import com.fuelbuddy.data.entity.ResponseEntity;

import java.util.List;

import rx.Observable;

/**
 * Created by zjuroszek on 08.11.16.
 */
public interface GasStationDataStore {


    Observable<List<GasStationEntity>> gasStationsEntityList(Position position);

    Observable<ResponseEntity> updateStation(Double iD, String userID, Double price92
            ,Double price95, Double priceDiesel);

    Observable<GasStationEntity> gasStationEntityDetails(final int userId);

}
