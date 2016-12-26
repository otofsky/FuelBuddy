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
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.fuelbuddy.data.FuelPricesUpdate;
import com.fuelbuddy.mobile.Config;
import com.fuelbuddy.mobile.R;
import com.fuelbuddy.mobile.TrackLocationService;
import com.fuelbuddy.mobile.base.BaseActivity;
import com.fuelbuddy.mobile.di.component.DaggerMapsComponent;
import com.fuelbuddy.mobile.di.component.MapsComponent;
import com.fuelbuddy.mobile.map.controller.FuelPriceController;
import com.fuelbuddy.mobile.map.controller.MapController;
import com.fuelbuddy.mobile.map.controller.MapInterface;
import com.fuelbuddy.mobile.map.event.LocationUpdateEvent;
import com.fuelbuddy.mobile.map.listener.OnFuelPriceClickListener;
import com.fuelbuddy.mobile.map.presenter.MapPresenter;
import com.fuelbuddy.mobile.map.view.MapMvpView;
import com.fuelbuddy.mobile.model.GasStationModel;
import com.fuelbuddy.mobile.util.AnimationHelper;
import com.fuelbuddy.mobile.util.DialogFactory;
import com.fuelbuddy.mobile.util.LocationUtil;
import com.fuelbuddy.mobile.util.PriceHelper;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
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

public class MapsActivity extends BaseActivity implements OnMapReadyCallback, MapMvpView, GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener, MapController.OnMarkerClickCallback, Dialog.OnClickListener {
    private static final String TAG = TrackLocationService.class.getCanonicalName();

    public static final String[] PERMISSIONS =
            new String[]{Manifest.permission.ACCESS_FINE_LOCATION};

    private static final int REQUEST_LOCATION_PERMISSION = 1;

    @Inject
    public MapPresenter mapPresenter;

    private MapInterface mapController;

    private GoogleApiClient googleApiClient;
    private MapsComponent mMapsComponent;

    @BindView(R.id.fuelPriceHolderView)
    LinearLayout fuelPriceHolderView;

