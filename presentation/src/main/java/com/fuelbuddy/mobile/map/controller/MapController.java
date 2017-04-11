package com.fuelbuddy.mobile.map.controller;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;

import com.fuelbuddy.data.FuelPriceMode;
import com.fuelbuddy.mobile.R;
import com.fuelbuddy.mobile.model.GasStationModel;
import com.fuelbuddy.mobile.model.MarkerEntry;
import com.fuelbuddy.mobile.model.MarkerModel;
import com.fuelbuddy.mobile.util.DateHelper;
import com.fuelbuddy.mobile.util.MapUtil;
import com.fuelbuddy.mobile.util.StringHelper;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.UiSettings;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by zjuroszek on 03.12.16.
 */

public class MapController implements MapInterface {


    public interface OnMarkerClickCallback {
        void onMarkerClick(GasStationModel gasStationModel);
    }


    Context mContext;
    private GoogleMap mMap;
    private List<GasStationModel> gasStationModelList;
    private Map<LatLng, MarkerEntry> mapEntries = new HashMap<LatLng, MarkerEntry>();
    OnMarkerClickCallback onMarkerClickCallback;

    public static final float MAP_DEFAULTCAMERA_BEARING = 334.04f;
    public static final float MAP_DEFAULTCAMERA_ZOOM = 16.7f;
    public static final float MAP_DEFAULTCAMERA_TILT = 0f;
    LatLng defaultLocation = MapUtil.getLatLng("55.4038", "10.4024");


    /**
     * Default position of the camera that shows the venue.
     */

    @Override
    public void initMap(Context context, GoogleMap map, OnMarkerClickCallback onMarkerClickCallback) {
        gasStationModelList = new ArrayList<>();
        this.mContext = context;
        this.mMap = map;
        this.onMarkerClickCallback = onMarkerClickCallback;
        mMap.setOnMarkerClickListener(onMarkerClickListener);
        UiSettings mapUiSettings = mMap.getUiSettings();
        mapUiSettings.setZoomControlsEnabled(false);
        mapUiSettings.setMapToolbarEnabled(false);
        mapUiSettings.setMyLocationButtonEnabled(true);
        if (mMap != null) {
            if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(mContext, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                // TODO: Consider calling
                return;
            }
            mMap.setMyLocationEnabled(true);
            centerOnDefaultPosition();
        }
    }

    public void centerOnDefaultPosition() {
        CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(defaultLocation, 6.3f);
        mMap.animateCamera(cameraUpdate);
    }


    public void centerOnPosition(boolean animate, LatLng latLng) {
        CameraUpdate camera = CameraUpdateFactory.newCameraPosition(centerOnPosition(latLng));
        if (animate) {
            mMap.animateCamera(camera);
        } else {
            mMap.moveCamera(camera);
        }
    }

    private static CameraPosition centerOnPosition(LatLng latLng) {
        return new CameraPosition.Builder().bearing(MAP_DEFAULTCAMERA_BEARING)
                .zoom(MAP_DEFAULTCAMERA_ZOOM)
                .target(latLng)
                .tilt(MAP_DEFAULTCAMERA_TILT)
                .build();
    }

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
    public void setClientPositions(LatLng latLng) {
        if (mMap != null) {
            centerOnClientPosition(latLng);
        }
    }

    @Override
    public void setFuelStationsPositions(List<GasStationModel> gasStationModelList, FuelPriceMode fuelPriceMode) {
        this.gasStationModelList.clear();
        for (GasStationModel gasStation : gasStationModelList) {
            switch (fuelPriceMode) {
                case BENZIN_92:
                    if (!StringHelper.isNullOrEmpty(gasStation.getPrice92())) {
                        this.gasStationModelList.add(gasStation);
                    }
                    break;
                case BENZIN_95:
                    if (!StringHelper.isNullOrEmpty(gasStation.getPrice95())) {
                        this.gasStationModelList.add(gasStation);
                    }
                    break;
                case DIESEL:
                    if (!StringHelper.isNullOrEmpty(gasStation.getPriceDiesel())) {
                        this.gasStationModelList.add(gasStation);
                    }
                    break;
            }
        }

        if (mMap != null) {
            initMapWithMarkers(this.gasStationModelList);
        }
    }

    @Override
    public void setFuelStationsPositions() {
        if (mMap != null && gasStationModelList != null) {
            initMapWithMarkers(gasStationModelList);
        }
    }

    @Override
    public void clear() {
        if (mMap != null) {
            mMap.clear();
        }
    }

    @Override
    public void showSelectedGasStation(String selectedMarkerId) {
        initMapWithSelectedMarker(selectedMarkerId);
    }

