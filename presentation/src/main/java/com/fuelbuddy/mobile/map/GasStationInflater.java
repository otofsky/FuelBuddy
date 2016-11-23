package com.fuelbuddy.mobile.map;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

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
            convertView = inflater.inflate(R.layout.dpsdk_payment_related_operation_view, null);
            holder = new ViewHolder();
            holder.fuelPriceTv = (TextView) convertView.findViewById(R.id.pro_relatedTransactions);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        initRelateOperationDataViews(item, holder);
        return convertView;
    }

    private void initRelateOperationDataViews(GasStationModel gasStationModel, ViewHolder viewHolder) {
        viewHolder.fuelPriceTv.setText(gasStationModel.getPrice95());
    }

    public static class ViewHolder {
        TextView fuelPriceTv;

    }
}








