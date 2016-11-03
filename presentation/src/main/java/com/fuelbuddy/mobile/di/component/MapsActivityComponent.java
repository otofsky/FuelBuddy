package com.fuelbuddy.mobile.di.component;

import com.fuelbuddy.mobile.base.ActivityScope;
import com.fuelbuddy.mobile.di.module.HomeActivityModule;
import com.fuelbuddy.mobile.di.module.MapsActivityModule;
import com.fuelbuddy.mobile.home.HomeActivity;
import com.fuelbuddy.mobile.map.MapsActivity;

import dagger.Subcomponent;

/**
 * Created by zjuroszek on 01.11.16.
 */

@ActivityScope
@Subcomponent(
        modules = MapsActivityModule.class
)

public interface MapsActivityComponent {

    MapsActivity inject(MapsActivity MapsActivity);




}
