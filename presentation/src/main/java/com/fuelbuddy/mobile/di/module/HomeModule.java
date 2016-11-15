package com.fuelbuddy.mobile.di.module;

import com.fuelbuddy.mobile.base.ActivityScope;
import com.fuelbuddy.mobile.home.HomeActivity;

import dagger.Module;
import dagger.Provides;


@Module
public class HomeModule {

    private HomeActivity homeActivity;

    public HomeModule(HomeActivity homeActivity) {
        this.homeActivity = homeActivity;
    }

    @Provides
    @ActivityScope
    HomeActivity provideHomeActivity() {
        return homeActivity;
    }

  /*  @Provides
    @ActivityScope
    HomePresenter provideHomeActivityPresenter() {
        return new HomePresenter();
    }*/


}