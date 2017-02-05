package com.fuelbuddy.mobile.editprice;

import com.fuelbuddy.mobile.base.MvpView;

/**
 * Created by zjuroszek on 31.12.16.
 */

public interface UpdateView extends MvpView {

    void showCamera();

    void updatePrice();

    void showVideoError();

    void show92Error();

    void show95Error();

    void showDieselError();

    void showMap();

}
