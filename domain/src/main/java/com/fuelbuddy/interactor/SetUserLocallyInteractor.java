package com.fuelbuddy.interactor;

import com.fuelbuddy.data.User;
import com.fuelbuddy.executor.PostExecutionThread;
import com.fuelbuddy.executor.ThreadExecutor;
import com.fuelbuddy.repository.UserRepository;

import javax.inject.Inject;

import rx.Observable;

/**
 * Created by zjuroszek on 20.11.16.
 */


public class SetUserLocallyInteractor extends UseCase  {


    UserRepository userRepository;
    private User mUser;

    @Inject
    public SetUserLocallyInteractor(UserRepository userRepository, ThreadExecutor threadExecutor, PostExecutionThread postExecutionThread) {
        super(threadExecutor, postExecutionThread);
        this.userRepository = userRepository;
    }

    public void setUser(User user){
        this.mUser = user;
    }

    @Override
    protected Observable buildUseCaseObservable() {
        return userRepository.setCurrentUser(mUser);
    }

}