package com.fuelbuddy.mobile.di.module;

import com.fuelbuddy.domain.ListGasStationsInteractor;
import com.fuelbuddy.domain.ListGasStationsInteractorImpl;
import com.fuelbuddy.interactor.GetGasStationList;
import com.fuelbuddy.mobile.base.ActivityScope;
import com.fuelbuddy.mobile.map.MapPresenter;
import com.fuelbuddy.mobile.map.MapsActivity;

import dagger.Module;
import dagger.Provides;




@Module
public class MapsActivityModule {

    private MapsActivity mapsActivity;

    public MapsActivityModule(MapsActivity mapsActivity) {
        this.mapsActivity = mapsActivity;
    }

    @Provides
    @ActivityScope
    MapsActivity provideHomeActivity() {
        return mapsActivity;
    }


    @Provides
    @ActivityScope
    MapPresenter provideHomeActivityPresenter(ListGasStationsInteractorImpl listGasStationsInteractor) {
        return new MapPresenter(listGasStationsInteractor);
    }


/*
    @Provides
    @ActivityScope
    MapPresenter provideHomeActivityPresenter(ListGasStationsInteractorImpl listGasStationsInteractor,GetGasStationList getGasStationList) {
        return new MapPresenter(listGasStationsInteractor,getGasStationList);
    }
*/






  /*  @Provides
    ListGasStationsInteractor provideRepositoriesManager() {
        return new ListGasStationsInteractorImpl();
    }*/

}