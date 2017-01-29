/**
 * Copyright (C) 2015 Fernando Cejas Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.fuelbuddy.mobile.di.module;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;


import com.fuelbuddy.interactor.LogOutUseCase;
import com.fuelbuddy.interactor.UseCase;
import com.fuelbuddy.mobile.di.PerActivity;

import javax.inject.Named;

import dagger.Module;
import dagger.Provides;

/**
 * A module to wrap the Activity state and expose it to the graph.
 */
@Module
public class ActivityModule {
  private final AppCompatActivity activity;

  public ActivityModule(AppCompatActivity activity) {
    this.activity = activity;
  }

  /**
  * Expose the activity to dependents in the graph.
  */
  @Provides
  @PerActivity
  AppCompatActivity activity() {
    return this.activity;
  }

  @Provides
  @Named("logOut")
  LogOutUseCase provideLogOutUseCase(LogOutUseCase logOutUseCase) {
    return logOutUseCase;
  }
}
