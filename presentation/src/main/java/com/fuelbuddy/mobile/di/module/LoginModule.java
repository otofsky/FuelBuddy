package com.fuelbuddy.mobile.di.module;


import com.fuelbuddy.interactor.AddNewUserInteractor;
import com.fuelbuddy.interactor.GetCurrentUser;
import com.fuelbuddy.interactor.GetGasStationList;
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
    @Named("addNewUser")
    AddNewUserInteractor provideAddUserUseCase(AddNewUserInteractor addNewUserInteractor) {
        return addNewUserInteractor;
    }

}