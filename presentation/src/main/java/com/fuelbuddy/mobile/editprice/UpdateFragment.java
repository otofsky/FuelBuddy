package com.fuelbuddy.mobile.editprice;

import android.content.Context;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.fuelbuddy.mobile.Config;
import com.fuelbuddy.mobile.R;
import com.fuelbuddy.mobile.base.BaseFragment;
import com.fuelbuddy.mobile.di.component.MapsComponent;
import com.fuelbuddy.mobile.di.component.UpdateComponent;
import com.fuelbuddy.mobile.mapper.FuelMapper;
import com.fuelbuddy.mobile.model.GasStationModel;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by zjuroszek on 25.12.16.
 */

public class UpdateFragment extends BaseFragment implements UpdateView {

    UpdatePresenter mPresenter;

    @BindView(R.id.fuelInput92)
    EditText fuelInput92;
    @BindView(R.id.fuelInput95)
    EditText fuelInput95;
    @BindView(R.id.fuelDiesel)
    EditText fuelDiesel;


    LinearLayout fuelPricePanel;
    GasStationModel gasStationModel;
    FuelMapper fuelMapper;


    public static UpdateFragment newInstance(GasStationModel gasStationModel) {
        UpdateFragment fragment = new UpdateFragment();
        Bundle arguments = new Bundle();
        arguments.putParcelable(Config.GAS_STATION_DETAIL, gasStationModel);
        fragment.setArguments(arguments);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.getComponent(UpdateComponent.class).inject(this);
        mPresenter.attachView(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View fragmentView = inflater.inflate(R.layout.fragment_edit_gas_station, container, false);
        ButterKnife.bind(this, fragmentView);
        fuelMapper = new FuelMapper();
        initData();
        return fragmentView;
    }

    public void initData() {
        Bundle data = getArguments();
        if (data != null) {
            gasStationModel = data.getParcelable(Config.GAS_STATION_DETAIL);
        }
    }

    @Override
    public void showCamera() {

    }

    @Override
    public void updatePrice() {
        fuelInput92.getText();
        fuelInput95.getText();
        fuelDiesel.getText();
        //mPresenter.updateFuelPrices();

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

    @Override
    public Context context() {
        return null;
    }
}



