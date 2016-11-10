package com.fuelbuddy.mobile.di.component;

import android.content.Context;

import com.fuelbuddy.executor.PostExecutionThread;
import com.fuelbuddy.executor.ThreadExecutor;
import com.fuelbuddy.mobile.base.BaseActivity;
import com.fuelbuddy.mobile.di.module.ApplicationModule;
import com.fuelbuddy.repository.GasStationsRepository;

import javax.inject.Singleton;

import dagger.Component;

/**
 * A component whose lifetime is the life of the application.
 */
@Singleton // Constraints this component to one-per-application or unscoped bindings.
@Component(modules = ApplicationModule.class)
public interface ApplicationComponent {
    void inject(BaseActivity baseActivity);

    //Exposed to sub-graphs.
    Context context();
    ThreadExecutor threadExecutor();
    PostExecutionThread postExecutionThread();
    GasStationsRepository userRepository();




}
