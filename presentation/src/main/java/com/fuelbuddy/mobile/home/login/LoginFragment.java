package com.fuelbuddy.mobile.home.login;

import android.app.Activity;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.widget.AppCompatButton;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.fuelbuddy.mobile.R;
import com.fuelbuddy.mobile.home.BaseFragment;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.GoogleApiClient;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import hugo.weaving.DebugLog;

/**
 * Created by zjuroszek on 14.11.16.
 */

public class LoginFragment extends BaseFragment {

    private GoogleApiClient mGoogleApiClient;
    public interface FragmentNavigator {
        public void navigateToHome();

    }

    @BindView(R.id.login_google_button)
    AppCompatButton mloginGoogleButton;
    @BindView(R.id.login_fb_button)
    AppCompatButton mLoginFbButton;

    @BindView(R.id.rl_progress)
    RelativeLayout rl_progress;

    FragmentNavigator mFragmentNavigator;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View fragmentView = inflater.inflate(R.layout.fragment_login, container, false);
        ButterKnife.bind(this, fragmentView);
        initButtonView();
        return fragmentView;
    }

    private void initButtonView() {
        initFacebookButton();
        initGoogleButton();

    }



    private void initGoogleApi(){
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .requestId()
                .build();
/*
        mGoogleApiClient = new GoogleApiClient.Builder(getActivity())
                .enableAutoManage(this, this)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)

                .build();*/
    }

    private void initGoogleButton() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            mloginGoogleButton.setCompoundDrawablesWithIntrinsicBounds(R.drawable.google_plus_vector, 0, 0, 0);
        } else {
            mloginGoogleButton.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_google_plus_white_36dp, 0, 0, 0);
        }
    }

    private void initFacebookButton() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            mLoginFbButton.setCompoundDrawablesWithIntrinsicBounds(R.drawable.facebook_vector, 0, 0, 0);
        } else {
            mLoginFbButton.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_facebook_white_36dp, 0, 0, 0);
        }
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        if (activity != null) {
            mFragmentNavigator = (FragmentNavigator) activity;
        }
    }

    public void showLoading() {
        this.rl_progress.setVisibility(View.VISIBLE);
        this.getActivity().setProgressBarIndeterminateVisibility(true);
    }

    public void hideLoading() {
        this.rl_progress.setVisibility(View.GONE);
        this.getActivity().setProgressBarIndeterminateVisibility(false);
    }


    @DebugLog
    @OnClick(R.id.login_google_button)
    public void loginGoogleButton() {
        showLoading();
        //mFragmentNavigator.navigateToHome();
        //Navigator.navigateToMapsActivity(HomeActivity.this,FUEL_TYPE_DIESEL);
    }

    @DebugLog
    @OnClick(R.id.login_fb_button)
    public void loginFbButton() {
        hideLoading();
        //mFragmentNavigator.navigateToHome();
        //Navigator.navigateToMapsActivity(HomeActivity.this,FUEL_TYPE_DIESEL);
    }


}
