package com.fuelbuddy.mobile.home.login;

import com.fuelbuddy.data.User;
import com.fuelbuddy.data.net.RetrofitException;
import com.fuelbuddy.interactor.CheckUserUseCase;
import com.fuelbuddy.interactor.DefaultSubscriber;
import com.fuelbuddy.interactor.SetUserInCloudUseCae;
import com.fuelbuddy.interactor.SetUserLocallyUseCase;
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
    private SetUserLocallyUseCase mSetUserLocallyUseCase;
    private SetUserInCloudUseCae mSetUserInCloudUseCae;
    private CheckUserUseCase mCheckUserUseCase;
    private UserModel mUserModel; //

    @Inject
    public LoginPresenter(@Named("setUserLocally") SetUserLocallyUseCase setUserLocallyUseCase,
                          @Named("setUserInCloud") SetUserInCloudUseCae mSetUserInCloudUseCae,
                          CheckUserUseCase checkUserUseCase,
                          UserModelDataMapper userModelDataMapper) {
        this.mSetUserLocallyUseCase = setUserLocallyUseCase;
        this.mSetUserInCloudUseCae = mSetUserInCloudUseCae;
        this.mCheckUserUseCase = checkUserUseCase;
        this.userModelDataMapper = userModelDataMapper;
    }

    public void checkUser(UserModel userModel) {
        mUserModel = userModel;
        mCheckUserUseCase.setUser(userModelDataMapper.transformToUser(userModel));
        this.mCheckUserUseCase.execute(new CheckUserSubscriber());
    }


    public void addNewUseInCloud(UserModel user) {
        mSetUserInCloudUseCae.setUser(userModelDataMapper.transformToUser(user));
        this.mSetUserInCloudUseCae.execute(new AddUserInCloudSubscriber());
    }

    public void addNewUserLocally(UserModel user) {
        mSetUserLocallyUseCase.setUser(userModelDataMapper.transformToUser(user));
        this.mSetUserLocallyUseCase.execute(new AddUserLocallySubscriber());
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
