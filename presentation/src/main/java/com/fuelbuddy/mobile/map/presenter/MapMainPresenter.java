package com.fuelbuddy.mobile.map.presenter;


import com.fuelbuddy.data.FuelPriceMode;
import com.fuelbuddy.data.GasStation;
import com.fuelbuddy.exception.DefaultErrorBundle;
import com.fuelbuddy.exception.ErrorBundle;
import com.fuelbuddy.interactor.DefaultObserver;
import com.fuelbuddy.interactor.GetGasStationsUseCase;
import com.fuelbuddy.mobile.base.BasePresenter;
import com.fuelbuddy.mobile.exeption.ErrorMessageFactory;
import com.fuelbuddy.mobile.map.view.MapMvpView;
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
public class MapMainPresenter extends BasePresenter<MapMvpView> {

    private final GetGasStationsUseCase mGetGasStationsUseCase;
    private PositionMapper mPositionMapper;


    @Inject
    public MapMainPresenter(@Named("gasStationList") GetGasStationsUseCase getGasStationsUseCase) {
        this.mGetGasStationsUseCase = getGasStationsUseCase;
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
        this.mGetGasStationsUseCase.dispose();
    }

    @DebugLog
    public void submitSearch(LatLng loLatLng, FuelPriceMode fuelPriceMode) {
        getMvpView().showLoading();
        loadUserList(loLatLng, fuelPriceMode);
    }

    @DebugLog
    private void loadUserList(LatLng loLatLng, FuelPriceMode fuelPriceMode) {
        this.getFuelPrices(loLatLng, fuelPriceMode);
    }

    private void getFuelPrices(LatLng loLatLn, FuelPriceMode fuelPriceMode) {
        this.mGetGasStationsUseCase.execute(new FuelPricesListSubscriber(),
                GetGasStationsUseCase.Params.forPosition(mPositionMapper.transformToPosition(loLatLn), fuelPriceMode));
    }

    public void getUpdatedFuelPrices(LatLng loLatLn, FuelPriceMode fuelPriceMode) {
        this.mGetGasStationsUseCase.execute(new FuelPricesListSubscriber(),
                GetGasStationsUseCase.Params.forPosition(mPositionMapper.transformToPosition(loLatLn), fuelPriceMode));
    }


    private void showErrorMessage(ErrorBundle errorBundle) {
        String errorMessage = ErrorMessageFactory.create(getMvpView().context(),
                errorBundle.getException());
        getMvpView().showError(errorMessage);
    }

    private final class FuelPricesListSubscriber extends DefaultObserver<List<GasStation>> {
        @DebugLog
        @Override
        public void onComplete() {
            getMvpView().hideLoading();
        }

        @DebugLog
        @Override
        public void onError(Throwable throwable) {
            getMvpView().hideLoading();
            showErrorMessage(new DefaultErrorBundle((Exception) throwable));
        }

        @DebugLog
        @Override
        public void onNext(List<GasStation> gasStations) {
            getMvpView().hideLoading();
            GasStationModelDataMapper gasStationModelDataMapper = new GasStationModelDataMapper();
            getMvpView().showGasStations(gasStationModelDataMapper.transform(gasStations));
        }
    }
}
