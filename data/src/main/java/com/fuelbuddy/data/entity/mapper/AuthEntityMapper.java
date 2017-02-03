package com.fuelbuddy.data.entity.mapper;

import com.fuelbuddy.data.Auth;
import com.fuelbuddy.data.User;
import com.fuelbuddy.data.entity.AuthEntity;
import com.fuelbuddy.data.entity.UserEntity;

import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * Created by zjuroszek on 18.11.16.
 */

@Singleton
public class AuthEntityMapper {

    @Inject
    public AuthEntityMapper() {
    }

    public Auth transformToAuth(AuthEntity authEntity) {
        if (authEntity == null) {
            throw new IllegalArgumentException("Cannot transformToUser a null value");
        }
        Auth auth = new Auth();
        auth.setCode(authEntity.getCode());
        auth.setMessage(authEntity.getMessage());
        return auth;
    }

}
