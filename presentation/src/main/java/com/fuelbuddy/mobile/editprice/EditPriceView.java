package com.fuelbuddy.mobile.editprice;

import com.fuelbuddy.mobile.base.MvpView;

/**
 * Created by zjuroszek on 31.12.16.
 */

public interface EditPriceView extends MvpView {

    void showCamera();

    void decreasePrice();

    void increasePrice();

    void updatePrice();

}
