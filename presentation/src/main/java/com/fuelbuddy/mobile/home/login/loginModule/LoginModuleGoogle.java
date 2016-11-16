package com.fuelbuddy.mobile.home.login.loginModule;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.GoogleApiClient;

/**
 * Created by zjuroszek on 11.11.16.
 */

public class LoginModuleGoogle implements LoginModule {

    private FragmentActivity context;


    private GoogleApiClient mGoogleApiClient;
    public LoginModuleGoogle(FragmentActivity context ,GoogleApiClient.OnConnectionFailedListener onConnectionFailedListener) {
        this.context =context;
        initGoogleApi(context,onConnectionFailedListener);
    }

    private void initGoogleApi(FragmentActivity context, GoogleApiClient.OnConnectionFailedListener onConnectionFailedListener){
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .requestProfile()
                .build();
        mGoogleApiClient = new GoogleApiClient.Builder(context)
                .enableAutoManage(context /* FragmentActivity */, onConnectionFailedListener /* On~ConnectionFailedListener */)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();
    }

    public Intent getSignInIntent(){
        Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
        return signInIntent;
    }

    @Override
    public void close() {
        if (mGoogleApiClient != null)
            mGoogleApiClient.stopAutoManage(context);
    }

}
