package com.fuelbuddy.mobile.map.adapter.colorInjector;

import android.content.Context;
import android.view.View;

/**
 * Created by zjuroszek on 04.12.17.
 */

public class PriceColorManager {

    ColorStrategy colorStrategy;

    public void setColorStrategy(ColorStrategy colorStrategy) {
        this.colorStrategy = colorStrategy;
    }

    public void inflateColorState(View view, Context context) {
        colorStrategy.setColor(view, context);
    }
}
