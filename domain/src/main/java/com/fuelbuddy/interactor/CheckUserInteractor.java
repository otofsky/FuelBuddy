package com.fuelbuddy.interactor;

import com.fuelbuddy.executor.PostExecutionThread;
import com.fuelbuddy.executor.ThreadExecutor;
import com.fuelbuddy.repository.UserRepository;

import javax.inject.Inject;

import rx.Observable;

/**
 * Created by zjuroszek on 20.11.16.
 */


public class CheckUserInteractor extends UseCase  {


    UserRepository userRepository;
    private String mUserId;

    @Inject
    public CheckUserInteractor(UserRepository userRepository, ThreadExecutor threadExecutor, PostExecutionThread postExecutionThread) {
        super(threadExecutor, postExecutionThread);
        this.userRepository = userRepository;
    }

    public void addUserId(String userId){
        this.mUserId = userId;
    }

    @Override
    protected Observable buildUseCaseObservable() {


        return userRepository.getCheckUser(mUserId);
    }

}
