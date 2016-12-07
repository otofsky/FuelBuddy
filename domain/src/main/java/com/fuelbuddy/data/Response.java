package com.fuelbuddy.data;

/**
 * Created by zjuroszek on 06.12.16.
 */

public class Response {

    public enum ResultTypeEnum {
        Success,  GeneralError,  UserNotFound,
    };

    private Integer code;

    private String message;

    private ResultTypeEnum resultType = null;

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setResultType(ResultTypeEnum resultType) {
        this.resultType = resultType;
    }

    public ResultTypeEnum getResultType() {
        return resultType;
    }

    @Override
    public String toString() {
        return "Response{" +
                "code=" + code +
                ", message='" + message + '\'' +
                ", resultType=" + resultType +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Response that = (Response) o;

        if (code != null ? !code.equals(that.code) : that.code != null) return false;
        return message != null ? message.equals(that.message) : that.message == null;

    }

    @Override
    public int hashCode() {
        int result = code != null ? code.hashCode() : 0;
        result = 31 * result + (message != null ? message.hashCode() : 0);
        return result;
    }
}
