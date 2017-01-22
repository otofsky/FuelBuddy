package com.fuelbuddy.interactor;


import com.fuelbuddy.FuelUpdateFactory;
import com.fuelbuddy.data.FuelPricesUpdate;
import com.fuelbuddy.data.Response;
import com.fuelbuddy.data.User;
import com.fuelbuddy.executor.PostExecutionThread;
import com.fuelbuddy.executor.ThreadExecutor;
import com.fuelbuddy.repository.GasStationsRepository;
import com.fuelbuddy.repository.UserRepository;

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

    @Inject
    public UpdateFuelPricesUseCase(FuelUpdateFactory fuelUpdateFactory, GasStationsRepository gasStationsRepository, UserRepository userRepository, ThreadExecutor threadExecutor, PostExecutionThread postExecutionThread) {
        super(threadExecutor, postExecutionThread);
        mFuelUpdateFactory = fuelUpdateFactory;
        this.userRepository = userRepository;
        this.gasStationsRepository = gasStationsRepository;
    }

    public void setFuelPricesUpdate(String gasStationId, String fuel92, String fuel95, String diesel) {
        mFuelPricesUpdate = mFuelUpdateFactory.createFuelUpdate(gasStationId, fuel92, fuel95, diesel, null);
    }

    @Override
    protected Observable buildUseCaseObservable() {
        return getCurrentUser().map(update);
    }

    private final Func1<User, Observable<Response>> update = new Func1<User, Observable<Response>>() {
        @Override
        public Observable<Response> call(User user) {
            mFuelPricesUpdate.setUserID(user.getUserID());
            mFuelPricesUpdate.toString();

            return gasStationsRepository.updateStation(mFuelPricesUpdate.getiD(),user.getUserID(),
                    mFuelPricesUpdate.getPrice92(),mFuelPricesUpdate.getPrice95(),mFuelPricesUpdate.getPriceDiesel(),null);
        }
    };
    public Observable<User> getCurrentUser() {
        return userRepository.getCurrentUser();
    }
}
