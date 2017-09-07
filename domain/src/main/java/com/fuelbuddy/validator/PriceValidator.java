package com.fuelbuddy.validator;

import javax.inject.Inject;

/**
 * Created by zjuroszek on 04.02.17.
 */

public class PriceValidator implements Validator<String> {

    @Inject
    public PriceValidator() {
    }

    @Override
    public boolean validate(String input) {
        return !isNullOrEmpty(input) && input.length() == 5;
    }

    public static boolean isNullOrEmpty(String string) {
        return string == null || string.isEmpty();
    }

}
