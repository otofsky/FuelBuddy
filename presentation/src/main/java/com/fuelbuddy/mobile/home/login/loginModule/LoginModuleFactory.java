package com.fuelbuddy.mobile.home.login.loginModule;

import android.content.Context;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;

import com.google.android.gms.common.api.GoogleApiClient;

/**
 * Created by zjuroszek on 11.11.16.
 */

public class LoginModuleFactory {

    public LoginModuleFactory( ) {

    }

    public LoginModule createGoogleLoginModule (FragmentActivity context , GoogleApiClient.OnConnectionFailedListener onConnectionFailedListener){
        LoginModule loginModule = new LoginModuleGoogle(context,onConnectionFailedListener);
        return loginModule;
    }

}
