package com.fuelbuddy.mobile.editprice;

import android.util.Log;

import com.fuelbuddy.data.GasStation;
import com.fuelbuddy.data.Response;
import com.fuelbuddy.exception.DefaultErrorBundle;
import com.fuelbuddy.exception.ErrorBundle;
import com.fuelbuddy.interactor.DefaultSubscriber;
import com.fuelbuddy.interactor.UpdateFuelPricesUseCase;
import com.fuelbuddy.mobile.base.BasePresenter;
import com.fuelbuddy.mobile.exeption.ErrorMessageFactory;
import com.fuelbuddy.mobile.mapper.GasStationModelDataMapper;
import com.fuelbuddy.mobile.mapper.PositionMapper;
import com.fuelbuddy.mobile.model.ErrorResponse;

import java.util.List;

import javax.inject.Inject;

import hugo.weaving.DebugLog;

/**
 * Created by zjuroszek on 08.01.17.
 */

public class UpdatePresenter extends BasePresenter<UpdateView> {


    private final UpdateFuelPricesUseCase mUpdateFuelPricesUseCase;
    private PositionMapper mPositionMapper;

    @Inject
    public UpdatePresenter(UpdateFuelPricesUseCase updateFuelPricesUseCase) {
        this.mUpdateFuelPricesUseCase = updateFuelPricesUseCase;
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
        this.mUpdateFuelPricesUseCase.unsubscribe();
    }

    public void updateFuelPrices(String gasStationId, String fuel92, String fuel95, String diesel) {
        this.mUpdateFuelPricesUseCase.setFuelPricesUpdate(gasStationId,fuel92,fuel95,diesel);
        this.mUpdateFuelPricesUseCase.execute(new UpdateFuelPriceSubscriber());
    }

    private void showErrorMessage(ErrorBundle errorBundle) {
        ErrorResponse errorResponse = ErrorMessageFactory.create(getMvpView().context(), errorBundle.getException());
       // Log.d("showErrorMessage", "showErrorMessage: " + errorResponse.getErrorMassage());
        //getMvpView().showError(errorResponse.getErrorMassage());
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
