/**
 * Copyright (C) 2015 Fernando Cejas Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.fuelbuddy.data.repository.datasource.UserDataStore;



import com.fuelbuddy.data.Response;
import com.fuelbuddy.data.entity.AuthEntity;
import com.fuelbuddy.data.entity.GasStationEntity;
import com.fuelbuddy.data.entity.ResponseEntity;
import com.fuelbuddy.data.entity.UserEntity;
import com.fuelbuddy.data.net.RestApiService;
import com.fuelbuddy.data.repository.datasource.GasStationDataStore.GasStationDataStore;

import java.util.List;

import javax.inject.Inject;

import rx.Observable;


class CloudUserStore implements UserDataStore {

  private final RestApiService mRestApiService;

@Inject
CloudUserStore(RestApiService restApiService) {
    this.mRestApiService = restApiService;
  }

    @Override
    public Observable<ResponseEntity> auth(String userId, String email) {
        return this.mRestApiService.auth(userId,email);
    }

    @Override
    public Observable<UserEntity> checkUser(String userId) {
        return this.mRestApiService.checkUser(userId);
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
    public Observable<ResponseEntity> addNewUser(UserEntity userEntity) {
        return mRestApiService.addNewUser(userEntity);
    }

    @Override
    public Observable<Boolean> logOut() {
        return null;
    }

    @Override
    public Observable<ResponseEntity> putToken(String token) {
        return null;
    }

}
