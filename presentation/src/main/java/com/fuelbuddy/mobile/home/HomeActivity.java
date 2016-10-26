package com.fuelbuddy.mobile.home;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.fuelbuddy.mobile.R;
import com.fuelbuddy.mobile.navigation.Navigator;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.OnClick;
import hugo.weaving.DebugLog;

import static com.fuelbuddy.mobile.Constants.FUEL_TYPE_92;
import static com.fuelbuddy.mobile.Constants.FUEL_TYPE_95;
import static com.fuelbuddy.mobile.Constants.FUEL_TYPE_DIESEL;

/**
 * Created by zjuroszek on 07.10.16.
 */
public class HomeActivity extends AppCompatActivity {

    @Inject
    HomePresenter homePresenter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        ButterKnife.bind(this);
    }

    @DebugLog
    @OnClick(R.id.fuelType92Btn)
    public void submitFuelType92() {
        Navigator.navigateToMapsActivity(HomeActivity.this,FUEL_TYPE_92);
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


}
