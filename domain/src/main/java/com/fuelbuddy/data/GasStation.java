package com.fuelbuddy.data;

/**
 * Created by zjuroszek on 07.10.16.
 */
public class GasStation {


    private String gasStationId;

    private String gasStationLatitude;

    String gasStationLongitude;

    private String gasStationName;

    private String timeUpdated;

    private String price92;

    private String price95;

    private String priceDiesel;

    private String distance;

    /**
     * @return The gasStationId
     */
    public String getGasStationId() {
        return gasStationId;
    }

    /**
     * @param gasStationId The gasStationId
     */
    public void setGasStationId(String gasStationId) {
        this.gasStationId = gasStationId;
    }

    /**
     * @return The gasStationLatitude
     */
    public String getGasStationLatitude() {
        return gasStationLatitude;
    }

    /**
     * @param gasStationLatitude The gasStationLatitude
     */
    public void setGasStationLatitude(String gasStationLatitude) {
        this.gasStationLatitude = gasStationLatitude;
    }

    /**
     * @return The gasStationLongitude
     */
    public String getGasStationLongitude() {
        return gasStationLongitude;
    }

    /**
     * @param gasStationLongitude The gasStationLongitude
     */
    public void setGasStationLongitude(String gasStationLongitude) {
        this.gasStationLongitude = gasStationLongitude;
    }

    /**
     * @return The gasStationName
     */
    public String getGasStationName() {
        return gasStationName;
    }

    /**
     * @param gasStationName The gasStationName
     */
    public void setGasStationName(String gasStationName) {
        this.gasStationName = gasStationName;
    }

    /**
     * @return The timeUpdated
     */
    public String getTimeUpdated() {
        return timeUpdated;
    }

    /**
     * @param timeUpdated The timeUpdated
     */
    public void setTimeUpdated(String timeUpdated) {
        this.timeUpdated = timeUpdated;
    }

    /**
     * @return The price92
     */
    public String getPrice92() {
        return price92;
    }

    /**
     * @param price92 The price92
     */
    public void setPrice92(String price92) {
        this.price92 = price92;
    }

    /**
     * @return The price95
     */
    public String getPrice95() {
        return price95;
    }

    /**
     * @param price95 The price95
     */
    public void setPrice95(String price95) {
        this.price95 = price95;
    }

    /**
     * @return The priceDiesel
     */
    public String getPriceDiesel() {
        return priceDiesel;
    }

    /**
     * @param priceDiesel The priceDiesel
     */
    public void setPriceDiesel(String priceDiesel) {
        this.priceDiesel = priceDiesel;
    }

    /**
     * @return The distance
     */
    public String getDistance() {
        return distance;
    }

    /**
     * @param distance The distance
     */
    public void setDistance(String distance) {
        this.distance = distance;
    }

    @Override
    public String toString() {
        return "GasStation{" +
                "gasStationId='" + gasStationId + '\'' +
                ", gasStationLatitude='" + gasStationLatitude + '\'' +
                ", gasStationLongitude='" + gasStationLongitude + '\'' +
                ", gasStationName='" + gasStationName + '\'' +
                ", timeUpdated='" + timeUpdated + '\'' +
                ", price92='" + price92 + '\'' +
                ", price95='" + price95 + '\'' +
                ", priceDiesel='" + priceDiesel + '\'' +
                ", distance='" + distance + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        GasStation that = (GasStation) o;

        if (gasStationId != null ? !gasStationId.equals(that.gasStationId) : that.gasStationId != null)
            return false;
        if (gasStationLatitude != null ? !gasStationLatitude.equals(that.gasStationLatitude) : that.gasStationLatitude != null)
            return false;
        if (gasStationLongitude != null ? !gasStationLongitude.equals(that.gasStationLongitude) : that.gasStationLongitude != null)
            return false;
        if (gasStationName != null ? !gasStationName.equals(that.gasStationName) : that.gasStationName != null)
            return false;
        if (timeUpdated != null ? !timeUpdated.equals(that.timeUpdated) : that.timeUpdated != null)
            return false;
        if (price92 != null ? !price92.equals(that.price92) : that.price92 != null) return false;
        if (price95 != null ? !price95.equals(that.price95) : that.price95 != null) return false;
        if (priceDiesel != null ? !priceDiesel.equals(that.priceDiesel) : that.priceDiesel != null)
            return false;
        return distance != null ? distance.equals(that.distance) : that.distance == null;

    }

    @Override
    public int hashCode() {
        int result = gasStationId != null ? gasStationId.hashCode() : 0;
        result = 31 * result + (gasStationLatitude != null ? gasStationLatitude.hashCode() : 0);
        result = 31 * result + (gasStationLongitude != null ? gasStationLongitude.hashCode() : 0);
        result = 31 * result + (gasStationName != null ? gasStationName.hashCode() : 0);
        result = 31 * result + (timeUpdated != null ? timeUpdated.hashCode() : 0);
        result = 31 * result + (price92 != null ? price92.hashCode() : 0);
        result = 31 * result + (price95 != null ? price95.hashCode() : 0);
        result = 31 * result + (priceDiesel != null ? priceDiesel.hashCode() : 0);
        result = 31 * result + (distance != null ? distance.hashCode() : 0);
        return result;
    }
}
