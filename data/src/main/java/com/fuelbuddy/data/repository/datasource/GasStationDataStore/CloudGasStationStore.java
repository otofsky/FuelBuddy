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
package com.fuelbuddy.data.repository.datasource.GasStationDataStore;


import com.fuelbuddy.data.Position;
import com.fuelbuddy.data.entity.GasStationEntity;
import com.fuelbuddy.data.entity.ResponseEntity;
import com.fuelbuddy.data.entity.UploadResponseEntity;
import com.fuelbuddy.data.net.RestApi;

import java.io.File;
import java.util.List;

import javax.inject.Inject;

import rx.Observable;


class CloudGasStationStore implements GasStationDataStore {

    private final RestApi mRestApi;


    @Inject
    CloudGasStationStore(RestApi restApi) {
        this.mRestApi = restApi;
    }


    @Override
    public Observable<List<GasStationEntity>> gasStationsEntityList(Position position) {
        return this.mRestApi.gasStationEntityList(position);
    }

    @Override
    public Observable<ResponseEntity> updateStation(String iD, String userID, String photoID, Double price92, Double price95, Double priceDiesel) {
        return mRestApi.updateStation(iD, userID, photoID, price92, price95, priceDiesel);
    }

    @Override
    public Observable<UploadResponseEntity> uploadVideo(File file) {
        return mRestApi.uploadVideo(file);
    }

    @Override
    public Observable<GasStationEntity> gasStationEntityDetails(int userId) {
        return null;
    }

}
