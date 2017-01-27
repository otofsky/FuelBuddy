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


public class AuthUseCase extends UseCase {

    UserRepository userRepository;


    @Inject
    public AuthUseCase( UserRepository userRepository, ThreadExecutor threadExecutor, PostExecutionThread postExecutionThread) {
        super(threadExecutor, postExecutionThread);
        this.userRepository = userRepository;
    }
    public void setFuelPricesUpdate(String userId, String email) {
    }

    @Override
    protected Observable buildUseCaseObservable() {
        return userRepository.auth("","");
    }
}
