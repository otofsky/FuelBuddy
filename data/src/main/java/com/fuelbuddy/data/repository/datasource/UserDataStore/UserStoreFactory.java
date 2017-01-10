package com.fuelbuddy.data.repository.datasource.UserDataStore;

import android.content.Context;
import android.support.annotation.NonNull;

import com.fuelbuddy.data.cache.UserCache;
import com.fuelbuddy.data.entity.mapper.GasStationEntityDataMapper;
import com.fuelbuddy.data.entity.mapper.UserJsonEntityMapper;
import com.fuelbuddy.data.net.RestApiImpl;
import com.fuelbuddy.data.net.RestApiService;
import com.fuelbuddy.data.repository.datasource.GasStationDataStore.GasStationDataStore;

import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * Created by zjuroszek on 18.11.16.
 */

@Singleton
public class UserStoreFactory {

    private final Context context;
    private final UserCache userCache;


    @Inject
    public UserStoreFactory(@NonNull Context context,@NonNull UserCache userCache ) {
        this.context = context;
        this.userCache = userCache;
    }

    public UserDataStore createDiskUserDataStore() {
        UserDataStore userDataStore = new DiskUserDataStore(this.userCache);
        return userDataStore;

    }

    public UserDataStore createCloudDataStore() {
        GasStationEntityDataMapper gasStationEntityDataMapper = new GasStationEntityDataMapper();
        RestApiService restApiService = new RestApiImpl(this.context, gasStationEntityDataMapper);
        return new CloudUserStore(restApiService);

    }


}
