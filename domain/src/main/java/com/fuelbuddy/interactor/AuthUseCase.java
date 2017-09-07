package com.fuelbuddy.interactor;


import com.fuelbuddy.data.Response;
import com.fuelbuddy.executor.PostExecutionThread;
import com.fuelbuddy.executor.ThreadExecutor;
import com.fuelbuddy.repository.UserRepository;

import javax.inject.Inject;

import io.reactivex.Observable;


/**
 * Created by zjuroszek on 20.11.16.
 */


public class AuthUseCase extends UseCase<Response, AuthUseCase.Params> {

    UserRepository userRepository;
    private String userId;
    private String email;


    @Inject
    public AuthUseCase(UserRepository userRepository, ThreadExecutor threadExecutor, PostExecutionThread postExecutionThread) {
        super(threadExecutor, postExecutionThread);
        this.userRepository = userRepository;
    }

    @Override
    Observable<Response> buildUseCaseObservable(Params params) {
        return userRepository.auth(userId, email);
    }


    public static final class Params {

        private String userId;
        private String email;

        private Params(String userId, String email) {
            this.userId = userId;
            this.email = email;
        }
        public static Params forProfile(String userI,String email) {
            return new Params(userI,email);
        }
    }

}
