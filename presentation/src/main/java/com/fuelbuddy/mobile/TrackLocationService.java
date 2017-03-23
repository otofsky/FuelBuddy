package com.fuelbuddy.mobile;

import android.Manifest;
import android.app.Service;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.preference.PreferenceManager;
import android.support.v4.app.ActivityCompat;
import android.util.Log;

import com.fuelbuddy.data.Response;
import com.fuelbuddy.data.UploadResponse;
import com.fuelbuddy.data.cache.SharePreferencesUserCacheImpl;
import com.fuelbuddy.data.entity.ResponseEntity;
import com.fuelbuddy.data.entity.mapper.EntityJsonMapper;
import com.fuelbuddy.data.entity.mapper.GasStationEntityDataMapper;
import com.fuelbuddy.data.entity.mapper.ResponseEntityMapper;
import com.fuelbuddy.data.net.ApiInvoker;
import com.fuelbuddy.data.net.RestApi;
import com.fuelbuddy.data.repository.GasStationDataRepository;
import com.fuelbuddy.data.repository.datasource.GasStationDataStore.GasStationStoreFactory;
import com.fuelbuddy.exception.DefaultErrorBundle;
import com.fuelbuddy.exception.ErrorBundle;
import com.fuelbuddy.interactor.DefaultObserver;
import com.fuelbuddy.mobile.di.component.ApplicationComponent;
import com.fuelbuddy.mobile.di.component.DaggerServiceComponent;
import com.fuelbuddy.mobile.di.component.ServiceComponent;
import com.fuelbuddy.mobile.di.module.ServiceModule;
import com.fuelbuddy.mobile.editprice.event.OnReturnToMapEvent;
import com.fuelbuddy.mobile.exeption.ErrorMessageFactory;
import com.fuelbuddy.mobile.map.event.LocationUpdateEvent;
import com.fuelbuddy.mobile.map.event.MissingLocationEvent;
import com.fuelbuddy.mobile.map.event.ResponseEvent;
import com.fuelbuddy.mobile.model.FuelPricesUpdateEntry;
import com.fuelbuddy.mobile.util.LocationUtil;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.model.LatLng;

import org.greenrobot.eventbus.EventBus;

import java.io.File;

import hugo.weaving.DebugLog;
import io.reactivex.ObservableSource;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;


public class TrackLocationService extends Service implements GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener, LocationListener {

    public static final String PARAM_SYNC_TYPE = "com.fuelbuddy.mobile.SYNC_TYPE";
    public static final String FUEL_PRICE_UPDATE_TASK = "FUEL_PRICE_UPDATE_TASK";
    public static final String VIDEO_FILE_TO_UPDATE = "VIDEO_FILE_TO_UPDATE";

    public static final int PARAM_UPDATE_FUEL = 1;


    //private ApiInvoker apiInvoker;


    private static boolean isServiceRunning;
    private static final String TAG = TrackLocationService.class.getCanonicalName();

    private GoogleApiClient googleApiClient;
    private AndroidApplication app;
    private SharePreferencesUserCacheImpl sharePreferencesUserCache;
    GasStationStoreFactory gasStationStoreFactory;

    ServiceComponent serviceComponent;

    GasStationDataRepository gasStationDataRepository;

    RestApi restApi;

    public static boolean isServiceRunning() {
        return isServiceRunning;
    }

    private static void setIsServiceRunning(boolean isServiceRunning) {
        TrackLocationService.isServiceRunning = isServiceRunning;
    }

    public TrackLocationService() {
    }


    @Override
    public void onCreate() {
        super.onCreate();
        Log.d(TAG, "onCreate");
        sharePreferencesUserCache = new SharePreferencesUserCacheImpl(this, PreferenceManager.getDefaultSharedPreferences(this), new EntityJsonMapper());
        gasStationStoreFactory = new GasStationStoreFactory(this, sharePreferencesUserCache, new ApiInvoker());
        gasStationDataRepository = new GasStationDataRepository(new GasStationEntityDataMapper(), new ResponseEntityMapper(), gasStationStoreFactory);
        //(ApiInvoker apiInvoker, Context context, GasStationEntityDataMapper gasStationEntityDataMapper

        app = (AndroidApplication) getApplication();
        createGoogleApiClient();
        connectGoogleApiClient();
        initializeInjector();
        if (gasStationDataRepository != null) {
            Log.d(TAG, "onCreate: gasStationDataRepository is not null");
        }
    }

