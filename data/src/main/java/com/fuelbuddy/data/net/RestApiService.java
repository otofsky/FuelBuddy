package com.fuelbuddy.data.net;

import com.fuelbuddy.data.GasStation;
import com.fuelbuddy.data.Position;
import com.fuelbuddy.data.entity.GasStationEntity;
import com.fuelbuddy.data.entity.ResponseEntity;
import com.fuelbuddy.data.entity.UserEntity;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.List;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import rx.Observable;

/**
 * Created by zjuroszek on 07.10.16.
 */
public interface RestApiService {

    String API_BASE_URL = "";
    String ENDPOINT = "http://fuelbuddy.dk/ws/stations?latitude=55.951869964599610&longitude=8.514181137084961";

    /** Api url for getting all users */
    String API_URL_GET_USER_LIST = API_BASE_URL + "users.json";
    /** Api url for getting a user profile: Remember to concatenate id + 'json' */
    String API_URL_GET_USER_DETAILS = API_BASE_URL + "user_";

    /**
     * Retrieves an {@link rx.Observable} which will emit a List of {@link GasStationEntity}.
     */


    Observable<List<GasStationEntity>> gasStationEntityList(Position position);

     Observable<ResponseEntity> updateStation(Double iD, String userID, Double price92
            , Double price95, Double priceDiesel);

    Observable<ResponseEntity> addNewUser(UserEntity userEntity);

    Observable<UserEntity> checkUser(String userId);

    Observable<GasStationEntity> gasStationEntityById(final int gasStationId);



    class Creator {
        public static RestApiService newRestApiService() {
            Gson gson = new GsonBuilder()
                    .setDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
                    .create();
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(RestApiService.ENDPOINT)
                    .addConverterFactory(GsonConverterFactory.create(gson))

                    .build();
            return retrofit.create(RestApiService.class);
        }
    }


}
