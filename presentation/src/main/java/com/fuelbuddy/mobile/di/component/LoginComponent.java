package com.fuelbuddy.mobile.di.component;

import com.fuelbuddy.mobile.di.PerActivity;
import com.fuelbuddy.mobile.di.module.ActivityModule;
import com.fuelbuddy.mobile.di.module.LoginModule;
import com.fuelbuddy.mobile.home.HomeActivity;
import com.fuelbuddy.mobile.home.fuelSelection.FuelSelectionFragment;
import com.fuelbuddy.mobile.home.login.LoginFragment;
import com.fuelbuddy.mobile.login.LoginActivity;

import dagger.Component;

/**
 * Created by zjuroszek on 01.11.16.
 */

@PerActivity
@Component(dependencies = ApplicationComponent.class, modules = {ActivityModule.class, LoginModule.class})


public interface LoginComponent extends ActivityComponent {
    void inject(LoginActivity loginActivity);
}
