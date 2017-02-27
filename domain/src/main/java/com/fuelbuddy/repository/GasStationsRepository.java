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

    /**
     * Get an {@link rx.Observable} which will emit a List of {@link GasStation}.
     */
    Observable<List<GasStation>> gasStations(Position position);

    Observable<Response> updateStation(String iD, String userID, String photoID, Double price92
            ,Double price95, Double priceDiesel);

    Observable<UploadResponse> uploadVideo(File file);

    /**
     * Get an {@link rx.Observable} which will emit a {@link GasStation}.
     *
     * @param userId The gasStation id used to retrieve user data.
     */
    Observable<GasStation> gasStation(final int userId);

}
