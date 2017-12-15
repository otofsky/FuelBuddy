package com.fuelbuddy.mobile.map.adapter.colorInjector;

import android.content.Context;
import android.view.View;

import com.fuelbuddy.mobile.R;
import com.fuelbuddy.mobile.util.ResourcesHelper;

/**
 * Created by zjuroszek on 04.12.17.
 */

public class MidPriceColor implements ColorStrategy {
    @Override
    public void setColor(View view, Context context) {
        ResourcesHelper.getDrawable(context, R.drawable.button_yellow_right_rounded);
    }
}
