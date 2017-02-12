package com.fuelbuddy.mobile.map.presenter;


import android.util.Log;

import com.fuelbuddy.data.GasStation;
import com.fuelbuddy.data.Response;
import com.fuelbuddy.exception.DefaultErrorBundle;
import com.fuelbuddy.exception.ErrorBundle;
import com.fuelbuddy.interactor.DefaultSubscriber;
import com.fuelbuddy.interactor.GetGasStationsUseCase;
import com.fuelbuddy.interactor.LogOutUseCase;
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
    private final LogOutUseCase logOutUseCase;
    private PositionMapper mPositionMapper;


    @Inject
    public MapMainPresenter(@Named("gasStationList")GetGasStationsUseCase getGasStationsUseCase,LogOutUseCase logOutUseCase) {
        this.mGetGasStationsUseCase = getGasStationsUseCase;
        this.logOutUseCase = logOutUseCase;
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

    public void logout() {
        getMvpView().showLoading();
        this.logOutUseCase.execute(new LogOutSubscriber());
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

    private final class LogOutSubscriber extends DefaultSubscriber<Boolean> {

        @DebugLog
        @Override
        public void onCompleted() {
        }

        @DebugLog
        @Override
        public void onError(Throwable e) {

        }

        @DebugLog
        @Override
        public void onNext(Boolean isLogout) {
            Log.d("Logout", "onNext: " + isLogout);
            getMvpView().hideLoading();
            getMvpView().logOut();
        }
    }
}
