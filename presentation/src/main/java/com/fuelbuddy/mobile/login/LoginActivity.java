package com.fuelbuddy.mobile.login;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.fuelbuddy.mobile.R;
import com.fuelbuddy.mobile.base.BaseActivity;
import com.fuelbuddy.mobile.di.component.DaggerLoginComponent;
import com.fuelbuddy.mobile.di.component.LoginComponent;
import com.fuelbuddy.mobile.model.UserModel;
import com.fuelbuddy.mobile.navigation.Navigator;
import com.fuelbuddy.mobile.util.DialogFactory;
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


public class LoginActivity extends BaseActivity implements LoginView, GoogleApiClient.OnConnectionFailedListener {


    private GoogleApiClient mGoogleApiClient;
    CallbackManager callbackManager;


    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    public static Intent getCallingIntent(Context context) {
        return new Intent(context, LoginActivity.class);
    }

    @BindView(R.id.login_google_button)
    Button mloginGoogleButton;
    @BindView(R.id.login_fb_button)
    Button mLoginFbButton;

    @BindView(R.id.progressView)
    RelativeLayout rl_progress;


    ProgressDialog progress;
    private static final int RC_SIGN_IN = 007;
    @Inject
    LoginPresenter mLoginPresenter;

    private LoginComponent mLoginComponent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_login);
        initializeInjector();
        ButterKnife.bind(this);
        initButtonView();
        FacebookSdk.sdkInitialize(this);
        mLoginPresenter.attachView(this);
        callbackManager = CallbackManager.Factory.create();
        initGoogleApi();
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

    private void initGoogleApi() {
        GoogleSignInOptions gso = initGoogleSignInOptions();
        initGoogleApiClient(gso);
    }

    private void initGoogleApiClient(GoogleSignInOptions gso) {
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this /* FragmentActivity */, this /* On~ConnectionFailedListener */)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();
    }

    @NonNull
    private GoogleSignInOptions initGoogleSignInOptions() {
        return new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestId()
                .requestEmail()
                .requestProfile()
                .build();
    }

    @Override
    public void navigateToHomeActivity() {

    }

    private void initializeInjector() {
        this.mLoginComponent = DaggerLoginComponent.builder()
                .applicationComponent(getApplicationComponent())
                .activityModule(getActivityModule())
                .build();
        mLoginComponent.inject(this);
    }


    public LoginComponent getLoginComponent() {
        return mLoginComponent;
    }

    @Override
    public void onPause() {
        super.onPause();
        mGoogleApiClient.stopAutoManage(this);
        mGoogleApiClient.disconnect();
    }

    @Override
    public void onStop() {
        super.onStop();
        if (mGoogleApiClient != null && mGoogleApiClient.isConnected()) {
            mGoogleApiClient.stopAutoManage(this);
            mGoogleApiClient.disconnect();
        }
    }

    @Override
    public void showFuelSectionView() {
        Navigator.navigateToHomeActivity(this);
        finish();

    }

    @DebugLog
    @OnClick(R.id.login_google_button)
    public void loginGoogleButton() {
        doGoogleLogin();
    }

    @DebugLog
    @OnClick(R.id.login_fb_button)
    public void loginFbButton() {
        doFacebookLogin();
    }

    private void doGoogleLogin() {
        progress = ProgressDialog.show(this, "", getString(R.string.google_login_text), true);
        Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
        startActivityForResult(signInIntent, RC_SIGN_IN);
        progress.dismiss();
    }

    //{"id":"10154705524014127","name":"Zbigniew Juroszek","email":"zbigniewjuroszek@interia.pl","gender":"male","birthday":"02\/08\/1983"}

    private void doFacebookLogin() {
        progress = ProgressDialog.show(this, "", getString(R.string.fb_login_text), true);
        ArrayList<String> permissions = LoginConfig.getDefaultFacebookPermissions();
        LoginManager.getInstance().logInWithReadPermissions(LoginActivity.this, permissions);
        LoginManager.getInstance().registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                GraphRequest request = GraphRequest.newMeRequest(loginResult.getAccessToken(), new GraphRequest.GraphJSONObjectCallback() {
                    @Override
                    public void onCompleted(JSONObject object, GraphResponse response) {
                        progessDismiss();
                        UserUtil util = new UserUtil();
                        UserModel facebookUser = util.populateFacebookUser(object);
                        if (facebookUser != null) {
                            mLoginPresenter.checkUser(facebookUser);
                            progessDismiss();
                        } else {
                            showError(getString(R.string.login_failed));
                        }
                    }
                });
                Bundle parameters = new Bundle();
                parameters.putString(getString(R.string.facebook_account_key_parameters), getString(R.string.facebook_account_value_parameters));
                request.setParameters(parameters);
                request.executeAsync();
            }

            @Override
            public void onCancel() {
                progessDismiss();
            }

            @Override
            public void onError(FacebookException e) {
                progessDismiss();
            }
        });
    }

    @DebugLog
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            handleGoogleLoginResult(requestCode, result);
        }

    }

    private void handleGoogleLoginResult(int requestCode, GoogleSignInResult result) {
        if (true) {
            // Signed in successfully, show authenticated UI.
            GoogleSignInAccount acct = result.getSignInAccount();
            UserUtil util = new UserUtil();
            UserModel googleUser = util.populateGoogleUser(acct);
            mLoginPresenter.checkUser(googleUser);
            progress.dismiss();

        } else {
            progessDismiss();
            showError(getString(R.string.sign_in_google_failed));
        }
        progessDismiss();
    }

    private void progessDismiss() {
        if (progress != null) {
            progress.dismiss();
        }
    }

    public void showLoading() {
        this.rl_progress.setVisibility(View.VISIBLE);
        setProgressBarIndeterminateVisibility(true);
    }

    public void hideLoading() {
        this.rl_progress.setVisibility(View.GONE);
        setProgressBarIndeterminateVisibility(false);
    }

    @Override
    public Context context() {
        return getApplicationContext();
    }

    @Override
    public void showError(String message) {
        DialogFactory.createSimpleSnackBarInfo(rl_progress, message);
        progessDismiss();
    }

    @Override
    public void logOut() {

    }

}
