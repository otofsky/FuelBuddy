package com.fuelbuddy.mobile.home.login;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatButton;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.fuelbuddy.mobile.R;
import com.fuelbuddy.mobile.base.BaseFragment;

import com.fuelbuddy.mobile.di.HasComponent;
import com.fuelbuddy.mobile.di.component.DaggerHomeComponent;
import com.fuelbuddy.mobile.di.component.HomeComponent;


import com.fuelbuddy.mobile.di.module.LoginModule;
import com.fuelbuddy.mobile.home.HomeActivity;
import com.fuelbuddy.mobile.model.UserModel;
import com.fuelbuddy.mobile.util.loginUtil.LoginConfig;
import com.fuelbuddy.mobile.util.loginUtil.UserUtil;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;

import org.json.JSONObject;

import java.util.ArrayList;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import hugo.weaving.DebugLog;


/**
 * Created by zjuroszek on 14.11.16.
 */

public class LoginFragment extends BaseFragment implements LoginView, GoogleApiClient.OnConnectionFailedListener {

    private GoogleApiClient mGoogleApiClient;
    CallbackManager callbackManager;

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    @Override
    public void showMap() {

    }

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
    ProgressDialog progress;
    private static final int RC_SIGN_IN = 007;
    @Inject
    LoginPresenter mLoginPresenter;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.getComponent(HomeComponent.class).inject(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View fragmentView = inflater.inflate(R.layout.fragment_login, container, false);
        ButterKnife.bind(this, fragmentView);
        initButtonView();
        FacebookSdk.sdkInitialize(getActivity());
        mLoginPresenter.attachView(this);
        callbackManager = CallbackManager.Factory.create();
        return fragmentView;
    }


    private void initButtonView() {
        initFacebookButton();
        initGoogleButton();

    }

    private void initGoogleButton() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            mloginGoogleButton.setCompoundDrawablesWithIntrinsicBounds(R.drawable.google_plus_vector, 0, 0, 0);
        } else {
            mloginGoogleButton.setCompoundDrawablesWithIntrinsicBounds(R.mipmap.ic_google_plus_white_36dp, 0, 0, 0);
        }
    }

    private void initFacebookButton() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            mLoginFbButton.setCompoundDrawablesWithIntrinsicBounds(R.drawable.facebook_vector, 0, 0, 0);
        } else {
            mLoginFbButton.setCompoundDrawablesWithIntrinsicBounds(R.mipmap.ic_facebook_white_36dp, 0, 0, 0);
        }
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        if (activity != null) {
            mFragmentNavigator = (FragmentNavigator) activity;
        }
    }

    @DebugLog
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
        Toast.makeText(getActivity(), " onActivityResult", Toast.LENGTH_SHORT).show();

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            progress = ProgressDialog.show(getActivity(), "", getString(R.string.getting_data), true);
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            handleGoogleLoginResult(requestCode, result);
        }

    }

    private void handleGoogleLoginResult(int requestCode, GoogleSignInResult result) {
        if (result.isSuccess()) {
            // Signed in successfully, show authenticated UI.
            GoogleSignInAccount acct = result.getSignInAccount();
            UserUtil util = new UserUtil();
            UserModel googleUser = util.populateGoogleUser(acct);
            Log.d("LoginFragment", "onActivityResult: " + acct.getDisplayName());
            mLoginPresenter.addNewUser(googleUser);
            finishLogin();
            progress.dismiss();

            //finishLogin(populateGoogleUser);
        } else {
            Log.d("GOOGLE SIGN IN", "" + requestCode);
            // Signed out, show unauthenticated UI.
            // progress.dismiss();
            //Toast.makeText(SmartLoginActivity.this, "Google Login Failed", Toast.LENGTH_SHORT).show();
            //finishLogin(null);

        }
        if (progress != null) {
            progress.dismiss();
        }

    }

    private void finishLogin() {
        mFragmentNavigator.navigateToHome();
        /*if (userModel != null) {
            mFragmentNavigator.navigateToHome();

        } else {
            *//*DialogUtil.getErrorDialog(R.string.login_failed, this);
            finish();*//*
        }*/
    }

    public void showLoading() {
        this.rl_progress.setVisibility(View.VISIBLE);
        this.getActivity().setProgressBarIndeterminateVisibility(true);
    }

    public void hideLoading() {
        this.rl_progress.setVisibility(View.GONE);
        this.getActivity().setProgressBarIndeterminateVisibility(false);
    }

    @Override
    public void showRetry() {

    }

    @Override
    public void hideRetry() {

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


    @DebugLog
    @OnClick(R.id.login_google_button)
    public void loginGoogleButton() {
        doGoogleLogin();
        //startActivityForResult(googleLoginModule.getSignInIntent(), RC_SIGN_IN);

        // showLoading();
        //mFragmentNavigator.navigateToHome();
        //Navigator.navigateToMapsActivity(HomeActivity.this,FUEL_TYPE_DIESEL);
    }

    @DebugLog
    @OnClick(R.id.login_fb_button)
    public void loginFbButton() {
        doFacebookLogin();
        // hideLoading();
        //mFragmentNavigator.navigateToHome();
        //Navigator.navigateToMapsActivity(HomeActivity.this,FUEL_TYPE_DIESEL);
    }

    private void doGoogleLogin() {
        progress = ProgressDialog.show(getActivity(), "", getString(R.string.logging_holder), true);
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestId()
                .requestEmail()
                .requestProfile()
                .build();
        mGoogleApiClient = new GoogleApiClient.Builder(getActivity())
                .enableAutoManage(getActivity() /* FragmentActivity */, this /* On~ConnectionFailedListener */)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();
        Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
        startActivityForResult(signInIntent, RC_SIGN_IN);
        progress.dismiss();
    }

    private void doFacebookLogin() {
        final ProgressDialog progress = ProgressDialog.show(getActivity(), "", getString(R.string.logging_holder), true);
        ArrayList<String> permissions = LoginConfig.getDefaultFacebookPermissions();
        LoginManager.getInstance().logInWithReadPermissions(LoginFragment.this, permissions);
        LoginManager.getInstance().registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                Log.d("Facebook facebook", "onSuccess: " + loginResult.getAccessToken());
                progress.setMessage(getString(R.string.getting_data));
                GraphRequest request = GraphRequest.newMeRequest(loginResult.getAccessToken(), new GraphRequest.GraphJSONObjectCallback() {
                    @Override
                    public void onCompleted(JSONObject object, GraphResponse response) {
                        finishLogin();
                        progress.dismiss();
                        UserUtil util = new UserUtil();
                        UserModel facebookUser = util.populateFacebookUser(object);
                        if (facebookUser != null) {
                            // finishLogin(facebookUser);
                        } else {
                            //finish();
                        }
                    }
                });
                request.executeAsync();
            }

            @Override
            public void onCancel() {
                progress.dismiss();
               /* progress.dismiss();
                finish();*/
                Log.d("Facebook Login", "User cancelled the login process");
            }

            @Override
            public void onError(FacebookException e) {
                Log.d("Facebook Login", "onError");
                progress.dismiss();
              /*
                finish();*/
                // Toast.makeText(SmartLoginActivity.this, R.string.network_error, Toast.LENGTH_SHORT).show();
            }
        });

    }


    @Override
    public void onStop() {
        //googleLoginModule.close();
        super.onStop();
    }
}