    @BindView(R.id.view_progress)
    RelativeLayout progressView;

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.fab)
    FloatingActionButton fab;


    BottomSheetBehavior bottomSheetBehavior;
    View bottomSheet;

    FuelPriceController mFuelPriceController;
    private ArrayList<LatLng> listLatLng;
    private LatLng currentPositionLatLng;
    private String selectedIdStation;

    private LatLng fakeCurrentPositionLatLng = new LatLng(Double.valueOf("55.951869964599610"), Double.valueOf("8.514181137084961"));


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
        mFuelPriceController = new FuelPriceController(this, fuelPriceHolderView, fuelPriceMode, mOnFuelPriceClickListener);
        mapPresenter.attachView(this);
        connectGoogleApiClient();
        AnimationHelper.startAnimatedActivity(this, AnimationHelper.AnimationDirection.RIGHT_LEFT);
        bottomSheet = findViewById(R.id.bottom_sheet);
        bottomSheetBehavior = BottomSheetBehavior.from(bottomSheet);

        if (!LocationUtil.isLocationEnabled(MapsActivity.this)) {
            DialogFactory.createErrorDialog(MapsActivity.this, this).show();
        }

        new TedPermission(this)
                .setPermissionListener(permissionlistener)
                .setDeniedMessage("If you reject permission,you can not use this service\n\nPlease turn on permissions at [Setting] > [Permission]")
                .setPermissions(Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION)
                .check();
    }

    PermissionListener permissionlistener = new PermissionListener() {
        @Override
        public void onPermissionGranted() {

            Toast.makeText(MapsActivity.this, "Permission Granted", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onPermissionDenied(ArrayList<String> deniedPermissions) {
            Toast.makeText(MapsActivity.this, "Permission Denied\n" + deniedPermissions.toString(), Toast.LENGTH_SHORT).show();
        }
    };


    @Override
    public void onClick(DialogInterface dialogInterface, int i) {
        Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
        startActivity(intent);
    }

    private void setToolbar() {
        setSupportActionBar(toolbar);
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
        mapController.showUserCurrentPosition(currentPositionLatLng);
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
    public void onBackPressed() {
        if (bottomSheetBehavior.getState() != BottomSheetBehavior.STATE_HIDDEN) {
            bottomSheetBehavior.setHideable(true);
            bottomSheetBehavior.setState(BottomSheetBehavior.STATE_HIDDEN);
        } else {
            super.onBackPressed();
            AnimationHelper.startAnimatedActivity(this, AnimationHelper.AnimationDirection.LEFT_RIGHT);
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

    @DebugLog
    @Override
    public void onMapReady(GoogleMap googleMap) {
        this.mapController = new MapController();
        mapController.initMap(getApplicationContext(), googleMap, this);
    }

    @Override
    public void onMarkerClick(GasStationModel gasStationModel) {

        TextView gasStation = (TextView) findViewById(R.id.gasStationName);
        TextView fuelType92Tv = (TextView) findViewById(R.id.fuelType92Tv);
        TextView fuelType95Tv = (TextView) findViewById(R.id.fuelType95Tv);
        TextView fuelTypeDieselTv = (TextView) findViewById(R.id.fuelTypeDieselTv);
        //bottomSheetBehavior.setPeekHeight(peakView.getHeight());
        //peakView.requestLayout();

        fab.setVisibility(View.VISIBLE);

        gasStation.setText(gasStationModel.getGasStationName());

        fuelType92Tv.setText(PriceHelper.generateFuelPrice(Config.FUEL_TYPE_92, gasStationModel.getPrice92()));
        fuelType95Tv.setText(PriceHelper.generateFuelPrice(Config.FUEL_TYPE_95, gasStationModel.getPrice95()));
        fuelTypeDieselTv.setText(PriceHelper.generateFuelPrice(Config.FUEL_TYPE_DIESEL, gasStationModel.getPriceDiesel()));
        bottomSheetBehavior.setHideable(false);
        bottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);

      /*  DialogFactory.createSimpleOkDialog(this, marker.getTitle(), marker.getSnippet(), getString(R.string.dialog_navigation_button_txt), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Uri gmmIntentUri = Uri.parse(StringHelper.getNavigationUrl(marker.getPosition()));
                Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
                mapIntent.setPackage(Config.GOOGLE_MAP_PACKAGE);
                startActivity(mapIntent);
            }
        }).show();*/
    }

    @Override
    public void showFuelPriceBars(List<GasStationModel> gasStationModelList) {
        mFuelPriceController.populateFuelPriceBarsSection(gasStationModelList);
        mapController.clear();
        mapController.seFuelStationsPositions(gasStationModelList, selectedIdStation);
        //mapController.showUserCurrentPosition(currentPositionLatLng);

        hideLoading();
    }

    @Override
    public void showSuccessMessage(String message) {
        DialogFactory.createSimpleSnackBarInfo(fuelPriceHolderView, message);
    }

    @Override
    public void refreshFuelPrices() {
        mapPresenter.getUpdatedFuelPrices(currentPositionLatLng);
    }

    @DebugLog
    @Override
    public void showLoading() {
        this.progressView.setVisibility(View.VISIBLE);
        //ProgressHelper.animateDefault(progressView, true);
        // setProgressBarIndeterminateVisibility(true);
    }

    @DebugLog
    @Override
    public void hideLoading() {
        // this.progressView.setVisibility(View.GONE);
        //ProgressHelper.animateDefault(progressView, false)
        //setProgressBarIndeterminateVisibility(false);
    }

    @DebugLog
    @Override
    public void showError(String message) {
        DialogFactory.createSimpleSnackBarInfo(toolbar, message);
    }

    @Override
    public void logOut() {

    }

    @Override
    public Context context() {
        return this;
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

    OnFuelPriceClickListener mOnFuelPriceClickListener = new OnFuelPriceClickListener() {
        @Override
        public void onFuelPriceClick(GasStationModel gasStationModel, FuelPriceUpdate fuelPriceUpdate) {
            switch (fuelPriceUpdate) {
                case GREEN:
                    selectedIdStation = gasStationModel.getGasStationId();
                    mapController.clear();
                    mapController.showSelectedGasStation(selectedIdStation);
                    DialogFactory.createSimpleSnackBarInfo(toolbar, getString(R.string.update_unavailable));
                    break;
                case YELLOW:
                    selectedIdStation = gasStationModel.getGasStationId();
                    mapController.showSelectedGasStation(selectedIdStation);
                    updateFuelPrices(gasStationModel);
                    break;
                case RED:
                    selectedIdStation = gasStationModel.getGasStationId();
                    mapController.showSelectedGasStation(selectedIdStation);
                    updateFuelPrices(gasStationModel);
                    break;
            }
        }

    };


    private void updateFuelPrices(GasStationModel gasStationModel) {

        mapController.clear();
       /* mapController.showUserCurrentPosition(currentPositionLatLng);*/
        mapPresenter.updateFuelPrices(new FuelPricesUpdate(gasStationModel.getGasStationId(), "1", 1.64000, 1.87000, 1.87000));
    }


}
