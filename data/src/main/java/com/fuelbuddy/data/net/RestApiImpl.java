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


import com.fuelbuddy.data.entity.GasStationEntity;
import com.fuelbuddy.data.entity.mapper.GasStationEntityDataMapper;

import java.util.ArrayList;
import java.util.List;

import rx.Observable;
import rx.Subscriber;

/**
 * {@link RestApiService} implementation for retrieving data from the network.
 */
public class RestApiImpl implements RestApiService {


   // http://fuelbuddy.dk/ws/stations?latitude=55.951869964599610&longitude=8.514181137084961

    String ENDPOINT = "http://fuelbuddy.dk/ws/stations?latitude=55.951869964599610&longitude=8.514181137084961";


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
    public Observable<List<GasStationEntity>> gasStationEntityList() {
        Observable<List<GasStationEntity>> observable = Observable.create(new Observable.OnSubscribe<List<GasStationEntity>>() {
            @Override
            public void call(Subscriber<? super List<GasStationEntity>> subscriber) {
                subscriber.onNext(getUserEntitiesFromApi());

            }
        });
        return observable;

    }



/*    @Override
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
    }*/

    @Override
    public Observable<GasStationEntity> gasStationEntityById(int gasStationId) {
        return null;
    }

    public List<GasStationEntity> getUserEntitiesFromApi() {
        GasStationEntity gasStationEntity = new GasStationEntity("Statoil");
        List<GasStationEntity> gasStationEntityList = new ArrayList<GasStationEntity>();
        gasStationEntityList.add(gasStationEntity);
        return gasStationEntityList;
    }
}
