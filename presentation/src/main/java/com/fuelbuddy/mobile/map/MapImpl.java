package com.fuelbuddy.mobile.map;

import android.support.annotation.NonNull;

import com.fuelbuddy.mobile.R;
import com.fuelbuddy.mobile.model.GasStationModel;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zjuroszek on 03.12.16.
 */

public class MapImpl implements Map {

    private GoogleMap mMap;

    private List<GasStationModel> gasStationModelList;

    @Override
    public void initMap(GoogleMap map, GoogleMap.OnMarkerClickListener onMarkerClickListener) {
        this.mMap = map;
        mMap.setOnMarkerClickListener(onMarkerClickListener);

    }

    @Override
    public void clear() {
        mMap.clear();
    }

    @Override
    public void seUserCurrentPosition(LatLng currentPositionLatLng) {
        if (mMap != null) {
            mMap.addMarker(initMarkerOptionsForUser(currentPositionLatLng));
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(currentPositionLatLng, 12));
        }
        else {
            //Toast.makeText(getApplicationContext(), "Sorry! unable to create maps", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public GasStationModel getItem(int position){
         return gasStationModelList.get(position);
    }

    @Override
    public void seFuelStationsPositions(List<GasStationModel> gasStationModelList) {
        this.gasStationModelList = gasStationModelList;
        if (mMap != null) {
            addMarkers(gasStationModelList);
        }
        else {
          /*  Toast.makeText(getApplicationContext(), "Sorry! unable to create maps", Toast.LENGTH_SHORT).show();*/
        }
    }

    public void adjustMap() {
        mMap.getUiSettings().setMyLocationButtonEnabled(true);
        //mMap.setMyLocationEnabled(true);
        mMap.getUiSettings().setZoomControlsEnabled(true);
    }

    private void addMarkers(List<GasStationModel> gasStationModelList) {
        List<LatLng> listLatLng = new ArrayList<LatLng>();
        for (GasStationModel gasStationModel : gasStationModelList) {
            LatLng latLng = getLatLng(gasStationModel);
            listLatLng.add(latLng);
            mMap.addMarker(initMarkerOptionForStations(gasStationModel, latLng));
        }
        setZoomlevel(listLatLng);
    }

    @NonNull
    private MarkerOptions initMarkerOptionForStations(GasStationModel gasStationModel, LatLng latLng) {
        return new MarkerOptions()
                .position(latLng)
                .title(gasStationModel.getGasStationName())
                .icon(BitmapDescriptorFactory.fromResource(R.mipmap.ic_launcher));
    }

    private MarkerOptions initMarkerOptionsForUser(LatLng currentPositionLatLng) {
        return new MarkerOptions().position(currentPositionLatLng)
                .title("current position")
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED));
    }

    public void setZoomlevel(List<LatLng> listLatLng) {
        if (listLatLng != null && listLatLng.size() == 1) {
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(listLatLng.get(0), 10));
        } else if (listLatLng != null && listLatLng.size() > 1) {
            final LatLngBounds.Builder builder = LatLngBounds.builder();
            for (int i = 0; i < listLatLng.size(); i++) {
                builder.include(listLatLng.get(i));
            }
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
