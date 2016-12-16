package com.fuelbuddy.mobile.map.controller;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;

import com.fuelbuddy.mobile.BuildConfig;
import com.fuelbuddy.mobile.R;
import com.fuelbuddy.mobile.model.GasStationModel;
import com.fuelbuddy.mobile.util.MapUtil;
import com.fuelbuddy.mobile.util.StringHelper;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.UiSettings;
import com.google.android.gms.maps.model.CameraPosition;
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
    public static final float MAP_DEFAULTCAMERA_BEARING = 334.04f;
    public static final float MAP_DEFAULTCAMERA_ZOOM = 17.7f;
    public static final float MAP_DEFAULTCAMERA_TILT = 0f;
    /**
     * Default position of the camera that shows the venue.
     */
/*    private static final CameraPosition VENUE_CAMERA =
            new CameraPosition.Builder().bearing(MAP_DEFAULTCAMERA_BEARING)
                    .zoom(MAP_DEFAULTCAMERA_ZOOM)
                    .tilt(MAP_DEFAULTCAMERA_TILT)
                    .build();*/


    @Override
    public void initMap(Context context, GoogleMap map, GoogleMap.OnMarkerClickListener onMarkerClickListener) {
        this.mContext = context;
        this.mMap = map;

      /*  mMap.setOnMarkerClickListener(this);
        mMap.setOnMapClickListener(this);
        mMap.setOnCameraChangeListener(this);*/
        mMap.setOnMarkerClickListener(onMarkerClickListener);

        UiSettings mapUiSettings = mMap.getUiSettings();
        mapUiSettings.setZoomControlsEnabled(false);
        mapUiSettings.setMapToolbarEnabled(false);


        if (mMap != null) {
            if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(mContext, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                // TODO: Consider calling
                return;
            }
            mMap.setMyLocationEnabled(true);

        }
        //centerOnVenue(false);
    }

/*    private void centerOnVenue(boolean animate) {
        CameraUpdate camera = CameraUpdateFactory.newCameraPosition(VENUE_CAMERA);
        if (animate) {
            mMap.animateCamera(camera);
        } else {
            mMap.moveCamera(camera);
        }
    }*/


    /*
    * Throw exception
    * */
    @Override
    public void showUserCurrentPosition(LatLng currentPositionLatLng) {
        if (mMap != null) {
            mMap.addMarker(MapUtil.initMarkerOptions(mContext.getString(R.string.map_user_current_position), currentPositionLatLng, MapUtil.MarkerType.USER, 0));
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(currentPositionLatLng, MapUtil.ZOOM));
        }
    }

    @Override
    public void seFuelStationsPositions(List<GasStationModel> gasStationModelList, String id) {
        this.gasStationModelList = gasStationModelList;
        if (mMap != null) {
            addMarkers(gasStationModelList,id);
        }
    }

    @Override
    public void clear() {
        mMap.clear();
    }

    private void addMarkers(List<GasStationModel> gasStationModelList, String id) {
        List<LatLng> listLatLng = new ArrayList<LatLng>();
        for (GasStationModel gs : gasStationModelList) {
            LatLng latLng = getLatLng(gs);
            if(!StringHelper.isNullOrEmpty(id)) {
                listLatLng.add(latLng);
                if (!gs.getGasStationId().equalsIgnoreCase(id)) {
                    initUnselectedMarker(gs, latLng);
                } else {
                    initSelectedMarker(gs, latLng);
                }
            }else{
                initUnselectedMarker(gs, latLng);
            }

        }
        setZoomLevel(listLatLng);
    }

    @Override
    public void showSelectedGasStation(String gasStationID) {

        List<LatLng> listLatLng = new ArrayList<LatLng>();
        for (GasStationModel gs : gasStationModelList) {
            LatLng latLng = getLatLng(gs);
            listLatLng.add(latLng);
            if (!gs.getGasStationId().equalsIgnoreCase(gasStationID)) {
                initUnselectedMarker(gs, latLng);
            } else {
                initSelectedMarker(gs, latLng);
            }
        }
        //setZoomLevel(listLatLng);
    }

    private void initSelectedMarker(GasStationModel gs, LatLng latLng) {
        mMap.addMarker(MapUtil.initMarkerOptions(gs.getGasStationName(),
                latLng,
                MapUtil.MarkerType.STATION,
                R.mipmap.drop_on));
    }

    private void initUnselectedMarker(GasStationModel gs, LatLng latLng) {
        mMap.addMarker(MapUtil.initMarkerOptions(gs.getGasStationName(),
                latLng,
                MapUtil.MarkerType.STATION,
                R.mipmap.ic_drop_off));
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
