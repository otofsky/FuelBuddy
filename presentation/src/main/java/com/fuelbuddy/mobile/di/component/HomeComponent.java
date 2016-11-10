package com.fuelbuddy.mobile.di.component;

import com.fuelbuddy.mobile.base.ActivityScope;
import com.fuelbuddy.mobile.di.module.HomeModule;
import com.fuelbuddy.mobile.home.HomeActivity;

import dagger.Subcomponent;

/**
 * Created by zjuroszek on 01.11.16.
 */

@ActivityScope
@Subcomponent(
        modules = HomeModule.class
)

public interface HomeComponent {

    HomeActivity inject(HomeActivity homeActivity);




}
