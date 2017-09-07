package com.fuelbuddy.mobile.login.loginModule;

import android.content.Intent;

/**
 * Created by zjuroszek on 11.11.16.
 */

public interface LoginModule {

    Intent getSignInIntent();

    void close();

}
