package com.fuelbuddy.mobile.home.login;

import android.util.Log;

import com.fuelbuddy.data.Response;
import com.fuelbuddy.data.User;
import com.fuelbuddy.data.net.RetrofitException;
import com.fuelbuddy.exception.DefaultErrorBundle;
import com.fuelbuddy.exception.ErrorBundle;
import com.fuelbuddy.interactor.CheckUserInteractor;
import com.fuelbuddy.interactor.DefaultSubscriber;
import com.fuelbuddy.interactor.SetUserInCloudInteractor;
import com.fuelbuddy.interactor.SetUserLocallyInteractor;
import com.fuelbuddy.mobile.base.BasePresenter;
import com.fuelbuddy.mobile.exeption.ErrorMessageFactory;
import com.fuelbuddy.mobile.mapper.UserModelDataMapper;
import com.fuelbuddy.mobile.model.ErrorResponse;
import com.fuelbuddy.mobile.model.UserModel;

import javax.inject.Inject;
import javax.inject.Named;

import hugo.weaving.DebugLog;

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

    private void showErrorMessage(String errorMessage) {
        getMvpView().showError(errorMessage);
    }

    private final class CheckUserSubscriber extends DefaultSubscriber<User> {
        @DebugLog
        @Override
        public void onCompleted() {
        }

        @DebugLog
        @Override
        public void onError(Throwable throwable) {
            if (throwable instanceof RetrofitException) {
                ErrorResponse errorResponse = ErrorMessageFactory.create(getMvpView().context(), (RetrofitException) throwable);
                if (errorResponse.getErrorCode() != null && errorResponse.getErrorCode() == 404) {
                    addNewUseInCloud(mUserModel);
                }
                showErrorMessage(errorResponse.getErrorMassage());
            }
        }

        @DebugLog
        @Override
        public void onNext(User response) {
            addNewUserLocally(mUserModel);
        }
    }

    private final class AddUserInCloudSubscriber extends DefaultSubscriber<User> {

        @DebugLog
        @Override
        public void onCompleted() {
            getMvpView().showFuelSectionView();
        }

        @DebugLog
        @Override
        public void onError(Throwable throwable) {
            //showErrorMessage(new DefaultErrorBundle((Exception) throwable));
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
            getMvpView().showFuelSectionView();

        }

        @DebugLog
        @Override
        public void onError(Throwable throwable) {
            ErrorResponse errorResponse = ErrorMessageFactory.create(getMvpView().context(), (RetrofitException) throwable);
            showErrorMessage(errorResponse.getErrorMassage());
        }

        @DebugLog
        @Override
        public void onNext(User user) {
            getMvpView().showFuelSectionView();
        }
    }


}
