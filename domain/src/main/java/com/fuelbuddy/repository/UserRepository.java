package com.fuelbuddy.repository;

import com.fuelbuddy.data.Auth;
import com.fuelbuddy.data.Response;
import com.fuelbuddy.data.User;

import rx.Observable;

/**
 * Created by zjuroszek on 18.11.16.
 */
public interface UserRepository {


    Observable<Response> auth(String userId, String email);

    Observable<User> getCurrentUser();

    Observable<User> getCheckUser(String UserId);

    Observable<Response> setCurrentUser(User user);

    Observable<Response> addNewUser(User user);

    Observable<Boolean> logOut();

}
