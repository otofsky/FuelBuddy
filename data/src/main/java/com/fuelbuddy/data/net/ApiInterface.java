package com.fuelbuddy.data.net;


import com.fuelbuddy.data.entity.AuthEntity;
import com.fuelbuddy.data.entity.GasStationEntity;
import com.fuelbuddy.data.entity.ResponseEntity;
import com.fuelbuddy.data.entity.UploadResponseEntity;
import com.fuelbuddy.data.entity.UserEntity;

import java.util.List;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Query;
import rx.Observable;


public interface ApiInterface {


    @GET("stations?")
    Observable<List<GasStationEntity>> getGasStations( @Query("latitude") String latitude,
                                                      @Query("longitude") String longitude);


    //fuelbuddy.dk/ws/updatestation?userID=5&ID=512&price92=1.20&price95=1.35&priceDiesel=1.1&photoID=2
    @GET("updatestation?")
    Observable<ResponseEntity> updatePrices(@Header("TokenAuth") String header, @Query("ID") String iD,
                                            @Query("userID") String userID,
                                            @Query("photoID") String photoID,
                                            @Query("price92") Double price92,
                                            @Query("price95") Double price95,
                                            @Query("priceDiesel") Double priceDiesel);
    @Multipart
    @POST("upload?")
    Observable<UploadResponseEntity> uploadVideo(@Header("TokenAuth") String header,@Part("description") RequestBody description, @Part MultipartBody.Part file);

    @GET("updatestation?")
    Call<Void> updatePrices(@Header("TokenAuth") String header, @Query("ID") String iD,
                                            @Query("userID") String userID,
                                            @Query("photoID") String photoID,
                                            @Query("price92") Double price92,
                                            @Query("price95") Double price95,
                                            @Query("priceDiesel") Double priceDiesel,  Callback<ResponseEntity> callback);
    @Multipart
    @POST("upload?")
    Call<Void> uploadVideo(@Header("TokenAuth") String header, @Part("description") RequestBody description, @Part MultipartBody.Part file, Callback<UploadResponseEntity> callback );


    @GET("adduser?")
    Observable<ResponseEntity> addNewUser(@Query("userID") String userID, @Query("profileName") String profileName, @Query("email") String email);

    @GET("checkuser?")
    Observable<UserEntity> checkUser(@Query("userID") String userID);

    @GET("auth?")
    Observable<ResponseEntity> auth(@Query("userID") String userID, @Query("email") String email );

}
