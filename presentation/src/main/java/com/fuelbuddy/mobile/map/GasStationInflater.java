package com.fuelbuddy.mobile.map;

import android.content.Context;
import android.support.v7.widget.AppCompatButton;
import android.view.LayoutInflater;
import android.view.View;

import com.fuelbuddy.mobile.R;
import com.fuelbuddy.mobile.model.GasStationModel;



/**
 * Created by zjuroszek on 06.07.16.
 */
public class GasStationInflater implements GenericCustomListAdapter.ListItemInflater<GasStationModel> {
    private LayoutInflater inflater;
    Context context;

    public GasStationInflater(Context context) {
        this.context = context;
        inflater = LayoutInflater.from(context);
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
        initRelateOperationDataViews(item, holder);
        return convertView;
    }

    private void initRelateOperationDataViews(GasStationModel gasStationModel, ViewHolder viewHolder) {
        viewHolder.fuelPriceBtn.setText(gasStationModel.getGasStationName());
    }

    public static class ViewHolder {
        AppCompatButton fuelPriceBtn;

    }
}








