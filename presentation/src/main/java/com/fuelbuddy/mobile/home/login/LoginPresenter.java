package com.fuelbuddy.mobile.home.login;

import com.fuelbuddy.data.User;
import com.fuelbuddy.interactor.AddNewUserInteractor;
import com.fuelbuddy.interactor.DefaultSubscriber;
import com.fuelbuddy.interactor.UseCase;
import com.fuelbuddy.mobile.base.BasePresenter;
import com.fuelbuddy.mobile.home.HomePresenter;
import com.fuelbuddy.mobile.home.HomeView;
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


    @Inject
    public LoginPresenter(@Named("addNewUser") AddNewUserInteractor addNewUser, UserModelDataMapper userModelDataMapper) {
        this.addNewUser = addNewUser;
        this.userModelDataMapper = userModelDataMapper;
    }

    public void addNewUser(UserModel user) {
        addNewUser.addNewUser(userModelDataMapper.transformToUser(user));
        this.addNewUser.execute(new LoginPresenter.AddUserSubscriber());
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

}
