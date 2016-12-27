package com.fuelbuddy.mobile.map.fragment;

import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.fuelbuddy.mobile.map.presenter.MapPresenter;
import com.fuelbuddy.mobile.map.controller.MapController;
import com.fuelbuddy.mobile.map.controller.MapInterface;
import com.fuelbuddy.mobile.model.GasStationModel;
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

public class MapFragment extends com.google.android.gms.maps.SupportMapFragment implements
        GoogleMap.OnMarkerClickListener, GoogleMap.OnMapClickListener, OnMapReadyCallback,
        GoogleMap.OnCameraChangeListener, MapController.OnMarkerClickCallback, Dialog.OnClickListener {


    public interface Callbacks {

        void onInfoHide();

        void onInfoShow();

    }

    private Callbacks mCallbacks;

    @Inject
    public MapPresenter mapPresenter;



    private MapInterface mapController;

    private GoogleApiClient googleApiClient;


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
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View mapView = super.onCreateView(inflater, container, savedInstanceState);

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


    @DebugLog
    @Override
    public void onMapReady(GoogleMap googleMap) {
        this.mapController = new MapController();
        mapController.initMap(getActivity(), googleMap, this);
    }


    public void loadGasStationPositions(List<GasStationModel> gasStationModelList){
        mapController.clear();
        mapController.seFuelStationsPositions(gasStationModelList, "");
    }

    @Override
    public void onCameraChange(CameraPosition cameraPosition) {

    }

    @Override
    public void onMapClick(LatLng latLng) {
        mCallbacks.onInfoHide();
    }

    @Override
    public boolean onMarkerClick(Marker marker) {
        mCallbacks.onInfoShow();
        return true;
    }


    @Override
    public void onClick(DialogInterface dialogInterface, int i) {

    }

    @Override
    public void onMarkerClick(GasStationModel gasStationModel) {

    }
}
