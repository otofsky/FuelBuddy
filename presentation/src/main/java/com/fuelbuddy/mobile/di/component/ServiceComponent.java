package com.fuelbuddy.mobile.di.component;

import android.support.v7.app.AppCompatActivity;

import com.fuelbuddy.mobile.TrackLocationService;
import com.fuelbuddy.mobile.base.BaseActivity;
import com.fuelbuddy.mobile.di.PerActivity;
import com.fuelbuddy.mobile.di.module.ActivityModule;
import com.fuelbuddy.mobile.di.module.ApplicationModule;
import com.fuelbuddy.mobile.di.module.LoginModule;
import com.fuelbuddy.mobile.di.module.ServiceModule;

import dagger.Component;

/**
 * Created by zjuroszek on 12.02.17.
 */
@PerActivity
@Component(dependencies = ApplicationComponent.class, modules = ServiceModule.class)

public interface ServiceComponent {
    void inject(TrackLocationService trackLocationService);
}
