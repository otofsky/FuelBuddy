package com.fuelbuddy.mobile.map;

import android.Manifest;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.fuelbuddy.data.FuelPriceMode;
import com.fuelbuddy.mobile.Config;
import com.fuelbuddy.mobile.R;
import com.fuelbuddy.mobile.TrackLocationService;
import com.fuelbuddy.mobile.base.BaseActivity;
import com.fuelbuddy.mobile.base.Event;
import com.fuelbuddy.mobile.di.HasComponent;
import com.fuelbuddy.mobile.di.component.DaggerMapsComponent;
import com.fuelbuddy.mobile.di.component.MapsComponent;
import com.fuelbuddy.mobile.map.event.LocationUpdateEvent;
import com.fuelbuddy.mobile.map.event.MissingLocationEvent;
import com.fuelbuddy.mobile.map.event.OnPriceClickEvent;
import com.fuelbuddy.mobile.map.event.UpdateResponseEvent;
import com.fuelbuddy.mobile.map.fragment.DetailInfoFragment;
import com.fuelbuddy.mobile.map.fragment.MapFragment;
import com.fuelbuddy.mobile.map.fragment.PriceListFragment;
import com.fuelbuddy.mobile.map.presenter.MapMainPresenter;
import com.fuelbuddy.mobile.map.view.MapMvpView;
import com.fuelbuddy.mobile.model.GasStationModel;
import com.fuelbuddy.mobile.util.AnimationHelper;
import com.fuelbuddy.mobile.util.DialogFactory;
import com.fuelbuddy.mobile.util.PermissionsUtils;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.model.LatLng;
import com.gun0912.tedpermission.PermissionListener;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import hugo.weaving.DebugLog;
import me.zhanghai.android.materialprogressbar.MaterialProgressBar;

public class MapsMainActivity extends BaseActivity implements GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener, MapMvpView, MapFragment.Callbacks, PriceListFragment.OnStationClickListener, Dialog.OnClickListener, HasComponent<MapsComponent> {
    private static final String TAG = MapsMainActivity.class.getCanonicalName();

    @Inject
    public MapMainPresenter mMapPresenter;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    /*@BindView(R.id.progressView)
    MaterialProgressBar materialProgressBar;*/

    private GoogleApiClient mGoogleApiClient;
    private MapsComponent mMapsComponent;
    protected MapFragment mMapFragment;
    protected PriceListFragment mPriceListFragment;
    protected DetailInfoFragment mDetailInfoFragment;
    private LatLng mCurrentPositionLatLng;
    private FuelPriceMode fuelPriceMode;

    private LatLng fakeCurrentPositionLatLng = new LatLng(Double.valueOf("55.68288039999999"), Double.valueOf("12.369392699999935"));



    public static Intent getCallingIntent(Context context) {
        return new Intent(context, MapsMainActivity.class);
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
        this.initializeInjector();
        ButterKnife.bind(this);
        setToolbar();
        mMapPresenter.attachView(this);
        connectGoogleApiClient();
        PermissionsUtils.initPermission(this, permissionlistener,
                Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION);

    }

    PermissionListener permissionlistener = new PermissionListener() {
        @Override
        public void onPermissionGranted() {
            connectGoogleApiClient();
        }

        @Override
        public void onPermissionDenied(ArrayList<String> deniedPermissions) {
            Toast.makeText(MapsMainActivity.this, "Permission Denied\n" + deniedPermissions.toString(), Toast.LENGTH_SHORT).show();
        }
    };

