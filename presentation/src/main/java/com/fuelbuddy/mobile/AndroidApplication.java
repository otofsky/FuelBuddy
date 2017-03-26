package com.fuelbuddy.mobile;


import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

import com.crashlytics.android.Crashlytics;
import com.crashlytics.android.ndk.CrashlyticsNdk;
import com.fuelbuddy.mobile.di.component.ApplicationComponent;

import com.fuelbuddy.mobile.di.component.DaggerApplicationComponent;
import com.fuelbuddy.mobile.di.module.ApplicationModule;

import com.fuelbuddy.mobile.util.LocationRequestData;
import com.google.android.gms.location.LocationRequest;
import io.fabric.sdk.android.Fabric;
import uk.co.chrisjenx.calligraphy.CalligraphyConfig;


public class AndroidApplication extends Application {

    private ApplicationComponent component;
    private LocationRequestData locationRequestData = LocationRequestData.FREQUENCY_MEDIUM;


    @Override
    public void onCreate() {
        super.onCreate();
        Fabric.with(this, new Crashlytics(), new CrashlyticsNdk());
        this.initializeInjector();
      //  initFont();
    }

    private void initFont() {
        CalligraphyConfig.initDefault(new CalligraphyConfig.Builder()
                .setDefaultFontPath("fonts/Roboto-RobotoRegular.ttf")
                .setFontAttrId(R.attr.fontPath)
                .build()
        );
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
