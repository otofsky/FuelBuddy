package com.fuelbuddy.mobile.editprice;

import android.net.Uri;

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
import com.fuelbuddy.mobile.map.view.MapMvpView;
import com.fuelbuddy.mobile.mapper.GasStationModelDataMapper;
import com.fuelbuddy.mobile.mapper.PositionMapper;
import com.fuelbuddy.mobile.model.ErrorResponse;
import com.fuelbuddy.mobile.util.StringHelper;
import com.google.android.gms.maps.model.LatLng;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

import hugo.weaving.DebugLog;

/**
 * Created by zjuroszek on 08.01.17.
 */

public class UpdatePresenter extends BasePresenter<UpdateView> {


    private final UpdateFuelPricesInteractor mUpdateFuelPricesInteractor;
    private PositionMapper mPositionMapper;

    @Inject
    public UpdatePresenter(UpdateFuelPricesInteractor updateFuelPricesInteractor) {
        this.mUpdateFuelPricesInteractor = updateFuelPricesInteractor;
        mPositionMapper = new PositionMapper();
    }

    @Override
    public void attachView(UpdateView mvpView) {
        super.attachView(mvpView);
    }

    @DebugLog
    @Override
    public void detachView() {
        super.detachView();
        this.mUpdateFuelPricesInteractor.unsubscribe();
    }

    public void updateFuelPrices(String fuel92, String fuel95, String diesel) {
        this.mUpdateFuelPricesInteractor.setFuelPricesUpdate(fuel92,fuel95,diesel);
        this.mUpdateFuelPricesInteractor.execute(new UpdateFuelPriceSubscriber());
    }

    private void showErrorMessage(ErrorBundle errorBundle) {
        getMvpView().context();
        ErrorResponse errorResponse = ErrorMessageFactory.create(getMvpView().context(), errorBundle.getException());
        getMvpView().showError(errorResponse.getErrorMassage());
    }


    private final class UpdateFuelPriceSubscriber extends DefaultSubscriber<Response> {
        @DebugLog
        @Override
        public void onCompleted() {
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
        public void onNext(Response response) {
            getMvpView().hideLoading();
            //getMvpView().showSuccessMessage(response.getMessage());

        }
    }

    private final class FuelUpdatedPricesListSubscriber extends DefaultSubscriber<List<GasStation>> {
        @DebugLog
        @Override
        public void onCompleted() {
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
           // getMvpView().showGasStations(gasStationModelDataMapper.transform(gasStations));

        }
    }
}
