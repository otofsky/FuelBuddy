package com.fuelbuddy.repository;

import com.fuelbuddy.data.GasStation;
import com.fuelbuddy.data.Position;

import java.util.List;

import rx.Observable;

/**
 * Created by zjuroszek on 06.11.16.
 */
public interface GasStationsRepository  {

    /**
     * Get an {@link rx.Observable} which will emit a List of {@link GasStation}.
     */
    Observable<List<GasStation>> gasStations(Position position);

    /**
     * Get an {@link rx.Observable} which will emit a {@link GasStation}.
     *
     * @param userId The gasStation id used to retrieve user data.
     */
    Observable<GasStation> gasStation(final int userId);

}
