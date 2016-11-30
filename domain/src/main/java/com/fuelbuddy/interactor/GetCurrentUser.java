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
import rx.schedulers.Schedulers;

/**
 * Created by zjuroszek on 14.11.16.
 */

public class GetCurrentUser extends UseCase {

    UserRepository userRepository;

    @Inject
    public GetCurrentUser(UserRepository userRepository, ThreadExecutor threadExecutor, PostExecutionThread postExecutionThread) {
        super(threadExecutor, postExecutionThread);
        this.userRepository = userRepository;
    }

    @Override
    protected Observable buildUseCaseObservable() {
        getUserObservable().concatMap(validateUser);
        return userRepository.getCurrentUser();
    }


    private final Func1<User, Observable<User>> validateUser =
            new Func1<User, Observable<User>>() {
                @Override
                public Observable<User> call(User user) {
                    if (user == null) {
                        return userRepository.getCheckUser("1");
                    } else {
                        return getUser(user);
                    }
                }
            };

    protected Observable getUserObservable() {
        return userRepository.getCurrentUser();
    }

    public Observable<User> getUser(User user) {
        return Observable.just(user);
    }
}

