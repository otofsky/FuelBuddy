package com.fuelbuddy.interactor;

import com.fuelbuddy.data.FuelPricesUpdate;
import com.fuelbuddy.executor.PostExecutionThread;
import com.fuelbuddy.executor.ThreadExecutor;
import com.fuelbuddy.repository.GasStationsRepository;

import javax.inject.Inject;

import rx.Observable;

/**
 * Created by zjuroszek on 20.11.16.
 */


public class UpdateFuelPricesInteractor extends UseCase  {

    GasStationsRepository gasStationsRepository;
    FuelPricesUpdate mFuelPricesUpdate;

    @Inject
    public UpdateFuelPricesInteractor(GasStationsRepository gasStationsRepository, ThreadExecutor threadExecutor, PostExecutionThread postExecutionThread) {
        super(threadExecutor, postExecutionThread);
        this.gasStationsRepository = gasStationsRepository;
    }

    public void setFuelPricesUpdate(FuelPricesUpdate fuelPricesUpdate) {
        this.mFuelPricesUpdate = fuelPricesUpdate;
    }

    @Override
    protected Observable buildUseCaseObservable() {
        return gasStationsRepository.updateStation(mFuelPricesUpdate.getiD(), mFuelPricesUpdate.getUserID(), mFuelPricesUpdate.getPrice92(),
                mFuelPricesUpdate.getPrice95(), mFuelPricesUpdate.getPriceDiesel(),mFuelPricesUpdate.getVideoFile());
    }
}
