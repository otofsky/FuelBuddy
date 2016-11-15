package com.fuelbuddy.mobile.home;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import com.fuelbuddy.interactor.GetCurrentUser;
import com.fuelbuddy.mobile.R;
import com.fuelbuddy.mobile.base.BaseActivity;
import com.fuelbuddy.mobile.home.fragment.HomeFragment;
import com.fuelbuddy.mobile.home.fragment.LoginFragment;
import com.fuelbuddy.mobile.navigation.Navigator;
import com.google.android.gms.common.api.GoogleApiClient;

import butterknife.ButterKnife;
import butterknife.OnClick;
import hugo.weaving.DebugLog;

import static com.fuelbuddy.mobile.Constants.FUEL_TYPE_92;
import static com.fuelbuddy.mobile.Constants.FUEL_TYPE_95;
import static com.fuelbuddy.mobile.Constants.FUEL_TYPE_DIESEL;

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
       // homePresenter.attachView(this);
        ButterKnife.bind(this);
        if (savedInstanceState == null) {
            addFragment(R.id.fragmentContainer, new LoginFragment());
        }

    }


    @Override
    public void navigateToHome() {
        addFragment(R.id.fragmentContainer, new HomeFragment());
    }


    @Override
    public void showMap() {

    }

    @Override
    public void showInfo(String userId) {
        Toast.makeText(this,  userId , Toast.LENGTH_SHORT).show();
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
