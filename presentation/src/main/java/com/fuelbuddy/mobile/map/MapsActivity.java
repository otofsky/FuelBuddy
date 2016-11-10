package com.fuelbuddy.mobile.map;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

import com.fuelbuddy.mobile.FuelBuddyApplication;
import com.fuelbuddy.mobile.R;
import com.fuelbuddy.mobile.TrackLocationService;
import com.fuelbuddy.mobile.base.BaseActivity;


import com.fuelbuddy.mobile.di.component.MapsComponent;
import com.fuelbuddy.mobile.di.module.MapsModule;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.GoogleMap;

import javax.inject.Inject;

import hugo.weaving.DebugLog;

public class MapsActivity extends BaseActivity implements MapMvpView, GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener {
    private static final String TAG = TrackLocationService.class.getCanonicalName();
    @Inject
    public MapPresenter mapPresenter;
    private GoogleMap mMap;
    private GoogleApiClient googleApiClient;
    private MapView mapViewController;

    private MapsComponent mMapsComponent;

    public static Intent getCallingIntent(Context context) {
        return new Intent(context, MapsActivity.class);
    }

    @DebugLog
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        mapViewController = new MapView(this);

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.


        this.initializeInjector();

        mapPresenter.attachView(this);
        mapPresenter.submitSearch();
        // startTracking();
        connectGoogleApiClient();

    }


    private void initializeInjector() {
        this.mMapsComponent = DaggerMapsComponent.builder()
                .applicationComponent(getApplicationComponent())
                .activityModule(getActivityModule())
                .build();
        mMapsComponent.inject(this);
    }

    private void startTracking() {
        connectGoogleApiClient();
    }


    private void connectGoogleApiClient() {
        if (googleApiClient == null) {
            if (createGoogleApiClient() != ConnectionResult.SUCCESS) {
                return;
            }
        }

        if (!(googleApiClient.isConnected() || googleApiClient.isConnecting())) {
            googleApiClient.connect();
        } else {
            Log.d(TAG, "Client is connected");
            startTrackLocationService();
        }
    }


    private int createGoogleApiClient() {
        int status = GooglePlayServicesUtil.isGooglePlayServicesAvailable(this);
        switch (status) {
            case ConnectionResult.SUCCESS:
                googleApiClient = new GoogleApiClient.Builder(this)
                        .addConnectionCallbacks(this)
                        .addOnConnectionFailedListener(this)
                        .addApi(LocationServices.API)
                        .build();
                break;
            case ConnectionResult.SERVICE_MISSING:
            case ConnectionResult.SERVICE_VERSION_UPDATE_REQUIRED:
            case ConnectionResult.SERVICE_DISABLED:
                /*Dialog dialog = GooglePlayServicesUtil.getErrorDialog(status, this, REQUEST_RESOLVE_ERROR);
                dialog.show();*/
                break;
        }
        return status;
    }


    private void startTrackLocationService() {
        startService(new Intent(this, TrackLocationService.class));
    }

    private void stopTracking() {
        stopService(new Intent(this, TrackLocationService.class));
    }

    @Override
    public void removeMarkers() {

    }

    @Override
    public void showMarkerAt(float latitude, float longitude) {
       mapViewController.updateMarkerAt(latitude,longitude);
    }

    @DebugLog
    @Override
    public void showMarkersAt(float latitude, float longitude) {
        mapViewController.updateMarkersAt(latitude,longitude);
    }

    @DebugLog
    @Override
    public void showLoading() {

    }

    @DebugLog
    @Override
    public void hideLoading() {

    }

    @DebugLog
    @Override
    public void showRetry() {

    }

    @DebugLog
    @Override
    public void hideRetry() {

    }

    @DebugLog
    @Override
    public void showError(String message) {

    }

    @DebugLog
    @Override
    public void onConnected(@Nullable Bundle bundle) {
        startTrackLocationService();

    }

    @DebugLog
    @Override
    public void onConnectionSuspended(int i) {

    }

    @DebugLog
    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }
}
