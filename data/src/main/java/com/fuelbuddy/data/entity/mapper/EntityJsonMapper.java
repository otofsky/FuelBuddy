package com.fuelbuddy.data.entity.mapper;

import com.fuelbuddy.data.entity.UserEntity;

import org.json.JSONException;
import org.json.JSONObject;

import javax.inject.Inject;

/**
 * Created by zjuroszek on 10.12.16.
 */

public class EntityJsonMapper {
    @Inject
    public EntityJsonMapper() {
    }

    public String toJson(UserEntity entity) throws JSONException {
        JSONObject obj = new JSONObject();
        obj.put("userId", entity.getUserId());
        obj.put("profileName", entity.getProfileName());
        obj.put("profileEmail", entity.getEmail());
        obj.put("profileLink", entity.getProfileLink());
        obj.put("token", entity.getTokens());
        return obj.toString();
    }


    public UserEntity fromJson(String obj) throws JSONException {
        UserEntity userEntity = null;
        if (!obj.equalsIgnoreCase("")) {
            JSONObject jsonObject = new JSONObject(obj);
            userEntity = new UserEntity();
            userEntity.setUserId(jsonObject.getString("userId"));
            userEntity.setProfileName("profileName");
        }
        return userEntity;
    }
}
