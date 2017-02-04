package com.fuelbuddy.data.net;


import com.fuelbuddy.data.cache.SharePreferencesUserCacheImpl;
import com.fuelbuddy.data.cache.UserCache;
import com.fuelbuddy.data.entity.AuthEntity;
import com.fuelbuddy.data.entity.GasStationEntity;
import com.fuelbuddy.data.entity.ResponseEntity;
import com.fuelbuddy.data.entity.UserEntity;
import com.fuelbuddy.data.net.utils.NetworkUtil;
import com.fuelbuddy.data.net.utils.PrimitiveConverterFactory;
import com.fuelbuddy.data.net.utils.RxErrorHandlingCallAdapterFactory;
import com.fuelbuddy.data.net.utils.StringHelper;
import com.fuelbuddy.data.repository.datasource.UserDataStore.UserStoreFactory;
import com.fuelbuddy.data.util.RequestHelper;
import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.inject.Inject;

import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Header;
import rx.Observable;
import rx.schedulers.Schedulers;

/**
 * Created by pliszka on 14.09.15.
 */
public class ApiInvoker {


    private ApiInterface apiInterface;

    UserCache mSharePreferencesUserCache;


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

        Gson gson = new GsonBuilder()
                .setLenient()
                .create();

        RxJavaCallAdapterFactory rxAdapter = RxJavaCallAdapterFactory.createWithScheduler(Schedulers.io());
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(NetworkUtil.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .addCallAdapterFactory(RxErrorHandlingCallAdapterFactory.create())
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

    public Observable<List<GasStationEntity>> getGasStations(String latitude, String longitude) {
        return apiInterface.getGasStations(latitude, longitude);
    }

    public Observable<ResponseEntity> updateStation(String iD, String userID,
                                                    String photoID, Double price92,
                                                    Double price95, Double priceDiesel) {
        return apiInterface.updateStation(iD, userID,photoID, price92, price95, priceDiesel);
    }

    public Observable<ResponseEntity> uploadVideo(File file) {
        MediaType mediaType2 = MediaType.parse("multipart/form-data");
        RequestBody requestFile = RequestBody.create(mediaType2, file);
        MultipartBody.Part body = MultipartBody.Part.createFormData("video", file.getName(), requestFile);
        String descriptionString = "hello, this is description speaking";
        RequestBody description = RequestBody.create(MediaType.parse("multipart/form-data"), descriptionString);
        return apiInterface.updateStation(description, body);
    }

    public Observable<ResponseEntity> addNewUser(String userID, String profileName, String email) {
        return apiInterface.addNewUser(userID, profileName, email);
    }

    public Observable<UserEntity> checkUser(String userID) {
        return apiInterface.checkUser(userID);
    }

    public Observable<ResponseEntity> auth(String userID, String email) {
        return apiInterface.auth(userID, email);
    }

}
