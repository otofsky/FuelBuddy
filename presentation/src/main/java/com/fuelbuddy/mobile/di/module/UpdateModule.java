package com.fuelbuddy.mobile.di.module;


import com.fuelbuddy.interactor.CheckUserInteractor;
import com.fuelbuddy.interactor.GetCurrentUser;
import com.fuelbuddy.interactor.LogOutInteractor;
import com.fuelbuddy.interactor.SetUserInCloudInteractor;
import com.fuelbuddy.interactor.SetUserLocallyInteractor;
import com.fuelbuddy.interactor.UpdateFuelPricesInteractor;
import com.fuelbuddy.interactor.UseCase;
import com.fuelbuddy.mobile.di.PerActivity;

import javax.inject.Named;

import dagger.Module;
import dagger.Provides;


@Module
public class UpdateModule {

    public UpdateModule() {}

    @Provides
    @PerActivity
    @Named("updateFuelPricesInteractor")
    UpdateFuelPricesInteractor provideUpdateFuelPricesInteractor(UpdateFuelPricesInteractor updateFuelPricesInteractor) {
        return updateFuelPricesInteractor;
    }

}