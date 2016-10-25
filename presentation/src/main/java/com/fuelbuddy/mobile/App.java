package com.fuelbuddy.mobile;

import android.app.Application;
import android.content.Context;

import com.fuelbuddy.mobile.di.AndroidModule;
import com.fuelbuddy.mobile.di.AppComponent;
import com.fuelbuddy.mobile.di.DaggerAppComponent;


public class App extends Application {
    private AppComponent component;

    @Override
    public void onCreate() {
        super.onCreate();
        component = DaggerAppComponent
                .builder()
                .androidModule(new AndroidModule(this))
                .build();
    }

    public static AppComponent getComponent(Context context) {
        return ((App) context.getApplicationContext()).component;
    }

}
