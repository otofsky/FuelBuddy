package com.fuelbuddy.mobile.home.fuelSelection;

import android.util.Log;

import com.fuelbuddy.interactor.LogOutUseCase;
import com.fuelbuddy.interactor.UseCase;
import com.fuelbuddy.mobile.base.BasePresenter;

import javax.inject.Inject;
import javax.inject.Named;

import hugo.weaving.DebugLog;
import com.fuelbuddy.interactor.DefaultObserver;

/**
 * Created by zjuroszek on 15.11.16.
 */
public class FuelSelectionPresenter extends BasePresenter<FuelSelectionView> {


    private final LogOutUseCase logOutUseCase;

    @Inject
    public FuelSelectionPresenter(@Named("logOut") LogOutUseCase logOutUseCase) {
        this.logOutUseCase = logOutUseCase;
    }

    public void logout() {
        getMvpView().showLoading();
        this.logOutUseCase.execute(new LogOutSubscriber(),null);
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
