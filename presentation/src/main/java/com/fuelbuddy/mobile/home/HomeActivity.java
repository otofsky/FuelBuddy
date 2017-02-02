package com.fuelbuddy.mobile.home;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.fuelbuddy.mobile.R;
import com.fuelbuddy.mobile.base.BaseActivity;
import com.fuelbuddy.mobile.di.HasComponent;
import com.fuelbuddy.mobile.di.component.DaggerHomeComponent;

import com.fuelbuddy.mobile.di.component.HomeComponent;

import com.fuelbuddy.mobile.home.fuelSelection.FuelSelectionFragment;
import com.fuelbuddy.mobile.home.login.LoginFragment;
import com.fuelbuddy.mobile.navigation.Navigator;
import com.fuelbuddy.mobile.util.DialogFactory;
import com.google.android.gms.common.api.GoogleApiClient;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by zjuroszek on 07.10.16.
 */
public class HomeActivity extends BaseActivity implements HomeView, LoginFragment.FragmentNavigator, HasComponent<HomeComponent> {

    private GoogleApiClient mGoogleApiClient;
    private static final int RC_SIGN_IN = 007;
    @Inject
    public  HomePresenter homePresenter;
    private HomeComponent homeComponent;

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    public static Intent getCallingIntent(Context context) {
        return new Intent(context, HomeActivity.class);
    }

    protected void onCreate(Bundle savedInstanceState) {
        Log.d("home", "onCreate: ");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        this.initializeInjector();
        initPresenter();
        ButterKnife.bind(this);
        setToolbar();
    }

    private void initPresenter() {
        Log.d("home", "onCreate: initPresenter");
        homePresenter.attachView(this);
        homePresenter.verifyCurrentUser();
    }

    private void setToolbar() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

    }

    @Override
    public void onStart() {
        super.onStart();

    }

    @Override
    public void onStop() {
        super.onStop();
    }

    @Override
    public void navigateToHomeActivity() {

    }

    private void initializeInjector() {
        this.homeComponent = DaggerHomeComponent.builder()
                .applicationComponent(getApplicationComponent())
                .activityModule(getActivityModule())
                .build();
        homeComponent.inject(this);
    }

    @Override
    public void navigateToHome() {
        Log.d("home ", "navigateToHome: FuelSelectionFragment");
        addFragment(R.id.fragmentContainer, new FuelSelectionFragment());
        toolbar.showOverflowMenu();
    }

    @Override
    public void showMap() {

    }

    @Override
    public void showInfo(String userId) {
        Toast.makeText(this,  userId , Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showLoginView() {
        Log.d("home", "showLoginView: ");
        Navigator.navigateToLoginActivity(this);
        finish();
    }

    @Override
    public void showFuelTypeView() {
        Log.d("home", "showFuelTypeView: FuelSelectionFragment");
        addFragment(R.id.fragmentContainer, new FuelSelectionFragment());
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

    @Override
    public HomeComponent getComponent() {
        return homeComponent;
    }
}
