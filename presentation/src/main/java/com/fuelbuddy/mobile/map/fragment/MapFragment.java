package com.fuelbuddy.mobile.map.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.fuelbuddy.data.FuelPriceMode;
import com.fuelbuddy.mobile.Config;
import com.fuelbuddy.mobile.map.controller.FuelPriceController;
import com.fuelbuddy.mobile.map.controller.MapController;
import com.fuelbuddy.mobile.map.controller.MapInterface;
import com.fuelbuddy.mobile.base.Event;
import com.fuelbuddy.mobile.map.event.OnPriceClickEvent;
import com.fuelbuddy.mobile.model.GasStationModel;
import com.fuelbuddy.mobile.util.MapUtil;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.List;

import hugo.weaving.DebugLog;


/**
 * Created by zjuroszek on 25.12.16.
 */

public class MapFragment extends com.google.android.gms.maps.SupportMapFragment implements
        GoogleMap.OnMapClickListener, GoogleMap.OnMapLongClickListener, OnMapReadyCallback,
        GoogleMap.OnCameraChangeListener, MapController.OnMarkerClickCallback {

    public interface Callbacks {

        void onInfoHide();

        void onInfoShow(GasStationModel gasStationModel);

    }

    public String DEFAULT_PRICE_SELECTION = "";

    private Callbacks mCallbacks;
    private PriceListFragment.OnStationClickListener onStationClickListener;

    private MapInterface mapController;

    View mapView;


    public static MapFragment newInstance(FuelPriceMode fuelPriceMode) {
        MapFragment fragment = new MapFragment();
        Bundle arguments = new Bundle();
        arguments.putSerializable(Config.FUEL_TYPE, fuelPriceMode);
        fragment.setArguments(arguments);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getMapAsync(this);
        this.mapController = new MapController();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mapView = super.onCreateView(inflater, container, savedInstanceState);
        return mapView;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        if (!(activity instanceof Callbacks)) {
            throw new ClassCastException(
                    "Activity must implement fragment's callbacks.");
        }
        mCallbacks = (Callbacks) activity;
        onStationClickListener = (PriceListFragment.OnStationClickListener)activity;
    }

    @Override
    public void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    public void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }


    @DebugLog
    @Override
    public void onMapReady(GoogleMap googleMap) {
        googleMap.setOnMapClickListener(this);
        mapController.initMap(getActivity(), googleMap, this);
    }

    @Override
    public void onMapLongClick(LatLng latLng) {

    }

    public void showGasStationPositions(List<GasStationModel> gasStationModelList) {
        Bundle args = getArguments();
        FuelPriceMode fuelPriceMode = (FuelPriceMode) args.getSerializable(Config.FUEL_TYPE);
        if (fuelPriceMode != null) {
            mapController.clear();
            mapController.setFuelStationsPositions(gasStationModelList, fuelPriceMode);
        }
    }

    public void showClientPositions(LatLng latLng) {
        mapController.clear();
        mapController.setClientPositions(latLng);
    }

    @Override
    public void onCameraChange(CameraPosition cameraPosition) {

    }

    @Override
    public void onMapClick(LatLng latLng) {
        mCallbacks.onInfoHide();
        mapController.setFuelStationsPositions();
        onStationClickListener.onMarkerClick(DEFAULT_PRICE_SELECTION);
    }

    @Override
    public void onMarkerClick(GasStationModel gasStationModel) {
        mapController.centerOnPosition(true, MapUtil.getLatLng(gasStationModel.getGasStationLatitude(),gasStationModel.getGasStationLongitude()));
        mCallbacks.onInfoShow(gasStationModel);
        onStationClickListener.onMarkerClick(gasStationModel.getGasStationId());
        //pull out
    }

    @Subscribe
    public void onEventMainThread(Event event) {
        if (event instanceof OnPriceClickEvent) {
            GasStationModel gasStationModel = ((OnPriceClickEvent) event).getGasStationModel();
            mapController.centerOnPosition(true, MapUtil.getLatLng(gasStationModel.getGasStationLatitude(),gasStationModel.getGasStationLongitude()));
            mCallbacks.onInfoShow(gasStationModel);
        }
    }
}
