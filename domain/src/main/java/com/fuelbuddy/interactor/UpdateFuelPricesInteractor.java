package com.fuelbuddy.interactor;

import com.fuelbuddy.data.FuelPricesUpdated;
import com.fuelbuddy.data.User;
import com.fuelbuddy.executor.PostExecutionThread;
import com.fuelbuddy.executor.ThreadExecutor;
import com.fuelbuddy.repository.GasStationsRepository;
import com.fuelbuddy.repository.UserRepository;

import javax.inject.Inject;

import rx.Observable;

/**
 * Created by zjuroszek on 20.11.16.
 */


public class UpdateFuelPricesInteractor extends UseCase  {

    GasStationsRepository gasStationsRepository;
    FuelPricesUpdated fuelPricesUpdated;

    @Inject
    public UpdateFuelPricesInteractor(GasStationsRepository gasStationsRepository, ThreadExecutor threadExecutor, PostExecutionThread postExecutionThread) {
        super(threadExecutor, postExecutionThread);
        this.gasStationsRepository = gasStationsRepository;
    }

    public void setFuelPricesUpdated(FuelPricesUpdated fuelPricesUpdated) {
        this.fuelPricesUpdated = fuelPricesUpdated;
    }

    @Override
    protected Observable buildUseCaseObservable() {
        return gasStationsRepository.updateStation(fuelPricesUpdated.getiD(),fuelPricesUpdated.getUserID(),fuelPricesUpdated.getPrice92(),
                fuelPricesUpdated.getPrice95(),fuelPricesUpdated.getPriceDiesel());
    }
}
