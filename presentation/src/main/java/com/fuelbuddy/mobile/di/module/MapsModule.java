package com.fuelbuddy.mobile.di.module;


import com.fuelbuddy.interactor.GetGasStationsUseCase;
import com.fuelbuddy.interactor.UpdateFuelPricesUseCase;
import com.fuelbuddy.mobile.di.PerActivity;

import javax.inject.Named;

import dagger.Module;
import dagger.Provides;




@Module
public class MapsModule {

    public MapsModule() {
    }

    @Provides
    @PerActivity
    @Named("gasStationList")
    GetGasStationsUseCase provideGetGasStationsListUseCase(GetGasStationsUseCase getGasStationsUseCase) {
        return getGasStationsUseCase;
    }

}