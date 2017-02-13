package com.fuelbuddy.data.entity.mapper;

import com.fuelbuddy.data.Response;
import com.fuelbuddy.data.UploadResponse;
import com.fuelbuddy.data.User;
import com.fuelbuddy.data.entity.ResponseEntity;
import com.fuelbuddy.data.entity.UploadResponseEntity;
import com.fuelbuddy.data.entity.UserEntity;

import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * Created by zjuroszek on 18.11.16.
 */

@Singleton
public class ResponseEntityMapper {
    @Inject
    public ResponseEntityMapper() {
    }

    public Response transformToResponse(ResponseEntity responseEntity) {
        if (responseEntity == null) {
            throw new IllegalArgumentException("Cannot transformToUser a null value");
        }
        Response response = new Response();
        response.setCode(responseEntity.getCode());
        response.setMessage(responseEntity.getMessage());
        return response;
    }

    public UploadResponse transformToUploadResponse(UploadResponseEntity responseEntity) {
        if (responseEntity == null) {
            throw new IllegalArgumentException("Cannot transformToUser a null value");
        }
        UploadResponse response = new UploadResponse();
        response.setCode(responseEntity.getCode());
        response.setFileID(responseEntity.getFileID());
        return response;
    }

}
