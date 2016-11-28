package com.fuelbuddy.mobile.home;


import android.util.Log;

import com.fuelbuddy.data.User;
import com.fuelbuddy.interactor.DefaultSubscriber;
import com.fuelbuddy.interactor.GetCurrentUser;
import com.fuelbuddy.interactor.UseCase;
import com.fuelbuddy.mobile.base.BasePresenter;
import com.fuelbuddy.mobile.map.MapPresenter;
import com.fuelbuddy.mobile.util.StringHelper;

import javax.inject.Inject;
import javax.inject.Named;

import hugo.weaving.DebugLog;
import rx.Observable;

import static com.facebook.login.widget.ProfilePictureView.TAG;

/**
 * Created by zjuroszek on 07.10.16.
 */
public class HomePresenter extends BasePresenter<HomeView> {

    private UseCase getCurrentUser;


 @Inject
    public HomePresenter(@Named("currentUser")UseCase getCurrentUser) {
        this.getCurrentUser = getCurrentUser;
    }


    @DebugLog
    public void verifyCurrentUser() {
        this.getCurrentUser.execute(new HomePresenter.CurrentUserSubscriber());
    }



    private final class CurrentUserSubscriber extends DefaultSubscriber<User> {
        @DebugLog
        @Override public void onCompleted() {
            Log.d(TAG, "onCompleted: ");
            //UserListPresenter.this.hideViewLoading();
        }
        @DebugLog
        @Override public void onError(Throwable e) {
            Log.d(TAG, "onError: ");
            //UserListPresenter.this.hideViewLoading();
            //UserListPresenter.this.showErrorMessage(new DefaultErrorBundle((Exception) e));
            //UserListPresenter.this.showViewRetry();
        }

        @DebugLog
        @Override public void onNext(User user) {
            Log.d(TAG, "onNext: ");
                if(!StringHelper.isNullOrEmpty(user.getUserId())) {
                    getMvpView().showLoginView();
                }
                else{
                    getMvpView().showLoginView();

            }
        }
    }
}


