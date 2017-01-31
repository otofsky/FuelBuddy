package com.fuelbuddy.mobile.editprice;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.content.CursorLoader;
import android.support.v7.widget.Toolbar;
import android.util.Log;
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
import com.fuelbuddy.mobile.model.GasStationModel;
import com.fuelbuddy.mobile.navigation.Navigator;
import com.fuelbuddy.mobile.util.AnimationHelper;
import com.fuelbuddy.mobile.util.FileUtils;
import com.fuelbuddy.mobile.util.PermissionsUtils;
import com.gun0912.tedpermission.PermissionListener;
import com.redmadrobot.inputmask.MaskedTextChangedListener;

import java.io.File;
import java.util.ArrayList;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
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
    TextView gasStationName;

    @BindView(R.id.stationAddressTv)
    TextView stationAddress;

    @BindView(R.id.infoTv)
    TextView info;

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

    String videoPath;
    Uri videoUri;

    Unbinder mUnbinder;


    private UpdateComponent mUpdateComponent;


    MaskedTextChangedListener.ValueListener valueListener = new MaskedTextChangedListener.ValueListener() {
        @Override
        public void onTextChanged(boolean maskFilled, @NonNull final String extractedValue) {
            Log.d(UpdateActivity.class.getSimpleName(), extractedValue);
            Log.d(UpdateActivity.class.getSimpleName(), String.valueOf(maskFilled));
        }
    };


    public static Intent getCallingIntent(Activity context) {
        return new Intent(context, UpdateActivity.class);
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);
        mUnbinder = ButterKnife.bind(this);
        openCameraBtn.setOnClickListener(this);
        initializeInjector();
        initPresenter();
        setToolbar();
        obtainData();
        init92PriceView();
        init95PriceView();
        initDieselPriceView();
    }

    private void init92PriceView() {
        final MaskedTextChangedListener listener = new MaskedTextChangedListener("[00].[00]", true, fuelInput92, null,valueListener);
        fuelInput92.addTextChangedListener(listener);
        fuelInput92.setOnFocusChangeListener(listener);
        fuelInput92.setHint(listener.placeholder());
        fuelInput92.setBackgroundResource(R.drawable.border_line_gray);
    }

    private void init95PriceView() {
        final MaskedTextChangedListener listener = new MaskedTextChangedListener("[00].[00]", true, fuelInput95, null,valueListener);
        fuelInput95.addTextChangedListener(listener);
        fuelInput95.setOnFocusChangeListener(listener);
        fuelInput95.setHint(listener.placeholder());
        fuelInput95.setBackgroundResource(R.drawable.border_line_gray);
    }

    private void initDieselPriceView() {
        final MaskedTextChangedListener listener = new MaskedTextChangedListener("[00].[00]", true, fuelInputDiesel, null,valueListener);
        fuelInputDiesel.addTextChangedListener(listener);
        fuelInputDiesel.setOnFocusChangeListener(listener);
        fuelInputDiesel.setHint(listener.placeholder());
        fuelInputDiesel.setBackgroundResource(R.drawable.border_line_gray);
    }


    private void obtainData() {
        Intent i = getIntent();
        gasStationModel = (GasStationModel) i.getParcelableExtra(Config.GAS_STATION_DETAIL);
        gasStationName.setText(gasStationModel.getCompanyName());
        stationAddress.setText(gasStationModel.getGasStationName());
        info.setText(R.string.map_direction_btn_text);
        info.setText(R.string.update_price_btn_text);
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
                PermissionsUtils.initPermission(this, permissionlistener,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE);
                break;
        }
    }

    PermissionListener permissionlistener = new PermissionListener() {
        @Override
        public void onPermissionGranted() {
            showCamera();
        }

        @Override
        public void onPermissionDenied(ArrayList<String> deniedPermissions) {
        }
    };

    @Override
    public void showCamera() {
        openCamera();
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
               File file  = FileUtils.getFile(this,videoUri);
                mPresenter.updateFuelPrices(file);
                /* mPresenter.updateFuelPrices(gasStationModel.getGasStationId(),
                         fuelInput92.getText().toString(), fuelInput95.getText().toString(),
                         fuelInputDiesel.getText().toString());*/
                return true;
            case android.R.id.home:
                onBackPressed();
                AnimationHelper.startAnimatedActivity(this, AnimationHelper.AnimationDirection.LEFT_RIGHT);
                return true;
          /*  case R.id.actionLogOut:
                mPresenter.logout();
                AnimationHelper.startAnimatedActivity(this, AnimationHelper.AnimationDirection.LEFT_RIGHT);
                return true;*/
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void openCamera() {
        Intent intent = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);
        startActivityForResult(intent, CAMERA_CAPTURE_VIDEO_REQUEST_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == CAMERA_CAPTURE_VIDEO_REQUEST_CODE) {
                videoUri = data.getData();
                getRealPathFromURI(videoUri);
            }
        }
    }

    private String getRealPathFromURI(Uri contentUri) {
        videoPath = "";
        if (contentUri != null) {
            String[] proj = {MediaStore.Images.Media.DATA};
            CursorLoader cursorLoader = new CursorLoader(this, contentUri, proj, null, null, null);
            Cursor cursor = cursorLoader.loadInBackground();
            if (cursor != null) {
                int columnIndex = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
                cursor.moveToFirst();
                videoPath = cursor.getString(columnIndex);
                cursor.close();
            } else {
                videoPath = contentUri.getPath();
            }
        }
        return videoPath;
    }


    @Override
    public void updatePrice() {

    }

    @Override
    public void show92Error() {
        fuelInput92.setBackgroundResource(R.drawable.border_line_red);
    }

    @Override
    public void show95Error() {
        fuelInput95.setBackgroundResource(R.drawable.border_line_red);
    }

    @Override
    public void showDieselError() {
        fuelInputDiesel.setBackgroundResource(R.drawable.border_line_red);
    }

    @Override
    public void showMap() {
        onBackPressed();
        AnimationHelper.startAnimatedActivity(this, AnimationHelper.AnimationDirection.LEFT_RIGHT);
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
    public void onDestroy() {
        super.onDestroy();
        mUnbinder.unbind();
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
        Navigator.navigateToHomeActivity(this);
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
