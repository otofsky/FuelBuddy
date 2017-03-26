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

import io.reactivex.Observable;
import io.reactivex.functions.Function;


/**
 * Created by zjuroszek on 20.11.16.
 */


public class UpdateFuelPricesUseCase extends UseCase<FuelPricesUpdate, UpdateFuelPricesUseCase.Params> {

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
    protected Observable<FuelPricesUpdate> buildUseCaseObservable(Params params) {
        return userRepository.getCurrentUser()
                .map(new Function<User, FuelPricesUpdate>() {
                    @Override
                    public FuelPricesUpdate apply(User user) throws Exception {
                        mFuelPricesUpdate.setUserID(user.getUserID());
                        return mFuelPricesUpdate;
                    }
                });
    }

    public static final class Params {}
}


