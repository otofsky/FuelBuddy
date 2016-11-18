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

/**
 * Created by zjuroszek on 14.11.16.
 */

public class GetCurrentUser extends UseCase  {
    protected GetCurrentUser(ThreadExecutor threadExecutor, PostExecutionThread postExecutionThread) {
        super(threadExecutor, postExecutionThread);
    }



 /*   @Inject
    public GetCurrentUser(UserRepository userRepository,ThreadExecutor threadExecutor,
                          PostExecutionThread postExecutionThread) {
        this.mUserRepository = userRepository;
    }*/



    @DebugLog
    public Observable<User> getCurrentUser(){
        Observable <User> observable = Observable.create(new Observable.OnSubscribe<User>() {
            @Override
            public void call(Subscriber<? super User> subscriber) {
                subscriber.onNext(new User("1"));
            }
        });
        return observable;
    }


    @Override
    protected Observable buildUseCaseObservable() {
        return null;
    }
}

