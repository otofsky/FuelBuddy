package com.fuelbuddy.mobile.base;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.fuelbuddy.mobile.Application;
import com.fuelbuddy.mobile.di.component.ApplicationComponent;
import com.fuelbuddy.mobile.di.module.ActivityModule;


public abstract class BaseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getApplicationComponent().inject(this);
    }

    /**
     * Get the Main Application component for dependency injection.
     */

    protected ApplicationComponent getApplicationComponent() {
        ApplicationComponent applicationComponent = Application.getInstance().getApplicationComponent();
        if(applicationComponent == null){

        }
        return applicationComponent;
    }

    /**
     * Get an Activity module for dependency injection.
     */
    protected ActivityModule getActivityModule() {
        return new ActivityModule(this);
    }
}