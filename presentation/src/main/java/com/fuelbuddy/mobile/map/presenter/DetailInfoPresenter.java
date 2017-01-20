package com.fuelbuddy.mobile.map.presenter;

import com.fuelbuddy.mobile.base.BasePresenter;
import com.fuelbuddy.mobile.map.view.DetailInfoView;

import javax.inject.Inject;

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
