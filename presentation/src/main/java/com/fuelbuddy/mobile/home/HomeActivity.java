package com.fuelbuddy.mobile.home;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.fuelbuddy.mobile.R;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.OnClick;

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


    @OnClick(R.id.fuelType92Btn)
    public void submitFuelType92() {


    }

    @OnClick(R.id.fuelType95Btn)
    public void submitFuelType95() {

    }

    @OnClick(R.id.fuelTypeDieselBtn)
    public void submitFuelTypeDiesel() {

    }


}
