package com.fuelbuddy.mobile.home;


import android.util.Log;

import com.fuelbuddy.data.GasStation;
import com.fuelbuddy.data.model.User;
import com.fuelbuddy.interactor.DefaultSubscriber;
import com.fuelbuddy.interactor.GetCurrentUser;
import com.fuelbuddy.mobile.base.BasePresenter;
import com.fuelbuddy.mobile.map.MapPresenter;

import java.util.List;

import javax.inject.Inject;

import hugo.weaving.DebugLog;

/**
 * Created by zjuroszek on 07.10.16.
 */
public class HomePresenter extends BasePresenter<HomeMvpView> {


    private GetCurrentUser getCurrentUser;


    public HomePresenter(GetCurrentUser getCurrentUser) {
        this.getCurrentUser = getCurrentUser;
    }


    @DebugLog
    public void getGetCurrentUser() {


       // this.getCurrentUser.getCurrentUser().subscribe(new CurrentUserSubscriber());
    }

    private final class CurrentUserSubscriber extends DefaultSubscriber<User> {
        @DebugLog
        @Override public void onCompleted() {
            //UserListPresenter.this.hideViewLoading();
        }
        @DebugLog
        @Override public void onError(Throwable e) {
            //UserListPresenter.this.hideViewLoading();
            //UserListPresenter.this.showErrorMessage(new DefaultErrorBundle((Exception) e));
            //UserListPresenter.this.showViewRetry();
        }

        @DebugLog
        @Override public void onNext(User user) {
            getMvpView().showInfo(user.getUserId());
            Log.d("UserListSubscriber", "onNext: " + user.getUserId());
        }
    }
}


