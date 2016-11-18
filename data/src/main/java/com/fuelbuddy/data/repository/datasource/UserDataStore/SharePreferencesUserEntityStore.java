package com.fuelbuddy.data.repository.datasource.UserDataStore;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.fuelbuddy.data.entity.UserEntity;
import com.fuelbuddy.data.entity.mapper.UserJsonEntityMapper;

import javax.inject.Inject;

import rx.Observable;

/**
 * Created by zjuroszek on 18.11.16.
 */
public class SharePreferencesUserEntityStore implements UserDataStore {

    private static final String SP_USER_ENTITY = "USER_ENTITY";
    private final SharedPreferences sharedPreferences;
    private final UserJsonEntityMapper mUserJsonEntityMapper;
    Context mContext;

    @Inject
    public SharePreferencesUserEntityStore(Context context, UserJsonEntityMapper userJsonEntityMapper) {
        this.sharedPreferences = getAndClearSharedPreferences();
        mUserJsonEntityMapper = userJsonEntityMapper;
    }

    private SharedPreferences getAndClearSharedPreferences() {
        return PreferenceManager.getDefaultSharedPreferences(mContext);
    }

    @Override
    public Observable<UserEntity> getCurrentUserEntity() {
        return null;
    }

    @Override
    public void storeUserEntity(UserEntity userEntity) {

    }
}
