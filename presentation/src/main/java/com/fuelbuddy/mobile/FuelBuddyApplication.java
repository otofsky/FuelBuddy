package com.fuelbuddy.mobile;

import android.app.Application;
import android.content.Context;

import com.fuelbuddy.mobile.di.component.DaggerAppComponent;
import com.fuelbuddy.mobile.di.module.AppModule;
import com.fuelbuddy.mobile.di.component.AppComponent;

import com.fuelbuddy.mobile.util.LocationRequestData;
import com.google.android.gms.location.LocationRequest;



public class FuelBuddyApplication extends Application {
    private AppComponent component;
    private LocationRequestData locationRequestData = LocationRequestData.FREQUENCY_MEDIUM_TWO;
    @Override
    public void onCreate() {
        super.onCreate();
        component = DaggerAppComponent
                .builder()
                .appModule(new AppModule(this))
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


    public static FuelBuddyApplication get(Context context) {
        return (FuelBuddyApplication) context.getApplicationContext();
    }

    public AppComponent getAppComponent() {
        return component;
    }

}
