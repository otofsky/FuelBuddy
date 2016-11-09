package com.fuelbuddy.mobile.map;


import com.fuelbuddy.interactor.GetGasStationList;
import com.fuelbuddy.interactor.UseCase;
import com.fuelbuddy.mobile.base.BasePresenter;
import com.fuelbuddy.repository.GasStationsRepository;

import javax.inject.Inject;

/**
 * Created by zjuroszek on 07.10.16.
 */
public class MapPresenter extends BasePresenter<MapMvpView> {


    //private final UseCase getUserListUseCase;

    @Inject
    public MapPresenter() {



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
       // this.getUserListUseCase.unsubscribe();
    }

    public void submitSearch() {
       // this.getUserListUseCase.execute(new UserListSubscriber());
    }

}
