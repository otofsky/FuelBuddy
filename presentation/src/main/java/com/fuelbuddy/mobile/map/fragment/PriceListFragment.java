package com.fuelbuddy.mobile.map.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.fuelbuddy.mobile.Config;
import com.fuelbuddy.mobile.R;
import com.fuelbuddy.mobile.base.BaseFragment;
import com.fuelbuddy.mobile.map.FuelPriceMode;
import com.fuelbuddy.mobile.map.FuelPriceUpdate;
import com.fuelbuddy.mobile.map.controller.FuelPriceController;
import com.fuelbuddy.mobile.map.event.Event;
import com.fuelbuddy.mobile.map.event.LocationUpdateEvent;
import com.fuelbuddy.mobile.map.event.OnPriceClickEvent;
import com.fuelbuddy.mobile.map.listener.OnFuelPriceClickListener;
import com.fuelbuddy.mobile.map.view.PriceListMvpView;
import com.fuelbuddy.mobile.model.GasStationModel;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by zjuroszek on 25.12.16.
 */

public class PriceListFragment extends BaseFragment implements PriceListMvpView {

    FuelPriceController mFuelPriceController;
    @BindView(R.id.fuelPriceHolderView)
    LinearLayout fuelPriceHolderView;
    Unbinder mUnbinder;

    public static PriceListFragment newInstance(FuelPriceMode fuelPriceMode) {
        Bundle args = new Bundle();
        args.putSerializable(Config.FUEL_TYPE, fuelPriceMode);
        PriceListFragment fragment = new PriceListFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View fragmentView = inflater.inflate(R.layout.price_holder_fragment, container, false);
        mUnbinder = ButterKnife.bind(this, fragmentView);
        Bundle args = getArguments();
        FuelPriceMode fuelPriceMode = (FuelPriceMode) args.getSerializable(Config.FUEL_TYPE);
        if (fuelPriceMode != null) {
            mFuelPriceController = new FuelPriceController(getActivity(), fuelPriceHolderView, fuelPriceMode, mOnFuelPriceClickListener);
        }
        return fragmentView;
    }

    @Override
    public void onStart() {
        EventBus.getDefault().register(this);
        super.onStart();
    }

    @Override
    public void onStop() {
        EventBus.getDefault().unregister(this);
        super.onStop();
    }

    @Override public void onDestroyView() {
        super.onDestroyView();
        mUnbinder.unbind();
    }

    @Subscribe
    public void onEventMainThread(Event event) {
    }

    @Override
    public void showFuelPriceBars(List<GasStationModel> gasStationModelList) {
        mFuelPriceController.populateFuelPriceBarsSection(gasStationModelList);
    }

    OnFuelPriceClickListener mOnFuelPriceClickListener = new OnFuelPriceClickListener() {
        @Override
        public void onFuelPriceClick(GasStationModel gasStationModel, FuelPriceUpdate fuelPriceUpdate) {
         EventBus.getDefault().post(new OnPriceClickEvent(gasStationModel));
        }
    };


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

    @Override
    public Context context() {
        return null;
    }
}
