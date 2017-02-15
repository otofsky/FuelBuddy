package com.fuelbuddy.interactor;


import com.fuelbuddy.FuelUpdateFactory;
import com.fuelbuddy.data.FuelPricesUpdate;
import com.fuelbuddy.data.User;
import com.fuelbuddy.executor.PostExecutionThread;
import com.fuelbuddy.executor.ThreadExecutor;
import com.fuelbuddy.repository.GasStationsRepository;
import com.fuelbuddy.repository.UserRepository;
import com.fuelbuddy.validator.InputValidator;

import java.io.File;

import javax.inject.Inject;

import rx.Observable;
import rx.functions.Func1;

/**
 * Created by zjuroszek on 20.11.16.
 */


public class UpdateFuelPricesUseCase extends UseCase {


    FuelUpdateFactory mFuelUpdateFactory;
    GasStationsRepository gasStationsRepository;
    UserRepository userRepository;
    InputValidator priceValidator;
    FuelPricesUpdate mFuelPricesUpdate = null;

    @Inject
    public UpdateFuelPricesUseCase(FuelUpdateFactory fuelUpdateFactory, InputValidator priceValidator, GasStationsRepository gasStationsRepository, UserRepository userRepository, ThreadExecutor threadExecutor, PostExecutionThread postExecutionThread) {
        super(threadExecutor, postExecutionThread);
        mFuelUpdateFactory = fuelUpdateFactory;
        this.priceValidator = priceValidator;
        this.userRepository = userRepository;
        this.gasStationsRepository = gasStationsRepository;
    }

    public boolean validateInputData(File file, String gasStationId, String fuel92, String fuel95, String diesel, InputValidator.UpdateFinishedListener updateFinishedListener) {
        if (priceValidator.validatePrice(file, fuel92, fuel95, diesel, updateFinishedListener)) {
            mFuelPricesUpdate = mFuelUpdateFactory.createFuelUpdate(file, gasStationId, fuel92, fuel95, diesel);
            return true;
        } else {
            return false;
        }
    }

    @Override
    protected Observable buildUseCaseObservable() {
        return userRepository.getCurrentUser()
                .map(new Func1<User, FuelPricesUpdate>() {
                    @Override
                    public FuelPricesUpdate call(User user) {
                        mFuelPricesUpdate.setUserID(user.getUserID());
                        return mFuelPricesUpdate;
                    }
                });
    }


    /*    public boolean validateInputData(File file, String gasStationId, String fuel92, String fuel95, String diesel, InputValidator.UpdateFinishedListener updateFinishedListener) {
        if (priceValidator.validatePrice(file,fuel92, fuel95, diesel, updateFinishedListener)) {
            mFuelPricesUpdate = mFuelUpdateFactory.createFuelUpdate(file,gasStationId, fuel92, fuel95, diesel);
            return true;
        } else {
            return false;
        }
    }*/


  /*  @Override
    protected Observable buildUseCaseObservable() {
        return null;*/

    /*    return Observable.zip(gasStationsRepository.uploadVideo(mFuelPricesUpdate.getFile()), userRepository.getCurrentUser(), new Func2<UploadResponse, User, Observable<Response>>() {
            @Override
            public Observable<Response> call(UploadResponse uploadResponse, User user) {
                return (Observable<Response>) gasStationsRepository.updateStation(mFuelPricesUpdate.getStationID(), user.getUserID(), uploadResponse.getFileID(),
                        mFuelPricesUpdate.getPrice92(), mFuelPricesUpdate.getPrice95(), mFuelPricesUpdate.getPriceDiesel()).subscribe(new Action1<Response>() {
                    @Override
                    public void call(Response response) {

                    }
                });
            }
        });*/
}

/*
    @Override
    protected Observable buildUseCaseObservable() {

        return  Observable.zip(gasStationsRepository.uploadVideo(mFuelPricesUpdate.getFile()),userRepository.getCurrentUser(), new Func2<UploadResponse, User, Response>() {
            @Override
            public Response call(UploadResponse uploadResponse, User user) {
                return Observable.amb(updateStation(uploadResponse,user)).map(new Func1<Object, Object>() {
                });
            }
        });
    }*/


/*    public Observable<Response> updateStation(UploadResponse uploadResponse, User user) {
        return (Observable<Response>) gasStationsRepository.updateStation(mFuelPricesUpdate.getStationID(), user.getUserID(), uploadResponse.getFileID(),
                mFuelPricesUpdate.getPrice92(), mFuelPricesUpdate.getPrice95(), mFuelPricesUpdate.getPriceDiesel());
    }*/



/*    Func1<Observable <Response>, Response> updateVideo =new Func2<Observable<Response>, Response>() {
        @Override
        public Response call(Response o, Object o2) {
            return null;
        }

        @Override
        public Response call(UploadResponse uploadResponse, User user) {
            return (Response) gasStationsRepository.updateStation(mFuelPricesUpdate.getStationID(), user.getUserID(), uploadResponse.getFileID() ,
                    mFuelPricesUpdate.getPrice92(), mFuelPricesUpdate.getPrice95(), mFuelPricesUpdate.getPriceDiesel())
                    .subscribe(new Action1<Response>() {
                        @Override
                        public void call(Response response) {

                        }
                    });
        }

    });*/


