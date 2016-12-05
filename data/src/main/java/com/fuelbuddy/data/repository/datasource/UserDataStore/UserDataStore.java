package com.fuelbuddy.data.repository.datasource.UserDataStore;

import com.fuelbuddy.data.entity.ResponseEntity;
import com.fuelbuddy.data.entity.UserEntity;

import rx.Observable;

/**
 * Created by zjuroszek on 18.11.16.
 */
public interface UserDataStore {


    Observable<UserEntity> checkUser(String userId);

    Observable<UserEntity> getCurrentUserEntity();

    Observable<UserEntity> setCurrentUserLocally(UserEntity userEntity);

    Observable<ResponseEntity> setCurrentUser(UserEntity userEntity);

    Observable<Boolean> logOut();
    
}
