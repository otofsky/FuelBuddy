package com.fuelbuddy.mobile.map;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.fuelbuddy.mobile.Config;
import com.fuelbuddy.mobile.R;
import com.fuelbuddy.mobile.TrackLocationService;
import com.fuelbuddy.mobile.base.BaseActivity;
import com.fuelbuddy.mobile.di.component.DaggerMapsComponent;
import com.fuelbuddy.mobile.di.component.MapsComponent;
import com.fuelbuddy.mobile.map.event.LocationUpdateEvent;
import com.fuelbuddy.mobile.map.fragment.DetailInfoFragment;
import com.fuelbuddy.mobile.map.fragment.MapFragment;
import com.fuelbuddy.mobile.map.fragment.PriceListFragment;
import com.fuelbuddy.mobile.map.presenter.MapPresenter;
import com.fuelbuddy.mobile.map.view.MapMvpView;
import com.fuelbuddy.mobile.model.GasStationModel;
import com.fuelbuddy.mobile.util.AnimationHelper;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.model.LatLng;
import com.gun0912.tedpermission.PermissionListener;
import com.gun0912.tedpermission.TedPermission;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import hugo.weaving.DebugLog;

public class MapsActivityTest extends BaseActivity implements GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener, MapMvpView, MapFragment.Callbacks {
    private static final String TAG = TrackLocationService.class.getCanonicalName();

    public static final String[] PERMISSIONS =
            new String[]{Manifest.permission.ACCESS_FINE_LOCATION};

    private static final int REQUEST_LOCATION_PERMISSION = 1;

    @Inject
    public MapPresenter mapPresenter;

    @BindView(R.id.toolbar)
    Toolbar toolbar;


    private GoogleApiClient googleApiClient;
    private MapsComponent mMapsComponent;

    protected MapFragment mMapFragment;

    protected PriceListFragment priceListFragment;

    protected DetailInfoFragment detailInfoFragment;

    // private MapInfoFragment mInfoFragment;


    private LatLng currentPositionLatLng;
    ;

    private LatLng fakeCurrentPositionLatLng = new LatLng(Double.valueOf("55.951869964599610"), Double.valueOf("8.514181137084961"));


    public static Intent getCallingIntent(Context context) {
        return new Intent(context, MapsActivityTest.class);
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
        setContentView(R.layout.map_act);
        ButterKnife.bind(this);
        setToolbar();

        this.initializeInjector();

        mapPresenter.attachView(this);
        connectGoogleApiClient();
        new TedPermission(this)
                .setPermissionListener(permissionlistener)
                .setDeniedMessage("If you reject permission,you can not use this service\n\nPlease turn on permissions at [Setting] > [Permission]")
                .setPermissions(Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION)
                .check();
    }

    PermissionListener permissionlistener = new PermissionListener() {
        @Override
        public void onPermissionGranted() {

            Toast.makeText(MapsActivityTest.this, "Permission Granted", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onPermissionDenied(ArrayList<String> deniedPermissions) {
            Toast.makeText(MapsActivityTest.this, "Permission Denied\n" + deniedPermissions.toString(), Toast.LENGTH_SHORT).show();
        }
    };


    private void setToolbar() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }


    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        if (mMapFragment == null) {
            mMapFragment = MapFragment.newInstance("");
            addFragment(R.id.fragment_container_map, mMapFragment);
        }

        if (priceListFragment == null) {
            FuelPriceMode fuelPriceMode = (FuelPriceMode) getIntent().getSerializableExtra(Config.FUEL_TYPE);
            priceListFragment = PriceListFragment.newInstance(fuelPriceMode);
            addFragment(R.id.fragment_price_container_map, priceListFragment);
        }

        if (detailInfoFragment == null) {
            detailInfoFragment = DetailInfoFragment.newInstance();
            addFragment(R.id.fragment_container_map_info, detailInfoFragment);
        }




/*        if (mInfoFragment == null) {
            mInfoFragment = MapInfoFragment.newInstace(this);
            getFragmentManager().beginTransaction()
                    .add(R.id.fragment_container_map_info, mInfoFragment, "mapsheet")
                    .commit();
        }

        mDetachedMode = getIntent().getBooleanExtra(EXTRA_DETACHED_MODE, false);

        attemptEnableMyLocation();*/

        //mapPresenter.submitSearch(fakeCurrentPositionLatLng);
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
         mapPresenter.submitSearch(currentPositionLatLng);
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
                AnimationHelper.startAnimatedActivity(this, AnimationHelper.AnimationDirection.LEFT_RIGHT);
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
            startTrackLocationService();
        }
    }

    private int createGoogleApiClient() {
        int status = GooglePlayServicesUtil.isGooglePlayServicesAvailable(this);
        switch (status) {
            case ConnectionResult.SUCCESS:
                googleApiClient = buildGoogleClientApi();
                break;
            case ConnectionResult.SERVICE_MISSING:
            case ConnectionResult.SERVICE_VERSION_UPDATE_REQUIRED:
            case ConnectionResult.SERVICE_DISABLED:
                break;
        }
        return status;
    }

    @NonNull
    private GoogleApiClient buildGoogleClientApi() {
        return new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
    }

    private void startTrackLocationService() {
        startService(new Intent(this, TrackLocationService.class));
    }

    private void stopTracking() {
        stopService(new Intent(this, TrackLocationService.class));
    }


    @Override
    public void showFuelPriceBars(List<GasStationModel> gasStationModelList) {
        priceListFragment.showFuelPriceBars(gasStationModelList);
        mMapFragment.loadGasStationPositions(gasStationModelList);
        //  mFuelPriceController.populateFuelPriceBarsSection(gasStationModelList);
    }

    @Override
    public void showSuccessMessage(String message) {
        // DialogFactory.createSimpleSnackBarInfo(fuelPriceHolderView, message);
    }

    @Override
    public void refreshFuelPrices() {
        mapPresenter.getUpdatedFuelPrices(currentPositionLatLng);
    }

    @DebugLog
    @Override
    public void showLoading() {
        // this.progressView.setVisibility(View.VISIBLE);

    }



    @DebugLog
    @Override
    public void hideLoading() {

    }

    @DebugLog
    @Override
    public void showError(String message) {
        // DialogFactory.createSimpleSnackBarInfo(toolbar, message);
    }

    @Override
    public void logOut() {

    }

    @Override
    public Context context() {
        return this;
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        startTrackLocationService();
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    @Override
    public void onInfoHide() {
        if (detailInfoFragment != null) {
            detailInfoFragment.hide();
        }
    }

    @Override
    public void onInfoShow(GasStationModel gasStationModel) {
        if (detailInfoFragment != null) {
            detailInfoFragment.showTitleOnly(gasStationModel);
        }

    }
}
