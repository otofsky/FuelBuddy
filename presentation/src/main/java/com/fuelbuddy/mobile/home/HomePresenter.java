package com.fuelbuddy.mobile.home;


import com.fuelbuddy.data.model.User;
import com.fuelbuddy.interactor.DefaultSubscriber;
import com.fuelbuddy.interactor.GetCurrentUser;
import com.fuelbuddy.mobile.base.BasePresenter;

import hugo.weaving.DebugLog;

/**
 * Created by zjuroszek on 07.10.16.
 */
public class HomePresenter extends BasePresenter<HomeView> {


    private GetCurrentUser getCurrentUser;


    public HomePresenter(GetCurrentUser getCurrentUser) {
        this.getCurrentUser = getCurrentUser;
    }


    @DebugLog
    public void verifyCurrentUser() {
        this.getCurrentUser.getCurrentUser().subscribe(new CurrentUserSubscriber());
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
            if(user!=null){
                getMvpView().showLoginView();
            }
            else{
                getMvpView().showLoginView();
            }
        }
    }
}


