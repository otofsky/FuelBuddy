package com.fuelbuddy.mobile.map;


import com.fuelbuddy.data.GasStation;
import com.fuelbuddy.interactor.DefaultSubscriber;
import com.fuelbuddy.interactor.UseCase;
import com.fuelbuddy.mobile.base.BasePresenter;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

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
    }

    @Override
    public void detachView() {
        super.detachView();
       // this.getGasStationList.unsubscribe();
    }

    public void submitSearch() {
       // this.getGasStationList.execute(new UserListSubscriber());
    }

    /**
     * Loads all users.
     */
    private void loadUserList() {
        //this.hideViewRetry();
        //this.showViewLoading();
        this.getGasStationList();
    }

    private void getGasStationList() {
        this.getGasStationList.execute(new UserListSubscriber());
    }


    private final class UserListSubscriber extends DefaultSubscriber<List<GasStation>> {

        @Override public void onCompleted() {
            //UserListPresenter.this.hideViewLoading();
        }

        @Override public void onError(Throwable e) {
            //UserListPresenter.this.hideViewLoading();
            //UserListPresenter.this.showErrorMessage(new DefaultErrorBundle((Exception) e));
            //UserListPresenter.this.showViewRetry();
        }

        @Override public void onNext(List<GasStation> users) {
           // UserListPresenter.this.showUsersCollectionInView(users);
        }
    }


}