    private void setToolbar() {
        setSupportActionBar(mToolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        if (mMapFragment == null) {
            initMapFragment();
        }
        if (mPriceListFragment == null) {
            initPriceListFragment();
        }
        if (mDetailInfoFragment == null) {
            initDetailInfoFragment();
        }
    }

    private void initDetailInfoFragment() {
        mDetailInfoFragment = DetailInfoFragment.newInstance();
        addFragment(R.id.fragment_container_map_info, mDetailInfoFragment);
    }

    private void initPriceListFragment() {
        FuelPriceMode fuelPriceMode = getFuelType();
        mPriceListFragment = PriceListFragment.newInstance(fuelPriceMode);
        addFragment(R.id.fragment_price_container_map, mPriceListFragment);
    }

    private FuelPriceMode getFuelType() {
        return (FuelPriceMode) getIntent().getSerializableExtra(Config.FUEL_TYPE);
    }

    private void initMapFragment() {
        FuelPriceMode fuelPriceMode = getFuelType();
        mMapFragment = MapFragment.newInstance(fuelPriceMode);
        addFragment(R.id.fragment_container_map, mMapFragment);
    }

    @Subscribe
    public void onEventMainThread(Event event) {

        if (event instanceof LocationUpdateEvent) {
            this.mCurrentPositionLatLng = ((LocationUpdateEvent) event).getLatLng();
            mMapPresenter.submitSearch(mCurrentPositionLatLng, getFuelType());
            //mMapPresenter.submitSearch(fakeCurrentPositionLatLng,getFuelType());
        }

       else if (event instanceof MissingLocationEvent) {
            DialogFactory.createErrorDialog(this, this).show();
        }
       else if (event instanceof UpdateResponseEvent) {
            DialogFactory.createSimpleSnackBarInfo(mToolbar, event.getMessage());
            mMapPresenter.updateMapView(mCurrentPositionLatLng, getFuelType());
        }
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

    @Override
    public void onStart() {
        super.onStart();
        Log.d(TAG, "onStart: ");
        EventBus.getDefault().register(this);
    }

    @Override
    public void onStop() {
        EventBus.getDefault().unregister(this);
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        stopTracking();
        super.onDestroy();
    }

    private void connectGoogleApiClient() {
        if (mGoogleApiClient == null) {
            if (createGoogleApiClient() != ConnectionResult.SUCCESS) {
                return;
            }
        }
        if (!(mGoogleApiClient.isConnected() || mGoogleApiClient.isConnecting())) {
            mGoogleApiClient.connect();
        } else {
            startTrackLocationService();
        }
    }

    private int createGoogleApiClient() {
        int status = GooglePlayServicesUtil.isGooglePlayServicesAvailable(this);
        switch (status) {
            case ConnectionResult.SUCCESS:
                mGoogleApiClient = buildGoogleClientApi();
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
        Log.d(TAG, "stopTracking: ");
        stopService(new Intent(this, TrackLocationService.class));
    }

    @Override
    public void showGasStations(List<GasStationModel> gasStationModelList) {

        if (!gasStationModelList.isEmpty()) {
            mPriceListFragment.showFuelPriceBars(gasStationModelList);
            mMapFragment.showGasStationPositions(gasStationModelList);
        } else {
            //mMapFragment.showClientPositions(mCurrentPositionLatLng);
            //mMapFragment.showClientPositions(fakeCurrentPositionLatLng);
            showError(getString(R.string.error_massage_no_gas_stations));
        }
    }

    @Override
    public void onClick(DialogInterface dialog, int which) {
        Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
        startActivity(intent);
    }

    @Override
    public void showSuccessMessage(String message) {
        // DialogFactory.createSimpleSnackBarInfo(fuelPriceHolderView, message);
    }

    @Override
    public void refreshFuelPrices(FuelPriceMode fuelPriceMode) {
        mMapPresenter.getUpdatedFuelPrices(mCurrentPositionLatLng,fuelPriceMode);
    }

    @DebugLog
    @Override
    public void showLoading() {
        progressDialog.show();
    }

    @DebugLog
    @Override
    public void hideLoading() {
        progressDialog.hide();
    }

    @DebugLog
    @Override
    public void showError(String message) {
        DialogFactory.createSimpleSnackBarInfo(mToolbar, message);
    }

    @Override
    public void logOut() {
    }

    @Override
    public Context context() {
        return this.getApplicationContext();
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {

    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    @Override
    public void onInfoHide() {
        if (mDetailInfoFragment != null) {
            mDetailInfoFragment.hide();
        }
    }

    @Override
    public void onInfoShow(GasStationModel gasStationModel) {
        if (mDetailInfoFragment != null) {
            mDetailInfoFragment.showTitleOnly(gasStationModel);
        }
    }

    @Override
    public MapsComponent getComponent() {
        return mMapsComponent;
    }

    @Override
    public void onMarkerClick(String selectedStationId) {
        EventBus.getDefault().post(new OnPriceClickEvent(selectedStationId));

    }
}
