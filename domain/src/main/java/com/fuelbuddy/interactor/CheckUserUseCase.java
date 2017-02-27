package com.fuelbuddy.interactor;

import com.fuelbuddy.data.Response;

import com.fuelbuddy.data.User;

import com.fuelbuddy.executor.PostExecutionThread;
import com.fuelbuddy.executor.ThreadExecutor;
import com.fuelbuddy.repository.UserRepository;

import javax.inject.Inject;


import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.functions.Function;


/**
 * Created by zjuroszek on 20.11.16.
 */


public class CheckUserUseCase extends UseCase <Response, CheckUserUseCase.Params>  {

    UserRepository userRepository;

    @Inject
    public CheckUserUseCase(UserRepository userRepository, ThreadExecutor threadExecutor, PostExecutionThread postExecutionThread) {
        super(threadExecutor, postExecutionThread);
        this.userRepository = userRepository;
    }

    @Override
    protected Observable<Response> buildUseCaseObservable(final Params params) {
        // jesli istnieje
        //to zapisje dane lokalnie
        // jesli nie istnieje wysyłam dane na server i zapisuje lokalnie
        //return userRepository.checkUser(mUser.getUserID()).concatMap(StoreRemoteUser);
        return userRepository.checkUser(params.user.getUserID())
                .onErrorResumeNext(error(params.user));

        // to zapisje dane lokalnie
        //wysyłam dane na server i zapisuje lokalnie
    }

    public static final class Params {

       private User user;

        private Params(User user) {
           this.user = user;
        }
        public static Params forProfile(User user) {
            return new Params(user);
        }
    }


    private Function error(final User user){
         final Function<Throwable, Observable<Response>> errorHandling = new Function<Throwable, Observable<Response>>() {
            @Override
            public Observable<Response> apply(Throwable e) {
                return userRepository.addNewUser(user);
            }
        };
        return errorHandling;
    }

    public static void create( Exception exception) {





    }

/*
    private final Function<Throwable, Observable<Response>> errorHandling = new Function<Throwable, Observable<Response>>() {

        @Override
        public Observable<Response> apply(Throwable throwable) {
            return userRepository.addNewUser(mUser);
        }
    };

    private final Function<User, Observable<Response>> onUserExist = new Function<User, Observable<Response>>() {
        @Override
        public Observable<Response> apply(User response) {
            return userRepository.setCurrentUser(mUser);
        }
    };

    private final Function<Response, Observable<Response>> onUserAdded = new Function<Response, Observable<Response>>() {
        @Override
        public Observable<Response> apply(Response response) {
            return userRepository.auth(mUser.getUserID(),mUser.getEmail());
        }
    };*/

}
