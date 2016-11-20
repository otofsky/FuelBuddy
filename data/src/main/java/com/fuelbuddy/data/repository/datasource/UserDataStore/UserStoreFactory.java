package com.fuelbuddy.data.repository.datasource.UserDataStore;

import android.content.Context;

import com.fuelbuddy.data.entity.mapper.UserJsonEntityMapper;

import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * Created by zjuroszek on 18.11.16.
 */

@Singleton
public class UserStoreFactory {

    private final Context context;

    @Inject
    public UserStoreFactory(Context context) {
        this.context = context;
    }

    public UserDataStore createSharePreferencesDataStore() {
        UserJsonEntityMapper userJsonEntityMapper = new UserJsonEntityMapper();
        SharePreferencesUserEntityStore sharePreferencesUserEntityStore = new SharePreferencesUserEntityStore(context, userJsonEntityMapper);
        return sharePreferencesUserEntityStore;
    }
}
