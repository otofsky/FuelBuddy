package com.fuelbuddy.mobile.map;

import com.fuelbuddy.domain.ListGasStationsInteractor;
import com.fuelbuddy.domain.ListGasStationsInteractorImpl;
import com.fuelbuddy.interactor.GetGasStationList;
import com.fuelbuddy.mobile.base.BasePresenter;
import com.fuelbuddy.repository.GasStationsRepository;

import javax.inject.Inject;

/**
 * Created by zjuroszek on 07.10.16.
 */
public class MapPresenter extends BasePresenter<MapMvpView> {


    ListGasStationsInteractorImpl listGasStationsInteractor;

    GetGasStationList getGasStationList;

    @Inject
    public MapPresenter(ListGasStationsInteractorImpl listGasStationsInteractor) {
        this.listGasStationsInteractor = listGasStationsInteractor;

    }


/*
    @Inject
    public MapPresenter(ListGasStationsInteractorImpl listGasStationsInteractor,GetGasStationList getGasStationList) {
        this.listGasStationsInteractor = listGasStationsInteractor;
        this.getGasStationList =  getGasStationList;
    }
*/

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
