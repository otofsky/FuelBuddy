package com.fuelbuddy.mobile.editprice;

import com.fuelbuddy.data.FuelPricesUpdate;
import com.fuelbuddy.mobile.base.MvpView;
import com.fuelbuddy.mobile.model.FuelPricesUpdateEntry;

import java.io.File;

/**
 * Created by zjuroszek on 31.12.16.
 */

public interface UpdateView extends MvpView {

    void showCamera();

    void updatePrice(FuelPricesUpdateEntry fuelPricesUpdateEntry, File file);

    void showVideoError();

    void show92Error();

    void show95Error();

    void showDieselError();

    void showMap();

    void showConfirmationMessage(FuelPricesUpdateEntry fuelPricesUpdateEntry, File file);
}
