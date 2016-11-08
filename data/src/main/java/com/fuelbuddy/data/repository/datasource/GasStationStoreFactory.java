package com.fuelbuddy.data.repository.datasource;

import android.content.Context;

import com.fuelbuddy.data.entity.mapper.GasStationEntityDataMapper;
import com.fuelbuddy.data.net.RestApi;
import com.fuelbuddy.data.net.RestApiImpl;

import javax.inject.Singleton;

/**
 * Created by zjuroszek on 08.11.16.
 */

@Singleton
public class GasStationStoreFactory {

    private final Context context;

    public GasStationStoreFactory(Context context) {
        this.context = context;
    }

    public GasStationDataStore createCloudDataStore() {
        GasStationEntityDataMapper gasStationEntityDataMapper = new GasStationEntityDataMapper();
        RestApi restApi = new RestApiImpl(this.context, gasStationEntityDataMapper);
        return new CloudGasStationStore(restApi);
    }

}
