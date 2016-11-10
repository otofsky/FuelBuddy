package com.fuelbuddy.mobile.di.module;

import android.app.Application;
import android.content.Context;

import com.fuelbuddy.data.executor.JobExecutor;
import com.fuelbuddy.data.net.RestApiService;
import com.fuelbuddy.data.repository.GasStationDataRepository;
import com.fuelbuddy.executor.PostExecutionThread;
import com.fuelbuddy.executor.ThreadExecutor;
import com.fuelbuddy.mobile.UIThread;
import com.fuelbuddy.repository.GasStationsRepository;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class ApplicationModule {
    private Application application;

    public ApplicationModule(Application application) {
        this.application = application;
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
    RestApiService provideRestApiService() {
        return RestApiService.Creator.newRestApiService();
    }

}
