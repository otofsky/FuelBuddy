package com.fuelbuddy.mobile.map;


import android.util.Log;

import com.fuelbuddy.data.GasStation;
import com.fuelbuddy.interactor.DefaultSubscriber;
import com.fuelbuddy.interactor.GetGasStationList;
import com.fuelbuddy.interactor.UseCase;
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
    private PositionMapper mPositionMapper;

    @Inject
    public MapPresenter(@Named("gasStationList")GetGasStationList getGasStationList) {
        this.getGasStationList =  getGasStationList;
        mPositionMapper = new PositionMapper();
    }

    @Override
    public void attachView(MapMvpView mvpView) {
        super.attachView(mvpView);
        getMvpView().showLoading();
    }
    @DebugLog
    @Override
    public void detachView() {
        super.detachView();
        this.getGasStationList.unsubscribe();
    }
    @DebugLog
    public void submitSearch(LatLng loLatLng) {
        loadUserList(loLatLng);
       // this.getGasStationList.execute(new UserListSubscriber());
    }

    /**
     * Loads all users.
     */
    @DebugLog
    private void loadUserList(LatLng loLatLng) {
        //this.hideViewRetry();
        //this.showViewLoading();
        this.getGasStationList(loLatLng);
    }

    private void getGasStationList(LatLng loLatLn) {

        this.getGasStationList.setCurrentPosition(mPositionMapper.transformToPosition(loLatLn));
        this.getGasStationList.execute(new GasStationsListSubscriber());
    }


    private final class GasStationsListSubscriber extends DefaultSubscriber<List<GasStation>> {
        @DebugLog
        @Override public void onCompleted() {
            Log.d("UserListSubscriber", "onCompleted: ");
            getMvpView().hideLoading();
        }
        @DebugLog
        @Override public void onError(Throwable e) {
            Log.d("UserListSubscriber", "onError: " + e.getMessage());
            Log.d("UserListSubscriber", "onError: " + e.getLocalizedMessage());
            Log.d("UserListSubscriber", "onError: " + e.getCause());
            Log.d("UserListSubscriber", "onError: " + e.getStackTrace());
            getMvpView().hideLoading();
            //UserListPresenter.this.showErrorMessage(new DefaultErrorBundle((Exception) e));
            //UserListPresenter.this.showViewRetry();
        }

        @DebugLog
        @Override public void onNext(List<GasStation> gasStations) {
            Log.d("UserListSubscriber", "onNext: ");
            for(GasStation gasStation: gasStations){
                Log.d("UserListSubscriber", "onNext: " + gasStation.toString());
            }
            GasStationModelDataMapper gasStationModelDataMapper = new GasStationModelDataMapper();
           // getMvpView().showInfoTest(gasStations.get(0).getName());
            getMvpView().showFuelPriceBars(gasStationModelDataMapper.transform(gasStations));
            getMvpView().hideLoading();


           // UserListPresenter.this.showUsersCollectionInView(users);
        }
    }


}
