package com.fuelbuddy.mobile.di.module;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.fuelbuddy.data.cache.UserCache;
import com.fuelbuddy.data.executor.JobExecutor;
import com.fuelbuddy.data.net.RestApiService;
import com.fuelbuddy.data.repository.GasStationDataRepository;
import com.fuelbuddy.data.repository.UserDataRepository;
import com.fuelbuddy.data.cache.SharePreferencesUserCacheImpl;
import com.fuelbuddy.executor.PostExecutionThread;
import com.fuelbuddy.executor.ThreadExecutor;
import com.fuelbuddy.interactor.LogOutUseCase;
import com.fuelbuddy.interactor.UseCase;
import com.fuelbuddy.mobile.AndroidApplication;
import com.fuelbuddy.mobile.UIThread;
import com.fuelbuddy.repository.GasStationsRepository;
import com.fuelbuddy.repository.UserRepository;

import javax.inject.Named;
import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class ApplicationModule {
    private final AndroidApplication application;

    public ApplicationModule(AndroidApplication application) {
        this.application = application;
        if (this.application != null) {

        }
    }

    @Provides
    @Singleton
    Context provideApplicationContext() {
        return this.application;
    }

    @Provides
    @Singleton
    ThreadExecutor provideThreadExecutor(JobExecutor jobExecutor) {
        return jobExecutor;
    }

    @Provides
    @Singleton
    PostExecutionThread providePostExecutionThread(UIThread uiThread) {
        return uiThread;
    }

    @Provides
    @Singleton
    GasStationsRepository provideGasStationRepository(GasStationDataRepository gasStationDataRepository) {
        return gasStationDataRepository;
    }

    @Provides
    @Singleton
    UserRepository provideUserRepositoryRepository(UserDataRepository userDataRepository) {
        return userDataRepository;
    }


    @Provides
    @Singleton
    RestApiService provideRestApiService() {
        return RestApiService.Creator.newRestApiService();
    }


    @Provides
    @Singleton
    SharedPreferences provideSharedPreferences() {
        return PreferenceManager.getDefaultSharedPreferences(application.getApplicationContext() );

    }

    @Provides
    @Singleton
    UserCache provideUserCache(SharePreferencesUserCacheImpl userCache) {
        return userCache;
    }


}

