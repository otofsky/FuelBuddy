package com.fuelbuddy.data;

/**
 * Created by zjuroszek on 11.12.16.
 */

public class FuelPricesUpdate {

    private final String iD;
    private final String userID;
    private final Double price92;
    private final Double price95;
    private final Double priceDiesel;


    public FuelPricesUpdate(String iD, String userID, Double price92, Double price95, Double priceDiesel) {
        this.iD = iD;
        this.userID = userID;
        this.price92 = price92;
        this.price95 = price95;
        this.priceDiesel = priceDiesel;
    }

    public String getiD() {
        return iD;
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
    public String toString() {
        return "FuelPricesUpdate{" +
                "iD='" + iD + '\'' +
                ", userID='" + userID + '\'' +
                ", price92=" + price92 +
                ", price95=" + price95 +
                ", priceDiesel=" + priceDiesel +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        FuelPricesUpdate that = (FuelPricesUpdate) o;

        if (iD != null ? !iD.equals(that.iD) : that.iD != null) return false;
        if (userID != null ? !userID.equals(that.userID) : that.userID != null) return false;
        if (price92 != null ? !price92.equals(that.price92) : that.price92 != null) return false;
        if (price95 != null ? !price95.equals(that.price95) : that.price95 != null) return false;
        return priceDiesel != null ? priceDiesel.equals(that.priceDiesel) : that.priceDiesel == null;

    }

    @Override
    public int hashCode() {
        int result = iD != null ? iD.hashCode() : 0;
        result = 31 * result + (userID != null ? userID.hashCode() : 0);
        result = 31 * result + (price92 != null ? price92.hashCode() : 0);
        result = 31 * result + (price95 != null ? price95.hashCode() : 0);
        result = 31 * result + (priceDiesel != null ? priceDiesel.hashCode() : 0);
        return result;
    }
}
