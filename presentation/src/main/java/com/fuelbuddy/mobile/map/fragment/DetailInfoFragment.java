package com.fuelbuddy.mobile.map.fragment;

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
import android.widget.TextView;

import com.fuelbuddy.mobile.Config;
import com.fuelbuddy.mobile.R;
import com.fuelbuddy.mobile.base.BaseFragment;
import com.fuelbuddy.mobile.model.GasStationModel;
import com.fuelbuddy.mobile.navigation.Navigator;
import com.fuelbuddy.mobile.util.MapUtil;
import com.fuelbuddy.mobile.util.PriceHelper;
import com.fuelbuddy.mobile.util.StringHelper;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by zjuroszek on 25.12.16.
 */

public class DetailInfoFragment extends BaseFragment implements View.OnClickListener {

    private View mBottomSheet;

    private CoordinatorLayout mCoordinator;

    private BottomSheetBehavior mBehavior;

    private int mHeightDetail;

    @BindView(R.id.gasStationName)
    TextView gasStation;
    @BindView(R.id.fuelType92Tv)
    TextView fuelType92Tv;
    @BindView(R.id.fuelType95Tv)
    TextView fuelType95Tv;
    @BindView(R.id.fuelTypeDieselTv)
    TextView fuelTypeDieselTv;
    @BindView(R.id.fab)
    FloatingActionButton fab;

    @BindView(R.id.editfab)
    FloatingActionButton editfab;


    GasStationModel mGasStationModel;


    public static DetailInfoFragment newInstance() {
        return new DetailInfoFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Load heights
        final Resources resources = getResources();
        mHeightDetail = resources
                .getDimensionPixelOffset(R.dimen.map_slideableinfo_height);
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View fragmentView = inflater.inflate(R.layout.map_info_bottom, container, false);
        ButterKnife.bind(this, fragmentView);
        fab.setOnClickListener(this);
        editfab.setOnClickListener(this);
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

    public void showTitleOnly(GasStationModel gasStationModel) {
        mGasStationModel = gasStationModel;
        initPriceDetailViews(mGasStationModel);
        setCollapsedOnly();
    }

    private void initPriceDetailViews(GasStationModel gasStationModel) {
        gasStation.setText(gasStationModel.getGasStationName());
        fuelType92Tv.setText(PriceHelper.generateFuelPrice(Config.FUEL_TYPE_92, gasStationModel.getPrice92()));
        fuelType95Tv.setText(PriceHelper.generateFuelPrice(Config.FUEL_TYPE_95, gasStationModel.getPrice95()));
        fuelTypeDieselTv.setText(PriceHelper.generateFuelPrice(Config.FUEL_TYPE_DIESEL, gasStationModel.getPriceDiesel()));
    }

    private void setCollapsedOnly() {
        // Set up panel: collapsed only with title height and icon
        mBehavior.setPeekHeight(mHeightDetail);
        mBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
        fab.show();
        editfab.show();
    }

    public void hide() {
        mBehavior.setState(BottomSheetBehavior.STATE_HIDDEN);
        fab.setVisibility(View.GONE);
        editfab.setVisibility(View.GONE);
    }

    public boolean isExpanded() {
        return mBehavior.getState() == BottomSheetBehavior.STATE_EXPANDED;
    }

    public void minimize() {
        mBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
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
                    fab.setVisibility(View.VISIBLE);
                    editfab.setVisibility(View.VISIBLE);
                    break;
                case BottomSheetBehavior.STATE_HIDDEN:
                    fab.setVisibility(View.GONE);
                    editfab.setVisibility(View.GONE);
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
            case R.id.fab:
                Uri gmmIntentUri = Uri.parse(StringHelper.getNavigationUrl(MapUtil.getLatLng(mGasStationModel)));
                Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
                mapIntent.setPackage(Config.GOOGLE_MAP_PACKAGE);
                startActivity(mapIntent);
            case R.id.editfab:
                Navigator.navigateToEditPriceActivity(getActivity());
                break;


        }
    }
}
