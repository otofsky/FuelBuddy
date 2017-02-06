package com.fuelbuddy.data;

import java.io.File;

/**
 * Created by zjuroszek on 11.12.16.
 */

public class FuelPricesUpdate {

    private File file;
    private String stationID;
    private String userID;
    private Double price92;
    private Double price95;
    private Double priceDiesel;


    public FuelPricesUpdate(File file, String stationID, Double price92, Double price95, Double priceDiesel) {

        this.file = file;
        this.stationID = stationID;
        this.price92 = price92;
        this.price95 = price95;
        this.priceDiesel = priceDiesel;
    }

    public File getFile() {
        return file;
    }

    public void setFile(File file) {
        this.file = file;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getStationID() {
        return stationID;
    }

    public Double getPrice92() {
        return price92;
    }

    public Double getPrice95() {
        return price95;
    }

    public Double getPriceDiesel() {
        return priceDiesel;
    }

    public String getUserID() {
        return userID;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        FuelPricesUpdate that = (FuelPricesUpdate) o;

        if (file != null ? !file.equals(that.file) : that.file != null) return false;
        if (stationID != null ? !stationID.equals(that.stationID) : that.stationID != null) return false;
        if (userID != null ? !userID.equals(that.userID) : that.userID != null) return false;
        if (price92 != null ? !price92.equals(that.price92) : that.price92 != null) return false;
        if (price95 != null ? !price95.equals(that.price95) : that.price95 != null) return false;
        return priceDiesel != null ? priceDiesel.equals(that.priceDiesel) : that.priceDiesel == null;

    }

    @Override
    public int hashCode() {
        int result = file != null ? file.hashCode() : 0;
        result = 31 * result + (stationID != null ? stationID.hashCode() : 0);
        result = 31 * result + (userID != null ? userID.hashCode() : 0);
        result = 31 * result + (price92 != null ? price92.hashCode() : 0);
        result = 31 * result + (price95 != null ? price95.hashCode() : 0);
        result = 31 * result + (priceDiesel != null ? priceDiesel.hashCode() : 0);
        return result;
    }
}
