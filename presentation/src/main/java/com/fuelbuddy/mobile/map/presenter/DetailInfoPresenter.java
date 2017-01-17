package com.fuelbuddy.mobile.map.presenter;

import com.fuelbuddy.data.FuelPricesUpdate;
import com.fuelbuddy.data.GasStation;
import com.fuelbuddy.data.Response;
import com.fuelbuddy.exception.DefaultErrorBundle;
import com.fuelbuddy.exception.ErrorBundle;
import com.fuelbuddy.interactor.DefaultSubscriber;
import com.fuelbuddy.interactor.GetGasStationList;
import com.fuelbuddy.interactor.UpdateFuelPricesInteractor;
import com.fuelbuddy.mobile.base.BasePresenter;
import com.fuelbuddy.mobile.exeption.ErrorMessageFactory;
import com.fuelbuddy.mobile.map.view.DetailInfoView;
import com.fuelbuddy.mobile.map.view.MapMvpView;
import com.fuelbuddy.mobile.mapper.GasStationModelDataMapper;
import com.fuelbuddy.mobile.mapper.PositionMapper;
import com.fuelbuddy.mobile.model.ErrorResponse;
import com.google.android.gms.maps.model.LatLng;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

import hugo.weaving.DebugLog;

/**
 * Created by zjuroszek on 08.01.17.
 */

public class DetailInfoPresenter extends BasePresenter<DetailInfoView> {


    @Inject
    public DetailInfoPresenter() {}

    @DebugLog
    @Override
    public void detachView() {
        super.detachView();
    }

    @DebugLog
    @Override
    public void attachView(DetailInfoView mvpView) {
        super.attachView(mvpView);
    }

    public void startNavigation(){
        getMvpView().showNavigationView();
    }

    public void startUpdate(){

        getMvpView().showEditPriceView();
    }


}
