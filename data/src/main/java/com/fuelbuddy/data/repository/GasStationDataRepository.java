package com.fuelbuddy.data.repository;

import com.fuelbuddy.data.GasStation;
import com.fuelbuddy.repository.GasStationsRepository;

import java.util.List;

import rx.Observable;

/**
 * Created by zjuroszek on 06.11.16.
 */

public class GasStationDataRepository implements GasStationsRepository {


    @Override
    public Observable<List<GasStation>> gasStations() {
        return null;
    }

    @Override
    public Observable<GasStation> gasStation(int userId) {
        return null;
    }
}
