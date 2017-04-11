package com.fuelbuddy.validator;

/**
 * Created by zjuroszek on 04.02.17.
 */

public interface Validator <T>{
    boolean validate(T t);
}