package com.fuelbuddy.data.net;


import com.fuelbuddy.data.entity.GasStationEntity;
import com.fuelbuddy.data.entity.ResponseEntity;
import com.fuelbuddy.data.entity.UserEntity;

import java.util.List;

import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by zjuroszek on 20.05.16.
 */
public interface ApiInterface {

    @GET("stations?")
    Observable<List<GasStationEntity>> getGasStations(@Query("latitude") String latitude, @Query("longitude") String longitude);

    @GET("updatestation?")
    Observable<ResponseEntity> updateStation(@Query("ID") String iD,
                                             @Query("userID") String userID,
                                             @Query("price92") Double price92,
                                             @Query("price95") Double price95,
                                             @Query("priceDiesel") Double priceDiesel);

    @GET("adduser?")
    Observable<ResponseEntity> addNewUser(@Query("userID") String userID, @Query("profileName") String profileName, @Query("email") String email);

    @GET("checkuser?")
    Observable<UserEntity> checkUser(@Query("userID") String userID);

}
