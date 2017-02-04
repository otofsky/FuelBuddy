package com.fuelbuddy.mobile.di.module;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.fuelbuddy.data.cache.SharePreferencesUserCacheImpl;
import com.fuelbuddy.data.cache.UserCache;
import com.fuelbuddy.data.executor.JobExecutor;
import com.fuelbuddy.data.net.ApiInvoker;
import com.fuelbuddy.data.repository.GasStationDataRepository;
import com.fuelbuddy.data.repository.UserDataRepository;
import com.fuelbuddy.executor.PostExecutionThread;
import com.fuelbuddy.executor.ThreadExecutor;
import com.fuelbuddy.mobile.AndroidApplication;
import com.fuelbuddy.mobile.UIThread;
import com.fuelbuddy.repository.GasStationsRepository;
import com.fuelbuddy.repository.UserRepository;
import com.fuelbuddy.validator.FileValidator;
import com.fuelbuddy.validator.InputValidator;
import com.fuelbuddy.validator.PriceValidator;
import com.fuelbuddy.validator.Validator;

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


/*
    @Provides
    @Singleton
    InputValidator provideInputValidator(InputValidator inputValidator) {
        return inputValidator;
    }
*/

    @Provides
    @Singleton
    InputValidator provideInputValidator(PriceValidator priceValidator, FileValidator fileValidator) {
        return new InputValidator(priceValidator, fileValidator);
    }

    @Provides
    @Singleton
    Validator provideFileValidator(FileValidator fileValidator) {
        return fileValidator;
    }


    @Provides
    @Singleton
    Validator<String> providePriceValidator(PriceValidator priceValidator) {
        return priceValidator;
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

