package com.fuelbuddy.mobile.map;


import android.util.Log;

import com.fuelbuddy.data.FuelPricesUpdate;
import com.fuelbuddy.data.GasStation;
import com.fuelbuddy.data.Response;
import com.fuelbuddy.interactor.DefaultSubscriber;
import com.fuelbuddy.interactor.GetGasStationList;
import com.fuelbuddy.interactor.UpdateFuelPricesInteractor;
import com.fuelbuddy.mobile.base.BasePresenter;
import com.fuelbuddy.mobile.mapper.GasStationModelDataMapper;
import com.fuelbuddy.mobile.mapper.PositionMapper;
import com.google.android.gms.maps.model.LatLng;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

import hugo.weaving.DebugLog;

/**
 * Created by zjuroszek on 07.10.16.
 */
public class MapPresenter extends BasePresenter<MapMvpView> {

    private final GetGasStationList getGasStationList;
    private final UpdateFuelPricesInteractor mUpdateFuelPricesInteractor;
    private PositionMapper mPositionMapper;


    @Inject
    public MapPresenter(@Named("gasStationList")GetGasStationList getGasStationList,
                        UpdateFuelPricesInteractor updateFuelPricesInteractor) {
        this.getGasStationList =  getGasStationList;
        this.mUpdateFuelPricesInteractor = updateFuelPricesInteractor;
        mPositionMapper = new PositionMapper();
    }

    @Override
    public void attachView(MapMvpView mvpView) {
        super.attachView(mvpView);
    }
    @DebugLog
    @Override
    public void detachView() {
        super.detachView();
        this.getGasStationList.unsubscribe();
    }
    @DebugLog
    public void submitSearch(LatLng loLatLng) {
        getMvpView().showLoading();
        loadUserList(loLatLng);
    }

    @DebugLog
    private void loadUserList(LatLng loLatLng) {
        this.getGasStationList(loLatLng);
    }

    private void getGasStationList(LatLng loLatLn) {

        this.getGasStationList.setCurrentPosition(mPositionMapper.transformToPosition(loLatLn));
        this.getGasStationList.execute(new GasStationsListSubscriber());
    }

    public void updateFuelPrices(FuelPricesUpdate fuelPricesUpdate) {
        this.mUpdateFuelPricesInteractor.setFuelPricesUpdate(fuelPricesUpdate);
        this.mUpdateFuelPricesInteractor.execute(new UpdateGasStationSubscriber());
    }


    private final class GasStationsListSubscriber extends DefaultSubscriber<List<GasStation>> {
        @DebugLog
        @Override public void onCompleted() {
            getMvpView().hideLoading();
        }
        @DebugLog
        @Override public void onError(Throwable e) {
            Log.d("UserListSubscriber", "onError: " + e.getMessage());
            Log.d("UserListSubscriber", "onError: " + e.getLocalizedMessage());
            getMvpView().hideLoading();
        }

        @DebugLog
        @Override public void onNext(List<GasStation> gasStations) {
            getMvpView().hideLoading();
            GasStationModelDataMapper gasStationModelDataMapper = new GasStationModelDataMapper();
            getMvpView().showFuelPriceBars(gasStationModelDataMapper.transform(gasStations));
        }
    }

    private final class UpdateGasStationSubscriber extends DefaultSubscriber<Response> {
        @DebugLog
        @Override public void onCompleted() {
            getMvpView().hideLoading();
        }
        @DebugLog
        @Override public void onError(Throwable e) {
            Log.d("UserListSubscriber", "onError: " + e.getMessage());
            Log.d("UserListSubscriber", "onError: " + e.getLocalizedMessage());
            getMvpView().hideLoading();
        }

        @DebugLog
        @Override public void onNext(Response response) {
            getMvpView().hideLoading();
            GasStationModelDataMapper gasStationModelDataMapper = new GasStationModelDataMapper();
            //getMvpView().showFuelPriceBars(gasStationModelDataMapper.transform(gasStations));
        }
    }
}
