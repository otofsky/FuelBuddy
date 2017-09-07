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
import com.fuelbuddy.mobile.navigation.Navigator;
import com.google.android.gms.common.api.GoogleApiClient;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import hugo.weaving.DebugLog;

/**
 * Created by zjuroszek on 07.10.16.
 */
public class HomeActivity extends BaseActivity implements HomeView, HasComponent<HomeComponent> {

    @Inject
    public HomePresenter homePresenter;
    private HomeComponent homeComponent;

    @BindView(R.id.toolbar)
    Toolbar toolbar;


    public static Intent getCallingIntent(Context context) {
        return new Intent(context, HomeActivity.class);
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        this.initializeInjector();
        initPresenter();
        ButterKnife.bind(this);
        setToolbar();
    }

    private void initPresenter() {
        homePresenter.attachView(this);
        homePresenter.verifyCurrentUser();
    }

    private void setToolbar() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @DebugLog
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.actionLogOut:
                homePresenter.logout();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
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
    public void showMap() {

    }

    @Override
    public void showInfo(String userId) {
        Toast.makeText(this, userId, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showLoginView() {
        Navigator.navigateToLoginActivity(this);
        finish();
    }

    @Override
    public void showFuelTypeView() {
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
        Navigator.navigateToLoginActivity(this);
        finish();
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
