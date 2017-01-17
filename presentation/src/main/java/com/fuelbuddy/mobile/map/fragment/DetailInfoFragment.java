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
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.fuelbuddy.mobile.Config;
import com.fuelbuddy.mobile.R;
import com.fuelbuddy.mobile.base.BaseFragment;
import com.fuelbuddy.mobile.di.component.MapsComponent;
import com.fuelbuddy.mobile.map.presenter.DetailInfoPresenter;
import com.fuelbuddy.mobile.map.view.DetailInfoView;
import com.fuelbuddy.mobile.model.GasStationModel;
import com.fuelbuddy.mobile.navigation.Navigator;
import com.fuelbuddy.mobile.util.MapUtil;
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


    public static DetailInfoFragment newInstance() {
        return new DetailInfoFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.getComponent(MapsComponent.class).inject(this);
        mPresenter.attachView(this);
        final Resources resources = getResources();
        mHeightDetail = resources
                .getDimensionPixelOffset(R.dimen.map_slideableinfo_height);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View fragmentView = inflater.inflate(R.layout.map_info_bottom, container, false);
        mUnbinder = ButterKnife.bind(this, fragmentView);
        navigateBtn.setOnClickListener(this);
        updateBtn.setOnClickListener(this);
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

    @Override public void onDestroyView() {
        super.onDestroyView();


        mUnbinder.unbind();
    }

    public void showTitleOnly(GasStationModel gasStationModel) {
        mGasStationModel = gasStationModel;
        initPriceDetailViews(mGasStationModel);
        setCollapsedOnly();
    }

    private void initPriceDetailViews(GasStationModel gasStationModel) {
        gasStationName.setText(gasStationModel.getCompanyName());
        stationAddress.setText(gasStationModel.getGasStationName());
        info.setText(R.string.map_direction_btn_text);

    }

    private void setCollapsedOnly() {
        // Set up panel: collapsed only with title height and icon
        mBehavior.setPeekHeight(mHeightDetail);
        mBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
        navigateBtn.show();
    }

    public void hide() {
        mBehavior.setState(BottomSheetBehavior.STATE_HIDDEN);
        navigateBtn.setVisibility(View.GONE);
    }

    private BottomSheetBehavior.BottomSheetCallback mBottomSheetCallback
            = new BottomSheetBehavior.BottomSheetCallback() {

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
                  /*  mCallback.onInfoSizeChanged(mBottomSheet.getLeft(), mBottomSheet.getTop(),
                            mBottomSheet.getRight(), mCoordinator.getHeight());*/
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
        Uri gmmIntentUri = Uri.parse(StringHelper.getNavigationUrl(MapUtil.getLatLng(mGasStationModel)));
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
