package com.fuelbuddy.interactor;


import com.fuelbuddy.FuelUpdateFactory;
import com.fuelbuddy.data.UploadResponse;
import com.fuelbuddy.validator.InputValidator;
import com.fuelbuddy.data.FuelPricesUpdate;
import com.fuelbuddy.data.Response;
import com.fuelbuddy.data.User;
import com.fuelbuddy.executor.PostExecutionThread;
import com.fuelbuddy.executor.ThreadExecutor;
import com.fuelbuddy.repository.GasStationsRepository;
import com.fuelbuddy.repository.UserRepository;

import java.io.File;

import javax.inject.Inject;

import rx.Observable;
import rx.functions.Func1;
import rx.functions.Func2;

/**
 * Created by zjuroszek on 20.11.16.
 */


public class UpdateFuelPricesUseCase extends UseCase {


    FuelUpdateFactory mFuelUpdateFactory;
    GasStationsRepository gasStationsRepository;
    UserRepository userRepository;
    FuelPricesUpdate mFuelPricesUpdate;
    InputValidator priceValidator;

    @Inject
    public UpdateFuelPricesUseCase(FuelUpdateFactory fuelUpdateFactory, InputValidator priceValidator, GasStationsRepository gasStationsRepository, UserRepository userRepository, ThreadExecutor threadExecutor, PostExecutionThread postExecutionThread) {
        super(threadExecutor, postExecutionThread);
        mFuelUpdateFactory = fuelUpdateFactory;
        this.priceValidator = priceValidator;
        this.userRepository = userRepository;
        this.gasStationsRepository = gasStationsRepository;
    }


    public boolean validateInputData(File file, String gasStationId, String fuel92, String fuel95, String diesel, InputValidator.UpdateFinishedListener updateFinishedListener) {

        if (priceValidator.validatePrice(file,fuel92, fuel95, diesel, updateFinishedListener)) {
            mFuelPricesUpdate = mFuelUpdateFactory.createFuelUpdate(file,gasStationId, fuel92, fuel95, diesel);
            return true;
        } else {
            return false;
        }
    }

    @Override
    protected Observable buildUseCaseObservable() {

        return  Observable.zip(
                gasStationsRepository.uploadVideo(mFuelPricesUpdate.getFile()),userRepository.getCurrentUser(), new Func2<UploadResponse, User, Observable<Response>>() {
                    @Override
                    public Observable<Response> call(UploadResponse uploadResponse, User user) {
                        return gasStationsRepository.updateStation(mFuelPricesUpdate.getStationID(), user.getUserID(), uploadResponse.getFileID() ,
                                mFuelPricesUpdate.getPrice92(), mFuelPricesUpdate.getPrice95(), mFuelPricesUpdate.getPriceDiesel());
                    }
                });
    }

/*    Func1<User, Observable<Response>> updateVideo = new Func1<User, Observable<Response>>() {
        @Override
        public Observable<Response> call(final User user) {
            return gasStationsRepository.uploadVideo(mFuelPricesUpdate.getFile())

                    .flatMap(new Func1<Response, Observable<Response>>() {
                        @Override
                        public Observable<Response> call(Response response) {
                            return gasStationsRepository.updateStation(mFuelPricesUpdate.getStationID(), user.getUserID(), response.getMessage(),
                                    mFuelPricesUpdate.getPrice92(), mFuelPricesUpdate.getPrice95(), mFuelPricesUpdate.getPriceDiesel());
                        }
                    });
        }
    };*/
}
