package com.fuelbuddy.data.repository;

import com.fuelbuddy.data.GasStation;
import com.fuelbuddy.data.entity.GasStationEntity;
import com.fuelbuddy.data.entity.mapper.GasStationEntityDataMapper;
import com.fuelbuddy.data.net.RestApi;
import com.fuelbuddy.data.net.RestApiImpl;
import com.fuelbuddy.data.repository.datasource.GasStationDataStore;
import com.fuelbuddy.data.repository.datasource.GasStationStoreFactory;
import com.fuelbuddy.repository.GasStationsRepository;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import rx.Observable;
import rx.functions.Action1;
import rx.functions.Func0;
import rx.functions.Func1;

/**
 * Created by zjuroszek on 06.11.16.
 */


@Singleton
public class GasStationDataRepository implements GasStationsRepository {

    private final GasStationStoreFactory mGasStationStoreFactory;
    GasStationEntityDataMapper mGasStationEntityDataMapper;


    @Inject
    public GasStationDataRepository(GasStationEntityDataMapper gasStationEntityDataMapper,GasStationStoreFactory mGasStationStoreFactory ) {
        mGasStationEntityDataMapper = gasStationEntityDataMapper;
        this.mGasStationStoreFactory = mGasStationStoreFactory;
    }

    @Override
    public Observable<List<GasStation>> gasStations() {
        GasStationDataStore gasStationDataStore = mGasStationStoreFactory.createCloudDataStore();
        return gasStationDataStore.userEntityList().map(new Func1<List<GasStationEntity>, List<GasStation>>() {
            @Override
            public List<GasStation> call(List<GasStationEntity> gasStationEntities) {
                return null;
            }
        });
    }

    @Override
    public Observable<GasStation> gasStation(int userId) {
        return null;
    }
}
