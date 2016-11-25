package com.fuelbuddy.mobile.map;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.fuelbuddy.mobile.Config;
import com.fuelbuddy.mobile.R;
import com.fuelbuddy.mobile.TrackLocationService;
import com.fuelbuddy.mobile.base.BaseActivity;
import com.fuelbuddy.mobile.di.component.DaggerMapsComponent;
import com.fuelbuddy.mobile.di.component.MapsComponent;
import com.fuelbuddy.mobile.model.GasStationModel;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import hugo.weaving.DebugLog;

public class MapsActivity extends BaseActivity implements OnMapReadyCallback, MapMvpView, GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener {
    private static final String TAG = TrackLocationService.class.getCanonicalName();
    @Inject
    public MapPresenter mapPresenter;
    private GoogleMap mMap;
    private GoogleApiClient googleApiClient;

    private MapsComponent mMapsComponent;

    @BindView(R.id.fuelPriceHolderView)
    LinearLayout fuelPriceHolderView;
    @BindView(R.id.progressView)
    RelativeLayout progressView;

    FuelPriceController mFuelPriceController;

    public static Intent getCallingIntent(Context context) {
        return new Intent(context, MapsActivity.class);
    }

    @DebugLog
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        ButterKnife.bind(this);

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        this.initializeInjector();
        FuelPriceMode fuelPriceMode = (FuelPriceMode) getIntent().getSerializableExtra(Config.FUEL_TYPE);

        LinearLayout fuelPriceHolderView = (LinearLayout) findViewById(R.id.fuelPriceHolderView);

        mFuelPriceController = new FuelPriceController(this, fuelPriceHolderView, fuelPriceMode);

        mapPresenter.attachView(this);

        mapPresenter.submitSearch();
        // startTracking();
        connectGoogleApiClient();


    }


    @Override
    public void navigateToHomeActivity() {
        //Navigator.navigateToHomeActivity(this);
        finish();
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

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the populateGoogleUser will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the populateGoogleUser has
     * installed Google Play services and returned to the app.
     */
    @DebugLog
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker in Sydney and move the camera
        LatLng sydney = new LatLng(-34, 151);
        mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
    }

    @Override
    public void removeMarkers() {

    }


    @Override
    public void showInfoTest(String info) {
        Toast.makeText(getApplicationContext(), info, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showMarkerAt(float latitude, float longitude) {

    }

    @DebugLog
    @Override
    public void showMarkersAt(float latitude, float longitude) {

    }

    @Override
    public void showFuelPriceBars(List<GasStationModel> gasStationModelList) {
        mFuelPriceController.populateFuelPriceBarsSection(gasStationModelList);
    }


    @DebugLog
    @Override
    public void showLoading() {
        Log.d(TAG, "showLoading: ");
        this.progressView.setVisibility(View.VISIBLE);
        setProgressBarIndeterminateVisibility(true);
    }

    @DebugLog
    @Override
    public void hideLoading() {
        this.progressView.setVisibility(View.GONE);
        setProgressBarIndeterminateVisibility(false);
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

    @Override
    public void logOut() {

    }

    @Override
    public Context context() {
        return null;
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
