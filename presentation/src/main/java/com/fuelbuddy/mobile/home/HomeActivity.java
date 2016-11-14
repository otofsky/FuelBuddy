package com.fuelbuddy.mobile.home;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import com.fuelbuddy.mobile.R;
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
public class HomeActivity extends AppCompatActivity implements HomeMvpView {

    private GoogleApiClient mGoogleApiClient;
    private static final int RC_SIGN_IN = 007;
    //sign_in_button
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
       // homePresenter.attachView(this);
        ButterKnife.bind(this);



    }

    @DebugLog
    @OnClick(R.id.fuelType92Btn)
    public void submitFuelType92() {
        Log.d("submitFuelType92", "submitFuelType92: ");
        Navigator.navigateToMapsActivity(HomeActivity.this,FUEL_TYPE_92);
        //homePresenter.showInfo();
    }

    @DebugLog
    @OnClick(R.id.fuelType95Btn)
    public void submitFuelType95() {
        Navigator.navigateToMapsActivity(HomeActivity.this,FUEL_TYPE_95);
    }

    @DebugLog
    @OnClick(R.id.fuelTypeDieselBtn)
    public void submitFuelTypeDiesel() {
        Navigator.navigateToMapsActivity(HomeActivity.this,FUEL_TYPE_DIESEL);
    }



    @Override
    public void showMap() {

    }

    @Override
    public void showInfo() {
        Toast.makeText(this, "updateLocationData " , Toast.LENGTH_SHORT).show();
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
