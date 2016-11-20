package com.fuelbuddy.repository;

import com.fuelbuddy.data.User;

import rx.Observable;

/**
 * Created by zjuroszek on 18.11.16.
 */
public interface UserRepository {

    /*
    return current logged user
    * */

    Observable<User> getCurrentUser();

    Observable<User> setCurrentUser(User user);

    Observable<User> logOut();

}
