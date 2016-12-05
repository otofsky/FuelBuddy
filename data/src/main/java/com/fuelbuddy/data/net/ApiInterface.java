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
    //http://api.openweathermap.org/data/2.5/weather?q=Cieszyn&APPID=ab6b80c5f1d39e5f3b6fac39594910c4

    //@"http://fuelbuddy.dk/ws/stations?latitude=55.951869964599610&longitude=8.514181137084961";

    //"http://fuelbuddy.dk/ws/adduser?userID=&profileName=&email="
    @GET("stations?")
    Observable<List<GasStationEntity>> getGasStations(@Query("latitude") String latitude, @Query("longitude") String longitude);

    @GET("adduser?")
    Observable<ResponseEntity> addNewUser(@Query("userID") String userID, @Query("profileName") String profileName, @Query("email") String email);

    @GET("checkuser?")
    Observable<UserEntity> checkUser(@Query("userID") String userID);

}
