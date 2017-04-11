package com.fuelbuddy.mobile.map.fragment;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.fuelbuddy.mobile.Config;
import com.fuelbuddy.mobile.R;
import com.fuelbuddy.mobile.base.BaseFragment;
import com.fuelbuddy.mobile.di.component.MapsComponent;
import com.fuelbuddy.mobile.map.presenter.DetailInfoPresenter;
import com.fuelbuddy.mobile.map.view.DetailInfoView;
import com.fuelbuddy.mobile.model.GasStationModel;
import com.fuelbuddy.mobile.navigation.Navigator;
import com.fuelbuddy.mobile.util.DateHelper;
import com.fuelbuddy.mobile.util.DialogFactory;
import com.fuelbuddy.mobile.util.MapUtil;
import com.fuelbuddy.mobile.util.Precision;
import com.fuelbuddy.mobile.util.StringHelper;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by zjuroszek on 25.12.16.
 */

public class DetailInfoFragment extends BaseFragment implements DetailInfoView, View.OnClickListener {
    @Inject
    DetailInfoPresenter mPresenter;

    private View mBottomSheet;

    private CoordinatorLayout mCoordinator;

    private BottomSheetBehavior mBehavior;

    private int mHeightDetail;

    @BindView(R.id.stationNameTv)
    TextView gasStationName;

    @BindView(R.id.distanceTv)
    TextView distance;

    @BindView(R.id.stationAddressTv)
    TextView stationAddress;

    @BindView(R.id.infoTv)
    TextView info;

    @BindView(R.id.locationIconImg)
    ImageView locationIconImg;

    @BindView(R.id.navigationBtn)
    FloatingActionButton navigateBtn;

    @BindView(R.id.updateBtn)
    Button updateBtn;

    GasStationModel mGasStationModel;

    Unbinder mUnbinder;

    ListView listView;


    public static DetailInfoFragment newInstance() {
        return new DetailInfoFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.getComponent(MapsComponent.class).inject(this);
        mPresenter.attachView(this);
        final Resources resources = getResources();
        mHeightDetail = resources.getDimensionPixelOffset(R.dimen.map_slideableinfo_height);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View fragmentView = inflater.inflate(R.layout.map_info_bottom, container, false);
        mUnbinder = ButterKnife.bind(this, fragmentView);
        navigateBtn.setOnClickListener(this);
        updateBtn.setOnClickListener(this);

/*        listView = (ListView) fragmentView.findViewById(R.id.list);

        String[] values = new String[] { "Android List View",
                "Adapter implementation",
                "Simple List View In Android",
                "Create List View Android",
                "Android Example",
                "List View Source Code",
                "List View Array Adapter",
                "Android Example List View"
        };
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, android.R.id.text1, values);


        // Assign adapter to ListView
        listView.setAdapter(adapter);*/
        return fragmentView;
    }

    @Override
    public void onViewCreated(final View view, final Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mCoordinator = (CoordinatorLayout) view.findViewById(R.id.map_coordinator);
        mBottomSheet = mCoordinator.findViewById(R.id.map_bottomsheet);
        mBehavior = BottomSheetBehavior.from(mBottomSheet);
        mBehavior.setBottomSheetCallback(mBottomSheetCallback);
        hide();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mUnbinder.unbind();
    }

    public void showTitleOnly(GasStationModel gasStationModel) {
        mGasStationModel = gasStationModel;
        initGasStationDetailViews(mGasStationModel);
        setCollapsedOnly();
    }

    private void initGasStationDetailViews(GasStationModel gasStationModel) {
        gasStationName.setText(gasStationModel.getCompanyName());
        stationAddress.setText(gasStationModel.getGasStationName());
        distance.setText(gasStationModel.getDistance());
        info.setText(R.string.map_direction_btn_text);

        if (isFuelPriceAvailableForUpdate(gasStationModel.getTimeUpdated())) {
            updateBtn.setEnabled(true);
            updateBtn.setBackgroundColor(getResources().getColor(R.color.app_green));
        } else {
            updateBtn.setEnabled(false);
            updateBtn.setBackgroundColor(getResources().getColor(R.color.gray));
            DialogFactory.createSimpleSnackBarInfo(mBottomSheet, getString(R.string.price_info_up_to_date));
        }
    }

    public boolean isFuelPriceAvailableForUpdate(String lastUpDatePrice) {
        int numOfHours = DateHelper.isOlderThanData(lastUpDatePrice);
        if (numOfHours < 2) {
            Log.d("is available", "isFuelPriceAvailableForUpdate: false");
            return false;
        } else if (numOfHours > 2 && numOfHours < 4) {
            Log.d("is available", "isFuelPriceAvailableForUpdate: true");
            return true;
        } else {
            Log.d("is available", "isFuelPriceAvailableForUpdate: true");
            return true;
        }
    }

    private void setCollapsedOnly() {
        mBehavior.setPeekHeight(mHeightDetail);
        mBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
        navigateBtn.show();
    }

    public void hide() {
        mBehavior.setState(BottomSheetBehavior.STATE_HIDDEN);
        navigateBtn.setVisibility(View.GONE);
    }

    private BottomSheetBehavior.BottomSheetCallback mBottomSheetCallback = new BottomSheetBehavior.BottomSheetCallback() {

        @Override
        public void onStateChanged(@NonNull final View bottomSheet, final int newState) {
            switch (newState) {
                case BottomSheetBehavior.STATE_COLLAPSED:
                    mCoordinator.requestLayout();
                    break;
                case BottomSheetBehavior.STATE_EXPANDED:
                    navigateBtn.setVisibility(View.VISIBLE);
                    break;
                case BottomSheetBehavior.STATE_HIDDEN:
                    navigateBtn.setVisibility(View.GONE);
                    break;
            }
        }

        @Override
        public void onSlide(@NonNull View bottomSheet, float slideOffset) {
        }
    };

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.navigationBtn:
                mPresenter.startNavigation();
                break;
            case R.id.updateBtn:
                mPresenter.startUpdate();
                break;
        }
    }

    @Override
    public void showNavigationView() {
        Uri gmmIntentUri = Uri.parse(StringHelper.getNavigationUrl(MapUtil.getLatLng(mGasStationModel.getGasStationLatitude(),mGasStationModel.getGasStationLongitude())));
        Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
        mapIntent.setPackage(Config.GOOGLE_MAP_PACKAGE);
        startActivity(mapIntent);
    }

    @Override
    public void showEditPriceView() {
        Navigator.navigateToEditPriceActivity(getActivity(), mGasStationModel);
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
        return null;
    }
}
