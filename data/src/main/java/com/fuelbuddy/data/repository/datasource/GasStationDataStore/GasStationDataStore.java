package com.fuelbuddy.data.repository.datasource.GasStationDataStore;

import com.fuelbuddy.data.Position;
import com.fuelbuddy.data.UploadResponse;
import com.fuelbuddy.data.entity.GasStationEntity;
import com.fuelbuddy.data.entity.ResponseEntity;
import com.fuelbuddy.data.entity.UploadResponseEntity;

import java.io.File;
import java.util.List;

import rx.Observable;

/**
 * Created by zjuroszek on 08.11.16.
 */
public interface GasStationDataStore {


    Observable<List<GasStationEntity>> gasStationsEntityList(Position position);

    Observable<ResponseEntity> updateStation(String iD, String userID,String photoID, Double price92,Double price95, Double priceDiesel);

    Observable<UploadResponseEntity> uploadVideo(File file);

    Observable<GasStationEntity> gasStationEntityDetails(final int userId);

}
