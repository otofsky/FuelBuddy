package com.fuelbuddy.mobile.home;

import android.util.Log;
import com.fuelbuddy.data.User;
import com.fuelbuddy.interactor.DefaultObserver;
import com.fuelbuddy.interactor.LogOutUseCase;
import com.fuelbuddy.interactor.UseCase;
import com.fuelbuddy.mobile.base.BasePresenter;


import javax.inject.Inject;
import javax.inject.Named;

import hugo.weaving.DebugLog;


/**
 * Created by zjuroszek on 07.10.16.
 */
public class HomePresenter extends BasePresenter<HomeView> {

    private UseCase getCurrentUser;
    private final LogOutUseCase logOutUseCase;

    @Inject
    public HomePresenter(@Named("currentUser") UseCase getCurrentUser,LogOutUseCase logOutUseCase) {
        this.getCurrentUser = getCurrentUser;
        this.logOutUseCase = logOutUseCase;
    }

    @DebugLog
    public void verifyCurrentUser() {
        getMvpView().showFuelTypeView();
        //this.getCurrentUser.execute(new HomePresenter.CurrentUserSubscriber(),null);
    }

    public void logout() {
        getMvpView().showLoading();
        this.logOutUseCase.execute(new LogOutSubscriber(),null);
    }


    private final class CurrentUserSubscriber extends DefaultObserver<User> {
        @DebugLog
        @Override
        public void onComplete() {

        }

        @DebugLog
        @Override
        public void onError(Throwable throwable) {
            //getMvpView().showLoginView();
            getMvpView().showFuelTypeView();
        }

        @DebugLog
        @Override
        public void onNext(User user) {
            getMvpView().showFuelTypeView();
        }
    }

    private final class LogOutSubscriber extends DefaultObserver<Boolean> {

        @DebugLog
        @Override
        public void onComplete() {
        }

        @DebugLog
        @Override
        public void onError(Throwable e) {

        }

        @DebugLog
        @Override
        public void onNext(Boolean isLogout) {
            Log.d("Logout", "onNext: " + isLogout);
            getMvpView().hideLoading();
            getMvpView().logOut();
        }
    }
}


