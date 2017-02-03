package com.fuelbuddy.interactor;

import com.fuelbuddy.data.Response;
import com.fuelbuddy.data.User;
import com.fuelbuddy.executor.PostExecutionThread;
import com.fuelbuddy.executor.ThreadExecutor;
import com.fuelbuddy.repository.UserRepository;

import javax.inject.Inject;

import rx.Observable;
import rx.functions.Func1;

/**
 * Created by zjuroszek on 20.11.16.
 */


public class SetUserLocallyUseCase extends UseCase  {


    UserRepository userRepository;
    private User mUser;

    @Inject
    public SetUserLocallyUseCase(UserRepository userRepository, ThreadExecutor threadExecutor, PostExecutionThread postExecutionThread) {
        super(threadExecutor, postExecutionThread);
        this.userRepository = userRepository;
    }

    public void setUser(User user){
        this.mUser = user;
    }

    @Override
    protected Observable buildUseCaseObservable() {
        return userRepository.setCurrentUser(mUser).flatMap(onUserExist);
    }

    private final Func1<Response, Observable<Response>> onUserExist = new Func1<Response, Observable<Response>>() {
        @Override
        public Observable<Response> call(Response response) {
            return userRepository.auth(mUser.getUserID(),mUser.getEmail());
        }
    };
}
