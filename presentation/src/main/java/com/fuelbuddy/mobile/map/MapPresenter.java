package com.fuelbuddy.mobile.map;

import com.fuelbuddy.domain.ListGasStationsInteractor;
import com.fuelbuddy.mobile.base.BasePresenter;

import javax.inject.Inject;

/**
 * Created by zjuroszek on 07.10.16.
 */
public class MapPresenter extends BasePresenter<MapMvpView> {

    @Inject
    ListGasStationsInteractor listGasStationsInteractor;

    public MapPresenter() {
    }

    @Override
    public void attachView(MapMvpView mvpView) {
        super.attachView(mvpView);
    }

    @Override
    public void detachView() {

        super.detachView();
    }

    public void submitSearch() {
        listGasStationsInteractor.getListGasStations();

    }

}
