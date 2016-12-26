package com.fuelbuddy.mobile.map.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.CoordinatorLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.fuelbuddy.mobile.R;
import com.fuelbuddy.mobile.base.BaseFragment;

/**
 * Created by zjuroszek on 25.12.16.
 */

public class DetailInfoFragment extends BaseFragment {

    private View mBottomSheet;

    private CoordinatorLayout mCoordinator;

    private BottomSheetBehavior mBehavior;


    public static DetailInfoFragment newInstance() {
        return new DetailInfoFragment();
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View fragmentView = inflater.inflate(R.layout.map_info_bottom, container, false);
        return fragmentView;
    }

    @Override
    public void onViewCreated(final View view, final Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mCoordinator = (CoordinatorLayout) view.findViewById(R.id.map_coordinator);
        mBottomSheet = mCoordinator.findViewById(R.id.map_bottomsheet);


        mBehavior = BottomSheetBehavior.from(mBottomSheet);
        mBehavior.setBottomSheetCallback(mBottomSheetCallback);
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

                    break;
                case BottomSheetBehavior.STATE_HIDDEN:
                  /*  mCallback.onInfoSizeChanged(mBottomSheet.getLeft(), mBottomSheet.getTop(),
                            mBottomSheet.getRight(), mCoordinator.getHeight());*/
                    break;
            }
        }

        @Override
        public void onSlide(@NonNull View bottomSheet, float slideOffset) {

        }


    };
}
