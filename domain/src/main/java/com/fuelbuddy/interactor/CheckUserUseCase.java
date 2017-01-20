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
        return userRepository.getCheckUser(mUser.getUserID());
    }

/*
    private final Func1<Response, Observable<Response>> StoreRemoteUser =
            new Func1<Response, Observable<Response>>() {
                @Override
                public Observable<Response> call(Response response) {
                    if (response.getResultType() == Response.ResultTypeEnum.UserNotFound) {

                        return userRepository.addNewUser(mUser).concatMap(storeUserLocally);
                    } else {

                        return userRepository.setCurrentUser(mUser);
                    }
                }
            };*/


    private final Func1<Response, Observable<Response>> storeUserLocally =
            new Func1<Response, Observable<Response>>() {
                @Override
                public Observable<Response> call(Response response) {
                    if (response != null && response.getCode() == 200) {
                        return userRepository.setCurrentUser(mUser);
                    } else {
                        return getResponse(getErrorResponse());
                    }
                }
            };

    private Response getErrorResponse() {
        Response response1 = new Response();
        response1.setResultType(Response.ResultTypeEnum.GeneralError);
        response1.setCode(500);
        response1.setMessage("User not found");
        return response1;
    }

    protected Observable getLocalUserObservable() {
        return userRepository.getCurrentUser();
    }

    protected Observable getRemoteUserObservable(String userId) {
        return userRepository.getCheckUser(userId);
    }

    public Observable<Response> getResponse(Response response) {
        return Observable.just(response);
    }

}
