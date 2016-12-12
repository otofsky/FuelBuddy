package com.fuelbuddy.data.net;


import com.fuelbuddy.data.entity.GasStationEntity;
import com.fuelbuddy.data.entity.ResponseEntity;
import com.fuelbuddy.data.entity.UserEntity;
import com.fuelbuddy.data.net.utils.PrimitiveConverterFactory;
import com.fuelbuddy.data.net.utils.RxErrorHandlingCallAdapterFactory;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Observable;
import rx.schedulers.Schedulers;

/**
 * Created by pliszka on 14.09.15.
 */
public class ApiInvoker {
    private String TAG = "WeatherInvoker";
    private static ApiInvoker httpsInvoker;

    // public static final String BASE_URL = "http://api.openweathermap.org/data/2.5/";
    // "http://fuelbuddy.dk/ws/stations?latitude=55.951869964599610&longitude=8.514181137084961";
    String BASE_URL = "http://fuelbuddy.dk/ws/";


    private ApiInterface apiInterface;

    private ApiInvoker() {
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient client = new OkHttpClient.Builder().addInterceptor(interceptor).build();

        Gson gson = new GsonBuilder()
                .setLenient()
                .create();

        RxJavaCallAdapterFactory rxAdapter = RxJavaCallAdapterFactory.createWithScheduler(Schedulers.io());
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .addConverterFactory(PrimitiveConverterFactory.create())
                .addCallAdapterFactory(RxErrorHandlingCallAdapterFactory.create())
                .client(client)
                .build();

        apiInterface = retrofit.create(ApiInterface.class);

    }

    public static ApiInvoker getInstance() {
        if (httpsInvoker == null) {
            httpsInvoker = new ApiInvoker();
        }
        return httpsInvoker;
    }


    public Observable<List<GasStationEntity>> getGasStations(String latitude, String longitude) {
        return apiInterface.getGasStations(latitude, longitude);
    }

    public Observable<ResponseEntity> updateStation(Double iD, String userID, Double price92
            , Double price95, Double priceDiesel) {
        return apiInterface.updateStation(iD, userID, price92, price95, priceDiesel);
    }

    public Observable<ResponseEntity> addNewUser(String userID, String profileName, String email) {
        return apiInterface.addNewUser(userID, profileName, email);
    }

    public Observable<UserEntity> checkUser(String userID) {
        return apiInterface.checkUser(userID);
    }


}
