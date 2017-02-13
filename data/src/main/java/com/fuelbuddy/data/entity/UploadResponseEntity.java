package com.fuelbuddy.data.entity;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by zjuroszek on 06.02.17.
 */

public class UploadResponseEntity {
    @SerializedName("code")
    @Expose
    private Integer code;
    @SerializedName("fileID")
    @Expose
    private String fileID;

    public Integer getCode() {
        return code;
    }

    public String getFileID() {
        return fileID;
    }

    @Override
    public String toString() {
        return "UploadResponseEntity{" +
                "code=" + code +
                ", fileID='" + fileID + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        UploadResponseEntity that = (UploadResponseEntity) o;

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
