package com.fuelbuddy.mobile.di.module;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.fuelbuddy.data.cache.SharePreferencesUserCacheImpl;
import com.fuelbuddy.data.cache.UserCache;
import com.fuelbuddy.data.net.ApiInvoker;
import com.fuelbuddy.mobile.AndroidApplication;
import com.fuelbuddy.mobile.TrackLocationService;
import com.fuelbuddy.mobile.base.ActivityScope;
import com.fuelbuddy.mobile.base.BaseActivity;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by zjuroszek on 12.02.17.
 */
@Module
public class ServiceModule {

    TrackLocationService trackLocationService;
    AndroidApplication application;

    public ServiceModule(TrackLocationService trackLocationService, AndroidApplication application) {
        this.trackLocationService = trackLocationService;
        this.application = application;
        if (this.application != null) {

        }
    }

    @Provides
    @ActivityScope
    TrackLocationService provideTrackLocationService() {
        return trackLocationService;
    }
    @Provides
    @Singleton
    ApiInvoker provideApiInvoker(UserCache sharePreferencesUserCacheImpl) {
        return new ApiInvoker(sharePreferencesUserCacheImpl);
    }


    @Provides
    @Singleton
    UserCache provideUserCache(SharePreferencesUserCacheImpl userCache) {
        return userCache;
    }

    @Provides
    @Singleton
    SharedPreferences provideSharedPreferences() {
        return PreferenceManager.getDefaultSharedPreferences(application.getApplicationContext());

    }

}
