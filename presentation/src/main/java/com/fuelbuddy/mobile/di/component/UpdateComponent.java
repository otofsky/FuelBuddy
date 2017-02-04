package com.fuelbuddy.mobile.di.component;

import com.fuelbuddy.mobile.di.PerActivity;
import com.fuelbuddy.mobile.di.module.ActivityModule;
import com.fuelbuddy.mobile.di.module.ApplicationModule;
import com.fuelbuddy.mobile.di.module.MapsModule;
import com.fuelbuddy.mobile.di.module.UpdateModule;
import com.fuelbuddy.mobile.editprice.UpdateActivity;
import com.fuelbuddy.mobile.map.MapsMainActivity;
import com.fuelbuddy.mobile.map.fragment.DetailInfoFragment;

import dagger.Component;

/**
 * Created by zjuroszek on 01.11.16.
 */

@PerActivity
@Component(dependencies = ApplicationComponent.class, modules = { ActivityModule.class, UpdateModule.class})

public interface UpdateComponent extends ActivityComponent {

    void inject(UpdateActivity updateActivity);


}
