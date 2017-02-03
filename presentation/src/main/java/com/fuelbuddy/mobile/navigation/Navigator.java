/**
 * Copyright (C) 2015 Fernando Cejas Open Source Project
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.fuelbuddy.mobile.navigation;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;

import com.fuelbuddy.mobile.Config;
import com.fuelbuddy.mobile.editprice.UpdateActivity;
import com.fuelbuddy.mobile.home.HomeActivity;
import com.fuelbuddy.mobile.login.LoginActivity;
import com.fuelbuddy.mobile.map.FuelPriceMode;
import com.fuelbuddy.mobile.map.MapsMainActivity;
import com.fuelbuddy.mobile.model.GasStationModel;
import com.fuelbuddy.mobile.util.AnimationHelper;

import static com.fuelbuddy.mobile.Config.FUEL_TYPE;


/**
 * Class used to navigate through the application.
 */

public class Navigator {

    /**
     * Goes to the populateGoogleUser list screen.
     *
     * @param context A Context needed to open the destiny activity.
     *
     *
     *
     *
     */
    public static void navigateToLoginActivity(FragmentActivity context) {
        if (context != null) {
            Intent intentToLaunch = LoginActivity.getCallingIntent(context);
            context.startActivity(intentToLaunch);
            AnimationHelper.startAnimatedActivity(context, AnimationHelper.AnimationDirection.LEFT_RIGHT);
        }
    }



    public static void navigateToMapsActivity(Context context, FuelPriceMode fuelPriceMode) {
        if (context != null) {
            Intent intentToLaunch = MapsMainActivity.getCallingIntent(context);
            intentToLaunch.putExtra(FUEL_TYPE, fuelPriceMode);
            context.startActivity(intentToLaunch);
        }
    }

    public static void navigateToHomeActivity(FragmentActivity context) {
        if (context != null) {
            Intent intentToLaunch = HomeActivity.getCallingIntent(context);
            context.finish();
            context.startActivity(intentToLaunch);
            AnimationHelper.startAnimatedActivity(context, AnimationHelper.AnimationDirection.RIGHT_LEFT);

        }
    }

    public static void navigateToEditPriceActivity(FragmentActivity context, GasStationModel gasStationModel) {
        if (context != null) {
            Intent intentToLaunch = UpdateActivity.getCallingIntent(context);
            intentToLaunch.putExtra(Config.GAS_STATION_DETAIL, gasStationModel);
            context.startActivity(intentToLaunch);
            AnimationHelper.startAnimatedActivity(context, AnimationHelper.AnimationDirection.RIGHT_LEFT);
        }
    }
}
