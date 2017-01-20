package com.fuelbuddy.mobile.di.module;


import com.fuelbuddy.interactor.CheckUserUseCase;
import com.fuelbuddy.interactor.GetCurrentUserUseCase;
import com.fuelbuddy.interactor.LogOutUseCase;
import com.fuelbuddy.interactor.SetUserInCloudUseCae;
import com.fuelbuddy.interactor.SetUserLocallyUseCase;
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
    UseCase provideGetUserUseCase(GetCurrentUserUseCase getCurrentUserUseCase) {
        return getCurrentUserUseCase;
    }

    @Provides
    @Named("setUserLocally")
    SetUserLocallyUseCase provideSetUserLocally(SetUserLocallyUseCase addNewUserInteractor) {
        return addNewUserInteractor;
    }

    @Provides
    @Named("setUserInCloud")
    SetUserInCloudUseCae provideSetUserInCloud(SetUserInCloudUseCae setUserInCloudUseCae) {
        return setUserInCloudUseCae;
    }

    @Provides
    @Named("logOut")
    UseCase provideLogOutUseCase(LogOutUseCase logOutUseCase) {
        return logOutUseCase;
    }

    @Provides
    @Named("checkUser")
    UseCase provideCheckUser(CheckUserUseCase checkUserUseCase) {
        return checkUserUseCase;
    }
}