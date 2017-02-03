package com.fuelbuddy;

import javax.inject.Inject;

/**
 * Created by zjuroszek on 27.01.17.
 */

public class PriceValidator {

    public interface UpdateFinishedListener {

        void show92Error();

        void show95Error();

        void showDieselError();
    }


    @Inject
    public PriceValidator() {
    }

    public boolean validatePrice(String fuel92, String fuel95, String diesel, UpdateFinishedListener updateFinishedListener) {
        boolean isValidate = true;
        if (!validate(fuel92)) {
            updateFinishedListener.show92Error();
            isValidate = false;
        }
        if (!validate(fuel95)) {
            updateFinishedListener.show95Error();
            isValidate = false;
        }
        if (!validate(diesel)) {
            updateFinishedListener.showDieselError();
            isValidate = false;
        }

        return isValidate;

    }

    private boolean validate(String input) {
        if (!isNullOrEmpty(input) && input.length() == 5) {
            return true;
        } else {
            return false;
        }
    }

    public static boolean isNullOrEmpty(String string) {
        return string == null || string.isEmpty();
    }
}
