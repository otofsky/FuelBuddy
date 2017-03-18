package com.fuelbuddy.repository;

import com.fuelbuddy.data.GasStation;
import com.fuelbuddy.data.Position;
import com.fuelbuddy.data.Response;
import com.fuelbuddy.data.UploadResponse;

import java.io.File;
import java.util.List;

import io.reactivex.Observable;


/**
 * Created by zjuroszek on 06.11.16.
 */
public interface GasStationsRepository {


    Observable<List<GasStation>> gasStations(Position position);

    Observable<Response> updateStation(String iD, String userID, String photoID, Double price92
            ,Double price95, Double priceDiesel);


}
