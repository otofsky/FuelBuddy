/**
 * Copyright (C) 2015 Fernando Cejas Open Source Project
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.fuelbuddy.data.repository.datasource.UserDataStore;


import com.fuelbuddy.data.cache.UserCache;
import com.fuelbuddy.data.entity.ResponseEntity;
import com.fuelbuddy.data.entity.UserEntity;
import com.fuelbuddy.data.net.RestApi;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.functions.Consumer;


class CloudUserStore implements UserDataStore {

    private final RestApi mRestApi;
    private final UserCache userCache;

    @Inject
    CloudUserStore(RestApi restApi, UserCache userCache) {
        this.mRestApi = restApi;
        this.userCache = userCache;
    }

    @Override
    public ResponseEntity addUser(UserEntity user)  {
        return mRestApi.addUser(user);
    }

    @Override
    public ResponseEntity authUser(String userId, String email)  {
        return this.mRestApi.authUser(userId, email);
    }

    @Override
    public Observable<ResponseEntity> auth(String userID, String email) {
        return this.mRestApi.auth(userID, email)
                .doOnNext(new Consumer<ResponseEntity>() {
                    @Override
                    public void accept(ResponseEntity responseEntity) throws Exception {
                        userCache.putToken(responseEntity);

                    }
                });
    }

    @Override
    public Observable<ResponseEntity> addNewUser(UserEntity userEntity) {
        return mRestApi.addNewUser(userEntity).
        doOnNext(new Consumer<ResponseEntity>() {
            @Override
            public void accept(ResponseEntity responseEntity) throws Exception {
                userCache.putUser(userEntity);
            }
        });
    }
    
    @Override
    public Observable<UserEntity> checkUser(String userId) {
        return this.mRestApi.checkUser(userId).doOnNext(new Consumer<UserEntity>() {
            @Override
            public void accept(UserEntity userEntity) throws Exception {
                userCache.putUser(userEntity);
            }
        });
    }

    @Override
    public Observable<UserEntity> getCurrentUserEntity() {
        return null;
    }

    @Override
    public Observable<ResponseEntity> setCurrentUser(UserEntity userEntity) {
        return null;
    }


    @Override
    public Observable<Boolean> logOut() {
        return null;
    }


}
