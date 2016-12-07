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

    UserModelDataMapper userModelDataMapper;
    private SetUserLocallyInteractor mSetUserLocallyInteractor;
    private SetUserInCloudInteractor mSetUserInCloudInteractor;
    private CheckUserInteractor mCheckUserInteractor;
    private UserModel mUserModel;

    private static String TAG = "LoginPresenter";


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
            Log.d("CheckUserSubscriber", "onCompleted: ");
            //UserListPresenter.this.hideViewLoading();
        }

        @DebugLog
        @Override
        public void onError(Throwable throwable) {
            addNewUseInCloud(mUserModel);
            if (throwable instanceof HttpException) {
                Log.d("CheckUserSubscriber", "HttpException: ");
                // We had non-2XX http error
            }
            if (throwable instanceof IOException) {
                Log.d("CheckUserSubscriber", "IOException: ");
                // A network or conversion error happened
            } else {
                Log.d("CheckUserSubscriber", "");
            }
        }

        @DebugLog
        @Override
        public void onNext(Response response) {
            Log.d("CheckUserSubscriber", "onNext: " + response.toString());
        }
    }

    private final class AddUserInCloudSubscriber extends DefaultSubscriber<User> {

        @DebugLog
        @Override
        public void onCompleted() {
            addNewUserLocally(mUserModel);
            Log.d(TAG, "AddUserLocallySubscriber onCompleted: ");
            //UserListPresenter.this.hideViewLoading();
        }

        @DebugLog
        @Override
        public void onError(Throwable throwable) {
            Log.d(TAG, "AddUserLocallySubscriber onError: ");
            if (throwable instanceof HttpException) {
                // We had non-2XX http error
            }
            if (throwable instanceof IOException) {
                // A network or conversion error happened
            }

            //UserListPresenter.this.hideViewLoading();
            //UserListPresenter.this.showErrorMessage(new DefaultErrorBundle((Exception) e));
            //UserListPresenter.this.showViewRetry();
        }

        @DebugLog
        @Override
        public void onNext(User user) {
            Log.d(TAG, "AddUserLocallySubscriber onNext: ");
        }
    }

    private final class AddUserLocallySubscriber extends DefaultSubscriber<User> {

        @DebugLog
        @Override
        public void onCompleted() {
            Log.d(TAG, "AddUserLocallySubscriber onCompleted: ");

        }

        @DebugLog
        @Override
        public void onError(Throwable throwable) {
            Log.d(TAG, "AddUserLocallySubscriber onError: ");
            if (throwable instanceof HttpException) {
                // We had non-2XX http error
            }
            if (throwable instanceof IOException) {
                // A network or conversion error happened
            }

            //UserListPresenter.this.hideViewLoading();
            //UserListPresenter.this.showErrorMessage(new DefaultErrorBundle((Exception) e));
            //UserListPresenter.this.showViewRetry();
        }

        @DebugLog
        @Override
        public void onNext(User user) {
            Log.d(TAG, "AddUserLocallySubscriber onNext: ");
        }
    }


}
