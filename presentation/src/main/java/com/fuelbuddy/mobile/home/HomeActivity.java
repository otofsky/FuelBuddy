package com.fuelbuddy.mobile.home;

import android.os.Bundle;
import android.widget.Toast;

import com.fuelbuddy.mobile.R;
import com.fuelbuddy.mobile.base.BaseActivity;
import com.fuelbuddy.mobile.di.component.DaggerHomeComponent;

import com.fuelbuddy.mobile.di.component.HomeComponent;

import com.fuelbuddy.mobile.home.fuelSelection.FuelTypeFragment;
import com.fuelbuddy.mobile.home.login.LoginFragment;
import com.google.android.gms.common.api.GoogleApiClient;

import javax.inject.Inject;

import butterknife.ButterKnife;

/**
 * Created by zjuroszek on 07.10.16.
 */
public class HomeActivity extends BaseActivity implements HomeView, LoginFragment.FragmentNavigator {

    private GoogleApiClient mGoogleApiClient;
    private static final int RC_SIGN_IN = 007;
    @Inject
    public  HomePresenter homePresenter;
    private HomeComponent homeComponent;
    //sign_in_button
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        this.initializeInjector();
        homePresenter.attachView(this);
        homePresenter.verifyCurrentUser();
        ButterKnife.bind(this);
    }


    private void initializeInjector() {
        this.homeComponent = DaggerHomeComponent.builder()
                .applicationComponent(getApplicationComponent())
                .activityModule(getActivityModule())
                .build();
        homeComponent.inject(this);
    }


    @Override
    public void navigateToHome() {
        addFragment(R.id.fragmentContainer, new FuelTypeFragment());
    }


    @Override
    public void showMap() {

    }

    @Override
    public void showInfo(String userId) {
        Toast.makeText(this,  userId , Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showLoginView() {
        addFragment(R.id.fragmentContainer, new LoginFragment());
    }

    @Override
    public void showFuelTypeView() {
        addFragment(R.id.fragmentContainer, new FuelTypeFragment());
    }


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
}
