package com.fuelbuddy.interactor;

import com.fuelbuddy.data.Response;
import com.fuelbuddy.data.User;
import com.fuelbuddy.exception.DefaultErrorBundle;
import com.fuelbuddy.executor.PostExecutionThread;
import com.fuelbuddy.executor.ThreadExecutor;
import com.fuelbuddy.repository.UserRepository;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;


/**
 * Created by zjuroszek on 20.11.16.
 */


public class CheckUserUseCase extends UseCase<Response, CheckUserUseCase.Params> {

    UserRepository userRepository;

    @Inject
    public CheckUserUseCase(UserRepository userRepository, ThreadExecutor threadExecutor, PostExecutionThread postExecutionThread) {
        super(threadExecutor, postExecutionThread);
        this.userRepository = userRepository;
    }

    // jesli istnieje
    //to zapisje dane lokalnie
    // jesli nie istnieje wysyłam dane na server i
    // zapisuje lokalnie -
    //
    // wywołanie metody auth

    // jesli instnieje

    // zapisujemy loalnie

    // i wywołaie metody auth
    //return userRepository.checkUser(mUser.getUserID()).concatMap(StoreRemoteUser);
    // to zapisje dane lokalnie
    //wysyłam dane na server i zapisuje lokalnie

    @Override
    protected Observable<Response> buildUseCaseObservable(final Params params) {
        return userRepository.checkUser(params.user.getUserID())
                .flatMap(new Function<User, ObservableSource<Response>>() {
                    @Override
                    public ObservableSource<Response> apply(User user) throws Exception {
                        return userRepository.auth(user.getUserID(), user.getEmail());
                    }
                })
                .onErrorResumeNext(error(params.user));

    }

    private Function error(final User user) {
        final Function<Throwable, Observable<Response>> errorHandling = new Function<Throwable, Observable<Response>>() {
            @Override
            public Observable<Response> apply(Throwable e) {
                return userRepository.addNewUser(user)
                        .doOnNext(new Consumer<Response>() {
                            @Override
                            public void accept(Response response) throws Exception {
                                 userRepository.auth(user.getUserID(), user.getEmail());
                            }
                        });
            }
        };
        return errorHandling;
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
}
