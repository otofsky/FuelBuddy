package com.fuelbuddy.data.net;


import android.util.Log;

import com.fuelbuddy.data.entity.GasStationEntity;
import com.fuelbuddy.data.entity.UserEntity;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Query;
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
                .addCallAdapterFactory(rxAdapter)
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
        Log.d(TAG, "getGasStations: ");
        return apiInterface.getGasStations(latitude,longitude);
    }

    public Observable<String> addNewUser( String userID, String profileName, String email) {
        Log.d(TAG, "getGasStations: ");
        return apiInterface.addNewUser(userID,profileName,email);
    }

    public Observable<UserEntity> checkUser(String userID) {
        Log.d(TAG, "getGasStations: ");
        return apiInterface.checkUser(userID);
    }


}
