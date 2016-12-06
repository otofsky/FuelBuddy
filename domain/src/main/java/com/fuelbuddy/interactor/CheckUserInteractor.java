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


public class CheckUserInteractor extends UseCase  {

    UserRepository userRepository;
    private User mUser;

    @Inject
    public CheckUserInteractor(UserRepository userRepository, ThreadExecutor threadExecutor, PostExecutionThread postExecutionThread) {
        super(threadExecutor, postExecutionThread);
        this.userRepository = userRepository;
    }

    public void addUser(User user){
        this.mUser = user;
    }

    @Override
    protected Observable buildUseCaseObservable() {
        // jesli istnieje
        //to zapisje dane lokalnie
        // jesli nie istnieje wysyłam dane na server i zapisuje lokalnie
        return userRepository.getCheckUser(mUser.getUserId()).concatMap(StoreRemoteUser);
    }


    private final Func1<Response, Observable<User>> StoreRemoteUser =
            new Func1<User, Observable<Response>>() {
                @Override
                public Observable<Response> call(User user) {
                    if (user == null) {
                        return userRepository.addNewUser(mUser).concatMap(storeUserLocally);
                    } else {
                        return getUser(user);
                    }
                }
            };


    private final Func1<User, Observable<User>> storeUserLocally =
            new Func1<User, Observable<User>>() {
                @Override
                public Observable<User> call(User user) {
                    if (user == null) {
                        return getRemoteUserObservable("43").concatMap(StoreRemoteUser);
                    } else {
                        return getUser(user);
                    }
                }
            };


    private final Func1<User, Observable<User>> storeUserLocally =
            new Func1<User, Observable<User>>() {
                @Override
                public Observable<User> call(User user) {
                    if (user == null) {
                        return getRemoteUserObservable("43").concatMap(StoreRemoteUser);
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
