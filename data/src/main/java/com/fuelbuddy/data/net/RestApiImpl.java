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
import com.fuelbuddy.data.entity.UploadResponseEntity;
import com.fuelbuddy.data.entity.UserEntity;
import com.fuelbuddy.data.entity.mapper.GasStationEntityDataMapper;
import com.fuelbuddy.data.exeption.NetworkConnectionException;
import com.fuelbuddy.data.exeption.UserNotFoundException;

import java.io.File;
import java.util.List;

import rx.Observable;

/**
 * {@link RestApi} implementation for retrieving data from the network.
 */
public class RestApiImpl implements RestApi {

    private final Context context;
    GasStationEntityDataMapper mGasStationEntityDataMapper;
    ApiInvoker mApiInvoker;

    public RestApiImpl(Context context) {
        this.context = context;
    }


    public RestApiImpl( ApiInvoker apiInvoker,Context context, GasStationEntityDataMapper gasStationEntityDataMapper) {
        this.mApiInvoker = apiInvoker;
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


    public Observable<ResponseEntity> auth(String userId, String email) {
        return Observable.create(emitter -> {
            if (isThereInternetConnection()) {
                try {
                    ResponseEntity responseEntity = authFromApi(userId,email);
                    if (responseEntity != null) {
                        emitter.onNext(responseEntity);
                        emitter.onCompleted();
                    } else {
                        emitter.onError(new NetworkConnectionException());
                    }
                } catch (Exception e) {
                    emitter.onError(new NetworkConnectionException(e.getCause()));
                }
            } else {
                emitter.onError(new NetworkConnectionException());
            }
        });
    }

    @Override
    public Observable<ResponseEntity> addNewUser(UserEntity userEntity) {
        return Observable.create(emitter -> {
            if (isThereInternetConnection()) {
                try {
                    ResponseEntity responseEntity = addNewUserFromApi(userEntity);
                    if (responseEntity != null) {
                        emitter.onNext(responseEntity);
                        emitter.onCompleted();
                    } else {
                        emitter.onError(new NetworkConnectionException());
                    }
                } catch (Exception e) {
                    emitter.onError(new NetworkConnectionException(e.getCause()));
                }
            } else {
                emitter.onError(new NetworkConnectionException());
            }
        });
    }

    public Observable<UserEntity> checkUser(String userId) {
        return Observable.create(emitter -> {
            if (isThereInternetConnection()) {
                try {
                    UserEntity userEntity = checkUserEntityFromApi(userId);
                    if (userEntity != null) {
                        emitter.onNext(userEntity);
                        emitter.onCompleted();
                    } else {
                        emitter.onError(new UserNotFoundException());
                    }
                } catch (Exception e) {
                    emitter.onError(new NetworkConnectionException(e.getCause()));
                }
            } else {
                emitter.onError(new NetworkConnectionException());
            }
        });
    }


    @Override
    public Observable<ResponseEntity> updateStation(String ID, String userID, String photoID, Double price92, Double price95, Double priceDiesel) {
        return Observable.create(emitter -> {
            if (isThereInternetConnection()) {
                try {
                    ResponseEntity responseEntity = updateStationFromApi(ID,userID,photoID,price92,price95,priceDiesel);
                    if (responseEntity != null) {
                        emitter.onNext(responseEntity);
                        emitter.onCompleted();
                    } else {
                        emitter.onError(new NetworkConnectionException());
                    }
                } catch (Exception e) {
                    emitter.onError(new NetworkConnectionException(e.getCause()));
                }
            } else {
                emitter.onError(new NetworkConnectionException());
            }
        });
    }

    @Override
    public Observable<UploadResponseEntity> uploadVideo(File file) {
        return Observable.create(emitter -> {
            if (isThereInternetConnection()) {
                try {
                    UploadResponseEntity responseEntity = uploadVideoFromApi(file);
                    if (responseEntity != null) {
                        emitter.onNext(responseEntity);
                        emitter.onCompleted();
                    } else {
                        emitter.onError(new NetworkConnectionException());
                    }
                } catch (Exception e) {
                    emitter.onError(new NetworkConnectionException(e.getCause()));
                }
            } else {
                emitter.onError(new NetworkConnectionException());
            }
        });
    }



    @Override
    public Observable<List<GasStationEntity>> gasStationEntityList(final Position position) {
        return Observable.create(subscriber -> {
            if (isThereInternetConnection()) {
                try {
                    List<GasStationEntity> gasStationEntityList = getGasStationEntitiesFromApi(position);
                    if (gasStationEntityList != null) {
                        subscriber.onNext(gasStationEntityList);
                        subscriber.onCompleted();
                    } else {
                        subscriber.onError(new NetworkConnectionException());
                    }
                } catch (Exception e) {
                    subscriber.onError(new NetworkConnectionException(e.getCause()));
                }
            } else {
                subscriber.onError(new NetworkConnectionException());
            }
        });
    }

    private List<GasStationEntity> getGasStationEntitiesFromApi(Position position) {
        return mApiInvoker.getGasStations(position.getLatitude(),position.getLongitude());
    }


    public UserEntity checkUserEntityFromApi(String userId) {
        return mApiInvoker.checkUser(userId);
    }


    public ResponseEntity updateStationFromApi(String iD, String userID,  String photoID, Double price92, Double price95, Double priceDiesel){
       return mApiInvoker.updateStation("",iD, userID,photoID, price92, price95, priceDiesel);
   }

    public UploadResponseEntity uploadVideoFromApi(File file){
        return mApiInvoker.uploadVideo("",file);
    }

    public ResponseEntity addNewUserFromApi(UserEntity userEntity){
        return mApiInvoker.addNewUser(userEntity.getUserID(),userEntity.getProfileName(),userEntity.getEmail());
    }

    public ResponseEntity authFromApi(String userId, String email){
        return mApiInvoker.auth(userId,email);
    }
}
