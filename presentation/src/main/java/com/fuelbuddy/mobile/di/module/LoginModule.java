package com.fuelbuddy.mobile.di.module;


import com.fuelbuddy.interactor.SetUserInCloudInteractor;
import com.fuelbuddy.interactor.SetUserLocallyInteractor;
import com.fuelbuddy.interactor.CheckUserInteractor;
import com.fuelbuddy.interactor.GetCurrentUser;
import com.fuelbuddy.interactor.LogOutInteractor;
import com.fuelbuddy.interactor.UseCase;
import com.fuelbuddy.mobile.di.PerActivity;

import javax.inject.Named;

import dagger.Module;
import dagger.Provides;


@Module
public class LoginModule {

    public LoginModule() {}

    @Provides @PerActivity
    @Named("currentUser")
    UseCase provideGetUserUseCase(GetCurrentUser getCurrentUser) {
        return getCurrentUser;
    }

    @Provides
    @Named("setUserLocally")
    SetUserLocallyInteractor provideSetUserLocally(SetUserLocallyInteractor addNewUserInteractor) {
        return addNewUserInteractor;
    }

    @Provides
    @Named("setUserInCloud")
    SetUserInCloudInteractor provideSetUserInCloud(SetUserInCloudInteractor setUserInCloudInteractor) {
        return setUserInCloudInteractor;
    }

    @Provides
    @Named("logOut")
    UseCase provideLogOutUseCase(LogOutInteractor logOutInteractor) {
        return logOutInteractor;
    }

    @Provides
    @Named("checkUser")
    UseCase provideCheckUser(CheckUserInteractor checkUserInteractor) {
        return checkUserInteractor;
    }
}