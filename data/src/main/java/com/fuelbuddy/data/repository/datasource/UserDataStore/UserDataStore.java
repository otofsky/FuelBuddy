package com.fuelbuddy.data.repository.datasource.UserDataStore;

import com.fuelbuddy.data.Response;
import com.fuelbuddy.data.User;
import com.fuelbuddy.data.entity.AuthEntity;
import com.fuelbuddy.data.entity.ResponseEntity;
import com.fuelbuddy.data.entity.UserEntity;

import io.reactivex.Observable;


/**
 * Created by zjuroszek on 18.11.16.
 */
public interface UserDataStore {

    Observable<ResponseEntity> auth(String userId, String email);



    Observable<UserEntity> checkUser(String userId);

    Observable<UserEntity> getCurrentUserEntity();

    Observable<ResponseEntity> setCurrentUser(UserEntity userEntity);

    Observable<ResponseEntity> addNewUser(UserEntity userEntity);

    Observable<Boolean> logOut();
    
}
