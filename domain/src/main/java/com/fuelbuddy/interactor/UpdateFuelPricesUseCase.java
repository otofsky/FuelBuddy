package com.fuelbuddy.interactor;


import com.fuelbuddy.FuelUpdateFactory;
import com.fuelbuddy.data.FuelPricesUpdate;
import com.fuelbuddy.executor.PostExecutionThread;
import com.fuelbuddy.executor.ThreadExecutor;
import com.fuelbuddy.repository.GasStationsRepository;

import javax.inject.Inject;

import rx.Observable;

/**
 * Created by zjuroszek on 20.11.16.
 */


public class UpdateFuelPricesUseCase extends UseCase  {

    FuelUpdateFactory mFuelUpdateFactory;
    GasStationsRepository gasStationsRepository;
    FuelPricesUpdate mFuelPricesUpdate;

    @Inject
    public UpdateFuelPricesUseCase(FuelUpdateFactory fuelUpdateFactory, GasStationsRepository gasStationsRepository, ThreadExecutor threadExecutor, PostExecutionThread postExecutionThread) {
        super(threadExecutor, postExecutionThread);
        mFuelUpdateFactory = fuelUpdateFactory;
        this.gasStationsRepository = gasStationsRepository;
    }

    public void setFuelPricesUpdate(String fuel92, String fuel95, String diesel) {
      //  this.mFuelPricesUpdate = fuelPricesUpdate;
    }

    @Override
    protected Observable buildUseCaseObservable() {

        return gasStationsRepository.updateStation(mFuelPricesUpdate.getiD(), mFuelPricesUpdate.getUserID(), mFuelPricesUpdate.getPrice92(),
                mFuelPricesUpdate.getPrice95(), mFuelPricesUpdate.getPriceDiesel(),mFuelPricesUpdate.getVideoFile());
    }
}
