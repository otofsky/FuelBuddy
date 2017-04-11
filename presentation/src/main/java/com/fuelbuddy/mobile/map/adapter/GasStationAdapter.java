package com.fuelbuddy.mobile.map.adapter;

import android.content.Context;

import com.fuelbuddy.mobile.map.GenericCustomListAdapter;

import com.fuelbuddy.mobile.model.GasStationModel;

import java.util.List;


/**
 * Created by zjuroszek on 08.07.16.
 */
public class GasStationAdapter extends GenericCustomListAdapter<GasStationModel> {

    public GasStationAdapter(final ListItemInflater<GasStationModel> listItemInflater, final List<GasStationModel> list, Context context) {
        super(listItemInflater, context,list);
    }

    public void setSelectedItem(String itemId) {
        setSelectedId(itemId);
    }
}

