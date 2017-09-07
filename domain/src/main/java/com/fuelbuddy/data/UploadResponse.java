package com.fuelbuddy.data;

/**
 * Created by zjuroszek on 06.12.16.
 */

public class UploadResponse {

    public enum ResultTypeEnum {
        Success,  GeneralError,  UserNotFound,
    }

    private Integer code;

    private String fileID;

    private ResultTypeEnum resultType = null;

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getFileID() {
        return fileID;
    }

    public void setFileID(String fileID) {
        this.fileID = fileID;
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
                ", fileID='" + fileID + '\'' +
                ", resultType=" + resultType +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        UploadResponse that = (UploadResponse) o;

        if (code != null ? !code.equals(that.code) : that.code != null) return false;
        return fileID != null ? fileID.equals(that.fileID) : that.fileID == null;

    }

    @Override
    public int hashCode() {
        int result = code != null ? code.hashCode() : 0;
        result = 31 * result + (fileID != null ? fileID.hashCode() : 0);
        return result;
    }
}
