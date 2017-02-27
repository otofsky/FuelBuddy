package com.fuelbuddy.interactor;

import com.fuelbuddy.executor.PostExecutionThread;
import com.fuelbuddy.executor.ThreadExecutor;
import com.fuelbuddy.repository.UserRepository;

import javax.inject.Inject;

import io.reactivex.Observable;


/**
 * Created by zjuroszek on 20.11.16.
 */

public class LogOutUseCase extends UseCase<Boolean, LogOutUseCase.Params> {

    UserRepository userRepository;

    @Inject
    public LogOutUseCase(UserRepository userRepository, ThreadExecutor threadExecutor, PostExecutionThread postExecutionThread) {
        super(threadExecutor, postExecutionThread);
        this.userRepository = userRepository;
    }

    @Override
    protected Observable<Boolean> buildUseCaseObservable(Params params) {
        return userRepository.logOut();
    }

    public static final class Params {
    }

}
