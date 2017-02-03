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


public class CheckUserUseCase extends UseCase {

    UserRepository userRepository;
    private User mUser;

    @Inject
    public CheckUserUseCase(UserRepository userRepository, ThreadExecutor threadExecutor, PostExecutionThread postExecutionThread) {
        super(threadExecutor, postExecutionThread);
        this.userRepository = userRepository;
    }

    public void setUser(User user) {
        this.mUser = user;
    }

    @Override
    protected Observable buildUseCaseObservable() {
        // jesli istnieje
        //to zapisje dane lokalnie
        // jesli nie istnieje wysyłam dane na server i zapisuje lokalnie
        //return userRepository.getCheckUser(mUser.getUserID()).concatMap(StoreRemoteUser);
        return userRepository.getCheckUser(mUser.getUserID())
                .flatMap(onUserExist)
                .onErrorResumeNext(errorHandling);

        // to zapisje dane lokalnie
        //wysyłam dane na server i zapisuje lokalnie
    }

    private final Func1<Throwable, Observable<Response>> errorHandling = new Func1<Throwable, Observable<Response>>() {

        @Override
        public Observable<Response> call(Throwable throwable) {
            return userRepository.addNewUser(mUser);
        }
    };

    private final Func1<User, Observable<Response>> onUserExist = new Func1<User, Observable<Response>>() {
        @Override
        public Observable<Response> call(User response) {
            return userRepository.setCurrentUser(mUser);
        }
    };

    private final Func1<Response, Observable<Response>> onUserAdded = new Func1<Response, Observable<Response>>() {
        @Override
        public Observable<Response> call(Response response) {
            return userRepository.auth(mUser.getUserID(),mUser.getEmail());
        }
    };

}
