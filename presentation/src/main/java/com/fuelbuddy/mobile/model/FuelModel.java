package com.fuelbuddy.mobile.model;

/**
 * Created by zjuroszek on 06.01.17.
 */

public class FuelModel {

    private static String NATURAL92 = "92";
    private static String NATURAL95 = "95";
    private static String DIESEL = "DIESEL";

    public enum Fuel {
        NATURAL92,
        NATURAL95,
        DIESEL
    }


    private String price;
    private String fuelName;

    public FuelModel(String price, Fuel fuel) {
        this.price = price;
        switch (fuel) {
            case NATURAL92:
                fuelName = NATURAL92;
                break;
            case NATURAL95:
                fuelName = NATURAL95;
                break;
            case DIESEL:
                fuelName = DIESEL;
                break;
        }
    }


    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getFuelName() {
        return fuelName;
    }

    public void setFuelName(String fuelName) {
        this.fuelName = fuelName;
    }

    @Override
    public String toString() {
        return "FuelModel{" +
                "price='" + price + '\'' +
                ", fuelName='" + fuelName + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        FuelModel fuelModel = (FuelModel) o;

        if (price != null ? !price.equals(fuelModel.price) : fuelModel.price != null) return false;
        return fuelName != null ? fuelName.equals(fuelModel.fuelName) : fuelModel.fuelName == null;

    }

    @Override
    public int hashCode() {
        int result = price != null ? price.hashCode() : 0;
        result = 31 * result + (fuelName != null ? fuelName.hashCode() : 0);
        return result;
    }
}
