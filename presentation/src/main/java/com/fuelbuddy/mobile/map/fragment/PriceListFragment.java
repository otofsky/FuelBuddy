package com.fuelbuddy.mobile.map.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.fuelbuddy.mobile.R;
import com.fuelbuddy.mobile.base.BaseFragment;
import com.fuelbuddy.mobile.map.FuelPriceMode;
import com.fuelbuddy.mobile.map.FuelPriceUpdate;
import com.fuelbuddy.mobile.map.controller.FuelPriceController;
import com.fuelbuddy.mobile.map.listener.OnFuelPriceClickListener;
import com.fuelbuddy.mobile.map.view.PriceListMvpView;
import com.fuelbuddy.mobile.model.GasStationModel;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by zjuroszek on 25.12.16.
 */

public class PriceListFragment extends BaseFragment implements PriceListMvpView {

    FuelPriceController mFuelPriceController;
    @BindView(R.id.fuelPriceHolderView)
    LinearLayout fuelPriceHolderView;

    public static PriceListFragment newInstance() {
        PriceListFragment fragment = new PriceListFragment();
      //  fragment.setArguments(savedState);
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
        ButterKnife.bind(this, fragmentView);
        mFuelPriceController = new FuelPriceController(getActivity(), fuelPriceHolderView, FuelPriceMode.BENZIN_92, mOnFuelPriceClickListener);
        return fragmentView;
    }


    @Override
    public void showFuelPriceBars(List<GasStationModel> gasStationModelList) {
        mFuelPriceController.populateFuelPriceBarsSection(gasStationModelList);
    }

    @Override
    public void showPriceEditDialog() {

    }

    OnFuelPriceClickListener mOnFuelPriceClickListener = new OnFuelPriceClickListener() {
        @Override
        public void onFuelPriceClick(GasStationModel gasStationModel, FuelPriceUpdate fuelPriceUpdate) {
            switch (fuelPriceUpdate) {
                case GREEN:

                    break;
                case YELLOW:

                    break;
                case RED:

                    break;
            }
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