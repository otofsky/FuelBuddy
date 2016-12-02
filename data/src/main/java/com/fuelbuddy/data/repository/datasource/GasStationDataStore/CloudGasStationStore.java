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
package com.fuelbuddy.data.repository.datasource.GasStationDataStore;



import com.fuelbuddy.data.Position;
import com.fuelbuddy.data.entity.GasStationEntity;
import com.fuelbuddy.data.net.RestApiService;
import com.fuelbuddy.data.repository.datasource.GasStationDataStore.GasStationDataStore;

import java.util.List;

import javax.inject.Inject;

import rx.Observable;


class CloudGasStationStore implements GasStationDataStore {

  private final RestApiService mRestApiService;



@Inject
  CloudGasStationStore(RestApiService restApiService) {
    this.mRestApiService = restApiService;
  }


  @Override
  public Observable<List<GasStationEntity>> gasStationsEntityList(Position position) {
    return this.mRestApiService.gasStationEntityList(position);
  }

  @Override
  public Observable<GasStationEntity> gasStationEntityDetails(int userId) {
    return null;
  }

}
