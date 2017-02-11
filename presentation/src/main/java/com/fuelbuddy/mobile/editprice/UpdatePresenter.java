package com.fuelbuddy.mobile.editprice;

import android.util.Log;

import com.fuelbuddy.data.UploadResponse;
import com.fuelbuddy.validator.InputValidator;
import com.fuelbuddy.data.Response;
import com.fuelbuddy.exception.DefaultErrorBundle;
import com.fuelbuddy.exception.ErrorBundle;
import com.fuelbuddy.interactor.DefaultSubscriber;
import com.fuelbuddy.interactor.LogOutUseCase;
import com.fuelbuddy.interactor.UpdateFuelPricesUseCase;
import com.fuelbuddy.interactor.UploadVideoUseCase;
import com.fuelbuddy.mobile.base.BasePresenter;
import com.fuelbuddy.mobile.exeption.ErrorMessageFactory;
import com.fuelbuddy.mobile.model.ErrorResponse;

import java.io.File;

import javax.inject.Inject;
import javax.inject.Named;

import hugo.weaving.DebugLog;

/**
 * Created by zjuroszek on 08.01.17.
 */

public class UpdatePresenter extends BasePresenter<UpdateView> implements InputValidator.UpdateFinishedListener {

    private final UpdateFuelPricesUseCase mUpdateFuelPricesUseCase;
    private final UploadVideoUseCase uploadVideoUseCase;
    private final LogOutUseCase logOutUseCase;  //


    @Inject
    public UpdatePresenter(UpdateFuelPricesUseCase updateFuelPricesUseCase,
                           UploadVideoUseCase uploadVideoUseCase,
                           @Named("logOut") LogOutUseCase logOutUseCase) {

        this.mUpdateFuelPricesUseCase = updateFuelPricesUseCase;
        this.uploadVideoUseCase = uploadVideoUseCase;
        this.logOutUseCase = logOutUseCase;
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


    public void updateVideo(File file) {
        Log.d("updateVideo", "updateVideo: ");
       // mUpdateFuelPricesUseCase.validateInputData(file);
        this.mUpdateFuelPricesUseCase.execute(new UpdateFuelPriceSubscriber());

    }

    public void updateVideo(File file, String gasStationId, String fuel92, String fuel95, String diesel) {
        Log.d("updateVideo", "updateVideo: " + fuel92 + " " + fuel95 + " " + diesel);
        boolean result = mUpdateFuelPricesUseCase.validateInputData(file,gasStationId, fuel92, fuel95, diesel,this);
        if (result) {
            this.mUpdateFuelPricesUseCase.execute(new UpdateFuelPriceSubscriber());
        }
    }

    public void logout() {
        getMvpView().showLoading();
        this.logOutUseCase.execute(new LogOutSubscriber());
    }

    @Override
    public void showVideoError() {
      getMvpView().showVideoError();
    }

    @Override
    public void show92Error() {
        getMvpView().show92Error();
    }

    @Override
    public void show95Error() {
        getMvpView().show95Error();
    }

    @Override
    public void showDieselError() {
        getMvpView().showDieselError();
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
            Log.d("update ", "onNext: " + response.toString());
            getMvpView().hideLoading();
            getMvpView().showMap();
            //getMvpView().showSuccessMessage(response.getFileID());
        }
    }

    private final class UploadVideoSubscriber extends DefaultSubscriber<Response> {
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
            Log.d("update ", "onNext: " + response.toString());
            getMvpView().hideLoading();
            getMvpView().showMap();
            //getMvpView().showSuccessMessage(response.getFileID());
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
            getMvpView().hideLoading();
            getMvpView().logOut();
        }
    }

}
