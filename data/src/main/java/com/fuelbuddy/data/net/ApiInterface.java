package com.fuelbuddy.data.net;


import com.fuelbuddy.data.entity.GasStationEntity;
import com.fuelbuddy.data.entity.ResponseEntity;
import com.fuelbuddy.data.entity.UploadResponseEntity;
import com.fuelbuddy.data.entity.UserEntity;

import java.util.List;

import io.reactivex.Observable;
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



public interface ApiInterface {


    @GET("stations?")
   Call<List<GasStationEntity>> getGasStations(@Header("TokenAuth") String header, @Query("latitude") String latitude,
                                          @Query("longitude") String longitude);
    @GET("updatestation?")
    Call<ResponseEntity> updatePrices(@Header("TokenAuth") String header, @Query("ID") String iD,
                                            @Query("userID") String userID,
                                            @Query("photoID") String photoID,
                                            @Query("price92") Double price92,
                                            @Query("price95") Double price95,
                                            @Query("priceDiesel") Double priceDiesel);

    @Multipart
    @POST("upload?")
    Call<UploadResponseEntity> uploadVideo(@Header("TokenAuth") String header, @Part("description") RequestBody description, @Part MultipartBody.Part file);

    @GET("adduser?")
    Call<ResponseEntity> addNewUser(@Query("userID") String userID, @Query("profileName") String profileName, @Query("email") String email);

    @GET("checkuser?")
    Call<UserEntity> checkUser(@Query("userID") String userID);

    @GET("auth?")
    Call<ResponseEntity> auth(@Query("userID") String userID, @Query("email") String email);

}
