package com.fuelbuddy.mobile.model;

import com.google.android.gms.maps.model.MarkerOptions;

/**
 * Created by zjuroszek on 23.12.16.
 */

public class MarkerEntry {

    public MarkerModel model;
    public MarkerOptions options;

    public MarkerEntry(MarkerModel model, MarkerOptions options) {
        this.model = model;
        this.options = options;
    }

    public MarkerModel getModel() {
        return model;
    }

    public void setModel(MarkerModel model) {
        this.model = model;
    }

    public MarkerOptions getOptions() {
        return options;
    }

    public void setOptions(MarkerOptions options) {
        this.options = options;
    }
}