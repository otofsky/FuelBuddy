package com.fuelbuddy.mobile.di;

import com.fuelbuddy.mobile.home.HomeActivity;

import javax.inject.Singleton;

import dagger.Component;


@Component(modules = {
        AndroidModule.class,

})
@Singleton
public interface AppComponent {
    void inject(HomeActivity homeActivity);
}
