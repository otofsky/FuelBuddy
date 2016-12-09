package com.fuelbuddy.mobile.home;


import android.util.Log;

import com.fuelbuddy.data.User;
import com.fuelbuddy.data.exeption.UserNotFoundException;
import com.fuelbuddy.interactor.DefaultSubscriber;
import com.fuelbuddy.interactor.GetCurrentUser;
import com.fuelbuddy.interactor.UseCase;
import com.fuelbuddy.mobile.base.BasePresenter;
import com.fuelbuddy.mobile.map.MapPresenter;
import com.fuelbuddy.mobile.util.StringHelper;

import javax.inject.Inject;
import javax.inject.Named;

import hugo.weaving.DebugLog;
import retrofit2.adapter.rxjava.HttpException;
import rx.Observable;

import static com.facebook.login.widget.ProfilePictureView.TAG;

/**
 * Created by zjuroszek on 07.10.16.
 */
public class HomePresenter extends BasePresenter<HomeView> {

    private UseCase getCurrentUser;


    @Inject
    public HomePresenter(@Named("currentUser") UseCase getCurrentUser) {
        this.getCurrentUser = getCurrentUser;
    }

    @DebugLog
    public void verifyCurrentUser() {
        this.getCurrentUser.execute(new HomePresenter.CurrentUserSubscriber());
    }


    private final class CurrentUserSubscriber extends DefaultSubscriber<User> {
        @DebugLog
        @Override
        public void onCompleted() {
        }

        @DebugLog
        @Override
        public void onError(Throwable throwable) {
            getMvpView().showLoginView();
        }

        @DebugLog
        @Override
        public void onNext(User user) {
            getMvpView().showFuelTypeView();
        }
    }
}


