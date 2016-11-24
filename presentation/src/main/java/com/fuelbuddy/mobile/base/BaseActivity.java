package com.fuelbuddy.mobile.base;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;

import com.fuelbuddy.mobile.AndroidApplication;
import com.fuelbuddy.mobile.R;
import com.fuelbuddy.mobile.di.component.ApplicationComponent;
import com.fuelbuddy.mobile.di.component.DaggerHomeComponent;
import com.fuelbuddy.mobile.di.component.HomeComponent;
import com.fuelbuddy.mobile.di.module.ActivityModule;
import com.fuelbuddy.mobile.util.AnimationHelper;


public abstract class BaseActivity extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getApplicationComponent().inject(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu, menu);//Menu Resource, Menu
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle presses on the action bar items
        switch (item.getItemId()) {
            case R.id.actionLogOut:
                navigateToHomeActivity();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }






    public abstract void navigateToHomeActivity();




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