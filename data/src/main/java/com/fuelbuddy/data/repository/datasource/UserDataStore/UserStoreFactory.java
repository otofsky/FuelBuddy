package com.fuelbuddy.data.repository.datasource.UserDataStore;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.fuelbuddy.data.entity.mapper.UserJsonEntityMapper;

/**
 * Created by zjuroszek on 18.11.16.
 */
public class UserStoreFactory {

    private final Context context;

    public UserStoreFactory(Context context) {
        this.context = context;
    }

    public UserDataStore createSharePreferencesDataStore(){
        UserJsonEntityMapper userJsonEntityMapper = new UserJsonEntityMapper();
        SharePreferencesUserEntityStore sharePreferencesUserEntityStore = new SharePreferencesUserEntityStore(context,userJsonEntityMapper);
        return sharePreferencesUserEntityStore;
    }
}
