package com.fuelbuddy.mobile.model;

/**
 * Created by zjuroszek on 22.12.16.
 */

public class ErrorResponse {

    private Integer errorCode;
    private String errorMassage;

    public ErrorResponse(int errorCode, String errorMassage) {
        this.errorCode = errorCode;
        this.errorMassage = errorMassage;
    }

    public Integer getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(int errorCode) {
        this.errorCode = errorCode;
    }

    public String getErrorMassage() {
        return errorMassage;
    }

    public void setErrorMassage(String errorMassage) {
        this.errorMassage = errorMassage;
    }

    @Override
    public String toString() {
        return "ErrorResponse{" +
                "errorCode=" + errorCode +
                ", errorMassage='" + errorMassage + '\'' +
                '}';
    }
}
