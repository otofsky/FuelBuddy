package com.fuelbuddy.mobile.home.login;

import android.util.Log;

import com.fuelbuddy.data.User;
import com.fuelbuddy.interactor.AddNewUserInteractor;
import com.fuelbuddy.interactor.CheckUserInteractor;
import com.fuelbuddy.interactor.DefaultSubscriber;
import com.fuelbuddy.mobile.base.BasePresenter;
import com.fuelbuddy.mobile.mapper.UserModelDataMapper;
import com.fuelbuddy.mobile.model.UserModel;

import javax.inject.Inject;
import javax.inject.Named;

import hugo.weaving.DebugLog;

/**
 * Created by zjuroszek on 15.11.16.
 */


public class LoginPresenter extends BasePresenter<LoginView> {

    UserModelDataMapper userModelDataMapper;
    private AddNewUserInteractor addNewUser;
    private CheckUserInteractor mCheckUserInteractor;


    @Inject
    public LoginPresenter(@Named("addNewUser") AddNewUserInteractor addNewUser, CheckUserInteractor checkUserInteractor, UserModelDataMapper userModelDataMapper) {
        this.addNewUser = addNewUser;
        this.mCheckUserInteractor = checkUserInteractor;
        this.userModelDataMapper = userModelDataMapper;
    }

    public void addNewUser(UserModel user) {
        addNewUser.addNewUser(userModelDataMapper.transformToUser(user));
        this.addNewUser.execute(new LoginPresenter.AddUserSubscriber());
    }

    public void checkUser(UserModel userModel) {
        mCheckUserInteractor.addUser(userModelDataMapper.transformToUser(userModel));
        this.mCheckUserInteractor.execute(new CheckUserSubscriber());
    }


    private final class AddUserSubscriber extends DefaultSubscriber<User> {

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
        public void onNext(User user) {

        }
    }


    private final class CheckUserSubscriber extends DefaultSubscriber<User> {

        @DebugLog
        @Override
        public void onCompleted() {
            Log.d("CheckUserSubscriber", "onCompleted: ");
            //UserListPresenter.this.hideViewLoading();
        }

        @DebugLog
        @Override
        public void onError(Throwable e) {
            Log.d("CheckUserSubscriber", "onError: ");
            //UserListPresenter.this.hideViewLoading();
            //UserListPresenter.this.showErrorMessage(new DefaultErrorBundle((Exception) e));
            //UserListPresenter.this.showViewRetry();
        }

        @DebugLog
        @Override
        public void onNext(User user) {
            Log.d("CheckUserSubscriber", "onNext: ");
        }
    }

}
