package com.fuelbuddy.mobile.map;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.fuelbuddy.mobile.Config;
import com.fuelbuddy.mobile.R;
import com.fuelbuddy.mobile.TrackLocationService;
import com.fuelbuddy.mobile.base.BaseActivity;
import com.fuelbuddy.mobile.di.component.DaggerMapsComponent;
import com.fuelbuddy.mobile.di.component.MapsComponent;
import com.fuelbuddy.mobile.map.controller.FuelPriceController;
import com.fuelbuddy.mobile.map.event.LocationUpdateEvent;
import com.fuelbuddy.mobile.model.GasStationModel;
import com.fuelbuddy.mobile.util.AnimationHelper;
import com.fuelbuddy.mobile.util.DialogFactory;
import com.fuelbuddy.mobile.util.ProgressHelper;
import com.fuelbuddy.mobile.util.StringHelper;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import hugo.weaving.DebugLog;

public class MapsActivity extends BaseActivity implements OnMapReadyCallback, MapMvpView, GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener, GoogleMap.OnMarkerClickListener {
    private static final String TAG = TrackLocationService.class.getCanonicalName();
    @Inject
    public MapPresenter mapPresenter;

    private Map map;

    private GoogleApiClient googleApiClient;

    private MapsComponent mMapsComponent;

    @BindView(R.id.fuelPriceHolderView)
    LinearLayout fuelPriceHolderView;
    @BindView(R.id.view_progress)
    FrameLayout progressView;

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    FuelPriceController mFuelPriceController;
    private ArrayList<LatLng> listLatLng;
    private LatLng currentPositionLatLng;


    public static Intent getCallingIntent(Context context) {
        return new Intent(context, MapsActivity.class);
    }

    private void initializeInjector() {
        this.mMapsComponent = DaggerMapsComponent.builder()
                .applicationComponent(getApplicationComponent())
                .activityModule(getActivityModule())
                .build();
        mMapsComponent.inject(this);
    }

    @DebugLog
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        ButterKnife.bind(this);
        setToolbar();
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        this.initializeInjector();
        FuelPriceMode fuelPriceMode = (FuelPriceMode) getIntent().getSerializableExtra(Config.FUEL_TYPE);
        LinearLayout fuelPriceHolderView = (LinearLayout) findViewById(R.id.fuelPriceHolderView);
        mFuelPriceController = new FuelPriceController(this, fuelPriceHolderView, fuelPriceMode);
        mapPresenter.attachView(this);
        connectGoogleApiClient();
        AnimationHelper.startAnimatedActivity(this, AnimationHelper.AnimationDirection.RIGHT_LEFT);
    }

    private void setToolbar() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    protected void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    protected void onStop() {
        EventBus.getDefault().unregister(this);
        super.onStop();
    }

    @Subscribe
    public void onEventMainThread(LocationUpdateEvent locationUpdateEvent) {
        this.currentPositionLatLng = locationUpdateEvent.getLatLng();
        map.seUserCurrentPosition(currentPositionLatLng);
        mapPresenter.submitSearch(locationUpdateEvent.getLatLng());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void navigateToHomeActivity() {
        finish();
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

    @DebugLog
    @Override
    public void onMapReady(GoogleMap googleMap) {
        this.map = new MapImpl();
        map.initMap(googleMap, this);
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
    public boolean onMarkerClick(final Marker marker) {

        //marker.
        DialogFactory.createSimpleOkDialog(this, marker.getTitle(), marker.getSnippet(), getString(R.string.dialog_navigation_button_txt), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Uri gmmIntentUri = Uri.parse(StringHelper.getNavigationUrl(marker.getPosition()));
                Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
                mapIntent.setPackage(Config.GOOGLE_MAP_PACKAGE);
                startActivity(mapIntent);
            }
        }).show();
        return false;

    }

    @Override
    public void showFuelPriceBars(List<GasStationModel> gasStationModelList) {
        mFuelPriceController.populateFuelPriceBarsSection(gasStationModelList);
        map.clear();
        map.seUserCurrentPosition(currentPositionLatLng);
        map.seFuelStationsPositions(gasStationModelList);
        hideLoading();
    }

    @DebugLog
    @Override
    public void showLoading() {
        this.progressView.setVisibility(View.VISIBLE);
        ProgressHelper.animateDefault(progressView, true);
        setProgressBarIndeterminateVisibility(true);
    }

    @DebugLog
    @Override
    public void hideLoading() {
        ProgressHelper.animateDefault(progressView, false);
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
