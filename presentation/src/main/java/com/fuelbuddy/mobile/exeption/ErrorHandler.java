package com.fuelbuddy.mobile.exeption;

/**
 * Created by zjuroszek on 01.03.17.
 */

public interface ErrorHandler<T, E> {

    T validateError(E e);
}
