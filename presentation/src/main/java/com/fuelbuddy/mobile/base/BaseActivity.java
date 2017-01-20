package com.fuelbuddy.mobile.base;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;

import com.fuelbuddy.mobile.AndroidApplication;
import com.fuelbuddy.mobile.di.component.ApplicationComponent;
import com.fuelbuddy.mobile.di.module.ActivityModule;
import com.fuelbuddy.mobile.util.AnimationHelper;

import org.greenrobot.eventbus.EventBus;


public abstract class BaseActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getApplicationComponent().inject(this);
    }


    public abstract void navigateToHomeActivity();


    /**
     * Adds a {@link Fragment} to this activity's layout.
     *
     * @param containerViewId The container view to where add the fragment.
     * @param fragment        The fragment to be added.
     */
    protected void addFragment(int containerViewId, Fragment fragment) {
        AnimationHelper.startAnimatedFragment(this, containerViewId, fragment, true);
    }

    /**
     * Get the Main AndroidApplication component for dependency injection.
     */

    protected ApplicationComponent getApplicationComponent() {
        return ((AndroidApplication) getApplication()).getApplicationComponent();
    }

    /**
     * Get an Activity module for dependency injection.
     */
    protected ActivityModule getActivityModule() {
        return new ActivityModule(this);
    }



}