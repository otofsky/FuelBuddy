package com.fuelbuddy.mobile.home.fuelSelection;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.fuelbuddy.mobile.Constants;
import com.fuelbuddy.mobile.R;
import com.fuelbuddy.mobile.base.BaseFragment;
import com.fuelbuddy.mobile.di.component.HomeComponent;
import com.fuelbuddy.mobile.home.HomeActivity;
import com.fuelbuddy.mobile.navigation.Navigator;
import com.fuelbuddy.mobile.util.AnimationHelper;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.internal.Utils;
import hugo.weaving.DebugLog;

/**
 * Created by zjuroszek on 14.11.16.
 */

public class FuelSelectionFragment extends BaseFragment implements FuelSelectionView {


    @Inject
    FuelSelectionPresenter mFuelSelectionPresenter;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.getComponent(HomeComponent.class).inject(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View fragmentView = inflater.inflate(R.layout.fragment_fuel_type, container, false);
        ButterKnife.bind(this, fragmentView);
        mFuelSelectionPresenter.attachView(this);
        return fragmentView;
    }


    @DebugLog
    @OnClick(R.id.fuelType92Btn)
    public void submitFuelType92() {
        Log.d("submitFuelType92", "submitFuelType92: ");
        // AnimationHelper.startAnimatedActivity(getActivity(), AnimationHelper.AnimationDirection.RIGHT_LEFT);
        Navigator.navigateToMapsActivity(getActivity(), Constants.FUEL_TYPE_92);
        //homePresenter.verifyCurrentUser();
    }

    @DebugLog
    @OnClick(R.id.fuelType95Btn)
    public void submitFuelType95() {
        Navigator.navigateToMapsActivity(getActivity(), Constants.FUEL_TYPE_95);
    }

    @DebugLog
    @OnClick(R.id.fuelTypeDieselBtn)
    public void submitFuelTypeDiesel() {
        mFuelSelectionPresenter.logout();
        //Navigator.navigateToMapsActivity(getActivity(),Constants.FUEL_TYPE_DIESEL);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu, menu);

    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle presses on the action bar items
        switch (item.getItemId()) {

            default:
                return super.onOptionsItemSelected(item);
        }//end switch
    }//end onOptionsItemSelected

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }

    @Override
    public void showRetry() {

    }

    @Override
    public void hideRetry() {

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
