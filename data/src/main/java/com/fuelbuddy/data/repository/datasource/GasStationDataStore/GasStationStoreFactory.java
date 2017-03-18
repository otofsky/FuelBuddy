package com.fuelbuddy.data.repository.datasource.GasStationDataStore;

import android.content.Context;
import android.support.annotation.NonNull;

import com.fuelbuddy.data.cache.UserCache;
import com.fuelbuddy.data.entity.mapper.GasStationEntityDataMapper;
import com.fuelbuddy.data.net.ApiInvoker;
import com.fuelbuddy.data.net.RestApi;
import com.fuelbuddy.data.net.RestApiImpl;

import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * Created by zjuroszek on 08.11.16.
 */

@Singleton
public class GasStationStoreFactory {

    private final Context context;
    private final UserCache userCache;
    private ApiInvoker mApiInvoker;

    @Inject
    public GasStationStoreFactory(@NonNull Context context,  UserCache userCache, ApiInvoker apiInvoker) {
        this.context = context;
        this.userCache = userCache;
        this.mApiInvoker = apiInvoker;
    }

    public GasStationDataStore createCloudDataStore() {
        GasStationEntityDataMapper gasStationEntityDataMapper = new GasStationEntityDataMapper();
        RestApi restApi = new RestApiImpl(mApiInvoker,this.context, gasStationEntityDataMapper);
        return new CloudGasStationStore(userCache,restApi);
    }

}
