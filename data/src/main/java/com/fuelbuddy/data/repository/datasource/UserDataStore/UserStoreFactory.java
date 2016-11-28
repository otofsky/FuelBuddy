package com.fuelbuddy.data.repository.datasource.UserDataStore;

import android.content.Context;
import android.support.annotation.NonNull;

import com.fuelbuddy.data.cache.UserCache;
import com.fuelbuddy.data.entity.mapper.UserJsonEntityMapper;

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
        UserDataStore userDataStore = new DiskUserDataStore(this.userCache);
        return userDataStore;

    }


}
