package com.fuelbuddy.data.repository.datasource.UserDataStore;

import android.content.Context;
import android.support.annotation.NonNull;

import com.fuelbuddy.data.cache.UserCache;
import com.fuelbuddy.data.entity.mapper.GasStationEntityDataMapper;
import com.fuelbuddy.data.net.ApiInvoker;
import com.fuelbuddy.data.net.RestApiImpl;
import com.fuelbuddy.data.net.RestApi;

import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * Created by zjuroszek on 18.11.16.
 */

@Singleton
public class UserStoreFactory {

    private final Context context;
    private final UserCache userCache;
    private ApiInvoker mApiInvoker;

    @Inject
    public UserStoreFactory(@NonNull Context context,@NonNull UserCache userCache ,ApiInvoker apiInvoker ) {
        this.context = context;
        this.userCache = userCache;
        this.mApiInvoker = apiInvoker;
    }

    public UserDataStore createDiskUserDataStore() {
        UserDataStore userDataStore = new DiskUserDataStore(this.userCache);
        return userDataStore;
    }

    public UserDataStore createCloudDataStore() {
        GasStationEntityDataMapper gasStationEntityDataMapper = new GasStationEntityDataMapper();
        RestApi restApi = new RestApiImpl(mApiInvoker,this.context, gasStationEntityDataMapper);
        return new CloudUserStore(restApi,userCache);
    }

}
