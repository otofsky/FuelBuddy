package com.fuelbuddy.mobile.di.component;

import com.fuelbuddy.mobile.base.BaseActivity;
import com.fuelbuddy.mobile.di.PerActivity;
import com.fuelbuddy.mobile.di.module.ActivityModule;
import com.fuelbuddy.mobile.di.module.LoginModule;
import com.fuelbuddy.mobile.home.HomeActivity;

import dagger.Component;

/**
 * Created by zjuroszek on 23.11.16.
 */

@PerActivity
@Component(dependencies = ApplicationComponent.class, modules = {ActivityModule.class, LoginModule.class})

public interface BaseComponent extends ActivityComponent {

    void inject(BaseActivity baseActivity);

}


