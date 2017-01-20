package com.fuelbuddy.mobile.map.presenter;


import com.fuelbuddy.data.FuelPricesUpdate;
import com.fuelbuddy.data.GasStation;
import com.fuelbuddy.data.Response;
import com.fuelbuddy.exception.DefaultErrorBundle;
import com.fuelbuddy.exception.ErrorBundle;
import com.fuelbuddy.interactor.DefaultSubscriber;
import com.fuelbuddy.interactor.GetGasStationsUseCase;
import com.fuelbuddy.interactor.UpdateFuelPricesUseCase;
import com.fuelbuddy.mobile.base.BasePresenter;
import com.fuelbuddy.mobile.exeption.ErrorMessageFactory;
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
 * Created by zjuroszek on 07.10.16.
 */
public class MapMainPresenter extends BasePresenter<MapMvpView> {

    private final GetGasStationsUseCase mGetGasStationsUseCase;
    private final UpdateFuelPricesUseCase mUpdateFuelPricesUseCase;
    private PositionMapper mPositionMapper;


    @Inject
    public MapMainPresenter(@Named("gasStationList")GetGasStationsUseCase getGasStationsUseCase,
                            UpdateFuelPricesUseCase updateFuelPricesUseCase) {
        this.mGetGasStationsUseCase = getGasStationsUseCase;
        this.mUpdateFuelPricesUseCase = updateFuelPricesUseCase;
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
        this.mGetGasStationsUseCase.unsubscribe();
    }
    @DebugLog
    public void submitSearch(LatLng loLatLng) {
        getMvpView().showLoading();
        loadUserList(loLatLng);
    }

    @DebugLog
    private void loadUserList(LatLng loLatLng) {
        this.getFuelPrices(loLatLng);
    }

    private void getFuelPrices(LatLng loLatLn) {
        this.mGetGasStationsUseCase.setCurrentPosition(mPositionMapper.transformToPosition(loLatLn));
        this.mGetGasStationsUseCase.execute(new FuelPricesListSubscriber());
    }

    public void getUpdatedFuelPrices(LatLng loLatLn) {
        this.mGetGasStationsUseCase.setCurrentPosition(mPositionMapper.transformToPosition(loLatLn));
        this.mGetGasStationsUseCase.execute(new FuelUpdatedPricesListSubscriber());
    }

    public void updateFuelPrices(FuelPricesUpdate fuelPricesUpdate) {
        this.mUpdateFuelPricesUseCase.execute(new UpdateFuelPriceSubscriber());
    }


    private void showErrorMessage(ErrorBundle errorBundle) {
        getMvpView().context();
        ErrorResponse errorResponse = ErrorMessageFactory.create(getMvpView().context(), errorBundle.getException());
        getMvpView().showError(errorResponse.getErrorMassage());
    }


    private final class FuelPricesListSubscriber extends DefaultSubscriber<List<GasStation>> {
        @DebugLog
        @Override public void onCompleted() {
            getMvpView().hideLoading();
        }
        @DebugLog
        @Override public void onError(Throwable throwable) {
            getMvpView().hideLoading();
            showErrorMessage(new DefaultErrorBundle((Exception) throwable));
        }

        @DebugLog
        @Override public void onNext(List<GasStation> gasStations) {
            getMvpView().hideLoading();
            GasStationModelDataMapper gasStationModelDataMapper = new GasStationModelDataMapper();
            getMvpView().showGasStations(gasStationModelDataMapper.transform(gasStations));
        }
    }

    private final class UpdateFuelPriceSubscriber extends DefaultSubscriber<Response> {
        @DebugLog
        @Override public void onCompleted() {
            getMvpView().hideLoading();
        }
        @DebugLog
        @Override public void onError(Throwable throwable) {
            getMvpView().hideLoading();
            showErrorMessage(new DefaultErrorBundle((Exception) throwable));
        }

        @DebugLog
        @Override public void onNext(Response response) {
            getMvpView().hideLoading();
            getMvpView().showSuccessMessage(response.getMessage());

        }
    }

    private final class FuelUpdatedPricesListSubscriber extends DefaultSubscriber<List<GasStation>> {
        @DebugLog
        @Override public void onCompleted() {
            getMvpView().hideLoading();
        }
        @DebugLog
        @Override public void onError(Throwable throwable) {
            getMvpView().hideLoading();
            showErrorMessage(new DefaultErrorBundle((Exception) throwable));
        }

        @DebugLog
        @Override public void onNext(List<GasStation> gasStations) {
            getMvpView().hideLoading();
            GasStationModelDataMapper gasStationModelDataMapper = new GasStationModelDataMapper();
            getMvpView().showGasStations(gasStationModelDataMapper.transform(gasStations));

        }
    }
}