    private void initializeInjector() {
        this.serviceComponent = DaggerServiceComponent.builder()
                .applicationComponent(getApplicationComponent())
                .serviceModule(new ServiceModule(this))
                .build();
        serviceComponent.inject(this);
    }


    protected ApplicationComponent getApplicationComponent() {
        return ((AndroidApplication) getApplication()).getApplicationComponent();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if (intent.getIntExtra(PARAM_SYNC_TYPE, 0) == PARAM_UPDATE_FUEL) {
            File videoFile = (File) intent.getSerializableExtra(VIDEO_FILE_TO_UPDATE);
            FuelPricesUpdateEntry fuelPricesUpdateEntry = (FuelPricesUpdateEntry) intent.getParcelableExtra(FUEL_PRICE_UPDATE_TASK);
            updateFuelStation(videoFile, fuelPricesUpdateEntry);
        } else {
            TrackLocationService.setIsServiceRunning(true);
        }
        return super.onStartCommand(intent, flags, startId);
    }

    private void updateFuelStation(File file, FuelPricesUpdateEntry fuelPricesUpdateEntry) {
        uploadVideoFile(file, fuelPricesUpdateEntry);
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                EventBus.getDefault().post(new OnReturnToMapEvent());
            }
        }, 1000);
    }

    private void uploadVideoFile(File file, final FuelPricesUpdateEntry fuelPricesUpdateEntry) {
        gasStationDataRepository.uploadVideo(file)
                .subscribe(new UpdateStationSubscriber());
    }

   /* private void uploadVideoFile(File file, final FuelPricesUpdateEntry fuelPricesUpdateEntry) {
        gasStationDataRepository.uploadVideo(file)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .flatMap(new Function<UploadResponse, ObservableSource<Response>>() {
                    @Override
                    public ObservableSource<Response> apply(UploadResponse uploadResponse) throws Exception {
                        return gasStationDataRepository.updateStation(
                                fuelPricesUpdateEntry.getStationID(),
                                fuelPricesUpdateEntry.getUserID(),
                                uploadResponse.getFileID(),
                                fuelPricesUpdateEntry.getPrice92(),
                                fuelPricesUpdateEntry.getPrice95(),
                                fuelPricesUpdateEntry.getPriceDiesel());
                    }
                })
                .subscribe(new UpdateStationSubscriber());
    }*/

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
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            Log.d(TAG, "\"  permision is not granted: ");
            return;
        } else {
            Log.d(TAG, "permision is granted");

            if (!LocationUtil.isLocationEnabled(getApplicationContext())) {
                EventBus.getDefault().post(new MissingLocationEvent());
            }
            LocationServices.FusedLocationApi.requestLocationUpdates(googleApiClient, locationRequest, this);
        }
    }


    private void stopLocationUpdates() {
        LocationServices.FusedLocationApi.removeLocationUpdates(
                googleApiClient, this);
    }

    @DebugLog
    private void updateLocationData(Location location) {
        Log.d(TAG, "updateLocationData: ");
        double latitude = location.getLatitude();
        double longitude = location.getLongitude();
        EventBus.getDefault().post(new LocationUpdateEvent(new LatLng(latitude, longitude)));
    }

    private void showErrorMessage(ErrorBundle errorBundle) {
        String errorMessage = ErrorMessageFactory.create(getApplicationContext(), errorBundle.getException());
        EventBus.getDefault().post(new ResponseEvent(null, errorMessage));
    }

    private final class UpdateStationSubscriber extends DefaultObserver<UploadResponse> {

        @DebugLog
        @Override
        public void onComplete() {
        }

        @DebugLog
        @Override
        public void onError(Throwable throwable) {
            showErrorMessage(new DefaultErrorBundle((Exception) throwable));
        }

        @DebugLog
        @Override
        public void onNext(UploadResponse responseEntity) {
            Log.d(TAG, "onNext:  "  +responseEntity.toString());
          //  EventBus.getDefault().post(new ResponseEvent(responseEntity.getCode(), responseEntity.getMessage()));
        }
    }
}
