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
package com.fuelbuddy.data.net;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;


import com.fuelbuddy.data.Position;
import com.fuelbuddy.data.entity.GasStationEntity;
import com.fuelbuddy.data.entity.ResponseEntity;
import com.fuelbuddy.data.entity.UserEntity;
import com.fuelbuddy.data.entity.mapper.GasStationEntityDataMapper;

import java.io.File;
import java.util.List;

import rx.Observable;

/**
 * {@link RestApiService} implementation for retrieving data from the network.
 */
public class RestApiImpl implements RestApiService {

    private final Context context;

    GasStationEntityDataMapper mGasStationEntityDataMapper;

    public RestApiImpl(Context context) {
        this.context = context;
    }


    public RestApiImpl(Context context, GasStationEntityDataMapper gasStationEntityDataMapper) {
        this.context = context;
        mGasStationEntityDataMapper = gasStationEntityDataMapper;
    }

    /**
     * Checks if the device has any active internet connection.
     *
     * @return true device with internet connection, otherwise false.
     */
    private boolean isThereInternetConnection() {
        boolean isConnected;

        ConnectivityManager connectivityManager =
                (ConnectivityManager) this.context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        isConnected = (networkInfo != null && networkInfo.isConnectedOrConnecting());

        return isConnected;
    }

    @Override
    public Observable<List<GasStationEntity>> gasStationEntityList(Position position) {
        return ApiInvoker.getInstance().getGasStations(position.getLatitude(),position.getLongitude());
    }

    @Override
    public Observable<ResponseEntity> updateStation(String iD, String userID, Double price92, Double price95, Double priceDiesel, File file) {
        return ApiInvoker.getInstance().updateStation(iD, userID, price92, price95, priceDiesel,file);
    }


    public Observable<UserEntity> checkUser(String userId) {
        return ApiInvoker.getInstance().checkUser(userId);
    }


    @Override
    public Observable<ResponseEntity> addNewUser(UserEntity userEntity) {
        return ApiInvoker.getInstance().addNewUser(userEntity.getUserID(),userEntity.getProfileName(),userEntity.getEmail());
    }



/*
    @Override
    public Observable<List<GasStationEntity>> gasStationEntityList() {
        return Observable.create(subscriber -> {
            if (isThereInternetConnection()) {
                try {
                    //String responseUserEntities = getUserEntitiesFromApi();
                    subscriber.onNext(getUserEntitiesFromApi());
                    subscriber.onCompleted();

                } catch (Exception e) {
                    subscriber.onError(new NetworkConnectionException(e.getCause()));
                }
            } else {
                subscriber.onError(new NetworkConnectionException());
            }
        });
    }
*/

    @Override
    public Observable<GasStationEntity> gasStationEntityById(int gasStationId) {
        return null;
    }
/*
    public List<GasStationEntity> getUserEntitiesFromApi() {
        RestApiService.Creator.newRestApiService().gasStationEntityList();
        GasStationEntity gasStationEntity = new GasStationEntity("Statoil");
        List<GasStationEntity> gasStationEntityList = new ArrayList<GasStationEntity>();
        gasStationEntityList.add(gasStationEntity);
        return gasStationEntityList;
    }*/
}
