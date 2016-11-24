package com.fuelbuddy.data.net;

import com.fuelbuddy.data.entity.GasStationEntity;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface WallAPI {
    @GET("/wall/posts/")
    Call<List<GasStationEntity>> getAllGasStations();


}
