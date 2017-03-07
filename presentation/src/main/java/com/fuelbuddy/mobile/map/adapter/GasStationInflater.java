package com.fuelbuddy.mobile.map.adapter;

import android.content.Context;
import android.support.v7.widget.AppCompatTextView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;

import com.fuelbuddy.data.FuelPriceMode;
import com.fuelbuddy.mobile.Config;
import com.fuelbuddy.mobile.R;
import com.fuelbuddy.mobile.map.FuelPriceUpdate;
import com.fuelbuddy.mobile.map.GenericCustomListAdapter;
import com.fuelbuddy.mobile.map.listener.OnFuelPriceClickListener;
import com.fuelbuddy.mobile.model.GasStationModel;
import com.fuelbuddy.mobile.util.DateHelper;
import com.fuelbuddy.mobile.util.PriceHelper;
import com.fuelbuddy.mobile.util.ResourcesHelper;
import com.fuelbuddy.mobile.util.StringHelper;
import com.fuelbuddy.mobile.util.ViewHelper;


/**
 * Created by zjuroszek on 06.07.16.
 */

public class GasStationInflater implements GenericCustomListAdapter.ListItemInflater<GasStationModel> {

    private LayoutInflater inflater;
    private FuelPriceMode fuelPriceMode;
    OnFuelPriceClickListener onFuelPriceClickListener;
    private Context context;
    String TAG = getClass().getName();


    public GasStationInflater(Context context, FuelPriceMode fuelPriceMode, OnFuelPriceClickListener onFuelPriceClickListener) {
        this.context = context;
        inflater = LayoutInflater.from(this.context);
        this.fuelPriceMode = fuelPriceMode;
        this.onFuelPriceClickListener = onFuelPriceClickListener;
    }

    @Override
    public View getView(GasStationModel item, View convertView, int positionFlag) {
        ViewHolder holder = null;
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.fuel_price_bar_benzin, null);
            holder = new ViewHolder();
            holder.fuelPriceBtn = (AppCompatTextView) convertView.findViewById(R.id.fuelPriceView);
            setBtnListener(holder.fuelPriceBtn, item);
            convertView.setTag(holder);

        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        initFuelPriceDataViews(item, holder);
        return convertView;
    }

    private void initFuelPriceDataViews(GasStationModel gasStationModel, ViewHolder viewHolder) {
        switch (fuelPriceMode) {
            case BENZIN_92:
                init92FuelPriceView(viewHolder, gasStationModel.getPrice92(),
                        PriceHelper.generateFuelPriceWithCurrency(Config.FUEL_TYPE_92,
                                gasStationModel.getPrice92()));
                setSetFuelColorState(gasStationModel.getTimeUpdated(), viewHolder.fuelPriceBtn);
                break;
            case BENZIN_95:
                init95FuelPriceView(viewHolder, gasStationModel.getPrice95(),
                        PriceHelper.generateFuelPriceWithCurrency(Config.FUEL_TYPE_95, gasStationModel.getPrice95()));
                setSetFuelColorState(gasStationModel.getTimeUpdated(), viewHolder.fuelPriceBtn);
                break;
            case DIESEL:
                initDieselFuelPriceView(viewHolder, gasStationModel.getPriceDiesel(),
                        PriceHelper.generateFuelPriceWithCurrency(Config.FUEL_TYPE_DIESEL,
                                gasStationModel.getPriceDiesel()));
                setSetFuelColorState(gasStationModel.getTimeUpdated(), viewHolder.fuelPriceBtn);
                break;
            default:
                Log.d(TAG, "invalid fuel type: ");
                break;
        }
    }


    private void setBtnListener(AppCompatTextView appCompatButton, final GasStationModel gasStationModel) {
        appCompatButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FuelPriceUpdate fuelPriceUpdate = isFuelPriceAvailableForUpdate(gasStationModel.getTimeUpdated());
                onFuelPriceClickListener.onFuelPriceClick(gasStationModel, fuelPriceUpdate);
            }
        });
    }

    private void initDieselFuelPriceView(ViewHolder viewHolder, String priceDiesel, String s) {
        if (!StringHelper.isNullOrEmpty(priceDiesel)) {
            viewHolder.fuelPriceBtn.setText(s);
        } else {
            ViewHelper.setVisible(viewHolder.fuelPriceBtn, false);
        }
    }

    private void init95FuelPriceView(ViewHolder viewHolder, String price95, String s) {
        if (!StringHelper.isNullOrEmpty(price95)) {
            viewHolder.fuelPriceBtn.setText(s);
        } else {
            ViewHelper.setVisible(viewHolder.fuelPriceBtn, false);
        }
    }

    private void init92FuelPriceView(ViewHolder viewHolder, String price92, String s) {
        if (!StringHelper.isNullOrEmpty(price92)) {
            viewHolder.fuelPriceBtn.setText(s);

        } else {
            ViewHelper.setVisible(viewHolder.fuelPriceBtn, false);
        }
    }

    public void setSetFuelColorState(String lastUpDatePrice, View view) {
        int numOfHours = DateHelper.isOlderThanData(lastUpDatePrice);
        if (numOfHours < 2) {
            view.setBackgroundDrawable(ResourcesHelper.getDrawable(context, R.drawable.button_green_right_rounded));
        } else if (numOfHours > 2 && numOfHours < 4) {
            view.setBackgroundDrawable(ResourcesHelper.getDrawable(context, R.drawable.button_yellow_right_rounded));
        } else {
            view.setBackgroundDrawable(ResourcesHelper.getDrawable(context, R.drawable.button_red_right_rounded));
        }
    }

    public FuelPriceUpdate isFuelPriceAvailableForUpdate(String lastUpDatePrice) {
        int numOfHours = DateHelper.isOlderThanData(lastUpDatePrice);
        if (numOfHours < 2) {
            return FuelPriceUpdate.GREEN;
        } else if (numOfHours > 2 && numOfHours < 4) {
            return FuelPriceUpdate.YELLOW;
        } else {
            return FuelPriceUpdate.RED;
        }
    }


    public static class ViewHolder {
        AppCompatTextView fuelPriceBtn;
    }
}








