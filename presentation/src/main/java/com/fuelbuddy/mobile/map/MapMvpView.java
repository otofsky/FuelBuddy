package com.fuelbuddy.mobile.map;

import com.fuelbuddy.mobile.base.MvpView;

/**
 * Created by zjuroszek on 25.10.16.
 */
public interface MapMvpView extends MvpView {

    public void removeMarkers();

    public void showInfoTest(String info);

    public void showMarkerAt(float latitude, float longitude);

    public void showMarkersAt(float latitude, float longitude);

}
