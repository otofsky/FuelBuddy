package com.fuelbuddy.interactor;


import com.fuelbuddy.executor.PostExecutionThread;
import com.fuelbuddy.executor.ThreadExecutor;
import com.fuelbuddy.repository.UserRepository;

import javax.inject.Inject;

import rx.Observable;

/**
 * Created by zjuroszek on 20.11.16.
 */


public class AuthUseCase extends UseCase {

    UserRepository userRepository;
    private String userId;
    private String email;


    @Inject
    public AuthUseCase(UserRepository userRepository, ThreadExecutor threadExecutor, PostExecutionThread postExecutionThread) {
        super(threadExecutor, postExecutionThread);
        this.userRepository = userRepository;
    }

    public void setFuelPricesUpdate(String userId, String email) {
        this.userId = userId;
        this.email = email;
    }

    @Override
    protected Observable buildUseCaseObservable() {
        return userRepository.auth(userId, email);
    }




}
