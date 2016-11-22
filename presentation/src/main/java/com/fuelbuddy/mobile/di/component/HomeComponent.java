package com.fuelbuddy.mobile.di.component;

import com.fuelbuddy.mobile.base.ActivityScope;
import com.fuelbuddy.mobile.di.PerActivity;
import com.fuelbuddy.mobile.di.module.ActivityModule;
import com.fuelbuddy.mobile.di.module.HomeModule;
import com.fuelbuddy.mobile.di.module.LoginModule;
import com.fuelbuddy.mobile.di.module.MapsModule;
import com.fuelbuddy.mobile.home.HomeActivity;
import com.fuelbuddy.mobile.home.fuelSelection.FuelSelectionFragment;
import com.fuelbuddy.mobile.home.login.LoginFragment;

import dagger.Component;
import dagger.Subcomponent;

/**
 * Created by zjuroszek on 01.11.16.
 */

@PerActivity
@Component(dependencies = ApplicationComponent.class, modules = {ActivityModule.class, LoginModule.class})


public interface HomeComponent extends ActivityComponent {

    void inject(HomeActivity homeActivity);
    void inject(LoginFragment loginFragment);
    void inject(FuelSelectionFragment fuelSelectionFragment);

}
