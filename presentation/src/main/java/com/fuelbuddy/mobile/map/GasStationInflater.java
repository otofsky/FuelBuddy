package com.fuelbuddy.mobile.map;

import android.content.Context;
import android.support.v7.widget.AppCompatButton;
import android.view.LayoutInflater;
import android.view.View;

import com.fuelbuddy.mobile.R;
import com.fuelbuddy.mobile.model.GasStationModel;
import com.fuelbuddy.mobile.util.PriceHelper;


/**
 * Created by zjuroszek on 06.07.16.
 */

public class GasStationInflater implements GenericCustomListAdapter.ListItemInflater<GasStationModel> {
    private LayoutInflater inflater;
    private FuelPriceMode fuelPriceMode;
    private Context context;

    public GasStationInflater(Context context, FuelPriceMode fuelPriceMode) {
        this.context = context;
        inflater = LayoutInflater.from(this.context);
        this.fuelPriceMode = fuelPriceMode;
    }

    @Override
    public View getView(GasStationModel item, View convertView, int positionFlag) {
        final ViewHolder holder;
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.fuel_price_bar, null);
            holder = new ViewHolder();
            holder.fuelPriceBtn = (AppCompatButton) convertView.findViewById(R.id.fuelPriceView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        initFuelPriceDataViews(item, holder);
        return convertView;
    }

    private void initFuelPriceDataViews(GasStationModel gasStationModel, ViewHolder viewHolder) {
        switch (fuelPriceMode){
            case BENZIN_92:
                viewHolder.fuelPriceBtn.setText(PriceHelper.toFormattedPrice(gasStationModel.getPrice92(), "92 kr"));
                break;
            case BENZIN_95:
                viewHolder.fuelPriceBtn.setText(PriceHelper.toFormattedPrice(gasStationModel.getPrice95(), "95 kr"));
                break;
            case DIESEL:
                viewHolder.fuelPriceBtn.setText(PriceHelper.toFormattedPrice(gasStationModel.getPriceDiesel(), "diesel kr"));
                break;
        }
    }

    public static class ViewHolder {
        AppCompatButton fuelPriceBtn;
    }
}








