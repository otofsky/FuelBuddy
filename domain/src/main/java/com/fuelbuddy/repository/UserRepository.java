package com.fuelbuddy.repository;

import com.fuelbuddy.data.Response;
import com.fuelbuddy.data.User;

import io.reactivex.Observable;


/**
 * Created by zjuroszek on 18.11.16.
 */
public interface UserRepository {


    Observable auth(String userId, String email);

    Observable<User> getCurrentUser();

    Observable<User> checkUser(String UserId);

    Observable<Response> setCurrentUser(User user);

    Observable<Response> addNewUser(User user);

    Observable<Boolean> logOut();

}
