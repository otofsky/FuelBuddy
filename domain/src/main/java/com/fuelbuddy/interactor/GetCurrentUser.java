package com.fuelbuddy.interactor;

import com.fuelbuddy.data.User;
import com.fuelbuddy.executor.PostExecutionThread;
import com.fuelbuddy.executor.ThreadExecutor;
import com.fuelbuddy.repository.UserRepository;


import javax.inject.Inject;

import rx.Observable;
import rx.functions.Func1;

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
        getLocalUserObservable().concatMap(validateRemoteUser);
        return userRepository.getCurrentUser();
    }

    private final Func1<User, Observable<User>> validateLocalUser =
            new Func1<User, Observable<User>>() {
                @Override
                public Observable<User> call(User user) {
                    if (user == null) {
                        return userRepository.getCheckUser("43");
                    } else {
                        return getUser(user);
                    }
                }
            };

    private final Func1<User, Observable<User>> validateRemoteUser =
            new Func1<User, Observable<User>>() {
                @Override
                public Observable<User> call(User user) {
                    if (user == null) {
                        return userRepository.getCheckUser("43");
                    } else {
                        return getUser(user);
                    }
                }
            };

    protected Observable getLocalUserObservable() {
        return userRepository.getCurrentUser();
    }

    protected Observable getRemoteUserObservable(String userId) {
        return userRepository.getCheckUser(userId);
    }

    public Observable<User> getUser(User user) {
        return Observable.just(user);
    }
}

