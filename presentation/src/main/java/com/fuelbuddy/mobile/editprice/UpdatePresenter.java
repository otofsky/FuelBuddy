package com.fuelbuddy.mobile.editprice;

import com.fuelbuddy.mobile.base.BasePresenter;
import com.fuelbuddy.mobile.map.view.DetailInfoView;

import javax.inject.Inject;

import hugo.weaving.DebugLog;

/**
 * Created by zjuroszek on 08.01.17.
 */

public class UpdatePresenter extends BasePresenter<UpdateView> {

    @Inject
    public UpdatePresenter() {}

    @DebugLog
    @Override
    public void detachView() {
        super.detachView();
    }

    @DebugLog
    @Override
    public void attachView(UpdateView updateView) {
        super.attachView(updateView);
    }

    public void startNavigation(){
        getMvpView().showCamera();
    }

    public void startUpdate(){
        getMvpView().updatePrice();
    }

}