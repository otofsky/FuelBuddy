package com.fuelbuddy.validator;

import java.io.File;

import javax.inject.Inject;

/**
 * Created by zjuroszek on 27.01.17.
 */

public class InputValidator {

    public interface UpdateFinishedListener {

        void showDiagMessage(String errorMessage);

        void showVideoError(String errorMessage);

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

    public boolean validate(File file) {
        if (file != null) {
            return true;
        } else {
            return false;
        }
    }

    public boolean validatePrice(File file, String fuel92, String fuel95, String diesel, UpdateFinishedListener updateFinishedListener) {
        boolean isValidate = true;

        if (file != null) {
        } else
        {
            updateFinishedListener.showVideoError("File is not Validate  ");
        }

        if (!mFileValidator.validate(null)) {
            updateFinishedListener.showVideoError("File is not Validate -> !mFileValidator.validate(file)");
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
