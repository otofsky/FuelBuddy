package com.fuelbuddy.data.repository.datasource.UserDataStore;

import com.fuelbuddy.data.cache.UserCache;
import com.fuelbuddy.data.entity.ResponseEntity;
import com.fuelbuddy.data.entity.UserEntity;
import com.fuelbuddy.data.net.RestApi;

import hugo.weaving.DebugLog;
import io.reactivex.Observable;


/**
 * Created by zjuroszek on 22.11.16.
 */
public class DiskUserDataStore implements UserDataStore {
    UserCache mUserCache;
    private final RestApi mRestApi;

    public DiskUserDataStore(UserCache userCache, RestApi restApi) {
        mUserCache = userCache;
        mRestApi = restApi;
    }

    @Override
    public Observable<ResponseEntity> auth(String userId, String email) {
        return null;
    }

    @Override
    public  Observable<ResponseEntity>  putToken(ResponseEntity responseEntity) {
       return   mUserCache.putToken(responseEntity);
    }

    public  String getToken(){
       return mUserCache.getToken();
    }

    @Override
    public Observable<UserEntity>  checkUser(String userId) {
        return null;
    }

    @Override
    public Observable<UserEntity> getCurrentUserEntity() {
        return getUserEntity();
    }

    @Override
    public Observable<ResponseEntity> setCurrentUser(final UserEntity userEntity) {
        return mUserCache.putUser(userEntity);
    }

    @Override
    public Observable<ResponseEntity> addNewUser(UserEntity userEntity) {
        return null;
    }

    @Override
    public Observable<Boolean> logOut() {
        return mUserCache.delete();
    }

    @DebugLog
    public Observable<UserEntity> getUserEntity() {
        return mUserCache.getUser();
    }
}
