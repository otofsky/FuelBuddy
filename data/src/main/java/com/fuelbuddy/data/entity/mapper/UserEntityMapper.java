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


    public UserEntity transformToUserEntity(User user) {
        if (user == null) {
            throw new IllegalArgumentException("Cannot transformToUser a null value");
        }
        UserEntity userEntity = new UserEntity();
        userEntity.setUserID(user.getUserID());
        userEntity.setProfileName(user.getProfileName());
        userEntity.setEmail(user.getEmail());
        userEntity.setTokens(user.getTokens());


        return userEntity;
    }

    public User transformToUser(UserEntity userEntity) {
        if (userEntity == null) {
            throw new IllegalArgumentException("Cannot transformToUser a null value");
        }
        User user = new User();
        user.setUserID(userEntity.getUserID());
        user.setProfileName(userEntity.getProfileName());
        user.setEmail(userEntity.getEmail());
        user.setTokens(userEntity.getTokens());
        return user;
    }

}
