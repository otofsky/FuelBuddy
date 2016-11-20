package com.fuelbuddy.mobile;


import com.fuelbuddy.mobile.di.component.ApplicationComponent;
import com.fuelbuddy.mobile.di.component.DaggerApplicationComponent;
import com.fuelbuddy.mobile.di.module.ApplicationModule;

import com.fuelbuddy.mobile.util.LocationRequestData;
import com.google.android.gms.location.LocationRequest;


public class Application extends android.app.Application {

    private static Application instance;

    private ApplicationComponent component;
    private LocationRequestData locationRequestData = LocationRequestData.FREQUENCY_MEDIUM_TWO;


    @Override
    public void onCreate() {
        super.onCreate();
        initlialize();
        initializeInjector();
    }

    public static Application getInstance() {
        initlialize();
        return instance;
    }

    private static void initlialize() {
        if (instance == null) {
            instance = new Application();
        }
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
        if (this.component == null) {
            initializeInjector();
        }

        if (this.component == null) {

        }


        return this.component;
    }

}
