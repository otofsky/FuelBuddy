package com.fuelbuddy.mobile.home;

import com.fuelbuddy.mobile.base.MvpView;

/**
 * Created by zjuroszek on 25.10.16.
 */
public interface HomeMvpView extends MvpView {

    void showMap();


    void showInfo(String userId);
}
