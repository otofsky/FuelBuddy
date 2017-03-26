package com.fuelbuddy.mobile.login;

import com.fuelbuddy.data.Response;
import com.fuelbuddy.data.exeption.UserNotFoundException;
import com.fuelbuddy.exception.DefaultErrorBundle;
import com.fuelbuddy.exception.ErrorBundle;
import com.fuelbuddy.interactor.AuthUseCase;
import com.fuelbuddy.interactor.CheckUserUseCase;
import com.fuelbuddy.interactor.DefaultObserver;
import com.fuelbuddy.interactor.SetUserInCloudUseCase;
import com.fuelbuddy.interactor.SetUserLocallyUseCase;
import com.fuelbuddy.mobile.base.BasePresenter;
import com.fuelbuddy.mobile.exeption.ErrorMessageFactory;
import com.fuelbuddy.mobile.mapper.UserModelDataMapper;
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
    private SetUserInCloudUseCase mSetUserInCloudUseCase;
    private CheckUserUseCase mCheckUserUseCase;
    private AuthUseCase authUseCase;
    private UserModel mUserModel;

    @Inject
    public LoginPresenter(@Named("setUserLocally") SetUserLocallyUseCase setUserLocallyUseCase,
                          @Named("setUserInCloud") SetUserInCloudUseCase mSetUserInCloudUseCase,
                          CheckUserUseCase checkUserUseCase,
                          AuthUseCase authUseCase,
                          UserModelDataMapper userModelDataMapper) {
        this.mSetUserLocallyUseCase = setUserLocallyUseCase;
        this.mSetUserInCloudUseCase = mSetUserInCloudUseCase;
        this.mCheckUserUseCase = checkUserUseCase;
        this.authUseCase = authUseCase;
        this.userModelDataMapper = userModelDataMapper;
    }

    public void checkUser(UserModel userModel) {
        mUserModel = userModel;
        this.mCheckUserUseCase.execute(new CheckUserSubscriber(), CheckUserUseCase.Params.forProfile(userModelDataMapper.transformToUser(userModel)));
    }

    public void addNewUseInCloud(UserModel user) {
        this.mSetUserInCloudUseCase.execute(new AddUserInCloudSubscriber(), SetUserInCloudUseCase.Params.forUser(userModelDataMapper.transformToUser(user)));
    }


    private void showErrorMessage(ErrorBundle errorBundle) {
        String errorMessage = ErrorMessageFactory.create(this.getMvpView().context(), errorBundle.getException());
        getMvpView().showError(errorMessage);
    }

    private boolean isUserException(Exception exception) {
        if (exception instanceof UserNotFoundException) {
            return true;
        } else {
            return false;
        }
    }


    private final class CheckUserSubscriber extends DefaultObserver<Response> {
        @DebugLog
        @Override
        public void onComplete() {
        }

        @DebugLog
        @Override
        public void onError(Throwable e) {
            if (isUserException((Exception) e)) {
                addNewUseInCloud(mUserModel);
            } else {
                showErrorMessage(new DefaultErrorBundle((Exception) e));
            }
        }

        @DebugLog
        @Override
        public void onNext(Response response) {
            getMvpView().showFuelSectionView();
        }
    }

    private final class AddUserInCloudSubscriber extends DefaultObserver<Response> {

        @DebugLog
        @Override
        public void onComplete() {
        }

        @DebugLog
        @Override
        public void onError(Throwable e) {
            showErrorMessage(new DefaultErrorBundle((Exception) e));
        }

        @DebugLog
        @Override
        public void onNext(Response user) {
            getMvpView().showFuelSectionView();

        }
    }
}
