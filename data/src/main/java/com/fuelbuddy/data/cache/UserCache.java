package com.fuelbuddy.data.cache;

import com.fuelbuddy.data.entity.ResponseEntity;
import com.fuelbuddy.data.entity.UserEntity;

import rx.Observable;

/**
 * Created by zjuroszek on 22.11.16.
 */
public interface UserCache {


    Observable<UserEntity> getUser();


    Observable<Boolean> delete();

    /**
     * Puts and element into the cache.
     *
     * @param userEntity Element to insert in the cache.
     */
    Observable<ResponseEntity> putUser(UserEntity userEntity);

    Observable<ResponseEntity> putToken(String token);

    String getToken();

    /**
     * Checks if an element (User) exists in the cache.
     *
     * @param userId The id used to look for inside the cache.
     * @return true if the element is cached, otherwise false.
     */
    boolean isCached(final int userId);

    /**
     * Checks if the cache is expired.
     *
     * @return true, the cache is expired, otherwise false.
     */
    boolean isExpired();

    /**
     * Evict all elements of the cache.
     */
    void evictAll();
}
