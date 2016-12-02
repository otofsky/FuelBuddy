package com.fuelbuddy.data.repository;

import com.fuelbuddy.data.GasStation;
import com.fuelbuddy.data.Position;
import com.fuelbuddy.data.entity.GasStationEntity;
import com.fuelbuddy.data.entity.mapper.GasStationEntityDataMapper;
import com.fuelbuddy.data.repository.datasource.GasStationDataStore.GasStationDataStore;
import com.fuelbuddy.data.repository.datasource.GasStationDataStore.GasStationStoreFactory;
import com.fuelbuddy.repository.GasStationsRepository;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import rx.Observable;
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
        this.mGasStationEntityDataMapper = gasStationEntityDataMapper;
        this.mGasStationStoreFactory = mGasStationStoreFactory;
    }

    @Override
    public Observable<List<GasStation>> gasStations(Position position) {
        GasStationDataStore gasStationDataStore = mGasStationStoreFactory.createCloudDataStore();

        return gasStationDataStore.gasStationsEntityList(position).map(new Func1<List<GasStationEntity>, List<GasStation>>() {
            @Override
            public List<GasStation> call(List<GasStationEntity> gasStationEntities) {

                return  mGasStationEntityDataMapper.transform(gasStationEntities);
            }
        });
    }

    @Override
    public Observable<GasStation> gasStation(int userId) {
        return null;
    }
}
