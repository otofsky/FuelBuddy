package com.fuelbuddy.data.repository.datasource.UserDataStore;

import com.fuelbuddy.data.cache.UserCache;
import com.fuelbuddy.data.entity.UserEntity;

import hugo.weaving.DebugLog;
import rx.Observable;

/**
 * Created by zjuroszek on 22.11.16.
 */
public class DiskUserDataStore implements UserDataStore {
    UserCache mUserCache;

    public DiskUserDataStore(UserCache userCache) {
        mUserCache = userCache;
    }

    @Override
    public Observable<UserEntity> getCurrentUserEntity() {
        return getUserEntity();
    }

    @Override
    public Observable<UserEntity> setCurrentUser(final UserEntity userEntity) {
        return mUserCache.put(userEntity);
    }

    @Override
    public Observable<Boolean> logOut() {
        return mUserCache.delete();
    }

    @DebugLog
    public Observable<UserEntity> getUserEntity() {
        return mUserCache.get();
    }
}