    public void setMarkerData(List<GasStationModel> gasStationModelList) {
        this.gasStationModelList = gasStationModelList;
    }

    private void initMapWithMarkers(List<GasStationModel> gasStations) {
        List<LatLng> listLatLng = initMarkMarkerEntryList(gasStations);
        addMarkersToMap();
        centerOnGasStations(listLatLng);
    }

    private void initMapWithSelectedMarker(String selectedMarkerId) {
        LatLng selectedLatLng = initSelectedMarkerEntryList(gasStationModelList, selectedMarkerId);
        addMarkersToMap();
        centerOnPosition(true, selectedLatLng);
    }

    private void addMarkersToMap() {
        for (MarkerEntry markerEntry : mapEntries.values()) {
            mMap.addMarker(markerEntry.options);
        }
    }

    private List<LatLng> initMarkMarkerEntryList(List<GasStationModel> gasStationModelList) {
        List<LatLng> listLatLng = new ArrayList<LatLng>();
        for (GasStationModel gs : gasStationModelList) {
            LatLng latLng = getLatLng(gs);
            listLatLng.add(latLng);
            MarkerModel markerModel = new MarkerModel(gs, null);
            MarkerOptions markerOptions = getMarkerUnselectedOptions(gs, latLng);
            MarkerEntry markerEntry = new MarkerEntry(markerModel, markerOptions);
            mapEntries.put(latLng, markerEntry);

        }
        return listLatLng;
    }

    private LatLng initSelectedMarkerEntryList(List<GasStationModel> gasStationModelList, String id) {
        LatLng selectedLatLng = null;
        for (GasStationModel gs : gasStationModelList) {
            LatLng latLng = getLatLng(gs);
            MarkerModel markerModel = new MarkerModel(gs, null);
            MarkerOptions markerOptions = null;
            if (!gs.getGasStationId().equalsIgnoreCase(id)) {
                markerOptions = getMarkerUnselectedOptions(gs, latLng);
            } else {
                markerOptions = getMarkerSelectedOptions(gs, latLng);
                selectedLatLng = latLng;
            }
            MarkerEntry markerEntry = new MarkerEntry(markerModel, markerOptions);
            mapEntries.put(latLng, markerEntry);
        }
        return selectedLatLng;
    }

    @NonNull
    private MarkerOptions getMarkerUnselectedOptions(GasStationModel gs, LatLng latLng) {
        return MapUtil.initMarkerOptions(gs.getGasStationName(), latLng, MapUtil.MarkerType.STATION, R.mipmap.petrol_stations_symbol_green);
     /*   int numOfHours = DateHelper.isOlderThanData(gs.getTimeUpdated());
        if (numOfHours < 2) {
            return MapUtil.initMarkerOptions(gs.getGasStationName(), latLng, MapUtil.MarkerType.STATION, R.mipmap.petrol_stations_symbol_green);
        } else if (numOfHours > 2 && numOfHours < 4) {
            return MapUtil.initMarkerOptions(gs.getGasStationName(), latLng, MapUtil.MarkerType.STATION, R.mipmap.petrol_stations_symbol_yellow);
        } else {
            return MapUtil.initMarkerOptions(gs.getGasStationName(), latLng, MapUtil.MarkerType.STATION, R.mipmap.petrol_stations_symbol_red);
        }*/
    }

    @NonNull
    private MarkerOptions getMarkerSelectedOptions(GasStationModel gs, LatLng latLng) {
        MarkerOptions markerOptions;
        markerOptions = MapUtil.initMarkerOptions(gs.getGasStationName(),
                latLng,
                MapUtil.MarkerType.STATION,
                R.mipmap.drop_selected);
        return markerOptions;
    }

    public void centerOnGasStations(List<LatLng> listLatLng) {
        LatLngBounds.Builder bounds = new LatLngBounds.Builder();
        for (LatLng latLng : listLatLng) {
            bounds.include(latLng);
        }
        mMap.animateCamera(CameraUpdateFactory.newLatLngBounds(bounds.build(), 200));
    }

    public void centerOnClientPosition(LatLng latLng) {
        LatLngBounds.Builder bounds = new LatLngBounds.Builder();
        bounds.include(latLng);
        mMap.animateCamera(CameraUpdateFactory.newLatLngBounds(bounds.build(), 200));
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

    GoogleMap.OnMarkerClickListener onMarkerClickListener = new GoogleMap.OnMarkerClickListener() {
        @Override
        public boolean onMarkerClick(Marker marker) {
            MarkerEntry markerEntry = mapEntries.get(marker.getPosition());
            MarkerModel markerModel = markerEntry.getModel();
            onMarkerClickCallback.onMarkerClick(markerModel.getGasStationModel());
            return true;
        }
    };

}
