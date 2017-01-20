package com.fuelbuddy.mobile;

import android.Manifest;
import android.app.Service;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.widget.Toast;

import com.fuelbuddy.mobile.map.MapsMainActivity;
import com.fuelbuddy.mobile.map.event.LocationUpdateEvent;
import com.fuelbuddy.mobile.util.PermissionsUtils;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.model.LatLng;
import com.gun0912.tedpermission.PermissionListener;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;

import hugo.weaving.DebugLog;


public class TrackLocationService extends Service implements GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener, LocationListener {

    private static boolean isServiceRunning;
    private static final String TAG = TrackLocationService.class.getCanonicalName();
    private int notificationId = 9999;
    private GoogleApiClient googleApiClient;
    private AndroidApplication app;

    public static boolean isServiceRunning() {
        return isServiceRunning;
    }

    private static void setIsServiceRunning(boolean isServiceRunning) {
        TrackLocationService.isServiceRunning = isServiceRunning;
        //EventBus.getDefault().post(AppEvent.SERVICE_STATE_CHANGED);
    }

    public TrackLocationService() {
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d(TAG, "onCreate");
        app = (AndroidApplication) getApplication();
        createGoogleApiClient();
        connectGoogleApiClient();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d(TAG, "onStartCommand");
        TrackLocationService.setIsServiceRunning(true);
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy");
        stopLocationUpdates();
        TrackLocationService.setIsServiceRunning(false);
    }

    @Override
    public IBinder onBind(Intent intent) {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @DebugLog
    @Override
    public void onLocationChanged(Location location) {
        Log.d(TAG, "onLocationChanged");
        updateLocationData(location);
    }

    @Override
    public void onConnected(Bundle bundle) {
        Log.d(TAG, "onConnected");
        startLocationUpdates();
    }

    @Override
    public void onConnectionSuspended(int i) {
        Log.d(TAG, "onConnectionSuspended");
    }

    @Override
    public void onConnectionFailed(ConnectionResult result) {
        Log.d(TAG, "onConnectionFailed");
    }

    @Override
    public void onTaskRemoved(Intent rootIntent) {
        super.onTaskRemoved(rootIntent);
        Log.d(TAG, "onTaskRemoved");
    }

    private void createGoogleApiClient() {
        googleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
    }

    private void connectGoogleApiClient() {
        Log.d(TAG, "connectGoogleApiClient: ");
        if (googleApiClient != null) {
            if (!(googleApiClient.isConnected() || googleApiClient.isConnecting())) {
                googleApiClient.connect();
            } else {
                Log.d(TAG, "Client is connected");
                startLocationUpdates();
            }
        } else {
            Log.d(TAG, "Client is null");
        }
    }

    @DebugLog
    private void startLocationUpdates() {
        Log.d(TAG, "startLocationUpdates: ");
        LocationRequest locationRequest = app.createLocationRequest();
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            Log.d(TAG, "\"  permision is not granted: ");
            return;
        } else {
            Log.d(TAG, "permision is granted");
            LocationServices.FusedLocationApi.requestLocationUpdates(googleApiClient, locationRequest, this);
        }
    }


    private void stopLocationUpdates() {
        LocationServices.FusedLocationApi.removeLocationUpdates(
                googleApiClient, this);
    }

    @DebugLog
    private void updateLocationData(Location location) {
        double latitude = location.getLatitude();
        double longitude = location.getLongitude();
        EventBus.getDefault().post(new LocationUpdateEvent(new LatLng(latitude, longitude)));
        //Toast.makeText(getApplicationContext(), "updateLocationData " + latitude, Toast.LENGTH_SHORT).show();
    }
}
