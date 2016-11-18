package com.fuelbuddy.data.entity;

/**
 * Created by zjuroszek on 18.11.16.
 */
public class UserEntity {

    private String userId;

    public UserEntity(String userId) {
        this.userId = userId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    @Override
    public String toString() {
        return "UserEntity{" +
                "userId='" + userId + '\'' +
                '}';
    }
}
