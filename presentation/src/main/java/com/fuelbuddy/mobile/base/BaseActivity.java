package com.fuelbuddy.mobile.base;


import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;

import com.fuelbuddy.mobile.Application;
import com.fuelbuddy.mobile.di.component.ApplicationComponent;
import com.fuelbuddy.mobile.di.module.ActivityModule;
import com.fuelbuddy.mobile.util.AnimationHelper;


public abstract class BaseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getApplicationComponent().inject(this);
    }


    /**
     * Adds a {@link Fragment} to this activity's layout.
     *
     * @param containerViewId The container view to where add the fragment.
     * @param fragment The fragment to be added.
     */
    protected void addFragment(int containerViewId, Fragment fragment) {
        AnimationHelper.startAnimatedFragment(this, containerViewId, fragment, true);
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