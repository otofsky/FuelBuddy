package com.fuelbuddy.mobile.home.fuelSelection;

import android.app.ProgressDialog;
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

import com.fuelbuddy.mobile.R;
import com.fuelbuddy.mobile.base.BaseFragment;
import com.fuelbuddy.mobile.di.component.HomeComponent;
import com.fuelbuddy.mobile.home.LoginViewEvent;
import com.fuelbuddy.mobile.map.FuelPriceMode;
import com.fuelbuddy.mobile.navigation.Navigator;
import com.fuelbuddy.mobile.util.DialogFactory;

import org.greenrobot.eventbus.EventBus;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.OnClick;
import hugo.weaving.DebugLog;

/**
 * Created by zjuroszek on 14.11.16.
 */

public class FuelSelectionFragment extends BaseFragment implements FuelSelectionView {

    @Inject
    FuelSelectionPresenter mFuelSelectionPresenter;

    ProgressDialog progressDialog;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.getComponent(HomeComponent.class).inject(this);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View fragmentView = inflater.inflate(R.layout.fragment_fuel_type, container, false);
        ButterKnife.bind(this, fragmentView);
        mFuelSelectionPresenter.attachView(this);
        return fragmentView;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override public void onDestroy() {
        super.onDestroy();
        this.mFuelSelectionPresenter.detachView();
    }


    @DebugLog
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle presses on the action bar items
        switch (item.getItemId()) {
            case R.id.actionLogOut:
                mFuelSelectionPresenter.logout();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @DebugLog
    @OnClick(R.id.fuelType92Btn)
    public void submitFuelType92() {
        // AnimationHelper.startAnimatedActivity(getActivity(), AnimationHelper.AnimationDirection.RIGHT_LEFT);
        Navigator.navigateToMapsActivity(getActivity(), FuelPriceMode.BENZIN_92);
        //homePresenter.verifyCurrentUser();
    }

    @DebugLog
    @OnClick(R.id.fuelType95Btn)
    public void submitFuelType95() {
        Navigator.navigateToMapsActivity(getActivity(), FuelPriceMode.BENZIN_95);
    }

    @DebugLog
    @OnClick(R.id.fuelTypeDieselBtn)
    public void submitFuelTypeDiesel() {
        // mFuelSelectionPresenter.logout();
        Navigator.navigateToMapsActivity(getActivity(), FuelPriceMode.DIESEL);
    }

    @Override
    public void showLoading() {
        progressDialog = DialogFactory.createProgressDialog(getActivity(), "Wylogowywanie");
        progressDialog.show();
    }

    @Override
    public void hideLoading() {
        if (progressDialog != null) {
            progressDialog.hide();
        }
    }

    @Override
    public void logOut() {
        EventBus.getDefault().post(new LoginViewEvent());
    }


    @Override
    public void showError(String message) {

    }

    @Override
    public Context context() {
        return null;
    }
}
