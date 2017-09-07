package com.fuelbuddy.mobile.util;

import android.app.Activity;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

import com.fuelbuddy.mobile.R;


/**
 * Created by zjuroszek on 14.07.16.
 */
public class AnimationHelper {

    public enum AnimationDirection {
        LEFT_RIGHT, RIGHT_LEFT
    }

    public static void startAnimatedFragment(AppCompatActivity context, int container, Fragment fragment, boolean isAnimated) {
        FragmentManager fm = context.getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        if (isAnimated) {
            ft.setCustomAnimations(R.anim.right_slide_in, R.anim.right_slide_out);
        }
        ft.replace(container, fragment, null).commit();
    }

    public static void startAnimatedActivity(FragmentActivity activity, AnimationDirection animation) {
        switch (animation) {
            case LEFT_RIGHT:
                activity.overridePendingTransition(R.anim.left_slide_in, R.anim.left_slide_out);
                break;
            case RIGHT_LEFT:
                activity.overridePendingTransition(R.anim.right_slide_in, R.anim.right_slide_out);
                break;
        }
    }
}
