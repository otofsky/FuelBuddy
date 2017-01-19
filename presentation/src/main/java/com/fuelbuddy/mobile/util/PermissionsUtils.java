/*
 * Copyright (c) 2016 Google Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 */

package com.fuelbuddy.mobile.util;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.widget.Toast;

import com.fuelbuddy.mobile.R;
import com.fuelbuddy.mobile.editprice.UpdateActivity;
import com.gun0912.tedpermission.PermissionListener;
import com.gun0912.tedpermission.TedPermission;

import java.util.ArrayList;

import butterknife.ButterKnife;

/**
 * Set of runtime permission utility methods.
 */
public class PermissionsUtils {
    private static final String TAG = "PermissionsUtils";


    public static void initPermission(Context context, PermissionListener permissionlistener,String... permissions) {
        new TedPermission(context)
                .setPermissionListener(permissionlistener)
                .setPermissions(permissions)
                .check();
    }


    /**
     * Determine if any permission is in the denied state.
     */
    public static boolean anyPermissionDenied(Context context, String[] permissions) {
        for (String permission : permissions) {
            if (ContextCompat.checkSelfPermission(context, permission) == PackageManager.PERMISSION_DENIED) {
                return true;
            }
        }
        return false;
    }


    public static boolean permissionsAlreadyGranted(@NonNull Context context,
                                                    @NonNull String[] permissions) {
        for (String permission : permissions) {
            if (ContextCompat.checkSelfPermission(context, permission) !=
                    PackageManager.PERMISSION_GRANTED) {
                return false;
            }
        }
        return true;
    }

    /**
     * Determines if {@code permission} rationale should be displayed for any {@code permission}.
     */
    public static boolean shouldShowAnyPermissionRationale(@NonNull Activity activity,
                                                           String[] permissions) {
        for (String permission : permissions) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(activity, permission)) {
                return true;
            }
        }
        return false;
    }
}
