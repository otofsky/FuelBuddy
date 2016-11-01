package com.fuelbuddy.mobile.di.module;

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
    MapPresenter provideHomeActivityPresenter() {
        return new MapPresenter();
    }


}