package com.fuelbuddy.data.repository;

import com.fuelbuddy.data.Auth;
import com.fuelbuddy.data.Response;
import com.fuelbuddy.data.User;
import com.fuelbuddy.data.entity.AuthEntity;
import com.fuelbuddy.data.entity.ResponseEntity;
import com.fuelbuddy.data.entity.UserEntity;
import com.fuelbuddy.data.entity.mapper.AuthEntityMapper;
import com.fuelbuddy.data.entity.mapper.ResponseEntityMapper;
import com.fuelbuddy.data.entity.mapper.UserEntityMapper;
import com.fuelbuddy.data.repository.datasource.UserDataStore.UserDataStore;
import com.fuelbuddy.data.repository.datasource.UserDataStore.UserStoreFactory;
import com.fuelbuddy.repository.UserRepository;

import javax.inject.Inject;
import javax.inject.Singleton;

import rx.Observable;
import rx.functions.Func1;

/**
 * Created by zjuroszek on 18.11.16.
 */

@Singleton
public class UserDataRepository implements UserRepository {


    private final UserStoreFactory mUserStoreFactory;
    ResponseEntityMapper mResponseEntityMapper;
    UserEntityMapper mUserEntityMapper;
    AuthEntityMapper authEntityMapper;

    @Inject
    public UserDataRepository(UserStoreFactory userStoreFactory, UserEntityMapper userEntityMapper,
                              ResponseEntityMapper responseEntityMapper,
                              AuthEntityMapper authEntityMapper) {
        mUserStoreFactory = userStoreFactory;
        mUserEntityMapper = userEntityMapper;
        mResponseEntityMapper = responseEntityMapper;
        this.authEntityMapper = authEntityMapper;
    }

    @Override
    public Observable<Response> auth(String userId, String email) {
        UserDataStore userDataStore = mUserStoreFactory.createCloudDataStore();
        return userDataStore.auth(userId,email).map(new Func1<ResponseEntity, Response>() {
            @Override
            public Response call(ResponseEntity responseEntity) {
                return mResponseEntityMapper.transformToResponse(responseEntity);
            }
        });
    }

    @Override
    public Observable<Response> putToken(String token) {
        UserDataStore userDataStore = mUserStoreFactory.createDiskUserDataStore();
        return userDataStore.putToken(token).map(new Func1<ResponseEntity, Response>() {
            @Override
            public Response call(ResponseEntity responseEntity) {
                return mResponseEntityMapper.transformToResponse(responseEntity);
            }
        });
    }

    @Override
    public Observable<User> getCurrentUser() {
        UserDataStore userDataStore = mUserStoreFactory.createDiskUserDataStore();
        return userDataStore.getCurrentUserEntity().map(new Func1<UserEntity, User>() {
            @Override
            public User call(UserEntity userEntity) {
                return mUserEntityMapper.transformToUser(userEntity);
            }
        });
    }

    @Override
    public Observable<User> checkUser(String userId) {
        UserDataStore userDataStore = mUserStoreFactory.createCloudDataStore();
        return userDataStore.checkUser(userId).map(new Func1<UserEntity, User>() {
            @Override
            public User call(UserEntity userEntity) {
                return mUserEntityMapper.transformToUser(userEntity);
            }
        });
    }

    @Override
    public Observable<Response> setCurrentUser(User user) {
        UserDataStore userDataStore = mUserStoreFactory.createDiskUserDataStore();
        return userDataStore.setCurrentUser(mUserEntityMapper.transformToUserEntity(user)).map(new Func1<ResponseEntity, Response>() {
            @Override
            public Response call(ResponseEntity responseEntity) {
                return mResponseEntityMapper.transformToResponse(responseEntity);
            }
        });
    }

    @Override
    public Observable<Response> addNewUser(User user) {
        UserDataStore userDataStore = mUserStoreFactory.createCloudDataStore();
        return userDataStore.addNewUser(mUserEntityMapper.transformToUserEntity(user)).map(new Func1<ResponseEntity, Response>() {
            @Override
            public Response call(ResponseEntity responseEntity) {
                return mResponseEntityMapper.transformToResponse(responseEntity);
            }
        });
    }

    @Override
    public Observable<Boolean> logOut() {
        UserDataStore userDataStore = mUserStoreFactory.createDiskUserDataStore();
        return userDataStore.logOut();
    }
}
