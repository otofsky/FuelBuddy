package com.fuelbuddy.data.entity;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by zjuroszek on 07.10.16.
 */
public class GasStationEntity {

    @SerializedName("gasStation_id")
    @Expose
    private String gasStation_id;
    @SerializedName("gasStationLatitude")
    @Expose
    private String gasStationLatitude;
    @SerializedName("gasStationLongitude")
    @Expose
    private String gasStationLongitude;
    @SerializedName("gasStationName")
    @Expose
    private String gasStationName;
    @SerializedName("timeUpdated")
    @Expose
    private String timeUpdated;
    @SerializedName("price92")
    @Expose
    private String price92;
    @SerializedName("price95")
    @Expose
    private String price95;
    @SerializedName("priceDiesel")
    @Expose
    private String priceDiesel;
    @SerializedName("distance")
    @Expose
    private String distance;

    /**
     *
     * @return
     * The gasStation_id
     */
    public String getGasStationId() {
        return gasStation_id;
    }

    /**
     *
     * @param gasStation_id
     * The gasStation_id
     */
    public void setGasStation_id(String gasStation_id) {
        this.gasStation_id = gasStation_id;
    }

    /**
     *
     * @return
     * The gasStationLatitude
     */
    public String getGasStationLatitude() {
        return gasStationLatitude;
    }

    /**
     *
     * @param gasStationLatitude
     * The gasStationLatitude
     */
    public void setGasStationLatitude(String gasStationLatitude) {
        this.gasStationLatitude = gasStationLatitude;
    }

    /**
     *
     * @return
     * The gasStationLongitude
     */
    public String getGasStationLongitude() {
        return gasStationLongitude;
    }

    /**
     *
     * @param gasStationLongitude
     * The gasStationLongitude
     */
    public void setGasStationLongitude(String gasStationLongitude) {
        this.gasStationLongitude = gasStationLongitude;
    }

    /**
     *
     * @return
     * The gasStationName
     */
    public String getGasStationName() {
        return gasStationName;
    }

    /**
     *
     * @param gasStationName
     * The gasStationName
     */
    public void setGasStationName(String gasStationName) {
        this.gasStationName = gasStationName;
    }

    /**
     *
     * @return
     * The timeUpdated
     */
    public String getTimeUpdated() {
        return timeUpdated;
    }

    /**
     *
     * @param timeUpdated
     * The timeUpdated
     */
    public void setTimeUpdated(String timeUpdated) {
        this.timeUpdated = timeUpdated;
    }

    /**
     *
     * @return
     * The price92
     */
    public String getPrice92() {
        return price92;
    }

    /**
     *
     * @param price92
     * The price92
     */
    public void setPrice92(String price92) {
        this.price92 = price92;
    }

    /**
     *
     * @return
     * The price95
     */
    public String getPrice95() {
        return price95;
    }

    /**
     *
     * @param price95
     * The price95
     */
    public void setPrice95(String price95) {
        this.price95 = price95;
    }

    /**
     *
     * @return
     * The priceDiesel
     */
    public String getPriceDiesel() {
        return priceDiesel;
    }

    /**
     *
     * @param priceDiesel
     * The priceDiesel
     */
    public void setPriceDiesel(String priceDiesel) {
        this.priceDiesel = priceDiesel;
    }

    /**
     *
     * @return
     * The distance
     */
    public String getDistance() {
        return distance;
    }

    /**
     *
     * @param distance
     * The distance
     */
    public void setDistance(String distance) {
        this.distance = distance;
    }


}
