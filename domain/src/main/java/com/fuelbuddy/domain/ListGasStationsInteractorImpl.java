package com.fuelbuddy.domain;

import com.fuelbuddy.data.GasStation;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

public class ListGasStationsInteractorImpl  {

    @Inject
    public ListGasStationsInteractorImpl() {
    }


    public List<GasStation> getListGasStations() {
       return new ArrayList<GasStation>();
    }
}


