package com.fuelbuddy.data.repository;

import com.fuelbuddy.data.User;
import com.fuelbuddy.data.entity.UserEntity;
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
    UserEntityMapper mUserEntityMapper;

    @Inject
    public UserDataRepository(UserStoreFactory userStoreFactory, UserEntityMapper userEntityMapper) {
        mUserStoreFactory = userStoreFactory;
        mUserEntityMapper = userEntityMapper;
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
    public Observable<User> setCurrentUser(User user) {
        UserDataStore userDataStore = mUserStoreFactory.createDiskUserDataStore();
        return userDataStore.setCurrentUser(mUserEntityMapper.transformToUserEntity(user)).map(new Func1<UserEntity, User>() {
            @Override
            public User call(UserEntity userEntity) {

                return mUserEntityMapper.transformToUser(userEntity);
            }
        });
    }

    @Override
    public Observable<Boolean> logOut() {
        UserDataStore userDataStore = mUserStoreFactory.createDiskUserDataStore();
        return userDataStore.logOut();
    }
}
