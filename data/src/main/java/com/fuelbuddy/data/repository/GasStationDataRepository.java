package com.fuelbuddy.data.repository;

import com.fuelbuddy.data.GasStation;
import com.fuelbuddy.data.Position;
import com.fuelbuddy.data.Response;
import com.fuelbuddy.data.UploadResponse;
import com.fuelbuddy.data.entity.GasStationEntity;
import com.fuelbuddy.data.entity.ResponseEntity;
import com.fuelbuddy.data.entity.UploadResponseEntity;
import com.fuelbuddy.data.entity.mapper.GasStationEntityDataMapper;
import com.fuelbuddy.data.entity.mapper.ResponseEntityMapper;
import com.fuelbuddy.data.repository.datasource.GasStationDataStore.GasStationDataStore;
import com.fuelbuddy.data.repository.datasource.GasStationDataStore.GasStationStoreFactory;
import com.fuelbuddy.repository.GasStationsRepository;

import java.io.File;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Observable;
import io.reactivex.functions.Function;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;


/**
 * Created by zjuroszek on 06.11.16.
 */


@Singleton
public class GasStationDataRepository implements GasStationsRepository {

    private final GasStationStoreFactory mGasStationStoreFactory;
    GasStationEntityDataMapper mGasStationEntityDataMapper;
    ResponseEntityMapper responseEntityMapper;

    @Inject
    public GasStationDataRepository(GasStationEntityDataMapper gasStationEntityDataMapper,ResponseEntityMapper responseEntityMapper,GasStationStoreFactory mGasStationStoreFactory ) {
        this.mGasStationEntityDataMapper = gasStationEntityDataMapper;
        this.responseEntityMapper = responseEntityMapper;
        this.mGasStationStoreFactory = mGasStationStoreFactory;
    }

    @Override
    public Observable<List<GasStation>> gasStations(Position position) {
        GasStationDataStore gasStationDataStore = mGasStationStoreFactory.createCloudDataStore();

        return gasStationDataStore.gasStationsEntityList(position).map(new Function<List<GasStationEntity>, List<GasStation>>() {
            @Override
            public List<GasStation> apply(List<GasStationEntity> gasStationEntities) {
                return  mGasStationEntityDataMapper.transform(gasStationEntities);
            }
        });
    }

    @Override
    public Observable<Response> updateStation(String iD, String userID, String photoID, Double price92, Double price95, Double priceDiesel) {
        GasStationDataStore gasStationDataStore = mGasStationStoreFactory.createCloudDataStore();
        return  gasStationDataStore.updateStation(iD,userID,photoID,price92,price95,priceDiesel).map(new Function<ResponseEntity, Response>() {
            @Override
            public Response apply(ResponseEntity responseEntity) {
                return responseEntityMapper.transformToResponse(responseEntity);
            }
        });
    }

    @Override
    public Observable<UploadResponse> uploadVideo(File file) {
        GasStationDataStore gasStationDataStore = mGasStationStoreFactory.createCloudDataStore();
        return  gasStationDataStore.uploadVideo(file).map(new Function<UploadResponseEntity, UploadResponse>() {
            @Override
            public UploadResponse apply(UploadResponseEntity uploadResponseEntity) {
                return responseEntityMapper.transformToUploadResponse(uploadResponseEntity);
            }
        });
    }

}
