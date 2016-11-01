package com.fuelbuddy.domain;

import com.fuelbuddy.data.GasStation;

import java.util.ArrayList;
import java.util.List;

public class ListGasStationsInteractorImpl implements ListGasStationsInteractor {

    public ListGasStationsInteractorImpl() {
    }

    @Override
    public List<GasStation> getListGasStations() {
       return new ArrayList<GasStation>();
    }
}


