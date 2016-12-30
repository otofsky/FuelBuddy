package com.fuelbuddy.mobile.editprice;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import com.fuelbuddy.mobile.R;
import com.fuelbuddy.mobile.base.BaseActivity;

import org.greenrobot.eventbus.EventBus;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by zjuroszek on 07.10.16.
 */
public class EditPriceActivity extends BaseActivity {

    /*@BindView(R.id.toolbar)
    Toolbar toolbar;*/

    public static Intent getCallingIntent(Context context) {
        return new Intent(context, EditPriceActivity.class);
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_edit_gas_station);
        ButterKnife.bind(this);
       // setToolbar();
    }
/*
    private void setToolbar() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
    }*/

    @Override
    public void onStart() {
        super.onStart();
        //EventBus.getDefault().register(this);
    }

    @Override
    public void onStop() {
        super.onStop();
       // EventBus.getDefault().unregister(this);
    }

    @Override
    public void navigateToHomeActivity() {
    }
}
