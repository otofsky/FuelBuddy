package com.fuelbuddy.mobile.map.controller;

import android.content.Context;
import android.view.ViewGroup;

import com.fuelbuddy.mobile.map.FuelPriceMode;
import com.fuelbuddy.mobile.map.adapter.GasStationAdapter;
import com.fuelbuddy.mobile.map.adapter.GasStationInflater;
import com.fuelbuddy.mobile.map.listener.OnFuelPriceClickListener;
import com.fuelbuddy.mobile.model.GasStationModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zjuroszek on 24.11.16.
 */

public class FuelPriceController {

    GasStationAdapter gasStationAdapter;
    List<GasStationModel> gasStationModelList = new ArrayList<>();
    private final Context context;
    ViewGroup view;

    public FuelPriceController(Context mapsActivity, ViewGroup view, FuelPriceMode fuelPriceMode, OnFuelPriceClickListener onFuelPriceClickListener) {
        this.context = mapsActivity;
        this.view = view;
        gasStationAdapter = new GasStationAdapter(new GasStationInflater(context,fuelPriceMode,onFuelPriceClickListener), context);
        initFuelPriceSection(this.gasStationModelList);
    }

    private void initFuelPriceSection(List<GasStationModel> gasStationModelList) {
        populateFuelPriceBarsSection(gasStationModelList);
    }

    public void populateFuelPriceBarsSection(List<GasStationModel> gasStationModels) {
        gasStationAdapter.clear();
        clearCurrentSectionTable();
        initAdapter(gasStationModels);
        initView();
        gasStationAdapter.notifyDataSetChanged();
    }
    private void initAdapter(List<GasStationModel> gasStationModels) {
        for (GasStationModel gasStationModel : gasStationModels) {
            gasStationAdapter.add(gasStationModel);
        }
    }
    private void initView() {
        view.removeAllViews();
        for (int i = 0; i < gasStationAdapter.getCount(); ++i) {
            view.addView(gasStationAdapter.getView(i, null, null));
        }
    }

    private void clearCurrentSectionTable() {
        int count = view.getChildCount();
        if (count > 1) {
            view.removeViews(0,count - 1);
        }
    }
}
