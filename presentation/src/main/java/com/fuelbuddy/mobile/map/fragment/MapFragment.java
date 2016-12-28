package com.fuelbuddy.mobile.map.fragment;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.fuelbuddy.data.FuelPricesUpdate;
import com.fuelbuddy.mobile.R;
import com.fuelbuddy.mobile.map.FuelPriceUpdate;
import com.fuelbuddy.mobile.map.listener.OnFuelPriceClickListener;
import com.fuelbuddy.mobile.map.presenter.MapPresenter;
import com.fuelbuddy.mobile.map.controller.MapController;
import com.fuelbuddy.mobile.map.controller.MapInterface;
import com.fuelbuddy.mobile.map.view.MapMvpView;
import com.fuelbuddy.mobile.model.GasStationModel;
import com.fuelbuddy.mobile.util.DialogFactory;
import com.fuelbuddy.mobile.util.MapUtil;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;

import java.util.List;

import javax.inject.Inject;

import hugo.weaving.DebugLog;


/**
 * Created by zjuroszek on 25.12.16.
 */

public class MapFragment extends com.google.android.gms.maps.SupportMapFragment implements MapMvpView,
        GoogleMap.OnMapClickListener, OnMapReadyCallback,
        GoogleMap.OnCameraChangeListener, MapController.OnMarkerClickCallback, Dialog.OnClickListener {


    @Override
    public Context context() {
        return null;
    }

    public interface Callbacks {

        void onInfoHide();

        void onInfoShow(GasStationModel gasStationModel);

    }

    private String selectedIdStation;

    private Callbacks mCallbacks;

    @Inject
    public MapPresenter mapPresenter;


    private MapInterface mapController;

    private GoogleApiClient googleApiClient;

    View mapView;

    public static MapFragment newInstance(String highlightedRoomId) {
        MapFragment fragment = new MapFragment();

        Bundle arguments = new Bundle();
        arguments.putString("", highlightedRoomId);
        fragment.setArguments(arguments);

        return fragment;
    }

    public static MapFragment newInstance(Bundle savedState) {
        MapFragment fragment = new MapFragment();
        fragment.setArguments(savedState);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // get DPI
        //mDPI = getActivity().getResources().getDisplayMetrics().densityDpi / 160f;

        // Get the arguments and restore the highlighted room or displayed floor.
        Bundle data = getArguments();
        if (data != null) {
           /* mHighlightedRoomId = data.getString(EXTRAS_HIGHLIGHT_ROOM, null);
            mInitialFloor = data.getInt(EXTRAS_ACTIVE_FLOOR, VENUE_DEFAULT_LEVEL_INDEX);*/
        }

        getMapAsync(this);
        this.mapController = new MapController();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mapView = super.onCreateView(inflater, container, savedInstanceState);

        //setMapInsets(mMapInsets);

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
    }

    private void updateFuelPrices(GasStationModel gasStationModel) {

        mapController.clear();
       /* mapController.showUserCurrentPosition(currentPositionLatLng);*/
        mapPresenter.updateFuelPrices(new FuelPricesUpdate(gasStationModel.getGasStationId(), "1", 1.64000, 1.87000, 1.87000));
    }

    @DebugLog
    @Override
    public void onMapReady(GoogleMap googleMap) {
        googleMap.setOnMapClickListener(this);
        mapController.initMap(getActivity(), googleMap, this);
    }


    public void loadGasStationPositions(List<GasStationModel> gasStationModelList) {
        mapController.clear();
        mapController.seFuelStationsPositions(gasStationModelList, "");
    }

    @Override
    public void onCameraChange(CameraPosition cameraPosition) {

    }

    @Override
    public void onMapClick(LatLng latLng) {
        mCallbacks.onInfoHide();
        mapController.seFuelStationsPositions("");
    }

    @Override
    public void onClick(DialogInterface dialogInterface, int i) {

    }

    @Override
    public void onMarkerClick(GasStationModel gasStationModel) {
        mapController.centerOnGasStation(true, MapUtil.getLatLng(gasStationModel));
        mCallbacks.onInfoShow(gasStationModel);
    }

    @Override
    public void showFuelPriceBars(List<GasStationModel> gasStationModelList) {

    }

    @Override
    public void showSuccessMessage(String message) {

    }

    @Override
    public void refreshFuelPrices() {

    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }

    @Override
    public void showError(String message) {

    }

    @Override
    public void logOut() {

    }
}
