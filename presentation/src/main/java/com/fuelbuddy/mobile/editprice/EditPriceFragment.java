package com.fuelbuddy.mobile.editprice;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.fuelbuddy.data.FuelPricesUpdate;
import com.fuelbuddy.mobile.Config;
import com.fuelbuddy.mobile.R;
import com.fuelbuddy.mobile.base.BaseFragment;
import com.fuelbuddy.mobile.mapper.FuelMapper;
import com.fuelbuddy.mobile.model.FuelModel;
import com.fuelbuddy.mobile.model.GasStationModel;
import com.fuelbuddy.mobile.widget.PricePicker;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by zjuroszek on 25.12.16.
 */

public class EditPriceFragment extends BaseFragment implements EditPriceView {


    @BindView(R.id.fuel_price_panel)
    LinearLayout fuelPricePanel;

    GasStationModel gasStationModel;

    FuelMapper fuelMapper;


    public static EditPriceFragment newInstance(GasStationModel gasStationModel) {
        Log.d("Edit price", "newInstance: " + gasStationModel.toString());
        EditPriceFragment fragment = new EditPriceFragment();
        Bundle arguments = new Bundle();
        arguments.putParcelable(Config.GAS_STATION_DETAIL, gasStationModel);
        fragment.setArguments(arguments);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

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
        List<FuelModel> transferToFuelModelList = fuelMapper.transferToFuelModelList(gasStationModel);
        for (FuelModel fuelModel : transferToFuelModelList) {
         initializePricePicker(fuelModel);
        }
    }

    private void updateFuelPrices(GasStationModel gasStationModel) {
       // mapController.clear();
      //  mapPresenter.updateFuelPrices(new FuelPricesUpdate(gasStationModel.getGasStationId(), "1", 1.64000, 1.87000, 1.87000));
    }


    private void initializePricePicker(FuelModel fuelModel) {
 /*       Date date = null;
        if (this.pickerChild > 10) {
            return false;
        }
        IPrice price = iPump.getPrice(fuel.name());
        this.priceStatus.put(fuel.name(), price != null ? Status.UPDATED : Status.NEW);
        Double valueOf = price == null ? null : Double.valueOf(price.getValue());
        if (price != null) {
            date = price.getTimestamp();


        }*/

        LayoutInflater inflater = LayoutInflater.from(getActivity());
        PricePicker pricePicker= (PricePicker) inflater.inflate(R.layout.price_picker_view, null);
        pricePicker.init(fuelModel);
        fuelPricePanel.addView(pricePicker);
       // PricePicker pricePicker = (PricePicker) this.panel.getChildAt(this.pickerChild);
        if (pricePicker == null) {
            // return false;
        }
        boolean z2;
        //   this.pickerChild++;
        //pricePicker.init(fuelModel date, valueOf, z, this.helper.getContext().getSettings());
        //pricePicker.setListener(this);
       // pricePicker.setVisibility(0);
/*        if (price != null) {
            z2 = true;
        } else {
            z2 = false;
        }
        return z2;*/
    }


    @Override
    public void showCamera() {

    }

    @Override
    public void decreasePrice() {

    }

    @Override
    public void increasePrice() {
        //  Collections.sort();
    }

    @Override
    public void updatePrice() {

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



