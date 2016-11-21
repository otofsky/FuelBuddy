package com.fuelbuddy.mobile.home.fuelSelection;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.fuelbuddy.mobile.Constants;
import com.fuelbuddy.mobile.R;
import com.fuelbuddy.mobile.base.BaseFragment;
import com.fuelbuddy.mobile.home.HomeActivity;
import com.fuelbuddy.mobile.navigation.Navigator;

import butterknife.ButterKnife;
import butterknife.OnClick;
import hugo.weaving.DebugLog;

/**
 * Created by zjuroszek on 14.11.16.
 */

public class FuelTypeFragment extends BaseFragment {

    @Override public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                       Bundle savedInstanceState) {
        final View fragmentView = inflater.inflate(R.layout.fragment_fuel_type, container, false);
        ButterKnife.bind(this, fragmentView);
        return fragmentView;
    }


    @DebugLog
    @OnClick(R.id.fuelType92Btn)
    public void submitFuelType92() {
        Log.d("submitFuelType92", "submitFuelType92: ");
        Navigator.navigateToMapsActivity(getActivity(), Constants.FUEL_TYPE_92);
        //homePresenter.verifyCurrentUser();
    }

    @DebugLog
    @OnClick(R.id.fuelType95Btn)
    public void submitFuelType95() {
        Navigator.navigateToMapsActivity(getActivity(),Constants.FUEL_TYPE_95);
    }

    @DebugLog
    @OnClick(R.id.fuelTypeDieselBtn)
    public void submitFuelTypeDiesel() {
       Navigator.navigateToMapsActivity(getActivity(),Constants.FUEL_TYPE_DIESEL);
    }
}
