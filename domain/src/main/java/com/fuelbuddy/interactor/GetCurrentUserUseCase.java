package com.fuelbuddy.interactor;

import com.fuelbuddy.data.User;
import com.fuelbuddy.executor.PostExecutionThread;
import com.fuelbuddy.executor.ThreadExecutor;
import com.fuelbuddy.repository.UserRepository;

import javax.inject.Inject;

import io.reactivex.Observable;


/**
 * Created by zjuroszek on 14.11.16.
 */

public class GetCurrentUserUseCase extends UseCase<User, GetCurrentUserUseCase.Param> {

    UserRepository userRepository;

    @Inject
    public GetCurrentUserUseCase(UserRepository userRepository, ThreadExecutor threadExecutor, PostExecutionThread postExecutionThread) {
        super(threadExecutor, postExecutionThread);
        this.userRepository = userRepository;
    }

    @Override
    protected Observable buildUseCaseObservable(Param param) {
        return getLocalUserObservable();

    }

    protected Observable getLocalUserObservable() {
        return userRepository.getCurrentUser();
    }

    public class Param {}
}

