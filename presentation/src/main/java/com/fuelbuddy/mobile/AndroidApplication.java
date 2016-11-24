package com.fuelbuddy.mobile;


import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

import com.fuelbuddy.mobile.di.component.ApplicationComponent;

import com.fuelbuddy.mobile.di.component.DaggerApplicationComponent;
import com.fuelbuddy.mobile.di.module.ApplicationModule;

import com.fuelbuddy.mobile.util.LocationRequestData;
import com.google.android.gms.location.LocationRequest;


public class AndroidApplication extends Application {

    private ApplicationComponent component;
    private LocationRequestData locationRequestData = LocationRequestData.FREQUENCY_MEDIUM_TWO;


    @Override
    public void onCreate() {
        super.onCreate();
        this.initializeInjector();
    }

    private void initializeInjector() {
        this.component = DaggerApplicationComponent.builder()
                .applicationModule(new ApplicationModule(this))
                .build();
    }


    public LocationRequest createLocationRequest() {
        LocationRequest locationRequest = new LocationRequest();
        locationRequest.setInterval(locationRequestData.getInterval());
        locationRequest.setFastestInterval(locationRequestData.getFastestInterval());
        locationRequest.setPriority(locationRequestData.getPriority());
        locationRequest.setSmallestDisplacement(locationRequestData.getSmallestDisplacement());
        return locationRequest;
    }

    public ApplicationComponent getApplicationComponent() {
        return this.component;
    }

}
