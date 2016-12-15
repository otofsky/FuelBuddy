package com.fuelbuddy.mobile.map.controller;

import android.content.Context;
import android.support.annotation.NonNull;

import com.fuelbuddy.mobile.R;
import com.fuelbuddy.mobile.model.GasStationModel;
import com.fuelbuddy.mobile.util.MapUtil;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zjuroszek on 03.12.16.
 */

public class MapController implements Map {

    Context mContext;
    private GoogleMap mMap;
    private List<GasStationModel> gasStationModelList;

    @Override
    public void initMap(Context context ,GoogleMap map, GoogleMap.OnMarkerClickListener onMarkerClickListener) {
        this.mContext = context;
        this.mMap = map;
        mMap.getUiSettings().setMyLocationButtonEnabled(true);
        mMap.getUiSettings().setZoomControlsEnabled(true);
        mMap.setOnMarkerClickListener(onMarkerClickListener);
    }

    /*
    * Throw exception
    * */
    @Override
    public void showUserCurrentPosition(LatLng currentPositionLatLng) {
        if (mMap != null) {
            mMap.addMarker(MapUtil.initMarkerOptions(mContext.getString(R.string.map_user_current_position), currentPositionLatLng, MapUtil.MarkerType.USER,0));
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(currentPositionLatLng, MapUtil.ZOOM));
        }
    }

    @Override
    public void seFuelStationsPositions(List<GasStationModel> gasStationModelList) {
        this.gasStationModelList = gasStationModelList;
        if (mMap != null) {
            addMarkers(gasStationModelList);
        }
    }

    @Override
    public void clear() {
        mMap.clear();
    }

    private void addMarkers(List<GasStationModel> gasStationModelList) {
        List<LatLng> listLatLng = new ArrayList<LatLng>();
        for (GasStationModel gs : gasStationModelList) {
            LatLng latLng = getLatLng(gs);
            listLatLng.add(latLng);
            mMap.addMarker(MapUtil.initMarkerOptions(gs.getGasStationName(),
                    latLng,
                    MapUtil.MarkerType.STATION,
                    R.mipmap.ic_drop_off));
        }
        setZoomLevel(listLatLng);
    }

    @Override
    public void showSelectedGasStation(String gasStationID) {

        List<LatLng> listLatLng = new ArrayList<LatLng>();
        for (GasStationModel gs : gasStationModelList) {
            LatLng latLng = getLatLng(gs);
            listLatLng.add(latLng);
            if(!gs.getGasStationId().equalsIgnoreCase(gasStationID)) {

                mMap.addMarker(MapUtil.initMarkerOptions(gs.getGasStationName(),
                        latLng,
                        MapUtil.MarkerType.STATION,
                        R.mipmap.ic_drop_off));
            }
            else{
                mMap.addMarker(MapUtil.initMarkerOptions(gs.getGasStationName(),
                        latLng,
                        MapUtil.MarkerType.STATION,
                        R.mipmap.drop_on));
            }
        }
        setZoomLevel(listLatLng);
    }


    public void setZoomLevel(List<LatLng> listLatLng) {
        if (listLatLng != null && listLatLng.size() == 1) {
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(listLatLng.get(0), MapUtil.ZOOM));
        } else if (listLatLng != null && listLatLng.size() > 1) {
            buildLatLngList(listLatLng);
        }
    }

    private void buildLatLngList(List<LatLng> listLatLng) {
        final LatLngBounds.Builder builder = LatLngBounds.builder();
        for (int i = 0; i < listLatLng.size(); i++) {
            builder.include(listLatLng.get(i));
        }
    }

    @NonNull
    private LatLng getLatLng(GasStationModel gasStationModel) {
        return new LatLng(getLat(gasStationModel.getGasStationLatitude()),
                getLng(gasStationModel.getGasStationLongitude()));
    }

    private double getLng(String gasStationLongitude) {
        return Double.parseDouble(gasStationLongitude);
    }

    private double getLat(String gasStationLatitude) {
        return Double.parseDouble(gasStationLatitude);
    }
}
