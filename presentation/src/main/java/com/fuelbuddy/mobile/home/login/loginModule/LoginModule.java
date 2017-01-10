package com.fuelbuddy.mobile.home.login.loginModule;

import android.content.Intent;

/**
 * Created by zjuroszek on 11.11.16.
 */

public interface LoginModule {

    public Intent getSignInIntent ();

    public void close();

}
