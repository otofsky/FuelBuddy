package com.fuelbuddy.interactor;


import com.fuelbuddy.FuelUpdateFactory;
import com.fuelbuddy.PriceValidator;
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

/**
 * Created by zjuroszek on 20.11.16.
 */


public class UpdateFuelPricesUseCase extends UseCase {


    FuelUpdateFactory mFuelUpdateFactory;
    GasStationsRepository gasStationsRepository;
    UserRepository userRepository;
    FuelPricesUpdate mFuelPricesUpdate;
    PriceValidator priceValidator;
    File file;


    @Inject
    public UpdateFuelPricesUseCase(FuelUpdateFactory fuelUpdateFactory, PriceValidator priceValidator, GasStationsRepository gasStationsRepository, UserRepository userRepository, ThreadExecutor threadExecutor, PostExecutionThread postExecutionThread) {
        super(threadExecutor, postExecutionThread);
        mFuelUpdateFactory = fuelUpdateFactory;
        this.priceValidator = priceValidator;
        this.userRepository = userRepository;
        this.gasStationsRepository = gasStationsRepository;
    }


    public void validateFuelPrices(File file) {
        this.file = file;
    }

/*    @Override
    protected Observable buildUseCaseObservable() {
        return gasStationsRepository.uploadVideo(file);

    }*/

    public boolean validateFuelPrices(String gasStationId, String fuel92, String fuel95, String diesel, PriceValidator.UpdateFinishedListener updateFinishedListener) {

        if (priceValidator.validatePrice(fuel92, fuel95, diesel, updateFinishedListener)) {
            mFuelPricesUpdate = mFuelUpdateFactory.createFuelUpdate(gasStationId, fuel92, fuel95, diesel);
            return true;
        } else {
            return false;
        }
    }

    @Override
    protected Observable buildUseCaseObservable() {

        return userRepository.getCurrentUser().flatMap(
                new Func1<User, Observable<Response>>() {
                    @Override
                    public Observable<Response> call(User user) {
                        return gasStationsRepository.updateStation(mFuelPricesUpdate.getiD(), user.getUserID(), mFuelPricesUpdate.getPrice92(), mFuelPricesUpdate.getPrice95(), mFuelPricesUpdate.getPriceDiesel());
                    }
                });
    }

}
