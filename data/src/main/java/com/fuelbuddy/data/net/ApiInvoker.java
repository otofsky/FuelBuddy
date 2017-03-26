package com.fuelbuddy.data.net;


import android.util.Log;

import com.fuelbuddy.data.cache.UserCache;
import com.fuelbuddy.data.entity.GasStationEntity;
import com.fuelbuddy.data.entity.ResponseEntity;
import com.fuelbuddy.data.entity.UploadResponseEntity;
import com.fuelbuddy.data.entity.UserEntity;
import com.fuelbuddy.data.net.utils.NetworkUtil;
import com.fuelbuddy.data.net.utils.StringHelper;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class ApiInvoker {


    private ApiInterface apiInterface;
    UserCache mSharePreferencesUserCache;

    private static ApiInvoker httpsInvoker;


    @Inject
    public ApiInvoker(final UserCache sharePreferencesUserCache) {
        this.mSharePreferencesUserCache = sharePreferencesUserCache;

        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .addInterceptor(interceptor)
                .addInterceptor(new Interceptor() {
                    @Override
                    public okhttp3.Response intercept(Chain chain) throws IOException {
                        String token = sharePreferencesUserCache.getToken();
                        Request original = chain.request();
                        Request request = null;
                        if (!StringHelper.isNullOrEmpty(token)) {
                            request = initRequestWithHeader(original, token);
                        } else {
                            request = initRequestWithNoHeader(original);
                        }
                        okhttp3.Response response = chain.proceed(request);
                        response.cacheResponse();
                        return response;
                    }
                })

                .build();


        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(NetworkUtil.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(okHttpClient)
                .build();

        apiInterface = retrofit.create(ApiInterface.class);

    }

    public ApiInvoker() {

        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .connectTimeout(60, TimeUnit.SECONDS)
                .readTimeout(60, TimeUnit.SECONDS)
                .writeTimeout(60, TimeUnit.SECONDS)
                .addInterceptor(interceptor)
                .addInterceptor(new Interceptor() {
                    @Override
                    public okhttp3.Response intercept(Chain chain) throws IOException {

                        Request original = chain.request();
                        Request request = null;

                        request = initRequestWithNoHeader(original);

                        okhttp3.Response response = chain.proceed(request);
                        response.cacheResponse();
                        return response;
                    }
                })

                .build();

        Gson gson = new GsonBuilder()
                .setLenient()
                .create();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(NetworkUtil.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .client(okHttpClient)
                .build();

        apiInterface = retrofit.create(ApiInterface.class);
    }

    private Request initRequestWithNoHeader(Request original) {
        Request request;
        request = original.newBuilder()
                .build();
        return request;
    }

    private Request initRequestWithHeader(Request original, String token) {
        Request request;
        request = original.newBuilder()
                .addHeader(NetworkUtil.HEADER, token)
                .build();
        return request;
    }

    public static ApiInvoker getInstance() {

        if (httpsInvoker == null) {
            httpsInvoker = new ApiInvoker();
        }
        return httpsInvoker;
    }

    public List<GasStationEntity> getGasStations(String token, String latitude, String longitude) {
        try {
            return apiInterface.getGasStations(token, latitude, longitude).execute().body();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public ResponseEntity updateStation(String token, String iD, String userID,
                                        String photoID, Double price92,
                                        Double price95, Double priceDiesel) {
        try {

            return apiInterface.updatePrices(token, iD, userID, photoID, price92, price95, priceDiesel).execute().body();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    public UploadResponseEntity uploadVideo(String token, File file) {
        MediaType mediaType2 = MediaType.parse("multipart/form-data");
        RequestBody requestFile = RequestBody.create(mediaType2, file);
        MultipartBody.Part body = MultipartBody.Part.createFormData("video", file.getName(), requestFile);
        String descriptionString = "hello, this is description speaking";
        RequestBody description = RequestBody.create(MediaType.parse("multipart/form-data"), descriptionString);
        try {
            return apiInterface.uploadVideo(token, description, body).execute().body();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    public ResponseEntity addNewUser(String userID, String profileName, String email) {
        try {
            return apiInterface.addNewUser(userID, profileName, email).execute().body();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public UserEntity checkUser(String userID) {
        try {
            return apiInterface.checkUser(userID).execute().body();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public ResponseEntity auth(String userID, String email) {
        try {
            return apiInterface.auth(userID, email).execute().body();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
