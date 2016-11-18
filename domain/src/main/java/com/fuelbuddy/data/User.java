package com.fuelbuddy.data;

/**
 * Created by zjuroszek on 14.11.16.
 */

public class User {

    private String userId;

    public User(String userId) {
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
        return "User{" +
                "userId='" + userId + '\'' +
                '}';
    }
}
