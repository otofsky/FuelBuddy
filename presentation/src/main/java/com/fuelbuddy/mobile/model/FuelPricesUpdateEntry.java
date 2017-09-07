package com.fuelbuddy.mobile.model;

import android.os.Parcel;
import android.os.Parcelable;

public class FuelPricesUpdateEntry implements Parcelable {

    private String stationID;
    private String userID;
    private Double price92;
    private Double price95;
    private Double priceDiesel;


    public FuelPricesUpdateEntry() {

    }

    public FuelPricesUpdateEntry(String stationID, String userID, Double price92, Double price95, Double priceDiesel) {
        this.stationID = stationID;
        this.userID = userID;
        this.price92 = price92;
        this.price95 = price95;
        this.priceDiesel = priceDiesel;
    }

    public String getStationID() {
        return stationID;
    }

    public void setStationID(String stationID) {
        this.stationID = stationID;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public Double getPrice92() {
        return price92;
    }

    public void setPrice92(Double price92) {
        this.price92 = price92;
    }

    public Double getPrice95() {
        return price95;
    }

    public void setPrice95(Double price95) {
        this.price95 = price95;
    }

    public Double getPriceDiesel() {
        return priceDiesel;
    }

    public void setPriceDiesel(Double priceDiesel) {
        this.priceDiesel = priceDiesel;
    }

    @Override
    public String toString() {
        return "FuelPricesUpdateEntry{" +
                "stationID='" + stationID + '\'' +
                ", userID='" + userID + '\'' +
                ", price92=" + price92 +
                ", price95=" + price95 +
                ", priceDiesel=" + priceDiesel +
                '}';
    }

    protected FuelPricesUpdateEntry(Parcel in) {
        stationID = in.readString();
        userID = in.readString();
        price92 = in.readByte() == 0x00 ? null : in.readDouble();
        price95 = in.readByte() == 0x00 ? null : in.readDouble();
        priceDiesel = in.readByte() == 0x00 ? null : in.readDouble();
    }

    @Override
    public int describeContents() {
        return 0;
    }



    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(stationID);
        dest.writeString(userID);
        if (price92 == null) {
            dest.writeByte((byte) (0x00));
        } else {
            dest.writeByte((byte) (0x01));
            dest.writeDouble(price92);
        }
        if (price95 == null) {
            dest.writeByte((byte) (0x00));
        } else {
            dest.writeByte((byte) (0x01));
            dest.writeDouble(price95);
        }
        if (priceDiesel == null) {
            dest.writeByte((byte) (0x00));
        } else {
            dest.writeByte((byte) (0x01));
            dest.writeDouble(priceDiesel);
        }
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<FuelPricesUpdateEntry> CREATOR = new Parcelable.Creator<FuelPricesUpdateEntry>() {
        @Override
        public FuelPricesUpdateEntry createFromParcel(Parcel in) {
            return new FuelPricesUpdateEntry(in);
        }

        @Override
        public FuelPricesUpdateEntry[] newArray(int size) {
            return new FuelPricesUpdateEntry[size];
        }
    };
}