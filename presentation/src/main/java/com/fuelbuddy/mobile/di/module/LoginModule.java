package com.fuelbuddy.mobile.di.module;


import com.fuelbuddy.interactor.CheckUserUseCase;
import com.fuelbuddy.interactor.GetCurrentUserUseCase;
import com.fuelbuddy.interactor.SetUserInCloudUseCase;
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
    SetUserInCloudUseCase provideSetUserInCloud(SetUserInCloudUseCase setUserInCloudUseCase) {
        return setUserInCloudUseCase;
    }



    @Provides
    @Named("checkUser")
    UseCase provideCheckUser(CheckUserUseCase checkUserUseCase) {
        return checkUserUseCase;
    }
}