package com.fuelbuddy.mobile.editprice;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.content.CursorLoader;
import android.support.v7.widget.Toolbar;
import android.util.Log;

import com.fuelbuddy.mobile.Config;
import com.fuelbuddy.mobile.R;
import com.fuelbuddy.mobile.base.BaseActivity;
import com.fuelbuddy.mobile.model.GasStationModel;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by zjuroszek on 07.10.16.
 */
public class EditPriceActivity extends BaseActivity {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    GasStationModel gasStationModel;

    public static Intent getCallingIntent(Context context) {
        return new Intent(context, EditPriceActivity.class);
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_price);
        Intent i = getIntent();
        gasStationModel = (GasStationModel) i.getParcelableExtra(Config.GAS_STATION_DETAIL);
        Log.d("Activity Edit price", "newInstance: " + gasStationModel.toString());
        ButterKnife.bind(this);
        setToolbar();
    }

    private void setToolbar() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

/*
    private String getRealPathFromURI(Uri contentUri) {
        // dla wszystkich telefonów oprócz Samsungów
        if (contentUri != null) {
            String[] proj = {MediaStore.Images.Media.DATA};
            CursorLoader cursorLoader = new CursorLoader(this, contentUri, proj, null, null, null);
            Cursor cursor = cursorLoader.loadInBackground();
            if (cursor != null) {
                int columnIndex = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
                cursor.moveToFirst();
                String result = cursor.getString(columnIndex);
                cursor.close();
                return result;
            } else {
                return contentUri.getPath();
            }
        }

    }*/

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        addFragment(R.id.fragmentContainer, EditPriceFragment.newInstance(gasStationModel));
    }

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
