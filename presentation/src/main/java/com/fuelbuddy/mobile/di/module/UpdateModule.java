package com.fuelbuddy.mobile.di.module;


import com.fuelbuddy.interactor.UpdateFuelPricesUseCase;
import com.fuelbuddy.mobile.di.PerActivity;
import com.fuelbuddy.validator.InputValidator;
import com.fuelbuddy.validator.Validator;

import javax.inject.Named;
import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;


@Module
public class UpdateModule {

    public UpdateModule() {}

    @Provides
    @PerActivity
    @Named("updateFuelPricesUseCase")
    UpdateFuelPricesUseCase provideUpdateFuelPricesInteractor(UpdateFuelPricesUseCase updateFuelPricesUseCase) {
        return updateFuelPricesUseCase;
    }

}