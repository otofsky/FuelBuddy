package com.fuelbuddy.data.entity.mapper;

import com.fuelbuddy.data.User;
import com.fuelbuddy.data.entity.UserEntity;

import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * Created by zjuroszek on 18.11.16.
 */

@Singleton
public class UserEntityMapper {
    @Inject
    public UserEntityMapper() {
    }

    public User transform(UserEntity userEntity) {
        User user = null;
        if (userEntity != null) {
            user = new User(userEntity.getUserId());

        }
        return user;
    }

}
