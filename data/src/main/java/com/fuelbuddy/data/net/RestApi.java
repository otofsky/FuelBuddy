package com.fuelbuddy.data.net;

import com.fuelbuddy.data.GasStation;
import com.fuelbuddy.data.entity.GasStationEntity;

import java.util.List;

import rx.Observable;

/**
 * Created by zjuroszek on 07.10.16.
 */
public interface RestApi {

    String API_BASE_URL = "http://www.android10.org/myapi/";

    /** Api url for getting all users */
    String API_URL_GET_USER_LIST = API_BASE_URL + "users.json";
    /** Api url for getting a user profile: Remember to concatenate id + 'json' */
    String API_URL_GET_USER_DETAILS = API_BASE_URL + "user_";

    /**
     * Retrieves an {@link rx.Observable} which will emit a List of {@link GasStationEntity}.
     */
    Observable<List<GasStationEntity>> gasStationEntityList();

    /**
     * Retrieves an {@link rx.Observable} which will emit a {@link GasStationEntity}.
     *
     * @param gasStationId The user id used to get user data.
     */
    Observable<GasStationEntity> gasStationEntityById(final int gasStationId);

}
