package com.fuelbuddy.validator;

import java.io.File;

import javax.inject.Inject;

/**
 * Created by zjuroszek on 27.01.17.
 */

public class InputValidator {

    public interface UpdateFinishedListener {

        void showVideoError();

        void show92Error();

        void show95Error();

        void showDieselError();
    }

    Validator mPriceValidator;

    Validator mFileValidator;

    @Inject
    public InputValidator(Validator priceValidator, Validator fileValidator) {
        this.mPriceValidator = priceValidator;
        this.mFileValidator = fileValidator;

    }

    public boolean validatePrice(File file, String fuel92, String fuel95, String diesel, UpdateFinishedListener updateFinishedListener) {
        boolean isValidate = true;

        if (!mFileValidator.validate(file)) {
            updateFinishedListener.showVideoError();
            isValidate = false;
        }

        if (!mPriceValidator.validate(fuel92)) {
            updateFinishedListener.show92Error();
            isValidate = false;
        }

        if (!mPriceValidator.validate(fuel92)) {
            updateFinishedListener.show92Error();
            isValidate = false;
        }
        if (!mPriceValidator.validate(fuel95)) {
            updateFinishedListener.show95Error();
            isValidate = false;
        }
        if (!mPriceValidator.validate(diesel)) {
            updateFinishedListener.showDieselError();
            isValidate = false;
        }

        return isValidate;

    }

}
