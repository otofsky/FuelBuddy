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


public class SetUserInCloudUseCase extends UseCase<Response, SetUserInCloudUseCase.Params> {

    UserRepository userRepository;

    @Inject
    public SetUserInCloudUseCase(UserRepository userRepository, ThreadExecutor threadExecutor, PostExecutionThread postExecutionThread) {
        super(threadExecutor, postExecutionThread);
        this.userRepository = userRepository;
    }

    @Override
    protected Observable <Response> buildUseCaseObservable( final Params params) {
        return userRepository.addNewUser(params.mUser)
                .flatMap(new Function<Response, ObservableSource<Response>>() {
                    @Override
                    public ObservableSource<Response> apply(Response response) throws Exception {
                        return userRepository.auth(params.mUser.getUserID(), params.mUser.getEmail());
                    }
                });
    }



/*
    @Override
    protected Observable <Response> buildUseCaseObservable(Params params) {
        return userRepository.addNewUser(params.mUser)
        .flatMap(new Function<User, ObservableSource<Response>>() {
            @Override
            public ObservableSource<Response> apply(User user) throws Exception {
                return userRepository.auth(user.getUserID(), user.getEmail());
            }
        });
    }
*/




    public static final class Params {

        private User mUser;

        private Params(User mUser) {
            this.mUser = mUser;
        }

        public static Params forUser(User user) {
            return new Params(user);
        }
    }

}
