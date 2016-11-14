package com.fuelbuddy.data.model;

/**
 * Created by zjuroszek on 14.11.16.
 */

public class User {

    public User(String userId) {
        this.userId = userId;
    }

    private String userId;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    @Override
    public String toString() {
        return "User{" +
                "userId='" + userId + '\'' +
                '}';
    }
}
