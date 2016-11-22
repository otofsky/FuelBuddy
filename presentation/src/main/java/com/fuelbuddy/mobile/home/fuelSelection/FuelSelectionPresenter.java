package com.fuelbuddy.mobile.home.fuelSelection;

import android.util.Log;

import com.fuelbuddy.data.User;
import com.fuelbuddy.interactor.DefaultSubscriber;
import com.fuelbuddy.interactor.UseCase;
import com.fuelbuddy.mobile.base.BasePresenter;
import com.fuelbuddy.mobile.home.login.LoginView;

import javax.inject.Inject;
import javax.inject.Named;

import hugo.weaving.DebugLog;

/**
 * Created by zjuroszek on 15.11.16.
 */
public class FuelSelectionPresenter extends BasePresenter<FuelSelectionView> {

    private UseCase logOutInteractor;

    @Inject
    public FuelSelectionPresenter(@Named("logOut")UseCase logOutInteractor) {
        this.logOutInteractor = logOutInteractor;
    }

    public void logout() {
     this.logOutInteractor.execute(new LogOutSubscriber());
    }


    private final class LogOutSubscriber extends DefaultSubscriber<Boolean> {

        @DebugLog
        @Override
        public void onCompleted() {
            //UserListPresenter.this.hideViewLoading();
        }

        @DebugLog
        @Override
        public void onError(Throwable e) {
            //UserListPresenter.this.hideViewLoading();
            //UserListPresenter.this.showErrorMessage(new DefaultErrorBundle((Exception) e));
            //UserListPresenter.this.showViewRetry();
        }

        @DebugLog
        @Override
        public void onNext(Boolean isLogout) {
            Log.d("Logout", "onNext: " + isLogout);

        }
    }
}
