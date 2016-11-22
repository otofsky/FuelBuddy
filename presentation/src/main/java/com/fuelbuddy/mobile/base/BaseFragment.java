/**
 * Copyright (C) 2014 android10.org. All rights reserved.
 *
 * @author Fernando Cejas (the android10 coder)
 */
package com.fuelbuddy.mobile.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.fuelbuddy.mobile.AndroidApplication;
import com.fuelbuddy.mobile.di.HasComponent;
import com.fuelbuddy.mobile.di.component.ApplicationComponent;
import com.fuelbuddy.mobile.home.HomeActivity;

/**
 * Base {@link android.app.Fragment} class for every fragment in this application.
 */
public abstract class BaseFragment extends Fragment {



  @Nullable
  @Override
  public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
    return super.onCreateView(inflater, container, savedInstanceState);

  }



  /**
   * Shows a {@link android.widget.Toast} message.
   *
   * @param message An string representing a message to be shown.
   */


  protected void showToastMessage(String message) {

    Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
  }


  /**
   * Get an Activity module for dependency injection.
   */

  /**
   * Gets a component for dependency injection by its type.
   */
  @SuppressWarnings("unchecked")
  protected <C> C getComponent(Class<C> componentType) {
    if(getActivity()instanceof HomeActivity){
      Log.d("id", "getComponent: ");
    }

    return componentType.cast(((HasComponent<C>) getActivity()).getComponent());
  }


  public void showSpinnerLoader(String text) {

  }

  public void hideSpinnerLoader() {


  }
}