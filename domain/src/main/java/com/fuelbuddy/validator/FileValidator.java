package com.fuelbuddy.validator;

import java.io.File;

import javax.inject.Inject;

/**
 * Created by zjuroszek on 04.02.17.
 */

public class FileValidator implements Validator<File> {

    @Inject
    public FileValidator() {
    }

    @Override
    public boolean validate(File file) {
        if (file != null) {
            return true;
        } else {
            return false;
        }
    }
}
