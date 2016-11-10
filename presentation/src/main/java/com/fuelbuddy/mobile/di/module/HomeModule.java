package com.fuelbuddy.mobile.di.module;

import com.fuelbuddy.mobile.base.ActivityScope;
import com.fuelbuddy.mobile.home.HomeActivity;
import com.fuelbuddy.mobile.home.HomePresenter;

import dagger.Module;
import dagger.Provides;



/**
 * Created by Miroslaw Stanek on 23.04.15.
 */


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

    @Provides
    @ActivityScope
    HomePresenter provideHomeActivityPresenter() {
        return new HomePresenter();
    }


}