package com.fuelbuddy.mobile.home.login;

import android.util.Log;

import com.fuelbuddy.data.Response;
import com.fuelbuddy.data.User;
import com.fuelbuddy.interactor.SetUserInCloudInteractor;
import com.fuelbuddy.interactor.SetUserLocallyInteractor;
import com.fuelbuddy.interactor.CheckUserInteractor;
import com.fuelbuddy.interactor.DefaultSubscriber;
import com.fuelbuddy.mobile.base.BasePresenter;
import com.fuelbuddy.mobile.mapper.UserModelDataMapper;
import com.fuelbuddy.mobile.model.UserModel;

import java.io.IOException;

import javax.inject.Inject;
import javax.inject.Named;

import hugo.weaving.DebugLog;
import retrofit2.adapter.rxjava.HttpException;

/**
 * Created by zjuroszek on 15.11.16.
 */


public class LoginPresenter extends BasePresenter<LoginView> {

    private static String TAG = "LoginPresenter";

    UserModelDataMapper userModelDataMapper;
    private SetUserLocallyInteractor mSetUserLocallyInteractor;
    private SetUserInCloudInteractor mSetUserInCloudInteractor;
    private CheckUserInteractor mCheckUserInteractor;
    private UserModel mUserModel;

    @Inject
    public LoginPresenter(@Named("setUserLocally") SetUserLocallyInteractor setUserLocallyInteractor,
                          @Named("setUserInCloud") SetUserInCloudInteractor mSetUserInCloudInteractor,
                          CheckUserInteractor checkUserInteractor,
                          UserModelDataMapper userModelDataMapper) {
        this.mSetUserLocallyInteractor = setUserLocallyInteractor;
        this.mSetUserInCloudInteractor = mSetUserInCloudInteractor;
        this.mCheckUserInteractor = checkUserInteractor;
        this.userModelDataMapper = userModelDataMapper;
    }

    public void checkUser(UserModel userModel) {
        mUserModel = userModel;
        mCheckUserInteractor.setUser(userModelDataMapper.transformToUser(userModel));
        this.mCheckUserInteractor.execute(new CheckUserSubscriber());
    }


    public void addNewUseInCloud(UserModel user) {
        mSetUserInCloudInteractor.setUser(userModelDataMapper.transformToUser(user));
        this.mSetUserInCloudInteractor.execute(new AddUserInCloudSubscriber());
    }

    public void addNewUserLocally(UserModel user) {
        mSetUserLocallyInteractor.setUser(userModelDataMapper.transformToUser(user));
        this.mSetUserLocallyInteractor.execute(new AddUserLocallySubscriber());
    }

    private final class CheckUserSubscriber extends DefaultSubscriber<Response> {
        @DebugLog
        @Override
        public void onCompleted() {
        }

        @DebugLog
        @Override
        public void onError(Throwable throwable) {
            addNewUseInCloud(mUserModel);

        }

        @DebugLog
        @Override
        public void onNext(Response response) {
            Log.d(TAG, "CheckUserSubscriber onNext: " + response.toString());
            addNewUserLocally(mUserModel);
        }
    }

    private final class AddUserInCloudSubscriber extends DefaultSubscriber<User> {

        @DebugLog
        @Override
        public void onCompleted() {

        }
        @DebugLog
        @Override
        public void onError(Throwable throwable) {
        }

        @DebugLog
        @Override
        public void onNext(User user) {
            addNewUserLocally(mUserModel);
        }
    }

    private final class AddUserLocallySubscriber extends DefaultSubscriber<User> {

        @DebugLog
        @Override
        public void onCompleted() {

        }

        @DebugLog
        @Override
        public void onError(Throwable throwable) {
        }

        @DebugLog
        @Override
        public void onNext(User user) {
        }
    }


}
