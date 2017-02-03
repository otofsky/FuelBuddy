package com.fuelbuddy.mobile.map.adapter;

import android.content.Context;

import com.fuelbuddy.mobile.map.GenericCustomListAdapter;
import com.fuelbuddy.mobile.model.GasStationModel;


/**
 * Created by zjuroszek on 08.07.16.
 */
public class GasStationAdapter extends GenericCustomListAdapter<GasStationModel> {

    public GasStationAdapter(ListItemInflater<GasStationModel> listItemInflater, Context context) {
        super(listItemInflater, context);
    }
}
