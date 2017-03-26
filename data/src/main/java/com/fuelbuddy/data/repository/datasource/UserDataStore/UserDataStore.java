package com.fuelbuddy.data.repository.datasource.UserDataStore;

import com.fuelbuddy.data.entity.ResponseEntity;
import com.fuelbuddy.data.entity.UserEntity;

import io.reactivex.Observable;


/**
 * Created by zjuroszek on 18.11.16.
 */
public interface UserDataStore {

    ResponseEntity addUser(UserEntity user) ;

    ResponseEntity authUser(String userId, String email);

    Observable<ResponseEntity> auth(String userId, String email);

    Observable<UserEntity> checkUser(String userId);

    Observable<UserEntity> getCurrentUserEntity();

    Observable<ResponseEntity> setCurrentUser(UserEntity userEntity);

    Observable<ResponseEntity> addNewUser(UserEntity userEntity);

    Observable<Boolean> logOut();
    
}
