package com.fuelbuddy.mobile.map;


import android.util.Log;

import com.fuelbuddy.data.GasStation;
import com.fuelbuddy.interactor.DefaultSubscriber;
import com.fuelbuddy.interactor.UseCase;
import com.fuelbuddy.mobile.base.BasePresenter;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

import hugo.weaving.DebugLog;

/**
 * Created by zjuroszek on 07.10.16.
 */
public class MapPresenter extends BasePresenter<MapMvpView> {

    private final UseCase getGasStationList;

    @Inject
    public MapPresenter(@Named("gasStationList")UseCase getGasStationList) {
        this.getGasStationList =  getGasStationList;
    }

    @Override
    public void attachView(MapMvpView mvpView) {
        super.attachView(mvpView);
        loadUserList();
    }
    @DebugLog
    @Override
    public void detachView() {
        super.detachView();
       // this.getGasStationList.unsubscribe();
    }
    @DebugLog
    public void submitSearch() {
       // this.getGasStationList.execute(new UserListSubscriber());
    }

    /**
     * Loads all users.
     */
    @DebugLog
    private void loadUserList() {
        //this.hideViewRetry();
        //this.showViewLoading();
        this.getGasStationList();
    }

    private void getGasStationList() {
        this.getGasStationList.execute(new UserListSubscriber());
    }


    private final class UserListSubscriber extends DefaultSubscriber<List<GasStation>> {
        @DebugLog
        @Override public void onCompleted() {
            //UserListPresenter.this.hideViewLoading();
        }
        @DebugLog
        @Override public void onError(Throwable e) {
            //UserListPresenter.this.hideViewLoading();
            //UserListPresenter.this.showErrorMessage(new DefaultErrorBundle((Exception) e));
            //UserListPresenter.this.showViewRetry();
        }

        @DebugLog
        @Override public void onNext(List<GasStation> gasStations) {
            ///Log.d("UserListSubscriber", "onNext: " + gasStations.get(0).getName());

           // UserListPresenter.this.showUsersCollectionInView(users);
        }
    }


}
