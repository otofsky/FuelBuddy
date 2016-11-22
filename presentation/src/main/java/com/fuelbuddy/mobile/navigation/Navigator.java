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

import com.fuelbuddy.mobile.map.MapsActivity;
import com.fuelbuddy.mobile.util.AnimationHelper;

import static com.fuelbuddy.mobile.Constants.FUEL_TYPE;


/**
 * Class used to navigate through the application.
 */

public class Navigator {

    /**
     * Goes to the populateGoogleUser list screen.
     *
     * @param context A Context needed to open the destiny activity.
     */
    public static void navigateToMapsActivity(Context context, String fuelType) {
        if (context != null) {



            Intent intentToLaunch = MapsActivity.getCallingIntent(context);
            intentToLaunch.putExtra(FUEL_TYPE, fuelType);
            context.startActivity(intentToLaunch);
        }
    }

}
