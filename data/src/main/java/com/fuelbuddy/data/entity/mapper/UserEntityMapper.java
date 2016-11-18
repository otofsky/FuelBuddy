package com.fuelbuddy.data.entity.mapper;

import com.fuelbuddy.data.GasStation;
import com.fuelbuddy.data.User;
import com.fuelbuddy.data.entity.GasStationEntity;
import com.fuelbuddy.data.entity.UserEntity;

/**
 * Created by zjuroszek on 18.11.16.
 */
public class UserEntityMapper {

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
