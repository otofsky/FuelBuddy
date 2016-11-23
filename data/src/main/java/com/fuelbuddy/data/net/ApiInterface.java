package com.fuelbuddy.data.net;


import com.fuelbuddy.data.entity.GasStationEntity;

import java.util.List;

import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by zjuroszek on 20.05.16.
 */
public interface ApiInterface {
    //http://api.openweathermap.org/data/2.5/weather?q=Cieszyn&APPID=ab6b80c5f1d39e5f3b6fac39594910c4

    //@GET("weather?&APPID=ab6b80c5f1d39e5f3b6fac39594910c4&units=metric")
    @GET("stations?")
    Observable<List<GasStationEntity>> getGasStations(@Query("latitude") String latitude, @Query("longitude") String longitude);

}
