package com.fuelbuddy.mobile.di.component;

import com.fuelbuddy.mobile.di.PerActivity;
import com.fuelbuddy.mobile.di.module.ActivityModule;
import com.fuelbuddy.mobile.di.module.MapsModule;
import com.fuelbuddy.mobile.map.MapsActivity;

import dagger.Component;

/**
 * Created by zjuroszek on 01.11.16.
 */

@PerActivity
@Component(dependencies = ApplicationComponent.class, modules = {ActivityModule.class, MapsModule.class})

public interface MapsComponent extends ActivityComponent {

    void inject(MapsActivity MapsActivity);




}
