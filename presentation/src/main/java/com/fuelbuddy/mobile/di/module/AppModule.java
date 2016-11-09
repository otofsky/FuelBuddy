package com.fuelbuddy.mobile.di.module;

import android.app.Application;
import android.content.Context;

import com.fuelbuddy.data.executor.JobExecutor;
import com.fuelbuddy.executor.PostExecutionThread;
import com.fuelbuddy.executor.ThreadExecutor;
import com.fuelbuddy.mobile.FuelBuddyApplication;
import com.fuelbuddy.mobile.UIThread;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class AppModule {
    private FuelBuddyApplication application;

    public AppModule(FuelBuddyApplication application) {
        this.application = application;
    }

    @Provides
    @Singleton
    public Context provideApplication() {
        return application;
    }

    @Provides @Singleton
    ThreadExecutor provideThreadExecutor(JobExecutor jobExecutor) {
        return jobExecutor;
    }

    @Provides @Singleton
    PostExecutionThread providePostExecutionThread(UIThread uiThread) {
        return uiThread;
    }


}
