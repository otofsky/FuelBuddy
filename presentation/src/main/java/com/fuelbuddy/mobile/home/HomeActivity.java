package com.fuelbuddy.mobile.home;

import android.os.Bundle;
import android.widget.Toast;

import com.fuelbuddy.interactor.GetCurrentUser;
import com.fuelbuddy.mobile.R;
import com.fuelbuddy.mobile.base.BaseActivity;
import com.fuelbuddy.mobile.home.fuelType.FuelTypeFragment;
import com.fuelbuddy.mobile.home.login.LoginFragment;
import com.google.android.gms.common.api.GoogleApiClient;

import butterknife.ButterKnife;

/**
 * Created by zjuroszek on 07.10.16.
 */
public class HomeActivity extends BaseActivity implements HomeMvpView, LoginFragment.FragmentNavigator {

    private GoogleApiClient mGoogleApiClient;
    private static final int RC_SIGN_IN = 007;

    private HomePresenter homePresenter;
    //sign_in_button
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        homePresenter = new HomePresenter(new GetCurrentUser());
        homePresenter.attachView(this);
        homePresenter.verifyCurrentUser();
        ButterKnife.bind(this);
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
