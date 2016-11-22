package com.fuelbuddy.mobile;

import android.content.Context;

import com.fuelbuddy.interactor.GetGasStationList;
import com.fuelbuddy.mobile.map.MapMvpView;
import com.fuelbuddy.mobile.map.MapPresenter;
import com.fuelbuddy.mobile.mapper.UserModelDataMapper;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import rx.Subscriber;

import static org.junit.Assert.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.verify;

/**
 * To work on unit tests, switch the Test Artifact in the Build Variants view.
 */
public class MapPresenterUnitTest {

    private MapPresenter mMapPresenter;

    @Mock
    private Context mockContext;
    @Mock
    private MapMvpView mMapMvpView;
    @Mock
    private GetGasStationList mGetGasStationList;
    @Mock
    private UserModelDataMapper mockUserModelDataMapper;

    @Before
    public void setUpPresenter() throws Exception {
        MockitoAnnotations.initMocks(this);
        mMapPresenter = new MapPresenter(mGetGasStationList);
        mMapPresenter.attachView(mMapMvpView);

    }

    @Test
    public void testGasStationListPresenterInitialize() throws Exception {
        //given(mMapMvpView.context()).willReturn(mockContext);
        mMapPresenter.submitSearch();
        verify(mGetGasStationList).execute(any(Subscriber.class));
    }
}