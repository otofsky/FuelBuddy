package com.fuelbuddy.mobile.di.module;


import com.fuelbuddy.interactor.GetGasStationList;
import com.fuelbuddy.interactor.UseCase;
import com.fuelbuddy.mobile.base.ActivityScope;
import com.fuelbuddy.mobile.di.PerActivity;
import com.fuelbuddy.mobile.map.MapPresenter;
import com.fuelbuddy.mobile.map.MapsActivity;

import javax.inject.Named;

import dagger.Module;
import dagger.Provides;




@Module
public class MapsModule {

    public MapsModule() {}

    @Provides @PerActivity
    @Named("gasStationList")
    UseCase provideGetGasStationsListUseCase(GetGasStationList getGasStationList) {
        return getGasStationList;
    }


}