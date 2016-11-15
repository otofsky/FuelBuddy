package com.fuelbuddy.mobile.home.fragment;

import android.app.Activity;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.widget.AppCompatButton;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.fuelbuddy.mobile.R;
import com.fuelbuddy.mobile.home.HomeActivity;
import com.fuelbuddy.mobile.navigation.Navigator;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import hugo.weaving.DebugLog;

/**
 * Created by zjuroszek on 14.11.16.
 */

public class LoginFragment extends BaseFragment {


    public interface FragmentNavigator{
        public void navigateToHome ();

    }

    @BindView(R.id.login_google_button)
    AppCompatButton mloginGoogleButton;
    @BindView(R.id.login_fb_button)
    AppCompatButton mLoginFbButton;

    FragmentNavigator mFragmentNavigator;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View fragmentView = inflater.inflate(R.layout.fragment_login, container, false);
        ButterKnife.bind(this, fragmentView);
        initButtonView();
        return fragmentView;
    }

    private void initButtonView (){
        initFacebookButton();
        initGoogleButton();

    }

    private void initGoogleButton() {
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP){
            mloginGoogleButton.setCompoundDrawablesWithIntrinsicBounds(R.drawable.google_plus_vector, 0, 0, 0);
        } else {
            mloginGoogleButton.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_google_plus_white_36dp, 0, 0, 0);
        }
    }

    private void initFacebookButton() {
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP){
            mLoginFbButton.setCompoundDrawablesWithIntrinsicBounds(R.drawable.facebook_vector, 0, 0, 0);
        } else {
            mLoginFbButton.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_facebook_white_36dp, 0, 0, 0);
        }
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        if(activity!=null){
            mFragmentNavigator = (FragmentNavigator) activity;
        }
    }

    @DebugLog
    @OnClick(R.id.login_google_button)
    public void submitFuelTypeDiesel() {
        mFragmentNavigator.navigateToHome();
         //Navigator.navigateToMapsActivity(HomeActivity.this,FUEL_TYPE_DIESEL);
    }
}
