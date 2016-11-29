package com.fuelbuddy.interactor;

import com.fuelbuddy.data.User;
import com.fuelbuddy.executor.PostExecutionThread;
import com.fuelbuddy.executor.ThreadExecutor;
import com.fuelbuddy.repository.GasStationsRepository;
import com.fuelbuddy.repository.UserRepository;


import javax.inject.Inject;

import hugo.weaving.DebugLog;
import rx.Observable;
import rx.Subscriber;
import rx.functions.Func1;

/**
 * Created by zjuroszek on 14.11.16.
 */

public class GetCurrentUser extends UseCase  {

    UserRepository userRepository;

    @Inject
    public GetCurrentUser(UserRepository userRepository,ThreadExecutor threadExecutor, PostExecutionThread postExecutionThread) {
        super(threadExecutor, postExecutionThread);
        this.userRepository = userRepository;
    }


    @Override
    protected Observable buildUseCaseObservable() {
       Observable observable  =  userRepository.getCurrentUser();
        observable.map(new Func1() {
            @Override
            public Object call(Object o) {
                return null;
            }
        });
        return observable;
    }
}

