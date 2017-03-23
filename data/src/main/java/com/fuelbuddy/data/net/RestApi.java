package com.fuelbuddy.data.net;

import com.fuelbuddy.data.Position;
import com.fuelbuddy.data.UploadResponse;
import com.fuelbuddy.data.entity.GasStationEntity;
import com.fuelbuddy.data.entity.ResponseEntity;
import com.fuelbuddy.data.entity.UploadResponseEntity;
import com.fuelbuddy.data.entity.UserEntity;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.File;
import java.util.List;

import io.reactivex.Observable;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;


/**
 * Created by zjuroszek on 07.10.16.
 */
public interface RestApi {

    Observable<List<GasStationEntity>> gasStationEntityList(String token,Position position);

    Observable<ResponseEntity> updateStation(String token,String iD, String userID,  String photoID, Double price92, Double price95, Double priceDiesel);

    Observable<UploadResponseEntity> uploadVideo(String token, File file);

    Observable<ResponseEntity> addNewUser(UserEntity userEntity);

    Observable<ResponseEntity> auth(String userId, String email);

    Observable<UserEntity> checkUser(String userId);

}
