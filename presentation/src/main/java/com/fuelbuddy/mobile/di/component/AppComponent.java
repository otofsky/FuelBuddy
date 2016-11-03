package com.fuelbuddy.mobile.di.component;

import com.fuelbuddy.mobile.di.module.AppModule;
import com.fuelbuddy.mobile.di.module.HomeActivityModule;
import com.fuelbuddy.mobile.di.module.MapsActivityModule;

import javax.inject.Singleton;

import dagger.Component;


@Singleton
@Component(
        modules = {
                AppModule.class,
        }
)

public interface AppComponent {
    HomeActivityComponent plus(HomeActivityModule homeActivityModule);

    MapsActivityComponent plus(MapsActivityModule mapsActivityModule);




}
