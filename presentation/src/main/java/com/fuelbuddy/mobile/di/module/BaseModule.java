package com.fuelbuddy.mobile.di.module;

import com.fuelbuddy.mobile.base.ActivityScope;
import com.fuelbuddy.mobile.base.BaseActivity;
import com.fuelbuddy.mobile.home.HomeActivity;

import dagger.Module;
import dagger.Provides;


@Module
public class BaseModule {

    private BaseActivity mBaseActivity;

    public BaseModule(BaseActivity baseActivity) {
        this.mBaseActivity = baseActivity;
    }

    @Provides
    @ActivityScope
    BaseActivity provideBaseActivity() {
        return mBaseActivity;
    }




}