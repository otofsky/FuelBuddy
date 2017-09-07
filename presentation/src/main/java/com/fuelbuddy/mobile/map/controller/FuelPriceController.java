package com.fuelbuddy.mobile.map.controller;

import android.content.Context;
import android.util.Log;
import android.view.ViewGroup;
import android.widget.ListView;

import com.fuelbuddy.data.FuelPriceMode;
import com.fuelbuddy.mobile.map.GenericCustomListAdapter;
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

    GasStationAdapter mGasStationAdapter;
    List<GasStationModel> mGasStationModelList = new ArrayList<>();
    private final Context mContext;
    ViewGroup mView;

    public FuelPriceController(Context mapsActivity, ViewGroup mView, FuelPriceMode fuelPriceMode, OnFuelPriceClickListener onFuelPriceClickListener) {
        this.mContext = mapsActivity;
        this.mView = mView;
        mGasStationAdapter = new GasStationAdapter(new GasStationInflater(mContext, fuelPriceMode, onFuelPriceClickListener, onItemRefresh),mGasStationModelList, mContext);
        initFuelPriceSection(this.mGasStationModelList);
    }

    private void initFuelPriceSection(List<GasStationModel> gasStationModelList) {
        populateFuelPriceBarsSection(gasStationModelList);
    }

    public void populateFuelPriceBarsSection(List<GasStationModel> gasStationModels) {
        this.mGasStationModelList = gasStationModels;
        mGasStationAdapter.clear();
        clearCurrentSection();
        initAdapter(gasStationModels);
        initView();
        mGasStationAdapter.notifyDataSetChanged();
    }

    public void refreshFuelPriceBarsSection(String gasStationId) {
        refreshPriceBarView(gasStationId);
    }

    private void refreshPriceBarView(String gasStationId) {
        mGasStationAdapter.clear();
        mGasStationAdapter.setSelectedItem(gasStationId);
        clearCurrentSection();
        initAdapter(mGasStationModelList);
        initView();
        mGasStationAdapter.notifyDataSetChanged();
    }

    private void initAdapter(List<GasStationModel> gasStationModels) {
        for (GasStationModel gasStationModel : gasStationModels) {
            mGasStationAdapter.add(gasStationModel);
        }
    }

    private void initView() {
        mView.removeAllViews();
        for (int i = 0; i < mGasStationAdapter.getCount(); ++i) {
            mView.addView(mGasStationAdapter.getView(i, null, null));
        }
    }

    private void clearCurrentSection() {
        int count = mView.getChildCount();
        if (count > 1) {
            mView.removeViews(0, count - 1);
        }
    }

    private GenericCustomListAdapter.OnClickItemRefreshListener onItemRefresh = new GenericCustomListAdapter.OnClickItemRefreshListener() {
        @Override
        public void onItemRefresh(String gasStationId) {
            refreshFuelPriceBarsSection(gasStationId);
        }
    };
}
