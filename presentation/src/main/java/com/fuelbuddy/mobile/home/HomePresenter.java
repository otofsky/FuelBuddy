package com.fuelbuddy.mobile.home;

import com.fuelbuddy.domain.ListGasStationsInteractorImpl;
import com.fuelbuddy.mobile.base.BasePresenter;

import javax.inject.Inject;

/**
 * Created by zjuroszek on 07.10.16.
 */
public class HomePresenter extends BasePresenter<HomeMvpView> {
    private ListGasStationsInteractorImpl mMyClass;

    @Inject
    public HomePresenter() {

    }
}


