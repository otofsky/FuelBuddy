package com.fuelbuddy.mobile.editprice;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.fuelbuddy.mobile.Config;
import com.fuelbuddy.mobile.R;
import com.fuelbuddy.mobile.base.BaseActivity;
import com.fuelbuddy.mobile.di.HasComponent;
import com.fuelbuddy.mobile.di.component.DaggerUpdateComponent;
import com.fuelbuddy.mobile.di.component.UpdateComponent;
import com.fuelbuddy.mobile.mapper.FuelMapper;
import com.fuelbuddy.mobile.model.GasStationModel;
import com.fuelbuddy.mobile.util.AnimationHelper;
import com.fuelbuddy.mobile.util.StringHelper;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import hugo.weaving.DebugLog;

/**
 * Created by zjuroszek on 07.10.16.
 */
public class UpdateActivity extends BaseActivity implements UpdateView, View.OnClickListener, HasComponent<UpdateComponent> {


    private static final int CAMERA_CAPTURE_VIDEO_REQUEST_CODE = 200;
    @Inject
    UpdatePresenter mPresenter;
    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.stationNameTv)
    TextView stationNameTv;

    @BindView(R.id.stationAddressTv)
    TextView stationAddressTv;

    @BindView(R.id.infoTv)
    TextView infoTv;

    @BindView(R.id.locationIconImg)
    ImageView locationIconImg;

    @BindView(R.id.fuelInput92)
    EditText fuelInput92;
    @BindView(R.id.fuelInput95)
    EditText fuelInput95;
    @BindView(R.id.fuelInputDiesel)
    EditText fuelInputDiesel;

    @BindView(R.id.openCameraBtn)
    FloatingActionButton openCameraBtn;


    GasStationModel gasStationModel;
    FuelMapper fuelMapper;

    String videoPath;
    Uri videoUri;


    private UpdateComponent mUpdateComponent;

    public static Intent getCallingIntent(Context context) {
        return new Intent(context, UpdateActivity.class);
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);
        ButterKnife.bind(this);
        openCameraBtn.setOnClickListener(this);
        initializeInjector();
        initPresenter();
        setToolbar();
        obtainData();
    }

    private void obtainData() {
        Intent i = getIntent();
        gasStationModel = (GasStationModel) i.getParcelableExtra(Config.GAS_STATION_DETAIL);
         stationNameTv.setText(gasStationModel.getGasStationName());
        // stationAddressTv;
         infoTv.setText("Optag pris");
    }




    private void initPresenter() {
        mPresenter.attachView(this);
    }

    private void setToolbar() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    private void initializeInjector() {
        this.mUpdateComponent = DaggerUpdateComponent.builder()
                .applicationComponent(getApplicationComponent())
                .activityModule(getActivityModule())
                .build();
        mUpdateComponent.inject(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.openCameraBtn:
                showCamera();
                break;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.send, menu);
        return true;
    }

    @DebugLog
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.actionUpdatePrice:
                //  mPresenter.updateFuelPrices(fuelInput92.getText().toString(), fuelInput95.getText().toString(), fuelInputDiesel.getText().toString());
                return true;
            case android.R.id.home:
                onBackPressed();
                AnimationHelper.startAnimatedActivity(this, AnimationHelper.AnimationDirection.LEFT_RIGHT);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }


    @Override
    public void showCamera() {
        openCamera();
    }

    private void openCamera() {
        Intent intent = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);
        startActivityForResult(intent, CAMERA_CAPTURE_VIDEO_REQUEST_CODE);
    }

    //   content://media/external/video/media/5490
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        String photoPath = "";
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == CAMERA_CAPTURE_VIDEO_REQUEST_CODE) {
                videoUri = data.getData();
                videoPath = videoUri.getPath();
            }
        }
    }

    @Override
    public void updatePrice() {
        String fuel92 = null, fuel95 = null, fuelDiesel = null;
        if (!StringHelper.isNullOrEmpty(fuelInput92.getText().toString())) {
            fuel92 = fuelInput92.getText().toString();
        }
        if (!StringHelper.isNullOrEmpty(fuelInput95.getText().toString())) {
            fuel95 = fuelInput95.getText().toString();
        }
        if (!StringHelper.isNullOrEmpty(fuelInputDiesel.getText().toString())) {
            fuelDiesel = fuelInputDiesel.getText().toString();
        }
        mPresenter.updateFuelPrices(fuel92,fuel95,fuelDiesel);
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

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }

    @Override
    public void showError(String message) {

    }

    @Override
    public void logOut() {

    }

    @Override
    public Context context() {
        return this.getApplicationContext();
    }

    @Override
    public UpdateComponent getComponent() {
        return mUpdateComponent;
    }


}
