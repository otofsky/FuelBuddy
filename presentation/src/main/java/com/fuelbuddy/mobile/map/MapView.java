package com.fuelbuddy.mobile.map;

import android.content.Context;

import com.fuelbuddy.mobile.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

/**
 * Created by zjuroszek on 09.11.16.
 */

public class MapView implements OnMapReadyCallback {

    private MapsActivity context;
    private GoogleMap mMap;

    public MapView(MapsActivity context) {
        this.context = context;
        SupportMapFragment mapFragment = (SupportMapFragment) context.getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        LatLng sydney = new LatLng(-34, 151);
        updateMapWithNewData(sydney);
    }

    public void updateMarkerAt(float latitude, float longitude) {
        LatLng sydney = new LatLng(latitude, longitude);
        updateMapWithNewData(sydney);
    }

    public void updateMarkersAt(float latitude, float longitude) {
        LatLng sydney = new LatLng(latitude, longitude);
        updateMapWithNewData(sydney);
    }
    private void updateMapWithNewData(LatLng sydney) {
        mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
    }
}
